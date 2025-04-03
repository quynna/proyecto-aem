package com.jadeveloper.core.servlets;

import com.jadeveloper.core.services.ExternalApiService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.osgi.framework.Constants.SERVICE_DESCRIPTION;
import static org.osgi.framework.Constants.SERVICE_VENDOR;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/searchcards",
                SERVICE_DESCRIPTION + "=Servlet para obtener tarjetas desde RAWG",
                SERVICE_VENDOR + "=JA Developer",
                "sling.servlet.methods=GET"
        }
)
public class SearchCardsServlet extends SlingAllMethodsServlet {

    private static final Logger log = LoggerFactory.getLogger(SearchCardsServlet.class);

    @Reference
    private ExternalApiService externalApiService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONArray cards = new JSONArray();

        try {
            // Obtener el endpoint configurado en el servicio
            String endpoint = externalApiService.getEndpointUrl();
            log.info("Consultando datos externos desde el endpoint: {}", endpoint);

            // Abrir conexión a la API externa
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(externalApiService.getTimeout());
            conn.setReadTimeout(externalApiService.getTimeout());

            int status = conn.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                String errorMsg = "Error al conectar con la API externa. Código de estado: " + status;
                log.error(errorMsg);
                throw new RuntimeException(errorMsg);
            }

            // Leer el contenido de la respuesta usando try-with-resources
            StringBuilder responseContent = new StringBuilder();
            try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8.name())) {
                while (scanner.hasNextLine()) {
                    responseContent.append(scanner.nextLine());
                }
            }
            conn.disconnect();

            // Procesar la respuesta JSON
            JSONObject rawgResponse = new JSONObject(responseContent.toString());
            JSONArray results = rawgResponse.optJSONArray("results");

            if (results != null) {
                for (int i = 0; i < results.length(); i++) {
                    JSONObject game = results.getJSONObject(i);
                    JSONObject card = new JSONObject();
                    card.put("title", game.optString("name", "Sin título"));
                    card.put("description", "Fecha: " + game.optString("released", "N/A")
                            + ", Rating: " + game.optDouble("rating", 0.0));
                    card.put("image", game.optString("background_image", ""));

                    JSONArray tags = new JSONArray();
                    if (game.has("genres")) {
                        for (int j = 0; j < game.getJSONArray("genres").length(); j++) {
                            JSONObject genre = game.getJSONArray("genres").getJSONObject(j);
                            tags.put(genre.optString("name", "Sin categoría"));
                        }
                    }
                    card.put("tags", tags);
                    cards.put(card);
                }
            }
        } catch (Exception e) {
            log.error("Error en SearchCardsServlet: {}", e.getMessage(), e);
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JSONObject error = new JSONObject();
            error.put("error", "No se pudo obtener datos del servicio externo");
            error.put("detail", e.getMessage());
            try (PrintWriter writer = response.getWriter()) {
                writer.write(error.toString(2));
            }
            return;
        }

        try (PrintWriter out = response.getWriter()) {
            out.write(cards.toString(2));
        }
    }
}

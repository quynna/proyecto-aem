package com.jadeveloper.core.services.impl;

import com.jadeveloper.core.services.ExternalApiService;
import org.apache.commons.io.IOUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component(
    service = ExternalApiService.class, 
    configurationPolicy = org.osgi.service.component.annotations.ConfigurationPolicy.REQUIRE
)
@Designate(ocd = ExternalApiServiceImpl.Config.class)
public class ExternalApiServiceImpl implements ExternalApiService {

    private static final Logger log = LoggerFactory.getLogger(ExternalApiServiceImpl.class);

    @ObjectClassDefinition(name = "JADeveloper - External API Service", description = "Consume un servicio externo")
    public @interface Config {
        @AttributeDefinition(name = "API Endpoint URL", description = "URL del servicio externo")
        String endpointUrl();

        @AttributeDefinition(name = "Timeout (ms)", description = "Timeout de conexión en milisegundos")
        int timeout() default 5000;
    }

    private String endpointUrl;
    private int timeout;

    @Activate
    @Modified
    protected void activate(Config config) {
        this.endpointUrl = config.endpointUrl();
        this.timeout = config.timeout();
        log.info("ExternalApiServiceImpl activado con endpointUrl={} y timeout={}", endpointUrl, timeout);
    }

    @Override
    public String fetchData() {
        // Validar que el endpoint esté configurado
        if (endpointUrl == null || endpointUrl.trim().isEmpty()) {
            log.error("Endpoint URL no configurado");
            return "{\"error\":\"Endpoint URL no configurado\"}";
        }

        HttpURLConnection conn = null;
        try {
            URL url = new URL(endpointUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.setRequestMethod("GET");

            int status = conn.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                log.error("Error al llamar a la API. Código de estado: {}", status);
                return "{\"error\":\"Error al llamar a la API: " + status + "\"}";
            }

            // Uso de try-with-resources para asegurar el cierre del InputStream
            try (InputStream is = conn.getInputStream()) {
                return IOUtils.toString(is, StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            log.error("Excepción al llamar a la API: {}", e.getMessage(), e);
            return "{\"error\":\"No se pudo obtener datos del servicio externo: " + e.getMessage() + "\"}";
        } finally {
            // Aseguramos que la conexión se desconecte para liberar recursos
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    @Override
    public String getEndpointUrl() {
        return this.endpointUrl;
    }

    @Override
    public int getTimeout() {
        return this.timeout;
    }
}

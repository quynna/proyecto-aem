package com.jadeveloper.core.models;

import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model para el componente Dynamic Search Cards.
 * Este modelo expone las propiedades configuradas en el diálogo del componente:
 * <ul>
 *   <li><strong>title:</strong> Título principal del componente.</li>
 *   <li><strong>searchBasePath:</strong> Ruta base para realizar la búsqueda de recursos.</li>
 *   <li><strong>externalApiEndpoint:</strong> URL del servicio externo a consumir.</li>
 * </ul>
 * Se utilizan valores por defecto para garantizar que el componente tenga información básica
 * en caso de que no se configure alguna propiedad.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DynamicSearchCardsModel {

    @ValueMapValue
    @Default(values = "Dynamic Search Cards")
    private String title;

    @ValueMapValue
    @Default(values = "/content/proyecteve")
    private String searchBasePath;

    @ValueMapValue
    @Default(values = "https://api.example.com/data")
    private String externalApiEndpoint;

    /**
     * Retorna el título del componente. Si no se ha configurado, se retorna un valor por defecto.
     * 
     * @return título principal del componente.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retorna la ruta base configurada para la búsqueda.
     * 
     * @return ruta base de búsqueda.
     */
    public String getSearchBasePath() {
        return searchBasePath;
    }

    /**
     * Retorna el endpoint configurado para el servicio externo.
     * 
     * @return URL del servicio externo.
     */
    public String getExternalApiEndpoint() {
        return externalApiEndpoint;
    }
}

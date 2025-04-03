package com.jadeveloper.core.services;

/**
 * Interfaz para el servicio que consume APIs externas.
 * Provee métodos para obtener la URL del endpoint configurado, el timeout de conexión,
 * y para obtener datos desde el servicio externo.
 */
public interface ExternalApiService {

    /**
     * Retorna la URL del endpoint externo configurado.
     *
     * @return la URL del servicio externo.
     */
    String getEndpointUrl();

    /**
     * Retorna el tiempo de espera (timeout) configurado para la conexión con el servicio externo.
     *
     * @return el timeout en milisegundos.
     */
    int getTimeout();

    /**
     * Realiza una solicitud al endpoint externo configurado y retorna los datos obtenidos.
     * Si ocurre algún error, se debe retornar un JSON con un mensaje de error adecuado.
     *
     * @return los datos en formato JSON o un mensaje de error en formato JSON.
     */
    String fetchData();
}

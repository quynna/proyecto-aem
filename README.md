Proyecto Proyecteve – Componente Advanced Dynamic Search Cards para AEM
Este proyecto demuestra una solución avanzada para Adobe Experience Manager (AEM) que integra datos dinámicos, permite filtrado basado en tags y consume un servicio externo. El objetivo es mostrar buenas prácticas en el desarrollo de componentes AEM, utilizando Sling Models, HTL, JavaScript moderno y servicios OSGi.

Tabla de Contenidos
Características

Arquitectura del Proyecto

Componentes Principales

Requisitos Previos

Instalación y Compilación

Despliegue en AEM

Configuración

Pruebas y Depuración

Licencia

Características
Componente dinámico: dynamic-search-cards que muestra una lista de tarjetas filtrables.

Tarjetas con información: Cada tarjeta muestra título, descripción, imagen destacada y categorías (tags).

Búsqueda y filtrado: Campo de búsqueda y filtros por categoría con resultados en tiempo real.

Integración con API externa: Consumo de un servicio externo (configurable) para complementar los datos.

Backend avanzado: Servicio OSGi configurable y servlet que expone los datos en formato JSON.

Buenas prácticas: Uso de Sling Models, HTL limpio, JavaScript moderno (ES6+), accesibilidad y performance.

Arquitectura del Proyecto
El proyecto está estructurado como un proyecto AEM multi-módulo basado en el arquetipo oficial, que incluye los siguientes módulos:

proyecteve.core:
Contiene la lógica Java y los servicios OSGi. Aquí se implementa el servicio que consume el endpoint externo (ExternalApiService) y se registran los Sling Models.

proyecteve.ui.apps:
Incluye los componentes de AEM (por ejemplo, el componente dynamic-search-cards), los diálogos de configuración, y los clientLibs (JavaScript y CSS) necesarios para la interfaz de usuario.

proyecteve.ui.content:
Se utiliza para empaquetar contenido y configuraciones que se desplegarán en AEM, como plantillas y diálogos.

proyecteve.all:
Módulo agregador que integra todos los subpaquetes (core, ui.apps, ui.content) en un único paquete instalable en AEM.

La comunicación entre módulos se realiza a través de Sling Models y servicios OSGi. El componente utiliza un diálogo configurable para definir parámetros como el título y el endpoint del servicio externo. El servlet expone los datos procesados como JSON, permitiendo que el JavaScript en el frontend actualice dinámicamente la vista sin recargar la página.

Componentes Principales
Dynamic Search Cards Component (HTL):
Ubicado en /apps/proyecteve/components/dynamic-search-cards/, este componente muestra las tarjetas dinámicas, el campo de búsqueda y los filtros por categoría.

Sling Model (DynamicSearchCardsModel.java):
Encapsula la lógica de negocio y lee la configuración del componente (título, ruta de búsqueda y endpoint).

Servicio OSGi (ExternalApiServiceImpl.java):
Consume un endpoint externo configurable, gestionando timeouts y errores de conexión.

Servlet (DynamicSearchDataServlet.java):
Expone los datos obtenidos del servicio externo en formato JSON a la ruta /bin/searchcards.

ClientLibs (JS y CSS):
Contienen la lógica de filtrado y búsqueda en tiempo real y los estilos del componente.

Requisitos Previos
Java: JDK 11 (recomendado para AEM).

Maven: Versión 3.6 o superior.

Adobe Experience Manager: Versión AEM 6.5 o AEM as a Cloud Service.

Git: Para clonar el proyecto (opcional).

Instalación y Compilación
Clonar el Proyecto:

git clone https://github.com/tuusuario/proyecteve.git
Navegar a la Raíz del Proyecto:

cd proyecteve
Compilar el Proyecto:

Ejecuta el siguiente comando para compilar todos los módulos y generar los paquetes correspondientes:

mvn clean install -U
Este comando compilará el módulo core, empaquetará los componentes y generará el paquete final en el módulo proyecteve.all.

Despliegue en AEM
Desplegar el Paquete:

Una vez compilado, el paquete final se encuentra en proyecteve.all/target/. Sube el paquete (.zip) a AEM mediante la consola CRX/DE o utilizando el Package Manager.

Verificar la Instalación:

Asegúrate de que el servicio OSGi se active correctamente en la consola de OSGi (http://localhost:4502/system/console/components).

Verifica que el servlet esté disponible en /bin/searchcards.

Configuración
Diálogo del Componente:

El diálogo (_cq_dialog.xml) permite configurar:

Título principal del componente.

Endpoint del servicio externo.

Configuración OSGi:

En AEM, crea un archivo de configuración en /apps/proyecteve/config (por ejemplo, com.jadeveloper.core.services.ExternalApiService.config) con el siguiente contenido:

properties

endpointUrl=https://api.example.com/data
timeout=5000
Pruebas y Depuración
Pruebas Unitarias:

Ejecuta las pruebas unitarias con:

mvn test
Logs y Debug:

Revisa los logs de AEM (error.log y stdout.log) para identificar problemas con la activación de servicios o la ejecución del servlet.

Modo Debug en Maven:

Si tienes problemas durante la compilación, utiliza:

mvn clean install -X
Esto mostrará información detallada para ayudar a diagnosticar cualquier error.

Licencia
Este proyecto se distribuye bajo [Inserta la licencia aquí, por ejemplo, MIT License].

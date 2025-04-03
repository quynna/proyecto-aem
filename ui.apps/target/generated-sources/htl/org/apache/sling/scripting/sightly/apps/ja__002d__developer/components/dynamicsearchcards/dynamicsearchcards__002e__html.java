/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ******************************************************************************/
package org.apache.sling.scripting.sightly.apps.ja__002d__developer.components.dynamicsearchcards;

import java.io.PrintWriter;
import java.util.Collection;
import javax.script.Bindings;

import org.apache.sling.scripting.sightly.render.RenderUnit;
import org.apache.sling.scripting.sightly.render.RenderContext;

public final class dynamicsearchcards__002e__html extends RenderUnit {

    @Override
    protected final void render(PrintWriter out,
                                Bindings bindings,
                                Bindings arguments,
                                RenderContext renderContext) {
// Main Template Body -----------------------------------------------------------------------------

Object _global_model = null;
Object _global_clientlib = null;
out.write("<!-- Uso de Sling Models y ClientLibs -->\r\n");
_global_model = renderContext.call("use", com.jadeveloper.core.models.DynamicSearchCardsModel.class.getName(), obj());
_global_clientlib = renderContext.call("use", "/libs/granite/sightly/templates/clientlib.html", obj());
out.write("\r\n\r\n<!-- Incluir los estilos del componente -->\r\n");
{
    Object var_templatevar0 = renderContext.getObjectModel().resolveProperty(_global_clientlib, "css");
    {
        String var_templateoptions1_field$_categories = "ja-developer.dynamicsearchcards";
        {
            java.util.Map var_templateoptions1 = obj().with("categories", var_templateoptions1_field$_categories);
            callUnit(out, renderContext, var_templatevar0, var_templateoptions1);
        }
    }
}
out.write("\r\n\r\n<!-- Componente Dynamic Search Cards -->\r\n<div class=\"cmp-dynamic-search-cards\"");
{
    Object var_attrvalue2 = renderContext.call("xss", renderContext.getObjectModel().resolveProperty(_global_model, "externalApiEndpoint"), "unsafe");
    {
        boolean var_shoulddisplayattr5 = ((!"".equals(var_attrvalue2)) && (!((Object)false).equals(var_attrvalue2)));
        if (var_shoulddisplayattr5) {
            out.write(" data-endpoint");
            {
                boolean var_istrueattr4 = (var_attrvalue2.equals(true));
                if (!var_istrueattr4) {
                    out.write("=\"");
                    out.write(renderContext.getObjectModel().toString(var_attrvalue2));
                    out.write("\"");
                }
            }
        }
    }
}
out.write(" role=\"region\" aria-label=\"B\u00FAsqueda din\u00E1mica de tarjetas\">\r\n\r\n    <!-- T\u00EDtulo configurado -->\r\n    <h2>");
{
    Object var_textcontent6 = renderContext.call("xss", renderContext.getObjectModel().resolveProperty(_global_model, "title"), "text");
    out.write(renderContext.getObjectModel().toString(var_textcontent6));
}
out.write("</h2>\r\n\r\n    <!-- Campo de b\u00FAsqueda con etiqueta visualmente oculta para accesibilidad -->\r\n    <div class=\"cmp-search-input\">\r\n        <label for=\"search-input\" class=\"visually-hidden\">Buscar tarjetas</label>\r\n        <input type=\"text\" id=\"search-input\" placeholder=\"Buscar tarjetas...\" aria-label=\"Buscar contenido\"/>\r\n    </div>\r\n\r\n    <!-- Contenedor de filtros por categor\u00EDa -->\r\n    <nav id=\"tag-filters\" class=\"cmp-tag-filters\" role=\"navigation\" aria-label=\"Filtrar por categor\u00EDa\">\r\n        <!-- Los filtros se generar\u00E1n din\u00E1micamente -->\r\n    </nav>\r\n\r\n    <!-- Loader para indicar que se est\u00E1n cargando las tarjetas -->\r\n    <div id=\"cards-loading\" class=\"cmp-cards-loading\" aria-hidden=\"true\">\r\n        <div class=\"spinner\"></div>\r\n        <span>Cargando tarjetas...</span>\r\n    </div>\r\n\r\n    <!-- Contenedor donde se renderizar\u00E1n las tarjetas -->\r\n    <div id=\"cards-container\" class=\"cmp-cards-container\" role=\"list\">\r\n        <!-- El contenido se inyectar\u00E1 din\u00E1micamente mediante JavaScript -->\r\n    </div>\r\n</div>\r\n\r\n<!-- Incluir los scripts del componente -->\r\n");
{
    Object var_templatevar7 = renderContext.getObjectModel().resolveProperty(_global_clientlib, "js");
    {
        String var_templateoptions8_field$_categories = "ja-developer.dynamicsearchcards";
        {
            java.util.Map var_templateoptions8 = obj().with("categories", var_templateoptions8_field$_categories);
            callUnit(out, renderContext, var_templatevar7, var_templateoptions8);
        }
    }
}
out.write("\r\n");


// End Of Main Template Body ----------------------------------------------------------------------
    }



    {
//Sub-Templates Initialization --------------------------------------------------------------------



//End of Sub-Templates Initialization -------------------------------------------------------------
    }

}


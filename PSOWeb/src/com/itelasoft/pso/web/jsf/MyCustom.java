package com.itelasoft.pso.web.jsf;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

@FacesComponent(value = "mycustom")
    public class MyCustom extends UIComponentBase {
    
	 
        @Override
        public String getFamily() {
            return "custom";
        }
    
        @Override
       public void encodeEnd(FacesContext context) throws IOException {
   
           ResponseWriter responseWriter = context.getResponseWriter();
           responseWriter.startElement("div", null);
           responseWriter.writeAttribute("id",getClientId(context),"id");
           responseWriter.writeAttribute("name", getClientId(context),"clientId");
           responseWriter.write("Howdy!");
           responseWriter.endElement("div");
       }
   }
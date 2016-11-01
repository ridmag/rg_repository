package com.itelasoft.pso.web.jsf;

import java.io.IOException;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 *
 * @author Uy Jerwin
 */
public class UIFrame extends UIOutput {
 
	
    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
 
        writer.startElement("iframe", null);
        writer.writeAttribute("src", this.getAttributes().get("src"), "src");
        writer.writeAttribute("style", this.getAttributes().get("style"), "style");
        writer.writeAttribute("height", this.getAttributes().get("height"), "height");
        writer.writeAttribute("width", this.getAttributes().get("width"), "width");
    }
 
    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
 
        writer.endElement("iframe");
    }
 
    @Override
    public void decode(FacesContext context) {
    // do nothing
    }
}
 
/**
 * 
 */
package com.itelasoft.pso.web.jsf;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import org.w3c.dom.Element;

import com.icesoft.faces.component.outputresource.OutputResource;
import com.icesoft.faces.component.outputresource.OutputResourceRenderer;
import com.icesoft.faces.context.DOMContext;
import com.icesoft.faces.renderkit.dom_html_basic.HTML;
import com.icesoft.faces.renderkit.dom_html_basic.PassThruAttributeRenderer;

/**
 * @author vajira
 * 
 */

public class OutputIFrameRenderer extends OutputResourceRenderer {
	
	
	
	/* (non-Javadoc)
	 * @see com.icesoft.faces.component.outputresource.OutputResourceRenderer#encodeBegin(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent)
			throws IOException {
	

	String clientId = uiComponent.getClientId(facesContext);
	OutputResource outputResource = (OutputResource) uiComponent;
	if (outputResource.getResource() != null) {
		DOMContext domContext = DOMContext.attachDOMContext(facesContext,
				uiComponent);
		if (!domContext.isInitialized()) {
			domContext.createRootElement(HTML.DIV_ELEM);
		}

		Element root = (Element) domContext.getRootNode();
		root.setAttribute(HTML.ID_ATTR, uiComponent
				.getClientId(facesContext)
				+ CONTAINER_DIV_SUFFIX);
		domContext.setCursorParent(root);
		
		String style = outputResource.getStyle();
		String styleClass = outputResource.getStyleClass();
		if(outputResource instanceof ItelaOutputResource){
			root.setAttribute(HTML.STYLE_ATTR, (String) outputResource.getAttributes().get("style"));
			
		}
		Element resource = null;

		if (OutputResource.TYPE_BUTTON.equals(outputResource.getType())) {
			resource = domContext.createElement(HTML.INPUT_ELEM);
			resource.setAttribute(HTML.TYPE_ATTR, "button");
			resource.setAttribute(HTML.VALUE_ATTR, outputResource
					.getLabel());
			resource.setAttribute(HTML.ONCLICK_ATTR, "window.open('"
					+ outputResource.getPath() + "');");
		} if ("iframe".equals(outputResource.getType())) {
			/*resource = domContext.createElement(HTML.IFRAME_ELEM);
			resource.setAttribute(HTML.TYPE_ATTR, "button");
			resource.setAttribute(HTML.VALUE_ATTR, outputResource
					.getLabel());
			resource.setAttribute(HTML.SRC_ATTR, "http://localhost:8080/PSOWeb/pdfrenderer.jsp");
			resource.setAttribute(HTML.HEIGHT_ATTR, "100%");
			resource.setAttribute(HTML.WIDTH_ATTR, "100%");*/
			Element div = domContext.createElement("div");
			div.setAttribute("id", "mydiv");
			
			/*String str = "var success = new PDFObject({" +
					"url : '"+outputResource.getPath().replaceAll("/PSOWeb/", "")+"'" +
				"}).embed('mydiv');";*/
			/*String str = "var success = new PDFObject({" +
			"url : 'http://localhost:8080"+outputResource.getPath()+"'" +
		"}).embed('mydiv');alert('hello')";*/
			
			String str ="<object data='"+outputResource.getPath()+"#toolbar=1&amp;navpanes=0&amp;scrollbar=1&amp;page=1&amp;view=FitH' type='application/pdf' width='100%' height='100%'><p>It appears you don't have a PDF plugin for this browser. No biggie... you can <a href='"+outputResource.getPath()+"#toolbar=1&amp;navpanes=0&amp;scrollbar=1&amp;page=1&amp;view=FitH'>click here to download the PDF file.</a></p></object>";
			/*String str = "var success = new PDFObject({" +
			"url : 'http://localhost:8080/PSOWeb/confidentiality__agreement_example.pdf"+"'" +
		"}).embed('mydiv');";*/
			div.setTextContent(str);
			div.setAttribute(HTML.STYLE_ATTR, "width:100%;height:100%");
			root.appendChild(div);
			resource = domContext.createElement(HTML.SCRIPT_ELEM);
			resource.setAttribute(HTML.SCRIPT_TYPE_ATTR, "text/javascript");
			//resource.setTextContent(str);
		
		} else {
			resource = domContext.createElement(HTML.ANCHOR_ELEM);
			resource.setAttribute(HTML.HREF_ATTR, outputResource.getPath());
			PassThruAttributeRenderer.renderNonBooleanHtmlAttributes(
					uiComponent, resource, new String[] { "target" });

			if (outputResource.getImage() != null) {
				Element img = domContext.createElement(HTML.IMG_ELEM);
				String image = outputResource.getImage();
				if (image != null) {
					img.setAttribute(HTML.SRC_ATTR, facesContext
							.getApplication().getViewHandler()
							.getResourceURL(facesContext, image));
				}
				resource.appendChild(img);
				img.setAttribute(HTML.ALT_ATTR, outputResource.getLabel());
			} else {
				resource.appendChild(domContext
						.createTextNode(outputResource.getLabel()));
			}

		}
		resource.setAttribute(HTML.ID_ATTR, clientId);
		root.appendChild(resource);
		if (style != null) {
			resource.setAttribute(HTML.STYLE_ATTR, style);
		}
		if (styleClass != null) {
			resource.setAttribute("class", styleClass);
		}

		domContext.stepOver();
	}


	}
}

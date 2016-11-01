/**
 * 
 */
package com.itelasoft.pso.web.jsf;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import com.icesoft.faces.component.outputresource.OutputResource;

/**
 * @author vajira
 *
 */
@FacesComponent(value="itelaoutput")
public class ItelaOutputResource extends OutputResource {
	
	
	@Override
	public String getFamily() {
		// TODO Auto-generated method stub
		return "itelaoutput";
	}
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponentBase#getRenderer(javax.faces.context.FacesContext)
	 */
	@Override
	protected Renderer getRenderer(FacesContext arg0) {
		// TODO Auto-generated method stub
		//Renderer  renderer= super.getRenderer(arg0);
		return new OutputIFrameRenderer();
	}
	/* (non-Javadoc)
	 * @see com.icesoft.faces.component.outputresource.OutputResource#getRendererType()
	 */
	@Override
	public String getRendererType() {
		return "com.itelasoft.pso.web.jsf.OutputIFrameRenderer";
	}
	
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponentBase#encodeBegin(javax.faces.context.FacesContext)
	 */
	@Override
	public void encodeBegin(FacesContext arg0) throws IOException {
		// TODO Auto-generated method stub
		super.encodeBegin(arg0);
	}
}

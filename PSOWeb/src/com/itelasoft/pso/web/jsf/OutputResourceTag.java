/**
 * 
 */
package com.itelasoft.pso.web.jsf;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

/**
 * @author vajira
 *
 */
public class OutputResourceTag extends
		com.icesoft.faces.component.outputresource.OutputResourceTag {
	private ValueExpression width = null;
    /**
	 * 
	 * @hibernate.property column = "width"
	 */
	public ValueExpression getWidth() {
		return width;
	}


	/**
	 * @param width the width to set
	 */
	public void setWidth(ValueExpression width) {
		this.width = width;
	}


	/**
	 * 
	 * @hibernate.property column = "height"
	 */
	public ValueExpression getHeight() {
		return height;
	}


	/**
	 * @param height the height to set
	 */
	public void setHeight(ValueExpression height) {
		this.height = height;
	}
	private ValueExpression height = null;
    
	/* (non-Javadoc)
	 * @see com.icesoft.faces.component.outputresource.OutputResourceTag#getComponentType()
	 */
	@Override
	public String getComponentType() {
		// TODO Auto-generated method stub
		return "com.itelasoft.pso.web.jsf.ItelaOutputResource";
	}
	
	
	/* (non-Javadoc)
	 * @see com.icesoft.faces.component.outputresource.OutputResourceTag#setProperties(javax.faces.component.UIComponent)
	 */
	@Override
	protected void setProperties(UIComponent component) {
		// TODO Auto-generated method stub
		super.setProperties(component);
		 component.setValueExpression("height", this.height);
	     component.setValueExpression("width", this.width);
	}
	/* (non-Javadoc)
	 * @see com.icesoft.faces.component.outputresource.OutputResourceTag#getRendererType()
	 */
	@Override
	public String getRendererType() {
		// TODO Auto-generated method stub
		return "com.itelasoft.pso.web.jsf.OutputIFrameRenderer";
	}
}

package com.itelasoft.pso.web.jsf;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentELTag;

/**
 *
 * @author Uy Jerwin
 */
public class FrameTag extends UIComponentELTag {
 
    private ValueExpression src = null;
    private ValueExpression style = null;
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

	private ValueExpression width = null;
    private ValueExpression height = null;
 
    @Override
    public String getRendererType() {
        return null;
    }
 
    @Override
    public String getComponentType() {
        return "com.itelasoft.web.jsf.Frame";
    }
 
    @Override
    public void setProperties(UIComponent component) {
        // always call the superclass method
        super.setProperties(component);
 
        component.setValueExpression("src", this.src);
        component.setValueExpression("style", this.style);
        component.setValueExpression("height", this.height);
        component.setValueExpression("width", this.width);
    }
 
    @Override
    public void release() {
        // always call the superclass method
        super.release();
 
        this.src = null;
        this.style = null;
    }
 
    public ValueExpression getStyle() {
        return style;
    }
 
    public void setStyle(ValueExpression style) {
        this.style = style;
    }
 
    public ValueExpression getSrc() {
        return src;
    }
 
    public void setSrc(ValueExpression src) {
        this.src = src;
    }
}
 

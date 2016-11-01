package com.itelasoft.pso.web;

import java.util.List;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;

import com.icesoft.faces.context.effects.Effect;
import com.icesoft.faces.context.effects.Highlight;

import com.itelasoft.pso.services.IServiceLocator;
import com.itelasoft.pso.services.ServiceLocator;
import com.sun.faces.component.visit.FullVisitContext;

public class UIModel {

	@ManagedProperty(value = "#{sessionContext}")
	protected SessionContext sessionContext;
	protected IServiceLocator serviceLocator = ServiceLocator
			.getServiceLocator();
	// effect that shows a value binding chance on there server
	protected Effect valueChangeEffect;

	public UIModel() {
		valueChangeEffect = new Highlight("#fda505");
		valueChangeEffect.setFired(true);
	}

	// Property Accessors

	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
		onLoadModel();
		initModel();
	}

	public Effect getValueChangeEffect() {
		return valueChangeEffect;
	}

	public void setValueChangeEffect(Effect valueChangeEffect) {
		this.valueChangeEffect = valueChangeEffect;
	}

	// Use this to call onSubmit() each time a page is reloaded (postback)
	// Call this method by binding in to a facelet which is placed very top in
	// the page.
	public boolean getModelReload() {
		initModel();
		onSubmit();
		return false;
	}

	// Service Methods

	// Initialization does not happen in the constructor and it's moved to
	// the session context injection to access session variables.
	// (container sets the SessionContext managed bean only after creating this
	// bean.
	// So, initModel should not be called in the constructor of any Model bean)
	// TODO: THIS METHOD IS DEPRECATED!!
	@Deprecated
	public void initModel() {

	}

	// This method gets called when the model bean is loaded for the first time.
	// Loading is performed by the container and it happens only once in the
	// life cycle.
	// Any model specific, one time initializations may happen here (in the
	// overridden implementation)
	protected void onLoadModel() {

	}

	// This method gets called each time when a page submit (or a partial
	// submit) takes place.
	// Any re-initialization needed for the model at post-backs, may implement
	// here (in the
	// overridden implementation
	protected void onSubmit() {

	}

	public TimeZone getTimeZone() {
		return java.util.TimeZone.getDefault();
	}

	protected void showInfo(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
	}

	protected void showError(String message, String... detail) {
		FacesContext.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, message,
								detail != null && detail.length > 0 ? detail[0]
										: null));
	}

	protected void showExceptionAsError(Exception exception) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, exception
						.getLocalizedMessage(), null));
	}

	protected void showExceptionAsWarning(Exception exception) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, exception
						.getLocalizedMessage(), null));
	}

	protected void showExceptionAsInfo(Exception exception) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, exception
						.getLocalizedMessage(), null));
	}

	public void highlightInputs(final List<String> componentIds) {
		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot root = context.getViewRoot();
		root.visitTree(new FullVisitContext(context), new VisitCallback() {
			@Override
			public VisitResult visit(VisitContext context, UIComponent component) {
				if (componentIds.contains(component.getId())) {
					component.getAttributes().put(
							"styleClass",
							addErrStylClass((String) component.getAttributes()
									.get("styleClass")));
					// return VisitResult.COMPLETE;
				}
				return VisitResult.ACCEPT;
			}
		});
	}

	public void clearInputs() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		root.visitTree(new FullVisitContext(facesContext), new VisitCallback() {
			@Override
			public VisitResult visit(VisitContext context, UIComponent component) {
				if (component.getId().contains("input_")
						|| component.getId().contains("select_")) {
					component.getAttributes().put(
							"styleClass",
							removeErrStylClass((String) component
									.getAttributes().get("styleClass")));
				}
				return VisitResult.ACCEPT;
			}
		});
	}

	private String addErrStylClass(String styleClass) {
		styleClass = removeSystmStylClass(styleClass);
		styleClass += " error-highlight";
		return styleClass.trim();
	}

	private String removeErrStylClass(String styleClass) {
		styleClass = removeSystmStylClass(styleClass);
		if (styleClass.contains("error-highlight"))
			styleClass = styleClass.replace("error-highlight", "");
		return styleClass.trim();
	}

	private String removeSystmStylClass(String styleClass) {
		if (styleClass.contains("iceInpTxt"))
			styleClass = styleClass.replace("iceInpTxt", "");
		else if (styleClass.contains("iceSelOneMnu"))
			styleClass = styleClass.replace("iceSelOneMnu", "");
		return styleClass.trim();
	}
	
	public static boolean isUserInRole(String role) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.isUserInRole(role);
	}
}
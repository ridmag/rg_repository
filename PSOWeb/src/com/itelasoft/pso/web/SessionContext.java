package com.itelasoft.pso.web;

import java.io.IOException;
import java.security.Principal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;

import com.icesoft.faces.component.ext.HtmlCommandButton;
import com.icesoft.faces.context.effects.Highlight;
import com.itelasoft.pso.beans.User;
import com.itelasoft.pso.services.IServiceLocator;
import com.itelasoft.pso.services.ServiceLocator;

@ManagedBean(name = "sessionContext")
@SessionScoped
public class SessionContext {
	private User user;
	private String currentUserName = null;
	private String activeStringDetails;
	private String iceFacesCSS = "./xmlhttp/css/rime/rime.css";
	private Highlight effectDetails = new Highlight("#ffcc99");
	//private HtmlCommandButton rejectConfirmation;

	public void setActiveString(String string) {
		activeStringDetails = string;
		effectDetails.setFired(false);
	}

	// Properties

	public void loadSystemConfiguration() {
		IServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
		this.setIceFacesCSS(serviceLocator.getConfigurationService()
				.retrieve("THEME").getContents());
	}

	// Information Services

	public String getCurrentUser() {
		if (user == null) {
			populateUser();
			if(user!=null)
			currentUserName = user.getContact().getFirstName();
		}
		return currentUserName;
	}

	private void populateUser() {
		Principal principal = ((HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest())
				.getUserPrincipal();
		if (principal != null) {
			String username = principal.getName();
			user = ServiceLocator.getServiceLocator().getUserService()
					.getUserByUserName(username);
			if(user == null)
				try {
					((HttpServletResponse)FacesContext
							.getCurrentInstance().getExternalContext().getResponse()).sendError(401);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public String logout() {
		SecurityContextHolder.clearContext();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession httpSession = (HttpSession) context.getExternalContext()
				.getSession(false);
		httpSession.invalidate();
		try {
			((HttpServletResponse)FacesContext
					.getCurrentInstance().getExternalContext().getResponse()).sendError(401);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ProgramManager";
	}

	public User getUser() {
		if (user == null) {
			populateUser();
		}
		return user;
	}

	public void setIceFacesCSS(String iceFacesCSS) {
		this.iceFacesCSS = iceFacesCSS;
	}

	public String getIceFacesCSS() {
		return iceFacesCSS;
	}

	public String getActiveStringDetails() {
		return activeStringDetails;
	}

	public Highlight getEffectDetails() {
		return effectDetails;
	}

	/*public void setRejectConfirmation(HtmlCommandButton rejectConfirmation) {
		this.rejectConfirmation = rejectConfirmation;
	}

	public HtmlCommandButton getRejectConfirmation() {
		return rejectConfirmation;
	}*/

}

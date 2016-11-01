package com.itelasoft.pso.web.jsf;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class SessionPhaseListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7346981515993792074L;
	private static final String homepage = "/";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
	 */
	public void beforePhase(PhaseEvent event) {
		System.out.println("paselistener fired");
		FacesContext facesCtx = event.getFacesContext();
		ExternalContext extCtx = facesCtx.getExternalContext();
		HttpSession session = (HttpSession) extCtx.getSession(false);
		boolean newSession = (session == null) || (session.isNew());
		boolean postback = !extCtx.getRequestParameterMap().isEmpty();
		boolean timedout = postback && newSession;
		
		System.out.println("paselistener fired:"+newSession+postback+timedout);
		if (timedout) {
			
			Application app = facesCtx.getApplication();
			ViewHandler viewHandler = app.getViewHandler();
			UIViewRoot view = viewHandler.createView(facesCtx,
					"/");
			facesCtx.setViewRoot(view);
			facesCtx.renderResponse();
			try {
				viewHandler.renderView(facesCtx, view);
				facesCtx.responseComplete();
			} catch (Throwable t) {
				throw new FacesException("Session timed out", t);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.event.PhaseListener#getPhaseId()
	 */
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
	 */
	public void afterPhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
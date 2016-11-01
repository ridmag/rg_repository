package com.itelasoft.pso.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import com.itelasoft.pso.services.ServiceLocator;

@SessionScoped
@ManagedBean
public class AdminModel {
	
	public void buildTSIndex(ActionEvent actionEvent){
		ServiceLocator.getServiceLocator().getTextSearchService().buildIndex();
		Util.showInfo("Indexes generation completed");
	}

}

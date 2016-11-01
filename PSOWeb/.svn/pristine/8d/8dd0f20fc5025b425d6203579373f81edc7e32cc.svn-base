package com.itelasoft.pso.web.jsf;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class hr_mi_se_Converter implements Converter {
      public hr_mi_se_Converter() {
}

public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String param){
	//System.out.println("startTime:"+param);
	if(param == null || param.equals(""))
		return null;
	param =param.replace("_", "");
	//this.startTimeHour = startTimeHour;
	SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
	try {
		
		Date time = dateFormat.parse(param);
		
//		System.out.println("startTime conv:"+time);
		return new Time(time.getTime());
	} catch (ParseException e) {
		
		throw new ConverterException(e);
	}
}

public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object obj) {
       try {
    	   SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    	  if(obj==null)
    		  return null;
    	   Date time = (Date)obj;
    	   String strTime=dateFormat.format(time);
    	   
    	   return strTime;
          //           return str_hours + ":" + str_minutes + ":" + str_seconds;
     }  
     catch (Exception exception) {
           throw new ConverterException(exception);
          
      }
   }
}
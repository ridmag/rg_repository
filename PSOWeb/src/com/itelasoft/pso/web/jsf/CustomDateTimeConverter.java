package com.itelasoft.pso.web.jsf;

import java.util.TimeZone;

import javax.faces.convert.DateTimeConverter;



public class CustomDateTimeConverter extends DateTimeConverter {

public CustomDateTimeConverter() {
super();
setTimeZone(TimeZone.getDefault());
// here you can set your custom date pattern for your project
// setPattern("M/d/yy");
}

}
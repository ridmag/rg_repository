function fireScheduleTimeClicked(obj, e, formId, scheduleId) {
	//make sure it works on IE too
	if (!e) var e = window.event;
	//capture the mouse coordinates relative to the foreground layer
	var y = e.layerY;
	var el = e.target;
	//make sure it works in IE too
	if (!y) y = e.offsetY;
	if (!el) el = e.srcElement;
	if (el != obj) return false;
	var dateInputId = scheduleId + "_last_clicked_date";
	var yInputId = scheduleId + "_last_clicked_y";
	document.forms[formId][dateInputId].value = el.id;
	document.forms[formId][yInputId].value = y;
	document.forms[formId].submit();
	return true;
}

function fireScheduleDateClicked(obj, e, formId, scheduleId) {
	//make sure it works on IE too
	if (!e) var e = window.event;
	var el = e.target;
	//make sure it works in IE too
	if (!el) el = e.srcElement;
	if (el != obj) return false;
	var dateInputId = scheduleId + "_last_clicked_date";
	document.forms[formId][dateInputId].value = el.id;
	document.forms[formId].submit();
	return true;
}

function fireEntrySelected(formId, scheduleId, entryId) {
	document.forms[formId][scheduleId].value = entryId;
	//document.forms[formId].submit();
	return true;
}

function fireVisitSelected(obj, e, formId, scheduleId,entryId,type) {
	document.forms[formId][scheduleId].value = entryId;
	if (!e) var e = window.event;
	var el= e.target;
	if (!el) el = e.srcElement;
	var eleName;
	document.forms[formId][scheduleId].value = entryId;
	if(type==0){
		eleName = scheduleId+"_apptId"	
		document.forms[formId][eleName].value = entryId;
	}else if(type==1){
		eleName = scheduleId+"_edit"	
		document.forms[formId][eleName].value = entryId;
	}else if(type==2){
		eleName = scheduleId+"_goto"	
		document.forms[formId][eleName].value = entryId;
	}
	document.forms[formId].submit();
	return true;
}
var lastHeight;
function onEntryMouseOver(obj, e) {
	if (!e) var e = window.event;
	var el= e.target;
	if (!el) el = e.srcElement;
	if (el != obj) return false;
	var current_mouse_target = null;
	if( e.toElement ) {				
		current_mouse_target 			 = e.toElement;
	} else if( e.relatedTarget ) {				
		current_mouse_target 			 = e.relatedTarget;
	}
	if(!is_child_of(obj,current_mouse_target) ){
		
		lastHeight =jQuery(el).css("height");
		jQuery(el).css("height","100px");
		jQuery(el).css("z-index","99999");
	}
	return true;
}
function is_child_of(parent, child) {
	var i=0;
	if( child != null ) {			
		while( child.parentNode ) {
			if( (child = child.parentNode) == parent ) {
				if(i++ > 4)
					break;
				return true;
			}
		}
	}
	return false;
}
function onEntryMouseOut(obj, e) {
	if (!e) var e = window.event;
	var el= e.target;
	if (!el) el = e.srcElement;
	if (el != obj) return false;
	
	var current_mouse_target = null;
	if( e.toElement ) {				
		current_mouse_target 			 = e.toElement;
	} else if( e.relatedTarget ) {				
		current_mouse_target 			 = e.relatedTarget;
	}
	
	if(lastHeight &&  !is_child_of(obj,current_mouse_target) )
		jQuery(el).css("height",lastHeight);
	return true;
}

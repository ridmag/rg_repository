package com.itelasoft.util;

import java.util.Comparator;

import com.itelasoft.pso.beans.AuthorisedStaff;
import com.itelasoft.pso.beans.Contact;
import com.itelasoft.pso.beans.GroupedStaff;
import com.itelasoft.pso.beans.GroupedStudent;
import com.itelasoft.pso.beans.GroupedStudentReturn;
import com.itelasoft.pso.beans.Guardian;
import com.itelasoft.pso.beans.HoursUtilizedReportItem;
import com.itelasoft.pso.beans.Invoice;
import com.itelasoft.pso.beans.ProgramType;
import com.itelasoft.pso.beans.StaffCheckRecord;
import com.itelasoft.pso.beans.StaffEvent;
import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.StudentEvent;
import com.itelasoft.pso.beans.StudentGroup;

public class SortObjectByName implements Comparator<Object> {

	@Override
	public int compare(Object obj1, Object obj2) {
		if (obj1 instanceof ProgramType && obj2 instanceof ProgramType){
			return ((ProgramType) obj1).getName().compareTo(((ProgramType) obj2).getName());
		}else if(obj1 instanceof StaffEvent && obj2 instanceof StaffEvent){
			Contact contact1 = ((StaffEvent)obj1).getStaffMember().getContact();
			String name1 = (contact1.getFirstName()+contact1.getLastName()).toLowerCase();
			
			Contact contact2 = ((StaffEvent)obj2).getStaffMember().getContact();
			String name2 = (contact2.getFirstName()+contact2.getLastName()).toLowerCase();
			
			return (name1.compareTo(name2));
		}else if(obj1 instanceof StudentEvent && obj2 instanceof StudentEvent){
			Contact contact1 = ((StudentEvent)obj1).getGroupedStudent().getStudent().getContact();
			String name1 = (contact1.getFirstName()+contact1.getLastName()).toLowerCase();
			
			Contact contact2 = ((StudentEvent)obj2).getGroupedStudent().getStudent().getContact();
			String name2 = (contact2.getFirstName()+contact2.getLastName()).toLowerCase();
			
			return (name1.compareTo(name2));
		}else if(obj1 instanceof GroupedStudent && obj2 instanceof GroupedStudent){
			Contact contact1 = ((GroupedStudent)obj1).getStudent().getContact();
			String name1 = (contact1.getFirstName()+contact1.getLastName()).toLowerCase();
			
			Contact contact2 = ((GroupedStudent)obj2).getStudent().getContact();
			String name2 = (contact2.getFirstName()+contact2.getLastName()).toLowerCase();
			
			return (name1.compareTo(name2));
		}else if(obj1 instanceof GroupedStaff && obj2 instanceof GroupedStaff){
			Contact contact1 = ((GroupedStaff)obj1).getStaffMember().getContact();
			String name1 = (contact1.getFirstName()+contact1.getLastName()).toLowerCase();
			
			Contact contact2 = ((GroupedStaff)obj2).getStaffMember().getContact();
			String name2 = (contact2.getFirstName()+contact2.getLastName()).toLowerCase();
			
			return (name1.compareTo(name2));
		}else if(obj1 instanceof GroupedStudentReturn && obj2 instanceof GroupedStudentReturn){
			Contact contact1 = ((GroupedStudentReturn)obj1).getMainStd().getStudent().getContact();
			String name1 = (contact1.getFirstName()+contact1.getLastName()).toLowerCase();
			
			Contact contact2 = ((GroupedStudentReturn)obj2).getMainStd().getStudent().getContact();
			String name2 = (contact2.getFirstName()+contact2.getLastName()).toLowerCase();
			
			return (name1.compareTo(name2));
		}else if(obj1 instanceof Student && obj2 instanceof Student){
			Contact contact1 = ((Student)obj1).getContact();
			String name1 = (contact1.getFirstName()+contact1.getLastName()).toLowerCase();
			
			Contact contact2 = ((Student)obj2).getContact();
			String name2 = (contact2.getFirstName()+contact2.getLastName()).toLowerCase();
			
			return (name1.compareTo(name2));
		}else if(obj1 instanceof Guardian && obj2 instanceof Guardian){
			Contact contact1 = ((Guardian)obj1).getContact();
			String name1 = (contact1.getFirstName()+contact1.getLastName()).toLowerCase();
			
			Contact contact2 = ((Guardian)obj2).getContact();
			String name2 = (contact2.getFirstName()+contact2.getLastName()).toLowerCase();
			
			return (name1.compareTo(name2));
		}else if(obj1 instanceof StaffMember && obj2 instanceof StaffMember){
			Contact contact1 = ((StaffMember)obj1).getContact();
			String name1 = (contact1.getFirstName()+contact1.getLastName()).toLowerCase();
			
			Contact contact2 = ((StaffMember)obj2).getContact();
			String name2 = (contact2.getFirstName()+contact2.getLastName()).toLowerCase();
			
			return (name1.compareTo(name2));
		}else if(obj1 instanceof Invoice && obj2 instanceof Invoice){
			Contact contact1 = ((Invoice)obj1).getStudent().getContact();
			String name1 = (contact1.getFirstName()+contact1.getLastName()).toLowerCase();
			
			Contact contact2 = ((Invoice)obj2).getStudent().getContact();
			String name2 = (contact2.getFirstName()+contact2.getLastName()).toLowerCase();
			
			return (name1.compareTo(name2));
		}else if(obj1 instanceof HoursUtilizedReportItem && obj2 instanceof HoursUtilizedReportItem){
			Contact contact1 = ((HoursUtilizedReportItem)obj1).getStudent().getContact();
			String name1 = (contact1.getFirstName()+contact1.getLastName()).toLowerCase();
			
			Contact contact2 = ((HoursUtilizedReportItem)obj2).getStudent().getContact();
			String name2 = (contact2.getFirstName()+contact2.getLastName()).toLowerCase();
			
			return (name1.compareTo(name2));
		}else if(obj1 instanceof StaffCheckRecord && obj2 instanceof StaffCheckRecord){
			Contact contact1 = ((StaffCheckRecord)obj1).getStaffMember().getContact();
			String name1 = (contact1.getFirstName()+contact1.getLastName()).toLowerCase();
			
			Contact contact2 = ((StaffCheckRecord)obj2).getStaffMember().getContact();
			String name2 = (contact2.getFirstName()+contact2.getLastName()).toLowerCase();
			
			return (name1.compareTo(name2));
		}else if(obj1 instanceof AuthorisedStaff && obj2 instanceof AuthorisedStaff){
			Contact contact1 = ((AuthorisedStaff)obj1).getStaffMember().getContact();
			String name1 = (contact1.getFirstName()+contact1.getLastName()).toLowerCase();
			
			Contact contact2 = ((AuthorisedStaff)obj2).getStaffMember().getContact();
			String name2 = (contact2.getFirstName()+contact2.getLastName()).toLowerCase();
			
			return (name1.compareTo(name2));
		}
		return 0;
	}

}

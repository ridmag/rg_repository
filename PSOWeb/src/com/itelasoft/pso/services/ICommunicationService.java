package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.Communication;
import com.itelasoft.pso.beans.CommunicationCat;
import com.itelasoft.pso.beans.CommunicationNote;
import com.itelasoft.pso.beans.ServiceArea;

public interface ICommunicationService extends
		IGenericService<Communication, Long> {

	public List<Communication> listByCategory(Long catId);

	public List<Communication> listBySubCategories(List<Long> subCatIds);

	public List<Communication> listByStudents(Long categoryId);

	public List<Communication> listByStaffType_Status_Category(
			Long staffTypeId, String staffStatus, CommunicationCat category);

	public List<CommunicationNote> listNotesByCommunication(Long communicationId);

	public List<Communication> listByCategoryNServiceArea(
			CommunicationCat category, ServiceArea area);

	public List<Communication> listByVehicleType(String vehicleType);

}
/*
 * <bean id="communicationService"
 * class="com.itelasoft.vet.services.CommunicationService"> <property name="dao"
 * ref="communicationDao" /> </bean> public ICommunicationService
 * getCommunicationService(){ return
 * (ICommunicationService)context.getBean("communicationService"); }
 */
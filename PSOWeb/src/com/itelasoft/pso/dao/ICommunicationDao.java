package com.itelasoft.pso.dao;

import java.util.List;

import com.itelasoft.pso.beans.Communication;
import com.itelasoft.pso.beans.CommunicationCat;
import com.itelasoft.pso.beans.CommunicationNote;
import com.itelasoft.pso.beans.ServiceArea;

/**
 * Dao class for Communication.
 */
public interface ICommunicationDao extends IGenericDao<Communication, Long> {

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
 * <bean id="communicationDao" class="com.itelasoft.vet.dao.CommunicationDao">
 * <property name="sessionFactory" ref="mySessionFactory" /> </bean>
 */
package com.itelasoft.pso.services;

import org.apache.velocity.app.VelocityEngine;

public interface IServiceLocator {
	public IUserService getUserService();

	public IConfigurationService getConfigurationService();

	public ILicenseService getLicenseService();

	public IContactService getContactService();

	public ILocationService getLocationService();

	public IProgramEventService getProgramEventService();

	public VelocityEngine getVelocityEngine();

	public IFileDataService getFileDataService();

	public IStaffMemberService getStaffMemberService();

	public IProgramService getProgramService();

	public IProgramTypeService getProgramTypeService();

	public IStudentService getStudentService();

	public IStudentEventService getStudentEventService();

	public ISpecialNeedService getSpecialNeedService();

	public ITransactionService getTransactionService();

	public IStaffTypeService getStaffTypeService();

	public IInvoiceService getInvoiceService();

	public IFundingSourceService getFundingSourceService();

	public IVehicleService getVehicleService();

	public IWeekDayService getWeekDayService();

	public ICalendarService getCalendarService();

	public IHolidayService getHolidayService();

	public IExternalOrganizationService getExternalOrganizationService();

	public INdisSupportItemService getNdisSupportItemService();

	public ICommunicationService getCommunicationService();

	public ICommunicationCatService getCommunicationCatService();

	public IServiceAreaService getServiceAreaService();

	public IStudentGroupService getStudentGroupService();

	public IGroupedStudentService getGroupedStudentService();

	public IReportService getReportService();

	public IReferenceItemService getReferenceItemService();

	public ILeavePolicyService getLeavePolicyService();

	public ILeaveService getLeaveService();

	public IStaffSkillService getStaffSkillService();

	public IReminderService getReminderService();

	public IOutletService getOutletService();

	public ICheckRecordService getCheckRecordService();

	public IStaffCheckRecordService getStaffCheckRecordService();

	public IConsentService getConsentService();

	public IStudentConsentService getStudentConsentService();

	public IHoursUtilizedReportService getUtilizedReportService();

	public IStaffRoasterService getStaffRoasterService();

	public IBusinessRulesService getBusinessRulesService();

	public ICollectionService getCollectionService();

	public IStaffEventService getStaffEventService();

	public ILeaveCategoryService getLeaveCategoryService();

	public IStudentFundingSourceService getStudentFundingSourceService();

	public IGuardianService getGuardianService();

	public IGroupedStaffService getGroupedStaffService();

	public ITextSearchService getTextSearchService();

	public IInternalOrganizationService getInternalOrganizationService();

	public IAuthorisedStaffService getAuthorisedStaffService();

	public IDashboardService getDashboardService();

	public INdisInvoiceItemService getNdisAncillaryService();

	public INdisInvoiceItemService getNdisInvoiceItemService();

	public INdisPriceService getNdisPriceService();

	public INdisCommittedEventService getNdisCommittedEventService();

	public INdisAncillaryCostService getNdisAncillaryCostService();

	public INdisContributionService getNdisContributionService();

	public IGroupedStaffWeekdayService getGroupedStaffWeekdayService();
	
	public INdisInvoiceService getNdisInvoiceService();
}
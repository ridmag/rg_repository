package com.itelasoft.pso.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.springframework.dao.DataIntegrityViolationException;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.itelasoft.pso.beans.Communication;
import com.itelasoft.pso.beans.CommunicationCat;
import com.itelasoft.pso.beans.CommunicationNote;
import com.itelasoft.pso.beans.EnumReminderType;
import com.itelasoft.pso.beans.ExternalOrganization;
import com.itelasoft.pso.beans.FundingSource;
import com.itelasoft.pso.beans.NodeCommunicationCat;
import com.itelasoft.pso.beans.Reminder;
import com.itelasoft.pso.beans.ServiceArea;
import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.beans.StaffType;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.Vehicle;

@ManagedBean(name = "communicationManagerModel")
@SessionScoped
public class CommunicationManagerModel extends UIModel {

	private DefaultTreeModel treeModel;
	private List<Communication> communications, allCommunications;
	// private List<SelectItem> keyPersonSelectItems;
	private Communication communication;
	private Communication tmpCommunication;
	private CommunicationCat category;
	private TimeBean eventTime;
	private DefaultMutableTreeNode branchNode;
	private List<SelectItem> selectKeyPersonList;
	private boolean visibleCommunication;
	private boolean visibleReminder, withReminders;
	private int photoW, photoH, wizardPage;
	private Student student;
	private StaffMember staffMember;
	private ExternalOrganization organization;
	private FundingSource source;
	private Vehicle vehicle;
	private String searchText;
	private List<StaffMember> staffList;
	private List<Student> stuList;
	private List<Vehicle> vehicles;
	private List<ExternalOrganization> orgList;
	private List<FundingSource> fundingList;
	private String searchValue, commSearchTxt, timePeriod;
	private static final String XP_BRANCH_CONTRACTED_ICON = "./xmlhttp/css/xp/css-images/tree_folder_close.gif";
	private static final String XP_BRANCH_EXPANDED_ICON = "./xmlhttp/css/xp/css-images/tree_folder_open.gif";
	private static final String XP_BRANCH_LEAF_ICON = "./xmlhttp/css/xp/css-images/tree_document.gif";
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private Date today, toDate;

	public void init() {
		branchNode = null;
		today = new Date();
		toDate = new Date();
		visibleCommunication = visibleReminder = withReminders = false;
		communications = new ArrayList<Communication>();
		stuList = new ArrayList<Student>();
		vehicles = new ArrayList<Vehicle>();
		staffList = new ArrayList<StaffMember>();
		orgList = new ArrayList<ExternalOrganization>();
		fundingList = new ArrayList<FundingSource>();
		searchText = "";
		searchValue = "name";
		student = null;
		vehicle = null;
		source = null;
		organization = null;
		staffMember = null;
		// create root node with its children expanded
		DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();
		NodeCommunicationCat rootObject = new NodeCommunicationCat(rootTreeNode);
		rootObject.setText("All Categories");
		rootTreeNode.setUserObject(setIcons(rootObject));
		//rootObject.setExpanded(true);
		// model is accessed by by the ice:tree component
		treeModel = new DefaultTreeModel(rootTreeNode);
		// add some child notes
		List<CommunicationCat> rootCategories = serviceLocator
				.getCommunicationCatService().getRootCategories();
		for (CommunicationCat cat : rootCategories) {
			DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode();
			NodeCommunicationCat branchObject = new NodeCommunicationCat(
					branchNode);
			branchObject.setText(cat.getName());
			branchObject.setCategory(cat);
			branchNode.setUserObject(setIcons(branchObject));
			createChildren(branchNode);
			rootTreeNode.add(branchNode);
		}
	}
	
	public void filterCommunications(){
		communications.clear();
		communications.addAll(allCommunications);
		if(!withReminders && (commSearchTxt==null || commSearchTxt.trim().isEmpty())){
			return;
		}
		List<Communication> removables = new ArrayList<Communication>();
		for(Communication comm : communications){
			if(withReminders){
				if(comm.getReminder() == null || comm.getReminder().getRemindOn().after(toDate) || comm.getReminder().getRemindOn().before(getStartDate())){
					removables.add(comm);
					continue;
				}				
			}
			if(!commSearchTxt.trim().isEmpty()){
				if(comm.getKeyPersonFunding() != null && !comm.getKeyPersonFunding().getFundingType().toLowerCase().contains(commSearchTxt.toLowerCase())){
					removables.add(comm);
					continue;
				}
				if(comm.getKeyPersonOrg() != null && !comm.getKeyPersonOrg().getName().toLowerCase().contains(commSearchTxt.toLowerCase())){
					removables.add(comm);
					continue;
				}
				if(comm.getKeyPersonVehicle() != null && 
						!comm.getKeyPersonVehicle().getName().toLowerCase().contains(commSearchTxt.toLowerCase())
								&& !comm.getKeyPersonVehicle().getRegistrationId().toLowerCase().contains(commSearchTxt.toLowerCase())){
					removables.add(comm);
					continue;
				}
				if(comm.getKeyPersonStaff() != null && 
						!comm.getKeyPersonStaff().getContact().getName().toLowerCase().contains(commSearchTxt.toLowerCase())){
					removables.add(comm);
					continue;
				}
				if(comm.getKeyPersonStudent() != null && 
						!comm.getKeyPersonStudent().getContact().getName().toLowerCase().contains(commSearchTxt.toLowerCase())){
					removables.add(comm);
					continue;
				}		
			}
		}
		if(!removables.isEmpty())
			communications.removeAll(removables);
		if(communications.isEmpty())
			showInfo("No communications found..");
	}

	private void createChildren(DefaultMutableTreeNode branchNode) {
		NodeCommunicationCat branchObject = (NodeCommunicationCat) branchNode
				.getUserObject();
		if (branchObject.getText().equals("Staff Member")) {
			for (SelectItem status : ((LookupListProvider) Util
					.getManagedBean("lookupListProvider")).getStaffStatus()) {
				DefaultMutableTreeNode subNode = new DefaultMutableTreeNode();
				NodeCommunicationCat subObject = new NodeCommunicationCat(
						subNode);
				subObject.setText(status.getLabel());
				subObject.setStaffStatus(status.getValue().toString());
				subNode.setUserObject(setIcons(subObject));
				createChildren(subNode);
				branchNode.add(subNode);
			}
		} else if (branchObject.getStaffStatus() != null
				&& !branchObject.getStaffStatus().isEmpty()
				&& branchObject.getStaffType() == null) {
			List<StaffType> staffTypes = serviceLocator.getStaffTypeService()
					.findAll();
			if (staffTypes != null & !staffTypes.isEmpty()) {
				for (StaffType type : staffTypes) {
					DefaultMutableTreeNode subNode = new DefaultMutableTreeNode();
					NodeCommunicationCat subObject = new NodeCommunicationCat(
							subNode);
					subObject.setText(type.getName());
					subObject.setStaffType(type);
					subObject.setStaffStatus(branchObject.getStaffStatus());
					subNode.setUserObject(setIcons(subObject));
					createChildren(subNode);
					branchNode.add(subNode);
				}
			}
		} else if (branchObject.getText().contains("Facilities")) {
			List<ServiceArea> areas = serviceLocator.getServiceAreaService()
					.findAll();
			if (areas != null & !areas.isEmpty()) {
				for (ServiceArea area : areas) {
					DefaultMutableTreeNode subNode = new DefaultMutableTreeNode();
					NodeCommunicationCat subObject = new NodeCommunicationCat(
							subNode);
					subObject.setText(area.getName());
					subObject.setServiceArea(area);
					subNode.setUserObject(setIcons(subObject));
					createChildren(subNode);
					branchNode.add(subNode);
				}
			}
		} else if (branchObject.getText().equals("Vehicle")) {
			for (SelectItem type : ((LookupListProvider) Util
					.getManagedBean("lookupListProvider")).getVehicleType()) {
				DefaultMutableTreeNode subNode = new DefaultMutableTreeNode();
				NodeCommunicationCat subObject = new NodeCommunicationCat(
						subNode);
				subObject.setText(type.getLabel());
				subObject.setVehicleType(type.getValue().toString());
				subNode.setUserObject(setIcons(subObject));
				createChildren(subNode);
				branchNode.add(subNode);
			}
		} else if (branchObject.getCategory() != null) {
			List<CommunicationCat> subCategories = serviceLocator
					.getCommunicationCatService().getSubCategories(
							branchObject.getCategory().getId());
			if (subCategories != null && !subCategories.isEmpty()) {
				branchNode.removeAllChildren();
				for (CommunicationCat cat : subCategories) {
					DefaultMutableTreeNode subNode = new DefaultMutableTreeNode();
					NodeCommunicationCat subObject = new NodeCommunicationCat(
							subNode);
					subObject.setText(cat.getName());
					subObject.setCategory(cat);
					subNode.setUserObject(setIcons(subObject));
					createChildren(subNode);
					branchNode.add(subNode);
				}
			}
		}
	}

	private NodeCommunicationCat setIcons(NodeCommunicationCat branchObject) {
		branchObject.setBranchContractedIcon(XP_BRANCH_CONTRACTED_ICON);
		branchObject.setBranchExpandedIcon(XP_BRANCH_EXPANDED_ICON);
		branchObject.setLeafIcon(XP_BRANCH_LEAF_ICON);
		branchObject.setExpanded(false);
		return branchObject;
	}

	private Date getStartDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(toDate);
		if (timePeriod.equals("1W")) {
			calendar.add(Calendar.DATE, -7);
		} else if (timePeriod.equals("2W")) {
			calendar.add(Calendar.DATE, -14);
		} else if (timePeriod.equals("1M")) {
			calendar.add(Calendar.MONTH, -1);
		} else if (timePeriod.equals("3M")) {
			calendar.add(Calendar.MONTH, -3);
		} else if (timePeriod.equals("6M")) {
			calendar.add(Calendar.MONTH, -6);
		}
		Date toDate = calendar.getTime();
		return toDate;
	}

	public void selectCategory(ActionEvent ae) {
		branchNode = (DefaultMutableTreeNode) ae.getComponent().getAttributes()
				.get("object");
		selectCategory();
	}

	public void selectCategory() {
		student = null;
		source = null;
		staffMember = null;
		vehicle = null;
		organization = null;
		allCommunications = new ArrayList<Communication>();
		NodeCommunicationCat branchObject = (NodeCommunicationCat) branchNode
				.getUserObject();
		if (branchObject.getCategory() != null) {
			category = branchObject.getCategory();
			communications = serviceLocator.getCommunicationService()
					.listByCategory(category.getId());
		} else
			communications = null;
		if (branchObject.getText().equals("All Categories")) {
			communications = serviceLocator.getCommunicationService().findAll();
		} else if (branchObject.getStaffStatus() != null
				&& !branchObject.getStaffStatus().isEmpty()
				&& branchObject.getStaffType() == null) {
			communications = serviceLocator.getCommunicationService()
					.listByStaffType_Status_Category(null,
							branchObject.getStaffStatus(), category);
		} else if (branchObject.getStaffType() != null) {
			communications = serviceLocator.getCommunicationService()
					.listByStaffType_Status_Category(
							branchObject.getStaffType().getId(),
							branchObject.getStaffStatus(), category);
		} else if (branchObject.getText().contains("Facilities")) {
		} else if ((branchObject.getServiceArea() != null)) {
			communications = serviceLocator.getCommunicationService()
					.listByCategoryNServiceArea(category,
							branchObject.getServiceArea());
		} else if (branchObject.getText().equals("Funding Source")) {
		} else if (branchObject.getText().equals("Vehicle")) {
		} else if ((branchObject.getVehicleType() != null && !branchObject
				.getVehicleType().isEmpty())) {
			communications = serviceLocator.getCommunicationService()
					.listByVehicleType(branchObject.getVehicleType());
		} else {
			List<CommunicationCat> subCategories = serviceLocator
					.getCommunicationCatService().getSubCategories(
							category.getId());
			if (subCategories != null && !subCategories.isEmpty()) {
				if (branchObject.getText().equals("Governance")) {
					List<Long> subCatIds = new ArrayList<Long>();
					for (CommunicationCat cat : subCategories)
						subCatIds.add(cat.getId());
					communications = serviceLocator
							.getCommunicationService().listBySubCategories(
									subCatIds);
				}
			}
		}
		allCommunications.addAll(communications);
		filterCommunications();
		
		// branchObject.setExpanded(!branchObject.isExpanded());
		/*if (branchObject.isExpanded())
			branchObject.setExpanded(false);
		else {
			int count = branchNode.getParent().getChildCount();
			for (int i = 0; i < count; i++) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) branchNode
						.getParent().getChildAt(i);
				NodeCommunicationCat cat = (NodeCommunicationCat) node
						.getUserObject();
				cat.setExpanded(false);
			}
			if (branchObject.getCategory() != null) {
				category = branchObject.getCategory();
				communications = serviceLocator.getCommunicationService()
						.listByCategory(category.getId());
			} else
				communications = null;
			// if (branchObject.getText().equals("Existing")
			// || branchObject.getText().equals("Inquiry")) {
			// communications = serviceLocator.getCommunicationService()
			// .listByStudents(category.getId());
			// } else
			if (branchObject.getText().equals("Staff Member")) {
				branchNode.removeAllChildren();
				for (SelectItem status : ((LookupListProvider) Util
						.getManagedBean("lookupListProvider")).getStaffStatus()) {
					DefaultMutableTreeNode subNode = new DefaultMutableTreeNode();
					NodeCommunicationCat subObject = new NodeCommunicationCat(
							subNode);
					subObject.setText(status.getLabel());
					subObject.setStaffStatus(status.getValue().toString());
					subNode.setUserObject(setIcons(subObject));
					branchNode.add(subNode);
				}
			} else if (branchObject.getStaffStatus() != null
					&& !branchObject.getStaffStatus().isEmpty()
					&& branchObject.getStaffType() == null) {
				branchNode.removeAllChildren();
				List<StaffType> staffTypes = serviceLocator
						.getStaffTypeService().findAll();
				if (staffTypes != null & !staffTypes.isEmpty()) {
					for (StaffType type : staffTypes) {
						DefaultMutableTreeNode subNode = new DefaultMutableTreeNode();
						NodeCommunicationCat subObject = new NodeCommunicationCat(
								subNode);
						subObject.setText(type.getName());
						subObject.setStaffType(type);
						subObject.setStaffStatus(branchObject.getStaffStatus());
						subNode.setUserObject(setIcons(subObject));
						branchNode.add(subNode);
					}
				}
				communications = serviceLocator.getCommunicationService()
						.listByStaffType_Status_Category(null,
								branchObject.getStaffStatus(), category);
			} else if (branchObject.getStaffType() != null) {
				// staffList = serviceLocator.getStaffMemberService()
				// .listByStaffTypeNStatus(
				// branchObject.getStaffType().getId(),
				// branchObject.getStaffStatus());
				communications = serviceLocator.getCommunicationService()
						.listByStaffType_Status_Category(
								branchObject.getStaffType().getId(),
								branchObject.getStaffStatus(), category);
			} else if (branchObject.getText().contains("Facilities")) {
				branchNode.removeAllChildren();
				List<ServiceArea> areas = serviceLocator
						.getServiceAreaService().findAll();
				if (areas != null & !areas.isEmpty()) {
					for (ServiceArea area : areas) {
						DefaultMutableTreeNode subNode = new DefaultMutableTreeNode();
						NodeCommunicationCat subObject = new NodeCommunicationCat(
								subNode);
						subObject.setText(area.getName());
						subObject.setServiceArea(area);
						subNode.setUserObject(setIcons(subObject));
						branchNode.add(subNode);
					}
				}
			} else if ((branchObject.getServiceArea() != null)) {
				// orgList = serviceLocator.getExternalOrganizationService()
				// .listByServiceArea(
				// branchObject.getServiceArea().getId());
				communications = serviceLocator.getCommunicationService()
						.listByCategoryNServiceArea(category,
								branchObject.getServiceArea());
			} else if (branchObject.getText().equals("Funding Source")) {
				// fundingList = serviceLocator.getFundingSourceService()
				// .findAll();
				// communications = serviceLocator.getCommunicationService()
				// .listByCategory(category.getId());
			} else if (branchObject.getText().equals("Vehicle")) {
				branchNode.removeAllChildren();
				for (SelectItem type : ((LookupListProvider) Util
						.getManagedBean("lookupListProvider")).getVehicleType()) {
					DefaultMutableTreeNode subNode = new DefaultMutableTreeNode();
					NodeCommunicationCat subObject = new NodeCommunicationCat(
							subNode);
					subObject.setText(type.getLabel());
					subObject.setVehicleType(type.getValue().toString());
					subNode.setUserObject(setIcons(subObject));
					branchNode.add(subNode);
				}
			} else if ((branchObject.getVehicleType() != null && !branchObject
					.getVehicleType().isEmpty())) {
				// vehicles = serviceLocator.getVehicleService().listByType(
				// branchObject.getVehicleType());
				communications = serviceLocator.getCommunicationService()
						.listByVehicleType(branchObject.getVehicleType());
			} else {
				List<CommunicationCat> subCategories = serviceLocator
						.getCommunicationCatService().getSubCategories(
								category.getId());
				if (subCategories != null && !subCategories.isEmpty()) {
					branchNode.removeAllChildren();
					for (CommunicationCat cat : subCategories) {
						DefaultMutableTreeNode subNode = new DefaultMutableTreeNode();
						NodeCommunicationCat subObject = new NodeCommunicationCat(
								subNode);
						subObject.setText(cat.getName());
						subObject.setCategory(cat);
						subNode.setUserObject(setIcons(subObject));
						branchNode.add(subNode);
					}
					if (branchObject.getText().equals("Governance")) {
						List<Long> subCatIds = new ArrayList<Long>();
						for (CommunicationCat cat : subCategories)
							subCatIds.add(cat.getId());
						communications = serviceLocator
								.getCommunicationService().listBySubCategories(
										subCatIds);
					}
				}
			}
			branchObject.setExpanded(true);
		}*/
	}

	public void selectCommunication(ActionEvent re) {
		tmpCommunication = (Communication) re.getComponent().getAttributes()
				.get("com");
		communication = serviceLocator.getCommunicationService().retrieve(
				tmpCommunication.getId());
		// communication.setNotes(serviceLocator.getCommunicationService()
		// .listNotesByCommunication(communication.getId()));
		wizardPage = 2;
		if (communication.getKeyPersonOrg() != null) {
			organization = communication.getKeyPersonOrg();
		}
		if (communication.getKeyPersonStaff() != null) {
			staffMember = communication.getKeyPersonStaff();
		}
		if (communication.getKeyPersonStudent() != null) {
			student = communication.getKeyPersonStudent();
		}
		if (communication.getKeyPersonFunding() != null) {
			source = communication.getKeyPersonFunding();
		}
		if (communication.getKeyPersonVehicle() != null) {
			vehicle = communication.getKeyPersonVehicle();
		}
		eventTime = new TimeBean();
		eventTime.setDateTime(communication.getCreatedTime());
		wizardPage = 2;
		communicationPopup();
	}

	public void addNewCommunication() {
		NodeCommunicationCat branchObject = (NodeCommunicationCat) branchNode
				.getUserObject();
		communication = new Communication();
		eventTime = new TimeBean();
		vehicle = null;
		staffMember = null;
		organization = null;
		source = null;
		if (branchObject.getText().equals("Existing")
				|| branchObject.getText().equals("Inquiry")) {
			searchText = "";
			student = null;
			stuList = null;
		} else if (branchObject.getStaffType() != null) {
			staffList = serviceLocator.getStaffMemberService()
					.listByStaffTypeNStatus(
							branchObject.getStaffType().getId(),
							branchObject.getStaffStatus());
		} else if ((branchObject.getServiceArea() != null)) {
			orgList = serviceLocator.getExternalOrganizationService()
					.listByServiceArea(branchObject.getServiceArea().getId());
		} else if (branchObject.getText().equals("Funding Source")) {
			fundingList = serviceLocator.getFundingSourceService().findAll();
		} else if ((branchObject.getVehicleType() != null && !branchObject
				.getVehicleType().isEmpty())) {
			vehicles = serviceLocator.getVehicleService().listByType(
					branchObject.getVehicleType());
		} else {
			staffList = serviceLocator.getStaffMemberService().findAll();
		}
		communication.setNotes(new ArrayList<CommunicationNote>());
		communication.setCategory(category);
		communication.setCreatedDate(new Date());
		eventTime.setDateTime(new Date());
		wizardPage = 1;
		communicationPopup();
	}

	public void saveCommunication() {
		if (validateCommunicationFields()) {
			communication.setCreatedTime(eventTime.getDateTime(communication
					.getCreatedDate()));
			if (communication.getId() == null) {
				communication.setCreatedBy(sessionContext.getUser());
				communication = serviceLocator.getCommunicationService()
						.create(communication);
				if (communications == null)
					communications = new ArrayList<Communication>();
				communications.add(communication);
				allCommunications.add(communication);
				showInfo("Communication added Successfully..");
			} else {
				communication = serviceLocator.getCommunicationService()
						.update(communication);
				communications.set(communications.indexOf(tmpCommunication),
						communication);
				allCommunications.set(allCommunications.indexOf(tmpCommunication),
						communication);
				showInfo("Communication saved successfully..");
			}
			communicationPopup();
		}
	}

	public void deleteCommunication(ActionEvent ae) {
		try {
			Communication com = (Communication) ae.getComponent()
					.getAttributes().get("com");
			serviceLocator.getCommunicationService().delete(com.getId());
			communications.remove(com);
			allCommunications.remove(com);
			showInfo("Communication deleted succesfully..");
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	public boolean validateCommunicationFields() {
		if (communication.getSummary() != null
				&& !communication.getSummary().isEmpty())
			return true;
		else {
			showError("Please specify the summary of the communication.");
			return false;
		}
	}

	public void addNote() {
		CommunicationNote comNote = new CommunicationNote();
		comNote.setCommunication(communication);
		communication.getNotes().add(0, comNote);
	}

	public void deleteNote(ActionEvent ae) {
		CommunicationNote note = (CommunicationNote) ae.getComponent()
				.getAttributes().get("note");
		if (note != null) {
			communication.getNotes().remove(note);
		}
	}

	public void wizardAction(ActionEvent ae) {
		String action = (String) ae.getComponent().getAttributes()
				.get("actionType");
		if (action.equals("next")) {
			NodeCommunicationCat branchObject = (NodeCommunicationCat) branchNode
					.getUserObject();
			if (branchObject.getText().equals("Inquiry")
					|| branchObject.getText().equals("Existing")) {
				communication.setKeyPersonStudent(student);
			} else if (branchObject.getServiceArea() != null) {
				communication.setKeyPersonOrg(organization);
			} else if (branchObject.getText().equals("Funding Source")) {
				communication.setKeyPersonFunding(source);
			} else if (branchObject.getVehicleType() != null
					&& !branchObject.getVehicleType().isEmpty()) {
				communication.setKeyPersonVehicle(vehicle);
				initVehicleImage(vehicle);
			} else
				communication.setKeyPersonStaff(staffMember);
			wizardPage = 2;
		} else if (action.equals("back")) {
			showInfo("Please select something");
			wizardPage = 1;
		} else if (action.equals("save")) {
			saveCommunication();
		}
	}

	public void communicationPopup() {
		if (visibleCommunication) {
			visibleCommunication = false;
			communication.setUi_selected(false);
		} else
			visibleCommunication = true;
	}

	private void initVehicleImage(Vehicle vehicle) {
		byte[] tmpData = null;
		if (vehicle.getPhoto() != null) {
			tmpData = vehicle.getPhoto().getBlobFileData().getData();
			if (tmpData != null) {
				BufferedImage loadImg;
				try {
					loadImg = ImageIO.read(new ByteArrayInputStream(tmpData));
					int w = loadImg.getWidth();
					int h = loadImg.getHeight();
					if (w > h) {
						photoW = 150;
						photoH = photoW * h / w;
					} else {
						photoH = 100;
						photoW = photoH * w / h;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void openReminder(Reminder reminder) {
		communication = serviceLocator.getCommunicationService().retrieve(
				reminder.getReferenceId());
		// communication.setNotes(serviceLocator.getCommunicationService()
		// .listNotesByCommunication(communication.getId()));
		DefaultMutableTreeNode tmpNode;
		if (communication.getKeyPersonOrg() != null) {
			// For parent Administration
			branchNode = (DefaultMutableTreeNode) treeModel.getChild(
					treeModel.getRoot(), 0);
			selectCategory();
			// For Administration -> Facilities
			for (int i = 0; i < branchNode.getChildCount(); i++) {
				tmpNode = (DefaultMutableTreeNode) branchNode.getChildAt(i);
				if (((NodeCommunicationCat) tmpNode.getUserObject()).getText()
						.equals(communication.getCategory().getName())) {
					branchNode = tmpNode;
					break;
				}
			}
			selectCategory();
			// For Administration -> Facilities -> Organization Type
			for (int i = 0; i < branchNode.getChildCount(); i++) {
				tmpNode = (DefaultMutableTreeNode) branchNode.getChildAt(i);
				if (((NodeCommunicationCat) tmpNode.getUserObject()).getText()
						.equals(communication.getKeyPersonOrg()
								.getServiceArea().getName())) {
					branchNode = tmpNode;
					break;
				}
			}
			organization = communication.getKeyPersonOrg();
		}
		if (communication.getKeyPersonStaff() != null) {
			if (communication.getCategory().getName()
					.equals("General Maintenance")) {
				// For parent Administration
				branchNode = (DefaultMutableTreeNode) treeModel.getChild(
						treeModel.getRoot(), 0);
				selectCategory();
				branchNode = (DefaultMutableTreeNode) branchNode.getChildAt(3);
			}
			if (communication.getCategory().getName().equals("Other")) {
				// For parent Governance
				branchNode = (DefaultMutableTreeNode) treeModel.getChild(
						treeModel.getRoot(), 1);
				selectCategory();
				if (communication.getCategory().getParentId() == 2) {
					// For Governance -> Other
					branchNode = (DefaultMutableTreeNode) branchNode
							.getChildAt(4);
				}
				if (communication.getCategory().getParentId() == 10) {
					// For Governance -> Management Team
					branchNode = (DefaultMutableTreeNode) branchNode
							.getChildAt(0);
					selectCategory();
					// For Governance -> Management Team -> Other
					branchNode = (DefaultMutableTreeNode) branchNode
							.getChildAt(2);
				}
				if (communication.getCategory().getParentId() == 12) {
					// For Governance -> Finance
					branchNode = (DefaultMutableTreeNode) branchNode
							.getChildAt(2);
					selectCategory();
					// For Governance -> Finance -> Other
					branchNode = (DefaultMutableTreeNode) branchNode
							.getChildAt(1);
				}
			}
			if (communication.getCategory().getName().equals("Administration")) {
				// For parent Governance
				branchNode = (DefaultMutableTreeNode) treeModel.getChild(
						treeModel.getRoot(), 1);
				selectCategory();
				// For Governance -> Management Team
				branchNode = (DefaultMutableTreeNode) branchNode.getChildAt(0);
				selectCategory();
				// For Governance -> Management Team -> Administration
				branchNode = (DefaultMutableTreeNode) branchNode.getChildAt(0);
			}
			if (communication.getCategory().getName().equals("Coordinators")) {
				// For parent Governance
				branchNode = (DefaultMutableTreeNode) treeModel.getChild(
						treeModel.getRoot(), 1);
				selectCategory();
				// For Governance -> Management Team
				branchNode = (DefaultMutableTreeNode) branchNode.getChildAt(0);
				selectCategory();
				// For Governance -> Management Team -> Coordinators
				branchNode = (DefaultMutableTreeNode) branchNode.getChildAt(1);
			}
			if (communication.getCategory().getName().equals("Bank")) {
				// For parent Governance
				branchNode = (DefaultMutableTreeNode) treeModel.getChild(
						treeModel.getRoot(), 1);
				selectCategory();
				// For Governance -> Finance
				branchNode = (DefaultMutableTreeNode) branchNode.getChildAt(2);
				selectCategory();
				// For Governance -> Finance -> Bank
				branchNode = (DefaultMutableTreeNode) branchNode.getChildAt(0);
			}
			if (communication.getCategory().getName().equals("Board")) {
				// For parent Governance
				branchNode = (DefaultMutableTreeNode) treeModel.getChild(
						treeModel.getRoot(), 1);
				selectCategory();
				// For Governance -> Board
				branchNode = (DefaultMutableTreeNode) branchNode.getChildAt(3);
			}
			if (communication.getCategory().getName().equals("Staff Member")) {
				// For parent Staff Member
				branchNode = (DefaultMutableTreeNode) treeModel.getChild(
						treeModel.getRoot(), 2);
				selectCategory();
				// For Staff Member ->
				// Status(Current/Exited/Returning/Prospective)
				for (int i = 0; i < branchNode.getChildCount(); i++) {
					tmpNode = (DefaultMutableTreeNode) branchNode.getChildAt(i);
					if (((NodeCommunicationCat) tmpNode.getUserObject())
							.getText().equals(
									communication.getKeyPersonStaff()
											.getStatus())) {
						branchNode = tmpNode;
						break;
					}
				}
				selectCategory();
				// For Staff Member -> Status ->
				// Type(Administrator/Coordinator/Other)
				for (int i = 0; i < branchNode.getChildCount(); i++) {
					tmpNode = (DefaultMutableTreeNode) branchNode.getChildAt(i);
					if (((NodeCommunicationCat) tmpNode.getUserObject())
							.getText().equals(
									communication.getKeyPersonStaff().getType()
											.getName())) {
						branchNode = tmpNode;
						break;
					}
				}
			}
			staffMember = communication.getKeyPersonStaff();
		}
		if (communication.getKeyPersonStudent() != null) {
			// For parent student
			branchNode = (DefaultMutableTreeNode) treeModel.getChild(
					treeModel.getRoot(), 3);
			selectCategory();
			// For student -> Existing/Inquiry
			for (int i = 0; i < branchNode.getChildCount(); i++) {
				tmpNode = (DefaultMutableTreeNode) branchNode.getChildAt(i);
				if (((NodeCommunicationCat) tmpNode.getUserObject()).getText()
						.equals(communication.getCategory().getName())) {
					branchNode = tmpNode;
					break;
				}
			}
			student = communication.getKeyPersonStudent();
		}
		if (communication.getKeyPersonFunding() != null) {
			// For parent Governance
			branchNode = (DefaultMutableTreeNode) treeModel.getChild(
					treeModel.getRoot(), 1);
			selectCategory();
			// For Governance -> Funding Source
			branchNode = (DefaultMutableTreeNode) branchNode.getChildAt(1);
			source = communication.getKeyPersonFunding();
		}
		if (communication.getKeyPersonVehicle() != null) {
			// For parent Vehicle
			branchNode = (DefaultMutableTreeNode) treeModel.getChild(
					treeModel.getRoot(), 4);
			selectCategory();
			// For Vehicle -> Car/Bus/Van
			for (int i = 0; i < branchNode.getChildCount(); i++) {
				tmpNode = (DefaultMutableTreeNode) branchNode.getChildAt(i);
				if (((NodeCommunicationCat) tmpNode.getUserObject())
						.getVehicleType().equals(
								communication.getKeyPersonVehicle().getType())) {
					branchNode = tmpNode;
					break;
				}
			}
			vehicle = communication.getKeyPersonVehicle();
		}
		selectCategory();
	}

	public void addReminder(ActionEvent ae) {
		communication = (Communication) ae.getComponent().getAttributes()
				.get("com");
		// communication.setNotes(serviceLocator.getCommunicationService()
		// .listNotesByCommunication(communication.getId()));
		communication.setReminder(new Reminder());
		reminderPopup();
	}

	public void editReminder(ActionEvent ae) {
		communication = (Communication) ae.getComponent().getAttributes()
				.get("com");
		// reminder = serviceLocator.getReminderService()
		// .retrieveByCommunication(communication.getId());
		// communication.setNotes(serviceLocator.getCommunicationService()
		// .listNotesByCommunication(communication.getId()));
		reminderPopup();
	}

	public void deleteReminder() {
		Long id = communication.getReminder().getId();
		communication.setReminder(null);
		communication = serviceLocator.getCommunicationService().update(
				communication);
		serviceLocator.getReminderService().delete(id);
		showInfo("Reminder removed successfully..");
		reminderPopup();
	}

	public void reminderPopup() {
		if (visibleReminder) {
			if (communication.getReminder() != null
					&& communication.getReminder().getId() == null)
				communication.setReminder(null);
			visibleReminder = false;
		} else
			visibleReminder = true;
	}

	public void saveReminder() {
		if (communication.getReminder().getRemindOn() != null) {
			if (dateFormat.format(new Date()).equals(
					dateFormat
							.format(communication.getReminder().getRemindOn()))
					|| communication.getReminder().getRemindOn()
							.after(new Date())) {
				if (communication.getReminder().getId() == null) {
					communication.getReminder().setType(
							EnumReminderType.COMMUNICATION);
					communication.getReminder().setReferenceId(
							communication.getId());
					communication.getReminder().setCreatedDate(new Date());
				}
				serviceLocator.getCommunicationService().update(communication);
				((ReminderManagerModel) Util
						.getManagedBean("reminderManagerModel"))
						.refreshReminders();
				showInfo("Reminder saved Successfully..");
				reminderPopup();
			} else
				showError("Remind-on date cannot be a past date..");
		} else
			showError("Remind-on date can not be empty..");
	}

	public void selectKeyPerson(ClickActionEvent ae) {
		Object object = ae.getComponent().getAttributes().get("keyPerson");
		if (object instanceof ExternalOrganization) {
			if (organization != null)
				organization.setUi_selected(false);
			organization = (ExternalOrganization) object;
			organization.setUi_selected(true);
			sessionContext.setActiveString(organization.getName());
		} else if (object instanceof StaffMember) {
			if (staffMember != null)
				staffMember.setUi_selected(false);
			staffMember = (StaffMember) object;
			staffMember.setUi_selected(true);
			sessionContext.setActiveString(staffMember.getContact().getName());
		} else if (object instanceof FundingSource) {
			if (source != null)
				source.setUi_selected(false);
			source = (FundingSource) object;
			source.setUi_selected(true);
			sessionContext.setActiveString(source.getFundingType());
		} else if (object instanceof Student) {
			if (student != null)
				student.setUi_selected(false);
			student = (Student) object;
			student.setUi_selected(true);
			sessionContext.setActiveString(student.getContact().getName());
		} else if (object instanceof Vehicle) {
			if (vehicle != null)
				vehicle.setUi_selected(false);
			vehicle = (Vehicle) object;
			vehicle.setUi_selected(true);
			sessionContext.setActiveString(vehicle.getName());
		}
	}

	public void searchStudents() {
		try {
			// See if the user has entered an ID instead name
			Long id = Long.parseLong(searchText);
			Student student = serviceLocator.getStudentService().retrieve(id);
			if (student == null)
				showError("No result available for this Id.");
			else {
				stuList.clear();
				stuList.add(student);
				this.student = null;
			}
		} catch (NumberFormatException nFE) {
			List<Student> students = serviceLocator.getStudentService()
					.listByName(searchText, false);
			if (students == null || students.isEmpty())
				showError("No results for the given search text.");
			else {
				stuList = students;
				student = null;
			}
		}
	}
	
	public String getTxtStyle(){
		if(!withReminders)
			return "color:grey";
		return "";
	}

	/*
	 * public void searchStaff() { if (searchValue == "id" ||
	 * searchValue.equals("id")) { StaffMember staff =
	 * serviceLocator.getStaffMemberService() .searchByStaffId(searchText); if
	 * (staff == null) showError("No result available for this Id."); else {
	 * staffList.clear(); staffList.add(staff); staffMember = null; } } else {
	 * List<StaffMember> staffMembers = serviceLocator
	 * .getStaffMemberService().listByName(searchText); if (staffMembers == null
	 * || staffMembers.isEmpty())
	 * showError("No results for the given search text."); else { staffList =
	 * staffMembers; staffMember = null; } } }
	 */

	/*
	 * public boolean validateReminder() { if (reminder.getRemindOn() != null) {
	 * if (reminder.getStatus() != null) return true; else {
	 * showError("Status should not be Empty"); return false; } } else {
	 * showError("Reminder Date should not be Empty"); return false; } }
	 */

	/*
	 * getters and setters
	 */

	public DefaultTreeModel getTreeModel() {
		return treeModel;
	}

	public DefaultMutableTreeNode getBranchNode() {
		return branchNode;
	}

	public List<Communication> getCommunications() {
		return communications;
	}

	public void setCommunication(Communication communication) {
		this.communication = communication;
	}

	public Communication getCommunication() {
		return communication;
	}

	public void setEventTime(TimeBean eventTime) {
		this.eventTime = eventTime;
	}

	public TimeBean getEventTime() {
		return eventTime;
	}

	public void setSelectKeyPersonList(List<SelectItem> selectKeyPersonList) {
		this.selectKeyPersonList = selectKeyPersonList;
	}

	public List<SelectItem> getSelectKeyPersonList() {
		return selectKeyPersonList;
	}

	/*
	 * public void setStudentList(List<Student> studentList) { this.studentList
	 * = studentList; }
	 * 
	 * public List<Student> getStudentList() { return studentList; }
	 */
	/*
	 * public void setInvStudentList(HashMap<Long, Student> invStudentList) {
	 * this.invStudentList = invStudentList; }
	 * 
	 * public HashMap<Long, Student> getInvStudentList() { return
	 * invStudentList; }
	 * 
	 * public List<SelectItem> getInvolvedSelectItems() { return
	 * involvedSelectItems; }
	 * 
	 * public void setSelectedStudentId(Long selectedStudentId) {
	 * this.selectedStudentId = selectedStudentId; }
	 * 
	 * public Long getSelectedStudentId() { return selectedStudentId; }
	 */

	public void setPhotoW(int photoW) {
		this.photoW = photoW;
	}

	public int getPhotoW() {
		return photoW;
	}

	public void setPhotoH(int photoH) {
		this.photoH = photoH;
	}

	public int getPhotoH() {
		return photoH;
	}

	/*
	 * public void setReminder(Reminder reminder) { this.reminder = reminder; }
	 * 
	 * public Reminder getReminder() { return reminder; }
	 */

	public boolean isVisibleCommunication() {
		return visibleCommunication;
	}

	public boolean isVisibleReminder() {
		return visibleReminder;
	}

	public void setStuList(List<Student> stuList) {
		this.stuList = stuList;
	}

	public List<Student> getStuList() {
		return stuList;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Student getStudent() {
		return student;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setStaffList(List<StaffMember> staffList) {
		this.staffList = staffList;
	}

	public List<StaffMember> getStaffList() {
		return staffList;
	}

	public void setStaffMember(StaffMember staffMember) {
		this.staffMember = staffMember;
	}

	public StaffMember getStaffMember() {
		return staffMember;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public void setOrgList(List<ExternalOrganization> orgList) {
		this.orgList = orgList;
	}

	public List<ExternalOrganization> getOrgList() {
		return orgList;
	}

	public void setOrganization(ExternalOrganization organization) {
		this.organization = organization;
	}

	public ExternalOrganization getOrganization() {
		return organization;
	}

	public void setFundingList(List<FundingSource> fundingList) {
		this.fundingList = fundingList;
	}

	public List<FundingSource> getFundingList() {
		return fundingList;
	}

	public void setSource(FundingSource source) {
		this.source = source;
	}

	public FundingSource getSource() {
		return source;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public int getWizardPage() {
		return wizardPage;
	}

	public Date getToday() {
		return today;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public String getCommSearchTxt() {
		return commSearchTxt;
	}

	public void setCommSearchTxt(String commSearchTxt) {
		this.commSearchTxt = commSearchTxt;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}

	public String getTimePeriod() {
		return timePeriod;
	}

	public void setWithReminders(boolean withReminders) {
		this.withReminders = withReminders;
	}

	public boolean isWithReminders() {
		return withReminders;
	}

}

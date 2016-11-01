package com.itelasoft.pso.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.springframework.dao.DataIntegrityViolationException;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.itelasoft.pso.beans.InternalOrganization;
import com.itelasoft.pso.beans.NdisPrice;
import com.itelasoft.pso.beans.NdisSupportItem;

@ManagedBean(name = "ndisSupportItemManagerModel")
@SessionScoped
public class NdisSupportItemManagerModel extends UIModel {

	private List<NdisSupportItem> allItems;
	private long id;
	private NdisSupportItem item, tmpItem;
	private NdisPrice price, tmpPrice;
	private String searchText;
	private boolean visibleAddPrice;
	private LookupListProvider listProvider;
	private List<SelectItem> currentNdisTimeList;
	private List<SelectItem> programTimes;
	private List<SelectItem> transportTimes;
	private List<SelectItem> ancillaryTimes;
	private List<SelectItem> individual;
	private List<SelectItem> availableUoms;
	private List<SelectItem> ndisTypes;
	private List<String> uoms; 
	private InternalOrganization internalOrganization;

	public NdisSupportItemManagerModel() {
		init();
	}

	public void init() {
		allItems = serviceLocator.getNdisSupportItemService().findAll();
		sortList(allItems);
		item = tmpItem = null;
		price = tmpPrice = null;
		searchText = "";
		visibleAddPrice = false;
		availableUoms = new ArrayList<SelectItem>();
		List<InternalOrganization> orgs = new ArrayList<InternalOrganization>();
		orgs = serviceLocator.getInternalOrganizationService().findAll();
		if (orgs.isEmpty() || orgs == null) {
			showError("There is no Internal Organization exists.");
		} else {
			internalOrganization = orgs.get(0);
		}
	}

	public void sortList(List<NdisSupportItem> list) {
		Collections.sort(list, new Comparator<NdisSupportItem>() {

			@Override
			public int compare(NdisSupportItem item1, NdisSupportItem item2) {
				return item1.getItemName().toLowerCase().compareTo(item2.getItemName().toLowerCase());
			}
		});

	}

	public void searchNdisSupportItem() {
		allItems = new ArrayList<NdisSupportItem>();
		try {
			Long id = Long.parseLong(searchText);
			NdisSupportItem item = serviceLocator.getNdisSupportItemService().retrieve(id);
			if (item == null) {
				showError("No external NDIS Support Items available for this Id.");
			} else {
				allItems.clear();
				allItems.add(item);
				this.item = tmpItem = null;
			}
		} catch (NumberFormatException nFE) {
			List<NdisSupportItem> allItems = serviceLocator.getNdisSupportItemService().listByName(searchText);
			if (allItems == null || allItems.isEmpty())
				showError("No results for the given search text.");
			else {
				this.allItems = allItems;
				tmpItem = item = null;
			}
		} catch (Exception exception) {
			showExceptionAsError(exception);
		}
	}

	public void newNdisItem() {
		if (tmpItem != null)
			tmpItem.setUi_selected(false);
		item = new NdisSupportItem();
		item.setGstCode(internalOrganization != null ? internalOrganization.getGst() : "");
		initNdisTypeList();
	}

	public void selectNdisSupportItem(ClickActionEvent re) {
		clearInputs();
		if (tmpItem != null)
			tmpItem.setUi_selected(false);
		tmpItem = (NdisSupportItem) re.getComponent().getAttributes().get("item");
		tmpItem.setUi_selected(true);
		item = serviceLocator.getNdisSupportItemService().retrieve(tmpItem.getId());
		if (item.getGstCode() == null || item.getGstCode().isEmpty()) {
			item.setGstCode(internalOrganization != null ? internalOrganization.getGst() : "");
		}
		initNdisTypeList();
	}

	public void saveNdisItem() {
		if (validateNdisSupportItem()) {
			if (item.getId() == null) {
				item = serviceLocator.getNdisSupportItemService().create(item);
				item.setUi_selected(true);
				tmpItem = item;
				allItems.add(tmpItem);
				item = serviceLocator.getNdisSupportItemService().retrieve(tmpItem.getId());
				showInfo("NDIS Support Item created successfully.");
			} else {
				item = serviceLocator.getNdisSupportItemService().update(item);
				item.setUi_selected(true);
				allItems.set(allItems.indexOf(tmpItem), item);
				tmpItem = item;
				item = serviceLocator.getNdisSupportItemService().retrieve(tmpItem.getId());
				showInfo("NDIS Support Item Collection updated successfully.");
			}
		}
	}

	public void deleteNdisItem() {
		try {
			serviceLocator.getNdisSupportItemService().delete(item.getId());
			allItems.remove(tmpItem);
			item = tmpItem = null;
			showInfo("NDIS Support Item deleted succesfully..");
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	public void initNdisPriceItems() {

	}

	private boolean validateNdisSupportItem() {
		List<String> componentIds = new ArrayList<String>();
		clearInputs();
		if (item.getItemName() == null || item.getItemName().isEmpty()) {
			showError(Util.getMessage("Support cluster name is required"));
			componentIds.add("input_ItemName");
			highlightInputs(componentIds);
			return false;
		}
		if (item.getNdisClusterType().equals("Please Select")) {
			showError("Select a cluster type");
			componentIds.add("select_ndisClusterType");
			highlightInputs(componentIds);
			return false;
		}
		if (item.getGstCode().equals("")) {
			showError("Select a GST code");
			componentIds.add("select_GST");
			highlightInputs(componentIds);
			return false;
		}
		if (item.getNdisClusterType().equals("Individual") || item.getNdisClusterType().equals("DayProgram")) {
			if (item.getNumerator() == 0) {
				showError(Util.getMessage("Staff field is required"));
				componentIds.add("input_StaffRatio");
				highlightInputs(componentIds);
				return false;
			}
			if (item.getDenominator() == 0) {
				showError(Util.getMessage("Student field is required"));
				componentIds.add("input_StudentRatio");
				highlightInputs(componentIds);
				return false;
			}
		}
		return true;
	}

	private void initNdisTypeList() {
		ndisTypes = new ArrayList<SelectItem>();
		if (item != null && item.getNdisPrice() != null && !item.getNdisPrice().isEmpty()) {
			if (item.getNdisClusterType().equals("Transport")) {
				ndisTypes.add(new SelectItem("Transport", "Transport"));
			}
			if (item.getNdisClusterType().equals("Ancillary")) {
				ndisTypes.add(new SelectItem("Ancillary", "Ancillary"));
			}
			if (item.getNdisClusterType().equals("DayProgram")) {
				ndisTypes.add(new SelectItem("Individual", "Individual"));
				ndisTypes.add(new SelectItem("DayProgram", "DayProgram"));
			}
			if (item.getNdisClusterType().equals("Individual")) {
				List<NdisPrice> list = item.getNdisPrice();
				boolean found = false;
				for (NdisPrice ndisPrice : list) {
					if (ndisPrice.getNdisTime().equals("Overnight Inactive")
							|| ndisPrice.getNdisTime().equals("Weekday Overnight Active")) {
						found = true;
						break;
					}
				}
				if (found == false) {
					ndisTypes.add(new SelectItem("Individual", "Individual"));
					ndisTypes.add(new SelectItem("DayProgram", "DayProgram"));
				} else {
					ndisTypes.add(new SelectItem("Individual", "Individual"));
				}
			}
		} else {
			ndisTypes.add(new SelectItem("Please Select", "Please Select"));
			ndisTypes.add(new SelectItem("DayProgram", "DayProgram"));
			ndisTypes.add(new SelectItem("Transport", "Transport"));
			ndisTypes.add(new SelectItem("Ancillary", "Ancillary"));
			ndisTypes.add(new SelectItem("Individual", "Individual"));
		}
	}

	public void addNewPrice() {
		clearInputs();
		price = new NdisPrice();
		price.setPrice(0.0);
		price.setSupportItem(item);
		String type = item.getNdisClusterType();
		availableUoms = new ArrayList<SelectItem>();
		if (type.equals("Transport")) {
			availableUoms.add(new SelectItem("Day", "Day"));
		} else if (type.equals("Ancillary")) {
			availableUoms.add(new SelectItem("Please Select", "Please Select"));
			availableUoms.add(new SelectItem("Hour", "Hour"));
			availableUoms.add(new SelectItem("Each", "Each"));
		} else {
			availableUoms.add(new SelectItem("Please Select", "Please Select", "", true));
		}
		initCurrentTimeList(type);
		addPricePopup();
	}

	public void editNdisPriceItem(ActionEvent ae) {
		tmpPrice = (NdisPrice) ae.getComponent().getAttributes().get("np");
		price = serviceLocator.getNdisPriceService().retrieve(tmpPrice.getId());
		initCurrentTimeList(item.getNdisClusterType());
		availableUoms.clear();
		if (item.getNdisClusterType().equals("Ancillary")) {
			availableUoms.add(new SelectItem("Hour", "Hour"));
			availableUoms.add(new SelectItem("Each", "Each"));
		} else {
			availableUoms.add(new SelectItem(price.getUom(), price.getUom()));
		}
		addPricePopup();
	}

	public void savePrice() {
		if (validateNdisPrice()) {
			if (price.getId() == null) {
				serviceLocator.getNdisPriceService().create(price);
				item.getNdisPrice().add(price);
				showInfo("NDIS Support Item Price created successfully.");

			} else {
				item.getNdisPrice().set(item.getNdisPrice().indexOf(tmpPrice), price);
				price = serviceLocator.getNdisPriceService().update(price);
				item.setUi_selected(true);
				allItems.set(allItems.indexOf(tmpItem), item);
				tmpItem = item;
				item = serviceLocator.getNdisSupportItemService().retrieve(tmpItem.getId());
				showInfo("NDIS Support Item Price updated successfully.");

			}
			initNdisTypeList();
			addPricePopup();
		}
	}

	public void removeNdisPrice(ActionEvent ae) {
		try {
			price = (NdisPrice) ae.getComponent().getAttributes().get("np");
			serviceLocator.getNdisPriceService().delete(price.getId());
			item.getNdisPrice().remove(price);
			item = serviceLocator.getNdisSupportItemService().update(item);
			item.setUi_selected(true);
			allItems.set(allItems.indexOf(tmpItem), item);
			tmpItem = item;
			item = serviceLocator.getNdisSupportItemService().retrieve(tmpItem.getId());
			showInfo("NDIS price has been removed successfully.");
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
		initNdisTypeList();
	}

	public boolean validateNdisPrice() {
		List<String> componentIds = new ArrayList<String>();
		clearInputs();
		if (price.getPriceName() == null || price.getPriceName().isEmpty()) {
			showError("Support item name is required ");
			componentIds.add("input_Name");
			highlightInputs(componentIds);
			return false;
		}
		if (price.getItemNumber() == null || price.getItemNumber().isEmpty()) {
			showError(Util.getMessage("Referance number is required"));
			componentIds.add("input_SupportNumber");
			highlightInputs(componentIds);
			return false;
		}
		if (price.getNdisTime().equals("Please Select")) {
			showError("Select a NDIS Time");
			componentIds.add("select_NDISTime");
			highlightInputs(componentIds);
			return false;
		}
		if (price.getUom().equals("Please Select")) {
			showError(Util.getMessage("Select a UOM"));
			componentIds.add("select_Uom");
			highlightInputs(componentIds);
			return false;
		}
		if (price.getPrice() == null || price.getPrice() == 0) {
			showError(Util.getMessage("Price is required"));
			componentIds.add("input_Price");
			highlightInputs(componentIds);
			return false;
		}
		if (price.getPrice() < 0) {
			showError(Util.getMessage("Price can't be negitive value"));
			componentIds.add("input_Price");
			highlightInputs(componentIds);
			return false;
		}

		if (price.getStartDate() == null) {
			showError("Start date is required");
			componentIds.add("input_PriceStartDate");
			highlightInputs(componentIds);
			return false;
		}

		return true;
	}

	public void addPricePopup() {
		visibleAddPrice = !visibleAddPrice;
	}

	public void selectNdisTime(ValueChangeEvent event) {
		price.setPrice(0.0);
		String time = (String) event.getNewValue();
		if(time != null) {
			availableUoms = new ArrayList<SelectItem>();
			if (!time.equals("Please Select")) {
				if (time.equals("Overnight Inactive")) {
					availableUoms.add(new SelectItem("Each", "Each"));
	
				} else if (item.getNdisClusterType().equals("Transport")) {
					availableUoms.add(new SelectItem("Day", "Day"));
				} else if (time.equals("Ancillary")) {
					availableUoms.add(new SelectItem("Please Select", "Please Select"));
					availableUoms.add(new SelectItem("Hour", "Hour"));
					availableUoms.add(new SelectItem("Each", "Each"));
				} else {
					availableUoms.add(new SelectItem("Hour", "Hour"));
				}
			} else {
				availableUoms.add(new SelectItem("Please Select", "Please Select"));
			}
		}
	}

	public void initCurrentTimeList(String clusterType) {
		if (clusterType.equals("DayProgram")) {
			currentNdisTimeList = getProgramTimes();
		}
		if (clusterType.equals("Transport")) {
			currentNdisTimeList = getTransportTimes();
		}
		if (clusterType.equals("Ancillary")) {
			currentNdisTimeList = getAncillaryTimes();
		}
		if (clusterType.equals("Individual")) {
			currentNdisTimeList = getIndividual();
		}
	}

	/*
	 * getters and setters
	 */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<NdisSupportItem> getAllItems() {
		return allItems;
	}

	public void setAllItems(List<NdisSupportItem> allItems) {
		this.allItems = allItems;
	}

	public NdisSupportItem getItem() {
		return item;
	}

	public void setItem(NdisSupportItem item) {
		this.item = item;
	}

	public NdisSupportItem getTmpItem() {
		return tmpItem;
	}

	public void setTmpItem(NdisSupportItem tmpItem) {
		this.tmpItem = tmpItem;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public boolean isVisibleAddPrice() {
		return visibleAddPrice;
	}

	public void setVisibleAddPrice(boolean visibleAddPrice) {
		this.visibleAddPrice = visibleAddPrice;
	}

	public NdisPrice getPrice() {
		return price;
	}

	public void setPrice(NdisPrice price) {
		this.price = price;
	}

	public NdisPrice getTmpPrice() {
		return tmpPrice;
	}

	public void setTmpPrice(NdisPrice tmpPrice) {
		this.tmpPrice = tmpPrice;
	}

	public LookupListProvider getListProvider() {
		return listProvider;
	}

	public void setListProvider(LookupListProvider listProvider) {
		this.listProvider = listProvider;
	}

	public void setCurrentNdisTimeList(List<SelectItem> currentNdisTimeList) {
		this.currentNdisTimeList = currentNdisTimeList;
	}

	public List<SelectItem> getCurrentNdisTimeList() {
		return currentNdisTimeList;
	}

	public List<SelectItem> getProgramTimes() {
		if (programTimes == null) {
			programTimes = new ArrayList<SelectItem>();
			programTimes.add(new SelectItem("Please Select", "Please Select"));
			programTimes.add(new SelectItem("Weekday Daytime", "Weekday Daytime"));
			programTimes.add(new SelectItem("Weekday Evening", "Weekday Evening"));
			programTimes.add(new SelectItem("Saturday", "Saturday"));
			programTimes.add(new SelectItem("Sunday", "Sunday"));
			programTimes.add(new SelectItem("Public Holiday", "Public Holiday"));
		}
		return programTimes;
	}

	public void setProgramTimes(List<SelectItem> programTimes) {
		this.programTimes = programTimes;
	}

	public List<SelectItem> getTransportTimes() {
		if (transportTimes == null) {
			transportTimes = new ArrayList<SelectItem>();
			transportTimes.add(new SelectItem("Ancillary", "Ancillary"));
		}
		return transportTimes;

	}

	public void setTransportTimes(List<SelectItem> transportTimes) {
		this.transportTimes = transportTimes;
	}

	public List<SelectItem> getAncillaryTimes() {
		if (ancillaryTimes == null) {
			ancillaryTimes = new ArrayList<SelectItem>();
			ancillaryTimes.add(new SelectItem("Ancillary", "Ancillary"));
		}

		return ancillaryTimes;
	}

	public void setAncillaryTimes(List<SelectItem> ancillaryTimes) {
		this.ancillaryTimes = ancillaryTimes;
	}

	public List<SelectItem> getIndividual() {
		if (individual == null) {
			individual = new ArrayList<SelectItem>();
			individual.add(new SelectItem("Please Select", "Please Select"));
			individual.add(new SelectItem("Weekday Evening", "Weekday Evening"));
			individual.add(new SelectItem("Weekday Daytime", "Weekday Daytime"));
			individual.add(new SelectItem("Weekday Overnight Active", "Weekday Overnight Active"));
			individual.add(new SelectItem("Overnight Inactive", "Overnight Inactive"));
			individual.add(new SelectItem("Saturday", "Saturday"));
			individual.add(new SelectItem("Sunday", "Sunday"));
			individual.add(new SelectItem("Public Holiday", "Public Holiday"));

		}

		return individual;
	}

	public void setIndividual(List<SelectItem> individual) {
		this.individual = individual;
	}

	public List<String> getUoms() {
		return uoms;
	}

	public void setUoms(List<String> uoms) {
		this.uoms = uoms;
	}

	public List<SelectItem> getSelectedUoms() {
		return availableUoms;
	}

	public void setSelectedUoms(List<SelectItem> selectedUoms) {
		this.availableUoms = selectedUoms;
	}

	public List<SelectItem> getNdisTypes() {
		return ndisTypes;
	}

	public void setNdisTypes(List<SelectItem> ndisTypes) {
		this.ndisTypes = ndisTypes;
	}

	public List<SelectItem> getAvailableUoms() {
		return availableUoms;
	}

	public InternalOrganization getInternalOrganization() {
		return internalOrganization;
	}

	public void setInternalOrganization(InternalOrganization internalOrganization) {
		this.internalOrganization = internalOrganization;
	}

}

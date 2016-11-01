package com.itelasoft.pso.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.springframework.dao.DataIntegrityViolationException;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.itelasoft.pso.beans.EnumItemCategory;
import com.itelasoft.pso.beans.ReferenceItem;

@ManagedBean(name = "referenceItemManagerModel")
@SessionScoped
public class ReferenceItemManagerModel extends UIModel {

	private List<ReferenceItem> items;
	private ReferenceItem item, tmpItem;
	private List<SelectItem> categories;
	private String categoryName;

	public void init() {
		item = tmpItem = null;
		categories = new ArrayList<SelectItem>();
		for (EnumItemCategory item : EnumItemCategory.values()) {
			categories.add(new SelectItem(item.getId(), item.toString()));
		}
		categoryName = EnumItemCategory.STAFF_SKILL.getId();
		items = serviceLocator.getReferenceItemService().findItemsByCategory(
				EnumItemCategory.STAFF_SKILL, null);
	}

	public void selectItem(ClickActionEvent re) {
		if (tmpItem != null)
			tmpItem.setUi_selected(false);
		tmpItem = (ReferenceItem) re.getComponent().getAttributes().get("item");
		tmpItem.setUi_selected(true);
		item = serviceLocator.getReferenceItemService().retrieve(
				tmpItem.getId());
		categoryName = item.getCategory().getId();
	}

	public void addNewItem() {
		if (tmpItem != null)
			tmpItem.setUi_selected(false);
		item = new ReferenceItem();
		item.setCategory(EnumItemCategory.valueOf(categoryName));
	}

	public void saveItem() {
		if (item.getItemValue() != null && !item.getItemValue().isEmpty()) {
			if (item.getId() == null) {
				item = serviceLocator.getReferenceItemService().create(item);
				tmpItem = item;
				tmpItem.setUi_selected(true);
				items.add(tmpItem);
				item = serviceLocator.getReferenceItemService().retrieve(
						tmpItem.getId());
				showInfo("Reference item added successfully..");
			} else {
				item = serviceLocator.getReferenceItemService().update(item);
				item.setUi_selected(true);
				items.set(items.indexOf(tmpItem), item);
				tmpItem = item;
				item = serviceLocator.getReferenceItemService().retrieve(
						tmpItem.getId());
				showInfo("Reference item updated successfully..");
			}
		} else
			showError("Reference name can not be empty..");
	}

	public void deleteItem() {
		try {
			serviceLocator.getReferenceItemService().delete(item.getId());
			items.remove(tmpItem);
			item = tmpItem = null;
			showInfo("Reference item deleted succesfully.");
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	public void findItems(ValueChangeEvent ve) {
		categoryName = (String) ve.getNewValue();
		items = serviceLocator.getReferenceItemService().findItemsByCategory(
				EnumItemCategory.valueOf(categoryName), null);
		item = null;
		if (items == null || items.isEmpty()) {
			showError("There are no Reference Item(s) for this Category");
		}
	}

	public List<ReferenceItem> getItems() {
		return items;
	}

	public List<SelectItem> getCategories() {
		return categories;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public ReferenceItem getItem() {
		return item;
	}

}

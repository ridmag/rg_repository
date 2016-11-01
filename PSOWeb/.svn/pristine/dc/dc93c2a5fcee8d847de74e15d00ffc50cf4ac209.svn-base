package com.itelasoft.pso.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name = "listBean")
@SessionScoped
public class ListBean {
	private List<SelectItem> list1;
	private List<SelectItem> list2;
	private Long selectedItemId1;
	private Long selectedItemId2;

	public ListBean() {
		list1 = new ArrayList<SelectItem>();
		list2 = new ArrayList<SelectItem>();
	}

	private int getIndex(List<SelectItem> list, Long selectedItemId) {
		SelectItem selectItem = null;
		for (SelectItem item1 : list) {
			if (item1.getValue().equals(selectedItemId)) {
				selectItem = item1;
				break;
			}
		}
		return list.indexOf(selectItem);
	}

	public void add() {
		SelectItem item = list1.get(getIndex(list1, selectedItemId1));
		list2.add(item);
		list1.remove(item);
		selectedItemId1 = null;
		selectedItemId2 = null;
	}

	public void addAll() {
		list2.addAll(list1);
		list1.clear();
	}

	public void remove() {
		SelectItem item = list2.get(getIndex(list2, selectedItemId2));
		list1.add(item);
		list2.remove(item);
		selectedItemId1 = null;
		selectedItemId2 = null;
	}

	public void removeAll() {
		list1.addAll(list2);
		list2.clear();
	}

	public void clear() {
		list1.clear();
		list2.clear();
		selectedItemId1 = null;
		selectedItemId2 = null;
	}

	public List<SelectItem> getList1() {
		return list1;
	}

	public void setList1(List<SelectItem> list1) {
		this.list1 = list1;
	}

	public List<SelectItem> getList2() {
		return list2;
	}

	public void setList2(List<SelectItem> list2) {
		this.list2 = list2;
	}

	public Long getSelectedItemId1() {
		return selectedItemId1;
	}

	public void setSelectedItemId1(Long selectedItemId1) {
		this.selectedItemId1 = selectedItemId1;
	}

	public Long getSelectedItemId2() {
		return selectedItemId2;
	}

	public void setSelectedItemId2(Long selectedItemId2) {
		this.selectedItemId2 = selectedItemId2;
	}

}

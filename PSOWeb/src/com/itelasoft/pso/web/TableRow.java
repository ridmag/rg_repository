package com.itelasoft.pso.web;

import java.util.ArrayList;
import java.util.List;

public class TableRow<T> {
	private T row;
	private boolean selected;

	public static <T> List<TableRow<T>> createRows(List<T> list) {
		if (list == null)
			return null;
		List<TableRow<T>> list2 = new ArrayList<TableRow<T>>();
		for (T o : list) {
			list2.add(new TableRow<T>(o));
		}
		return list2;
	}

	public static <T> List<T> createList(List<TableRow<T>> list) {
		if (list == null)
			return null;
		List<T> list2 = new ArrayList<T>();
		for (TableRow<T> o : list) {
			list2.add(o.getRow());
		}
		return list2;
	}

	public TableRow(T o) {
		row = o;
	}

	public void setRow(T row) {
		this.row = row;
	}

	public T getRow() {
		return row;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}
}

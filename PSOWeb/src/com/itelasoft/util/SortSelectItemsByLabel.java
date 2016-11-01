package com.itelasoft.util;

import java.util.Comparator;

import javax.faces.model.SelectItem;

public class SortSelectItemsByLabel implements Comparator<SelectItem> {

	@Override
	public int compare(SelectItem item1, SelectItem item2) {
		return item1.getLabel().toLowerCase().compareTo(item2.getLabel().toLowerCase());
	}

}

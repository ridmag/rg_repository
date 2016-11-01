package com.itelasoft.util;

import java.util.Comparator;

import com.itelasoft.pso.beans.Transaction;

public class SortObjectById implements Comparator<Object> {

	@Override
	public int compare(Object obj1, Object obj2) {
		if (obj1 instanceof Transaction && obj2 instanceof Transaction) {
			if (((Transaction) obj1).getProgramEvent() != null && ((Transaction) obj2).getProgramEvent() != null) {
				return ((Transaction) obj1).getProgramEvent().getId()
						.compareTo(((Transaction) obj2).getProgramEvent().getId());
			}

		}
		return 0;
	}

}

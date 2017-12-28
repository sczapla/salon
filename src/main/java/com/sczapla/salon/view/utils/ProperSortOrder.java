package com.sczapla.salon.view.utils;

import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Sort.Direction;

public class ProperSortOrder {

	public static Direction getDirection(SortOrder order) {
		if (order.equals(SortOrder.ASCENDING)) {
			return Direction.ASC;
		} else if (order.equals(SortOrder.DESCENDING)) {
			return Direction.DESC;
		}
		return null;
	}
}

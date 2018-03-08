package com.sczapla.salon.model;

public enum Status {
	WOLNE, ZAREZERWOWANE, ANULOWANE, ZAKONCZONE;

	public String getName() {
		return name();
	}
}

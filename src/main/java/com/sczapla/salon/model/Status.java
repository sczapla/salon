package com.sczapla.salon.model;

public enum Status {
	WOLNE, ZAREZEROWANE, ANULOWANE, ZAKONCZONE;

	public String getName() {
		return name();
	}
}

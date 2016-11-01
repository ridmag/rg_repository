package com.itelasoft.pso.services;

import java.util.Date;

import com.itelasoft.pso.beans.License;

public interface ILicenseService {
	public String getSerialNumber();

	// public String encode(String plainText);
	// public String decode(String encodedText);
	public License generateLicense(String serialNumber, String licenseType,
			Date expiryDate);

	public License validateLicense(String licenseKey);
}

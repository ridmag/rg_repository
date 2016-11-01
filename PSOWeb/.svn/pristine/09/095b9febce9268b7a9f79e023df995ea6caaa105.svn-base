package com.itelasoft.pso.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.itelasoft.pso.beans.License;
import com.itelasoft.util.TextEncoder;

public class LicenseService implements ILicenseService {

	// Retrieve product serial number (derived from the clinic name)
	public String getSerialNumber() {
		try {
			String clinicName = ServiceLocator.getServiceLocator()
					.getConfigurationService().retrieveContents("CLINIC_NAME");
			return TextEncoder.digest(clinicName);
		} catch (Exception exception) {
			return "00000";
		}
	}

	// Generate an encrypted license key with multiple contents
	public License generateLicense(String serialNumber, String licenseType,
			Date expiryDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			String licenseParams = serialNumber + "|" + licenseType + "|"
					+ simpleDateFormat.format(expiryDate);
			String encodedParams = TextEncoder.encode(licenseParams);
			License license = new License(serialNumber, encodedParams);
			license.setLicenseKey(encodedParams);
			return license;
		} catch (Exception exception) {
			return new License();
		}
	}

	// Validate a license key and retrieve encoded information
	public License validateLicense(String licenseKey) {
		try {
			String systemSerialNumber = getSerialNumber();
			License license = new License(systemSerialNumber, licenseKey);
			return license;
		} catch (Exception exception) {
			return new License();
		}

	}
}

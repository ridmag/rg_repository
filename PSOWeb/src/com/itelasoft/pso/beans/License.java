package com.itelasoft.pso.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import com.itelasoft.util.TextEncoder;

public class License {
	private String serialNumber;
	private String licenseKey;
	private String licenseType;
	private Date expiryDate;
	private String decodedSerialNumber = "";

	// Properties

	public String getSerialNumber() {
		return serialNumber;
	}

	public String getLicenseKey() {
		return licenseKey;
	}

	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}

	public boolean isValid() {
		// First check if license key is authentic by checking serial number
		if ((decodedSerialNumber.compareTo(serialNumber) == 0)) {
			if (licenseType.compareTo("TRAIL") == 0) {
				// For trail licenses check date
				if (expiryDate.after(GregorianCalendar.getInstance().getTime())) {
					return true; // within trail period
				} else {
					return false; // license date expired
				}
			} else {
				return true; // no expiry date limit
			}
		} else {
			return false; // key is not authentic!
		}
	}

	public String getLicenseType() {
		return this.licenseType;
	}

	public Date getExpiryDate() {
		return this.expiryDate;
	}

	// Services
	public License() {
		this.serialNumber = "x";
		this.licenseKey = "y";
		this.licenseType = "FAKE";
		this.expiryDate = GregorianCalendar.getInstance().getTime();
	}

	public License(String serialNumber, String licenseKey) {
		this.serialNumber = serialNumber;
		this.licenseKey = licenseKey;
		this.licenseType = "FAKE";
		this.expiryDate = GregorianCalendar.getInstance().getTime();
		try {
			String licenseParams = TextEncoder.decode(licenseKey);
			// Check if license key was decoded properly
			if (!licenseParams.isEmpty()) {
				StringTokenizer stringTokenizer = new StringTokenizer(
						licenseParams, "|");
				List<String> list = new ArrayList<String>();
				while (stringTokenizer.hasMoreTokens()) {
					list.add(stringTokenizer.nextToken());
				}
				if (list.size() < 3)
					throw new Exception("Not enough license parameters decoded");
				this.decodedSerialNumber = list.get(0);
				this.licenseType = list.get(1);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"dd-MMM-yyyy");
				this.expiryDate = simpleDateFormat.parse(list.get(2));
			} else {
				throw new Exception("License key decode failure");
			}
		} catch (Exception exception) {
		}
	}
}

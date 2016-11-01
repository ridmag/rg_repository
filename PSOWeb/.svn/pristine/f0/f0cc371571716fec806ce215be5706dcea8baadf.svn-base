package com.itelasoft.pso.services.test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itelasoft.util.TextEncoder;
import com.itelasoft.pso.beans.License;
import com.itelasoft.pso.services.ILicenseService;
import com.itelasoft.pso.services.ServiceLocator;

import junit.framework.TestCase;

public class LicenseServiceTest extends TestCase {

	private ILicenseService licenseService;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		licenseService = ServiceLocator.getServiceLocator().getLicenseService();
	}
	
	public void testEncodeDecode(){
		String str1 = "Text to be Encoded";
		String str2 = "";
		String strBase64 = "";
		// Try encoding and decoding
		strBase64 = TextEncoder.encode(str1);
		str2 = TextEncoder.decode(strBase64);
		// Test
		assertEquals(str1, str2);
	}
	
	public void testSerialNumber(){
		assertEquals("18386", licenseService.getSerialNumber());
	}
	
	public void testGenerateLicence(){
		String serialNumber = "18386"; 
		String productType = "TRIAL";
		//String productType = "ENTERPRISE";
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String licenseKey = "";
		try {
			Date expiryDate = dateFormat.parse("23-Aug-2010");
			License license = licenseService.generateLicense(serialNumber, productType, expiryDate);
			licenseKey = license.getLicenseKey();
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		assertEquals("g81SPc3Ushz/464tFyJ23LxNSUjl9vSz", licenseKey);
	}
	
	public void testValidateLicense(){
		String key = "g81SPc3Ushz/464tFyJ23LxNSUjl9vSz";
		License license = licenseService.validateLicense(key);
		assertEquals(true, license.isValid());
	}
}

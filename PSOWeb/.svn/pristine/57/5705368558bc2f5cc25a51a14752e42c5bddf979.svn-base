package com.itelasoft.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class TextEncoder {
	
	// Encrypts input text with a static key and encode in base 64	
	public static String encode(String plainText){
		BASE64Encoder base64Encoder = new BASE64Encoder();
		try{
			return base64Encoder.encode(encrypt(plainText));
		}
		catch (Exception exception){
			return "";
		}
	}
	
	// Decode the input string from base 64 and then decrypt in to a string
	public static String decode(String encodedText){
		BASE64Decoder base64Decoder = new BASE64Decoder();
		try{
			return decrypt(base64Decoder.decodeBuffer(encodedText));
		}
		catch(Exception exception){
			return "";
		}
	}
	
	public static byte[] encrypt(String input) throws Exception{
		byte[] keyBytes = new byte[] { 0x23, 0x45, 0x54, 0x78, 0x76, 0x28, 0x43, 0x12, 0x08, 0x54, 0x67, 0x1A, 0x45, 0x2D, 0x4E, 0x2F, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };
		SecretKeySpec key = new SecretKeySpec(keyBytes, "DESede");
		Cipher cipher = Cipher.getInstance("DESede");
	    cipher.init(Cipher.ENCRYPT_MODE, key);
	    byte[] inputBytes = input.getBytes();
	    return cipher.doFinal(inputBytes);
	}
	
	public static String decrypt(byte[] encryptionBytes) throws Exception{
		byte[] keyBytes = new byte[] { 0x23, 0x45, 0x54, 0x78, 0x76, 0x28, 0x43, 0x12, 0x08, 0x54, 0x67, 0x1A, 0x45, 0x2D, 0x4E, 0x2F, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };
		SecretKeySpec key = new SecretKeySpec(keyBytes, "DESede");
		Cipher cipher = Cipher.getInstance("DESede");
	    cipher.init(Cipher.DECRYPT_MODE, key);
	    byte[] recoveredBytes = cipher.doFinal(encryptionBytes);
	    String recovered = new String(recoveredBytes);
	    return recovered;
	  }
	
	public static String digest(String text){
		// Start with a larger start point to inflate complexity
		Long charValue = new Long(16320);
		// Add byte values of each character 
		byte[] buffer = text.getBytes();
		for (byte b: buffer){
			charValue = charValue + b;
		}
		return charValue.toString();
	}

}

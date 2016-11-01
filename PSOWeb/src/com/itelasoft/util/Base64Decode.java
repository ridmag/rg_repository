package com.itelasoft.util;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;


public class Base64Decode {
	public void decode(String path,byte[] bs) throws IOException {
		byte[] decoded = Base64.decodeBase64(bs);
		FileOutputStream fileOutputStream = new FileOutputStream(path);
		fileOutputStream.write(decoded);
		
	}
	public byte[] decode(byte[] bs) throws IOException {
		byte[] decoded = Base64.decodeBase64(bs);
		return decoded;
		
	}
}

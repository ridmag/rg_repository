package com.itelasoft.util.test;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import com.itelasoft.util.Base64Decode;

import junit.framework.TestCase;

public class Base64DecodeTest extends TestCase {

	private ByteArrayOutputStream value = new ByteArrayOutputStream();
	private byte[] buf = new byte[10000];

	public void testDecode() {
		try {
			FileInputStream fileInputStream = new FileInputStream(
					"c:\\temp\\encoded.txt");
			int ret;

			while ((ret = fileInputStream.read(buf)) >= 0) {
				value.write(buf, 0, ret);
			}
			fileInputStream.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Base64Decode base64Decode = new Base64Decode();
		try {
			base64Decode.decode("c:\\temp\\out.png", value.toByteArray());
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
	}

}

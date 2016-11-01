package com.itelasoft.sample;

import junit.framework.TestCase;


public class MailTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSend() {
		new Mail().send();
	}

	public void testReceiveImap() {
		new Mail().receiveImap();
	}

}

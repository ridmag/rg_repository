/**
 * 
 */
package com.itelasoft.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * @author vajira
 * 
 */
public class MySqlBackup {
	
	private static final int BUFFER = 10000;
	public String getData(String host, String port, String user,
			String password, String db, String path, String output) throws Exception {
		Process run = Runtime.getRuntime().exec(
				path+"\\mysqldump --host=" + host + " --port=" + port + " --user="
						+ user + " --password=" + password
						+ " --compact --complete-insert --extended-insert "
						+ "--skip-comments --skip-triggers " + db);
		InputStream in = run.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("mm-hh-dd-MM-yyyy");
		String filename = db+"_"+dateFormat.format(GregorianCalendar.getInstance().getTime());
		BufferedWriter temp = new BufferedWriter(new FileWriter(output+"\\"+ filename));

		int count;
		char[] cbuf = new char[BUFFER];

		while ((count = br.read(cbuf, 0, BUFFER)) != -1)
			temp.write(cbuf, 0, count);

		br.close();
		in.close();

		return filename.toString();
	}
}

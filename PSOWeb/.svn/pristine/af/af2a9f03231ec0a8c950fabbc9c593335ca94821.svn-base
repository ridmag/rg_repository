/**
 * 
 */
package com.itelasoft.xslt;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

/**
 * @author vajira
 * 
 */
public class TransformerUtil {

	public static String transformToPdf(InputStream xsltfile,
			Reader xmlfile,OutputStream out)  {
		FopFactory fopFactory = FopFactory.newInstance();

		FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
		
	 try {
		Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);


			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(
					xsltfile));
			transformer.setParameter("versionParam", "2.0");
			transformer.setParameter("page-margin-top", ".5in");
			transformer.setParameter("page-margin-left", ".5in");
			transformer.setParameter("page-margin-bottom", ".5in");
			transformer.setParameter("page-margin-right", ".5in");
			Source src = new StreamSource(xmlfile);

			// Resulting SAX events (the generated FO) must be piped through to FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			// Start XSLT transformation and FOP processing
			transformer.transform(src, res);
	} catch (FOPException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (TransformerFactoryConfigurationError e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (TransformerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return null;
	}

}

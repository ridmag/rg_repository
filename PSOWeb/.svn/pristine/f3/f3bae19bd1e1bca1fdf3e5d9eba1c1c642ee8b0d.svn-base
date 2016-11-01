package com.itelasoft.sample;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;


public class Mail {

	public void send(){
		  String to = "test@itelasoft.com";
	        String from = "test@itelasoft.com";
	        // SUBSTITUTE YOUR ISP'S MAIL SERVER HERE!!!
	        String host = "smtp.gmail.com";

	        // Create properties, get Session
	        Properties props = new Properties();

	        // If using static Transport.send(),
	        // need to specify which host to send it to
	        props.put("mail.smtp.host", host);
	        // To see what is going on behind the scene
	        //props.put("mail.debug", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        Session session = Session.getInstance(props);

	        try {
	            // Instantiatee a message
	            Message msg = new MimeMessage(session);

	            //Set message attributes
	            msg.setFrom(new InternetAddress(from));
	            InternetAddress[] address = {new InternetAddress(to)};
	            msg.setRecipients(Message.RecipientType.TO, address);
	            msg.setSubject("Test E-Mail through Java");
	            msg.setSentDate(new Date());

	            // Set message content
	            msg.setText("This is a test of sending a " +
	                        "plain text e-mail through Java.\n" +
	                        "Here is line 2.");

	            //Send the message
	            Transport transport = session.getTransport("smtp");
			    transport.connect(host, from, "pass123");
			    transport.sendMessage(msg, msg.getAllRecipients());
			    transport.close();
	        }
	        catch (MessagingException mex) {
	            // Prints all nested (chained) exceptions as well
	            mex.printStackTrace();
	        }

	}
	
	public void receiveImap(){
		   // SUBSTITUTE YOUR ISP's POP3 SERVER HERE!!!
        String host = "imap.gmail.com";
        // SUBSTITUTE YOUR USERNAME AND PASSWORD TO ACCESS E-MAIL HERE!!!
        String user = "test@itelasoft.com";
        String password = "pass123";
        // SUBSTITUTE YOUR SUBJECT SUBSTRING TO SEARCH HERE!!!
        String subjectSubstringToSearch = "Test E-Mail through Java";
        Properties props = new Properties();
        props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    	// don't fallback to normal IMAP connections on failure.
    	props.setProperty("mail.imap.socketFactory.fallback", "false");
    	// use the simap port for imap/ssl connections.
    	props.setProperty("mail.imap.socketFactory.port", "993");

    	
        // Get a session.  Use a blank Properties object.
        Session session = Session.getInstance(props);

        try {

            // Get a Store object
            Store store = session.getStore("imap");
            store.connect(host, user, password);

            // Get "INBOX"
            Folder fldr =  store.getFolder("INBOX");
            fldr.open(Folder.READ_WRITE);
            int count = fldr.getUnreadMessageCount();
            //System.out.println(count  + " New messages");
            //SearchTerm searchTerm = new SubjectTerm("Email");
            //Message[] messages = fldr.search(searchTerm);            
            if(count > 0){
            	Message[] messages = fldr.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            	System.out.println(messages.length  + " New messages\n--------------------------\n");
            	for (Message message : messages) {
                	try{
                		System.out.println("Subject: " + message.getSubject());
                		if(message.getContent() instanceof Multipart){
                			Multipart multipart = (Multipart) message.getContent();
                            for (int i = 0; i < multipart.getCount(); i++) {
                                BodyPart bodyPart = multipart.getBodyPart(i);
                                if(!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                                	continue; // dealing with attachments only
                                } 
                                System.out.println("Attachment: " + bodyPart.getFileName());
                                InputStream is = bodyPart.getInputStream();
                                File f = new File(bodyPart.getFileName());
                                FileOutputStream fos = new FileOutputStream(f);
                                byte[] buf = new byte[4096];
                                int bytesRead;
                                while((bytesRead = is.read(buf))!=-1) {
                                    fos.write(buf, 0, bytesRead); // save file to the Hard Drive
                                }
                                fos.close();
                            }               			
                		}else System.out.println("Text/Plain");
                	}catch (Exception e) {
                		 System.out.println(e);
    				}  
                	System.out.println(" ");
                }
            }else System.out.println("No new messages");
            
            /*for(int i =0; i < messages.length; i++){
            	System.out.println(messages[i].getSubject());            	
            }*/            
            


            // Message numebers start at 1
            /*for(int i = 1; i <= count; i++) {
								// Get  a message by its sequence number
                Message m = fldr.getMessage(i);

                // Get some headers
                Date date = m.getSentDate();
                Address [] from = m.getFrom();
                String subj = m.getSubject();
                String mimeType = m.getContentType();
                System.out.println(date + "\t" + from[0] + "\t" +
                                    subj + "\t" + mimeType);
            }

            // Search for e-mails by some subject substring
            String pattern = subjectSubstringToSearch;
            SubjectTerm st = new SubjectTerm(pattern);
            // Get some message references
            Message [] found = fldr.search(st);

            System.out.println(found.length +
                                " messages matched Subject pattern \"" +
                                pattern + "\"");

            for (int i = 0; i < found.length; i++) {
                Message m = found[i];
                // Get some headers
                Date date = m.getSentDate();
                Address [] from = m.getFrom();
                String subj = m.getSubject();
                String mimeType = m.getContentType();
                System.out.println(date + "\t" + from[0] + "\t" +
                                    subj + "\t" + mimeType);

                Object o = m.getContent();
                if (o instanceof String) {
                    System.out.println("**This is a String Message**");
                    System.out.println((String)o);
                }
                else if (o instanceof Multipart) {
                    System.out.print("**This is a Multipart Message.  ");
                    Multipart mp = (Multipart)o;
                    int count3 = mp.getCount();
                    System.out.println("It has " + count3 +
                        " BodyParts in it**");
                    for (int j = 0; j < count3; j++) {
                        // Part are numbered starting at 0
                        BodyPart b = mp.getBodyPart(j);
                        String mimeType2 = b.getContentType();
                        System.out.println( "BodyPart " + (j + 1) +
                                            " is of MimeType " + mimeType);

                        Object o2 = b.getContent();
                        if (o2 instanceof String) {
                            System.out.println("**This is a String BodyPart**");
                            System.out.println((String)o2);
                        }
                        else if (o2 instanceof Multipart) {
                            System.out.print(
                                "**This BodyPart is a nested Multipart.  ");
                            Multipart mp2 = (Multipart)o2;
                            int count2 = mp2.getCount();
                            System.out.println("It has " + count2 +
                                "further BodyParts in it**");
                        }
                        else if (o2 instanceof InputStream) {
                            System.out.println(
                                "**This is an InputStream BodyPart**");
                        }
                    } //End of for
                }
                else if (o instanceof InputStream) {
                    System.out.println("**This is an InputStream message**");
                    InputStream is = (InputStream)o;
                    // Assumes character content (not binary images)
                    int c;
                    while ((c = is.read()) != -1) {
                        System.out.write(c);
                    }
                }

                // Uncomment to set "delete" flag on the message
                //m.setFlag(Flags.Flag.DELETED,true);

            } //End of for
*/
            // "true" actually deletes flagged messages from folder
            fldr.close(true);
            store.close();

        }
        catch (MessagingException mex) {
            // Prints all nested (chained) exceptions as well
            mex.printStackTrace();
        }
        

	}
}

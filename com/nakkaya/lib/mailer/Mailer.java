package com.nakkaya.lib.mailer;

import com.nakkaya.lib.Defaults;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.util.prefs.Preferences;

public class Mailer{

    private class SMTPAuthenticator extends javax.mail.Authenticator{

	String username;
	String password;

	public SMTPAuthenticator(String user , String pass ){
	    username = user;
	    password = pass;
	}

        public PasswordAuthentication 
	    getPasswordAuthentication(){

            return new PasswordAuthentication(username, password);
        }
    }

    Preferences preferences = Preferences.userRoot();
    Properties props;
    Authenticator authenticator;

    public Mailer(){
	
	String userName = preferences.get
	    ( "mocha.smtp.username", Defaults.mocha_smtp_username );
	String passWord = preferences.get
	    ( "mocha.smtp.password", Defaults.mocha_smtp_password );

	props = new Properties();
	props.put("mail.smtp.host", 
		  preferences.get( "mocha.smtp.server", Defaults.mocha_smtp_server ));

	props.put("mail.smtp.port", 
		  preferences.getInt( "mocha.smtp.port", Defaults.mocha_smtp_port ));

	props.put("mail.smtp.user", userName);

	props.put("mail.smtp.auth", "true");

	bool useSSL = preferences.getBoolean
	    ("mocha.notify.mail.use.SSL",Defaults.mocha_notify_mail_use_SSL);

	props.put("mail.smtp.socketFactory.port",
		  preferences.getInt
		  ( "mocha.smtp.port", Defaults.mocha_smtp_port ));

	if ( useSSL == true ){	    
	    props.put("mail.smtp.starttls.enable","true");

	    props.put("mail.smtp.socketFactory.class", 
		      "javax.net.ssl.SSLSocketFactory");

	    props.put("mail.smtp.socketFactory.fallback", "false");
	}

	authenticator = new SMTPAuthenticator( userName , passWord );
    }

    public Mailer(String host,String userName,String passWord,String port){

	props = new Properties();
	props.put("mail.smtp.host", host);
	props.put("mail.smtp.port", port);
	props.put("mail.smtp.user", userName);
	props.put("mail.smtp.starttls.enable","true");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.socketFactory.port", port);
	props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.socketFactory.fallback", "false");

	authenticator = new SMTPAuthenticator( userName , passWord );
    }

    /**
     * "send" method to send the message.
     */
    public void send( String to, String from , String subject, String body){
	try{
	    Session session = Session.getDefaultInstance(props, authenticator);
	    Message msg = new MimeMessage(session);

	    msg.setFrom(new InternetAddress(from));
	    msg.setRecipients(Message.RecipientType.TO,
			      InternetAddress.parse(to, false));

	    msg.setSubject(subject);
	    msg.setText(body);

	    Transport.send(msg);
	    System.out.println("Message sent OK.");
	}
	catch (Exception ex){
		ex.printStackTrace();
	}
    }

}

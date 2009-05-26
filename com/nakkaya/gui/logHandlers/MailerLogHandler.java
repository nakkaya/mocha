package com.nakkaya.gui.logHandler;

import com.nakkaya.lib.Defaults;
import com.nakkaya.lib.mailer.*;

import java.util.prefs.Preferences;
import java.util.logging.LogRecord;
import java.util.logging.Handler;

//log handler for log panels adds given message to list
public class MailerLogHandler extends Handler {

    Preferences preferences = Preferences.userRoot();

    Mailer mailer;
    String toFrom;

    public MailerLogHandler(Mailer agent ) {
	super();
	mailer = agent;

	toFrom = preferences.get
	    ( "mocha.smtp.email", Defaults.mocha_smtp_email );
    }

    public void publish(LogRecord record) {
	// ensure that this log record should be logged by this Handler
	if (!isLoggable(record))
	    return;
	
	//process log
	String message = record.getMessage();

	//if warning message do not discard after 5 secs. 
	if ( message.matches("Warning.*?changed.*") == true ){

	    //if changed to incomplete message check prefs
	    if ( message.matches(".*?\\(incomplete\\).*?") == true &&
		 preferences.getBoolean
		 ("mocha.suppress.incomplete" ,
		  Defaults.mocha_suppress_incomplete)==true)
		return;
	    
	    //do it
	    mailer.send(toFrom,toFrom,"Mocha: Arp Change", message);

	}

    }

    public void flush() { }
    public void close()  { }

}

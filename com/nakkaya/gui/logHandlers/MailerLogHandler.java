// Copyright 2011 Nurullah Akkaya

// Mocha is free software: you can redistribute it and/or modify it
// under the terms of the GNU General Public License as published by the
// Free Software Foundation, either version 3 of the License, or (at your
// option) any later version.

// Mocha is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
// for more details.

// You should have received a copy of the GNU General Public License
// along with Mocha. If not, see http://www.gnu.org/licenses/.

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
	
	String message = record.getMessage();

	// send new host message
	if ( message.indexOf("New Host") >= 0 && 
	     preferences.getBoolean
	     ("mocha.suppress.newhost" ,
	      Defaults.mocha_suppress_newhost) == false ){
	    
	    mailer.send(toFrom,toFrom,"Mocha: New Host", message);
	}

	//send arp change warning...
	if ( message.matches("Warning.*?changed.*") == true ){

	    //check to see user wants incompletes messages
	    if ( message.matches(".*?\\(incomplete\\).*?") == true &&
		 preferences.getBoolean
		 ("mocha.suppress.incomplete" ,
		  Defaults.mocha_suppress_incomplete)==true)
		return;
	    
	    mailer.send(toFrom,toFrom,"Mocha: Arp Change", message);

	}

    }

    public void flush() { }
    public void close()  { }

}

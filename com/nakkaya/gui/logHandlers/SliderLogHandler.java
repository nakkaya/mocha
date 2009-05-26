package com.nakkaya.gui.logHandler;

import java.util.prefs.Preferences;
import java.util.logging.LogRecord;
import java.util.logging.Handler;

import com.nakkaya.lib.Defaults;

//log handler for log panels adds given message to list
public class SliderLogHandler extends Handler {

    Preferences preferences = Preferences.userRoot();

    public void publish(LogRecord record) {
	// ensure that this log record should be logged by this Handler
	if (!isLoggable(record))
	    return;

	String message = record.getMessage();
	SlideInNotification slider = new SlideInNotification ( message );

	//check to see if new host messages are suppressed
	if ( message.indexOf("New Host") >= 0 && 
	     preferences.getBoolean
	     ("mocha.suppress.newhost" ,
	      Defaults.mocha_suppress_newhost) == true)
	    return;
	

	//if warning message do not discard after 5 secs. 
	if ( message.matches("Warning.*?changed.*") == true ){

	    //if changed to incomplete message check prefs
	    if ( message.matches(".*?\\(incomplete\\).*?") == true &&
		 preferences.getBoolean
		 ("mocha.suppress.incomplete" ,
		  Defaults.mocha_suppress_incomplete)==true)
		return;
	    
	    slider.showAt (0 , 0 );

	} else
	    slider.showAt (0 , 5000 );
    }

    public void flush() { }
    public void close()  { }

}

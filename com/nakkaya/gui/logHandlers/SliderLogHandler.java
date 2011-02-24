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

package com.nakkaya.gui.logHandlers;

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

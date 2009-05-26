package com.nakkaya.gui.logHandler;

import java.util.logging.LogRecord;
import java.util.logging.Handler;

//log handler for log panels adds given message to list
public class ListLogHandler extends Handler {

    //model of the JList which will print the handled log.
    ListLogModel listModel;

    public ListLogHandler( ListLogModel mdl ) {
	super();
	listModel = mdl;
    }

    public void publish(LogRecord record) {
	// ensure that this log record should be logged by this Handler
	if (!isLoggable(record))
	    return;
	
	//process log
	//add to JList in gui
	listModel.addToList( record.getMessage() );

    }

    public void flush() { }
    public void close()  { }

}

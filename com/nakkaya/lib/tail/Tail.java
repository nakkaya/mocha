package com.nakkaya.lib.tail;

// Import the Java classes
import java.util.*;
import java.io.*;
import java.util.logging.Logger;

/**
 * Implements console-based log file tailing, or more specifically, tail following:
 * it is somewhat equivalent to the unix command "tail -f"
 */
public class Tail implements LogFileTailerListener{

    String grepExp = null;
    Logger firewallLogger = Logger.getLogger("FirewallLog");

    /**
     * The log file tailer
     */
    private LogFileTailer tailer;

    /**
     * Creates a new Tail instance to follow the specified file
     */
    public Tail( String filename ){
	tailer = new LogFileTailer( new File( filename ), 1000, false );
	tailer.addLogFileTailerListener( this );
	tailer.start();
    }

    public void grep( String exp ){
	grepExp = exp;
    }

    /**
     * A new line has been added to the tailed log file
     * 
     * @param line   The new line that has been added to the tailed log file
     */
    public void newLogFileLine(String line){
	if (grepExp == null ){
	    firewallLogger.info( line );
	    return;
	}
	if ( line.contains( grepExp ) == true ){
	    firewallLogger.info( line );
	    return;
	}
    }
}

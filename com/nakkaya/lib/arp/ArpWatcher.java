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

package com.nakkaya.lib.arp;

import java.lang.String;
import java.util.logging.Logger;
import java.util.Vector;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.prefs.Preferences;

import com.nakkaya.lib.Defaults;

public class ArpWatcher implements Runnable{

    Preferences preferences = Preferences.userRoot();

    Logger logger = Logger.getLogger("ArpLog");
    Logger SysLogger = Logger.getLogger("SysLog");

    ArpTable arpTable = null;

    public ArpWatcher( ArpTable at ){
	arpTable = at;
    }

    //read current arp table
    private Vector read(){
	try {
	    Vector tmpTable = new Vector();

	    String arpExecutable = preferences.get
		( "mocha.arp.command", Defaults.mocha_arp_command );


	    Process p = Runtime.getRuntime().exec( arpExecutable  + " -a" );

	    BufferedReader stdInput = new BufferedReader
		(new InputStreamReader(p.getInputStream()));

	    String line;
	    Host host = new Host();
	    //create arp table
	    while ((line = stdInput.readLine()) != null){
		//parse ip
		int index = line.indexOf("(");
		if (index != -1 ){
		    String ip = line.substring( index+1,line.indexOf(")",index));
		    //parse mac id
		    index = line.indexOf("at" , index );
		    String mac = line.substring
			( index+3, line.indexOf(" ",index+3));
		    
		    host.ipAddr = ip;
		    host.macId = mac;
		    host.firstSeen = System.currentTimeMillis();
		    tmpTable.add( host );
		    host = new Host();
		}
	    }
	    p.waitFor();
	    return tmpTable;
	}catch (Exception e) {
	    SysLogger.warning( e.toString() );
	    return new Vector();
	}
    }

    public void warnUser( String ip , String oldMac , String newMac ){	    
	    String message = "Warning " + ip + " changed MAC from " 
		+ oldMac + " to " + newMac;
	    logger.warning ( message );
    }

    public void run() {
	while(true){
	    try{		
		Vector systemArpTable = read();

		Iterator it = systemArpTable.iterator();
		while(it.hasNext() ){
		    Host host =  (Host)it.next();
		    if (arpTable.contains(host) == false ){
			//new host add
			arpTable.add( host );
			logger.info( "New Host " + host.toString() );
		    }else{
			//else check its integrity
			if ( host.macId.equals(arpTable.getMAC(host.ipAddr)) == false
			     && 
			     host.macId.matches(".*?\\(incomplete\\).*?") == false ){

			    warnUser
				(host.ipAddr,arpTable.getMAC(host.ipAddr),host.macId);
			    //use new identity keep on
			    arpTable.setMac( host.ipAddr , host.macId );
			}

		    }
			
		}

		Integer sleep = preferences.getInt
		    ("arpWatchInterval" , Defaults.mocha_arp_interval );

		Thread.sleep( sleep *1000 );
	    }catch( Exception e ) { 
		SysLogger.warning( e.toString() );
	    }
	}
    }

}

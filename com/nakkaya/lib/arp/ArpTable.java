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
import java.util.Collections;
import java.util.Observable;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;


//system arp table
public class ArpTable extends Observable{
    Vector<Host> arpTable = new Vector<Host>();

    public ArpTable( ){
    }

    //add to arp table
    public void add( Host host ){
	arpTable.add( host );
	Collections.sort(arpTable, new IpComparator());

	setChanged();
	notifyObservers( );
    }

    //iterate throug arp table return mac address for
    //given ip else return null
    public String getMAC( String ip ){

	for( Host host : arpTable ){
	    if ( host.ipAddr.equals( ip ) == true ){
		return host.macId;
	    }
	}
	
	return null;
    }

    public void setMac(String ip , String newMac ){
	for( Host host : arpTable ){
	    if ( host.ipAddr.equals( ip ) == true ){
		host.macId = newMac;
	    }
	}

	setChanged();
	notifyObservers( );
    }

    //check if given host is in table
    public boolean contains(Host test){
	return arpTable.contains( test );
    }

    //return arp table
    public Vector getTable(){
	return arpTable;
    }

    public int getSize(){
	return arpTable.size();
    }
    
    public Host getRow( int row ){
	Host host = (Host)arpTable.get( row );
	return host;
    }

    //print arp table
    public String toString(){
	StringBuilder table = new StringBuilder();

	for( Host host : arpTable ){
	    table.append( host + "\n");
	}

	return table.toString();
    }

    public void exportToFile(String fileName){
	StringBuilder table = new StringBuilder();

	for( Host host : arpTable ){
	    table.append( host.getIP() + "\t");
	    table.append( host.getMacID() + "\n");
	}

	try{
	    File outFile = new File(fileName);
	    BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));

	    writer.write( table.toString() );
	    writer.close();
	}catch( Exception e ) { 
	    
	}
    }

    public boolean importFromFile(String file){
	try{
	    File outFile = new File(file);
	    BufferedReader reader = new BufferedReader(new FileReader(outFile));

	    String line = null;
	    while ((line=reader.readLine()) != null) {
		String[] pair = line.split(" ");
		
		Host host = new Host();
		host.ipAddr = pair[0];
		host.macId = pair[1];
		host.firstSeen = System.currentTimeMillis();
		
		if ( contains( host ) == false )
		    add( host );		
	    }

	    reader.close();

	    setChanged();
	    notifyObservers( );
	
	    return true;
	}catch( Exception e ) {
	    return false;
	}
    }
}

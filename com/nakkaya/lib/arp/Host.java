package com.nakkaya.lib.arp;

import java.lang.String;

//entry in the arp table
public class Host{
    public String ipAddr = "";
    String macId = "";
    Long firstSeen = new Long(0);

    public String getIP(){
	return ipAddr;
    }

    public String getMacID(){
	return macId;
    }

    public boolean equals(Object obj){
	if(this == obj)
	    return true;
	if((obj == null) || (obj.getClass() != this.getClass()))
	    return false;
	// object must be Test at this point
	Host test = (Host)obj;
	return ipAddr.equals( test.ipAddr );
    }
    
    public String toString(){
	return ipAddr + " at " + macId;
    }
}

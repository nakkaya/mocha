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

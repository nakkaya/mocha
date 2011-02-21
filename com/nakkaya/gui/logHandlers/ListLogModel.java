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

import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import java.util.Vector;
import java.util.prefs.Preferences;

import com.nakkaya.lib.Defaults;

//list of log messages created for log panel
public class ListLogModel extends AbstractListModel {
    
    Preferences preferences = Preferences.userRoot();

    Vector logList = new Vector();

    public int getSize() { return logList.size(); }
    public Object getElementAt(int index) { return logList.get(index); }
    public void addToList(String message){
	Integer logSize = preferences.getInt
	    ( "mocha.log.size", Defaults.mocha_log_size );
	
	//trim list if bigger than max size 
	if ( ( logList.size() >= logSize ) && logSize != 0 ){
	    logList.remove(0);
	}

	logList.add(message); 
	fireContentsChanged(this , logList.size() , logList.size() );
    }
}

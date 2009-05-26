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

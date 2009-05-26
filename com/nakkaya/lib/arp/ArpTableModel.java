package com.nakkaya.lib.arp;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.Observer;
import java.util.Observable;

public class ArpTableModel extends AbstractTableModel implements Observer {
    
    String headers[] = { "IP",
			 "MAC",
			 "First Seen"};
    ArpTable arpTable;

    public ArpTableModel( ArpTable at ) {
	arpTable = at;
	arpTable.addObserver(this);
    }
    
    public int getRowCount() {
	return arpTable.getSize();
    }

    public int getColumnCount() {
	    return 3;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
	try{
	    Host host = arpTable.getRow( rowIndex );

	    if (columnIndex == 0 ){		
		return  host.ipAddr;
	    }else if (columnIndex == 1 ){	    
		return host.macId;
	    }else if ( columnIndex == 2 ){
		return new Date( host.firstSeen ).toString();
	    }else{
		return rowIndex+" "+columnIndex;
	    }

	}catch( Exception e ) { 
	    System.out.println( "row " + rowIndex + " columnIndex " + columnIndex );
	    return 0;
	}
    }    

    public String getColumnName(int column) {
	return headers[column];
    }

    public void update(Observable obj, Object arg){
	fireTableDataChanged();
    }

    public void exportToFile(String fileName){
	arpTable.exportToFile(fileName);
    }

    public void importFromFile(String file){
	arpTable.importFromFile(file);
    }
}

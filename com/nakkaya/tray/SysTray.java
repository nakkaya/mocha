package com.nakkaya.tray;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.net.*;
import java.util.logging.Logger;
import com.nakkaya.gui.ApplicationWindow;

public class SysTray {
    
    static Logger sysLogger = Logger.getLogger("SysLog");

    ApplicationWindow infoWindow;

    public SysTray(  ApplicationWindow iw ) {
	infoWindow = iw;

        if (SystemTray.isSupported()) {
	    SystemTray tray = SystemTray.getSystemTray();
	    URL imageURL = this.getClass().getClassLoader()
		.getResource( "icons/mocha25.png" );
	    Image image = 
		Toolkit.getDefaultToolkit().getImage(imageURL);
	    PopupMenu popup = new PopupMenu();

	    MenuItem startItem = new MenuItem("Show");
	    startItem.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent event){
			infoWindow.setVisible(true);
			infoWindow.setState ( Frame.NORMAL );
			infoWindow.toFront();
		    }});
	    popup.add(startItem);

	    MenuItem exitItem = new MenuItem("Exit");
	    exitItem.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent event){
			System.exit(0);
		    }});
	    popup.add(exitItem);
	    
	    TrayIcon trayIcon = new TrayIcon(image, "Mocha", popup);
	    // Action listener for left click.
	    trayIcon.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			infoWindow.setVisible(true);
			infoWindow.setState ( Frame.NORMAL );
			infoWindow.toFront();
		    }
		});
	    
	    try {
		tray.add(trayIcon);
	    } catch (AWTException e) {
		sysLogger.warning("Can't add to tray");
		infoWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    }
        } else {
	    sysLogger.warning("Tray unavailable");
	    infoWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
}

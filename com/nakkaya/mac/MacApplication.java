package com.nakkaya.mac;

import javax.swing.*;
import com.apple.eawt.*;
import com.apple.mrj.*;
import java.io.*;
import java.util.logging.Logger;

import com.nakkaya.gui.*;

public class MacApplication extends Application {

    public MacApplication( final JFrame infoWindow ) {
	
	addApplicationListener( new ApplicationAdapter(){
		public void handleReOpenApplication(ApplicationEvent event) {
		    infoWindow.setVisible(true);
		}
		public void handleQuit( ApplicationEvent event ) {
		    System.exit(0);
		}

		public void handleAbout(ApplicationEvent event){
		    event.setHandled(true);
		    AboutWindow aboutWindow = new AboutWindow();
		    aboutWindow.setLocationRelativeTo(infoWindow);
 		    aboutWindow.setVisible(true);
		}

	    });
    }
}

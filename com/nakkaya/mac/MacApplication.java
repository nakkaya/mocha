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

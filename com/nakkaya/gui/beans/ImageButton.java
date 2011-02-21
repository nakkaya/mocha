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

package com.nakkaya.gui.beans;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Insets;
import java.net.URL;

public class ImageButton extends JButton {

    String iconPath = new String();
    String iconPressedPath = new String();

    public ImageButton(){
	super();
	setBorderPainted(false);
	setMargin(new Insets(0, 0, 0, 0));
	setFocusPainted(false);
	setContentAreaFilled(false);
	setIconTextGap(0);
    }

    public void setIconPath(String path){
	URL imageURL = this.getClass().getClassLoader()
	    .getResource( path );
	ImageIcon icon = new ImageIcon( imageURL );
	setIcon( icon );

	iconPath = path;
    }

    public String getIconPath(){
	return iconPath;
    }

    public void setIconPressedPath(String path){
	URL imageURL = this.getClass().getClassLoader()
	    .getResource( path );
	ImageIcon icon = new ImageIcon( imageURL );
	setPressedIcon( icon );

	iconPressedPath = path;
    }

    public String getIconPressedPath(){
	return iconPressedPath;
    }
}

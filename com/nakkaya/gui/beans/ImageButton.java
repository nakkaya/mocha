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

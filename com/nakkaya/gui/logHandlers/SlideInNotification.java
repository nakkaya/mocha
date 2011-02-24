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

package com.nakkaya.gui.logHandlers;

import javax.swing.*;
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.awt.image.*; 
import java.net.URL;
import java.util.logging.Logger;

public class SlideInNotification extends Object { 
    Logger SysLogger = Logger.getLogger("SysLog");

    public class AnimatingSheet extends JPanel { 
	Dimension animatingSize = new Dimension (0, 1); 
	JComponent source; 
	BufferedImage offscreenImage; 
	public AnimatingSheet () { 
	    super(); 
	    setOpaque(true); 	    
	} 
	public void setSource (JComponent source) { 
	    this.source = source; 
	    animatingSize.width = source.getWidth(); 
	    makeOffscreenImage(source); 
	}
	public void setAnimatingHeight (int height) { 
	    animatingSize.height = height; 
	    setSize (animatingSize); 
	} 
	private void makeOffscreenImage(JComponent source) { 
	    GraphicsEnvironment ge = 
		GraphicsEnvironment.getLocalGraphicsEnvironment(); 
	    GraphicsConfiguration gfxConfig = 
		ge.getDefaultScreenDevice().getDefaultConfiguration(); 
	    offscreenImage = 
		gfxConfig.createCompatibleImage(source.getWidth(), 
						source.getHeight()); 
	    Graphics2D offscreenGraphics = 
		(Graphics2D) offscreenImage.getGraphics(); 
	    // windows workaround 
	    offscreenGraphics.setColor (source.getBackground()); 
	    offscreenGraphics.fillRect (0, 0, 
					source.getWidth(), source.getHeight()); 
	    // paint from source to offscreen buffer 
	    source.paint (offscreenGraphics); 
	} 
	public Dimension getPreferredSize() { return animatingSize; } 
	public Dimension getMinimumSize() { return animatingSize; } 
	public Dimension getMaximumSize() { return animatingSize; } 
	public void update (Graphics g) { 
	    // override to eliminate flicker from 
	    // unnecessary clear 
	    paint (g); 
	} 
	public void paint (Graphics g) { 
	    // get the top-most n pixels of source and 
	    // paint them into g, where n is height 
	    // (different from sheet example, which used bottom-most) 
	    BufferedImage fragment = 
		offscreenImage.getSubimage (0, 
					    0, 
					    source.getWidth(), 
					    animatingSize.height); 
	    g.drawImage (fragment, 0, 0, this); 
	} 
    }

    public class NotificationBox extends JPanel{
	public NotificationBox( String message ){
	    super(  new FlowLayout(FlowLayout.LEFT , 0 , 0 ) );
	    
	    Color bg = new Color(0x4D4D4D);
	    setBackground( bg );

	    //warning sign
	    URL imageURL = this.getClass().getClassLoader()
		.getResource( "icons/warning.png" );
	    ImageIcon icon = new ImageIcon( imageURL );
	    JLabel warning = new JLabel( icon );
	    add( warning );

	    //middle padding
	    add(Box.createRigidArea( new Dimension ( 10 , 0)));

	    //message
	    message = processMessage(message);
	    JLabel label = new JLabel ( message ,
				       SwingConstants.LEFT);
	    label.setForeground(Color.white);
	    setPreferredSize(new Dimension( 270 , 60 ));

	    add( label );
	}

	//create a two line 70 char text from string
	public String wrapText( String message ){
	    try{
		StringBuilder buffer = new StringBuilder();
		buffer.append("<html><FONT COLOR=\"FFFF00\">Info</font><br>");

		//number of charecter that fit in to the window.
		int lineLength = 35;

		//if fits as is just return. 
		if (message.length() <= lineLength ){
		    buffer.append( message );
		    return buffer.toString();
		}

		//grab first line
		String tmpLine =  message.substring( 0 , lineLength );

		//locate last space in first line
		int indexLastSpace = tmpLine.lastIndexOf( " " );
		//get line up to space
		tmpLine =  message.substring( 0 , indexLastSpace );
		buffer.append( tmpLine );
		
		buffer.append("<br>");

		//grab second line
		int endIndex = indexLastSpace + lineLength ;
		if ( indexLastSpace+lineLength > message.length() )
		    endIndex = message.length();
		
		tmpLine = message.substring
		    ( indexLastSpace , endIndex );

		//locate last space in second line
		indexLastSpace = tmpLine.lastIndexOf( " " );
		if ( indexLastSpace == 0 )
		    indexLastSpace = tmpLine.length();


		//cut line
		buffer.append( tmpLine.substring( 0 , indexLastSpace) );
		
		return buffer.toString();
	    }catch( Exception e ) { 
		SysLogger.info( e.toString() );
		return new String("");
	    }
	}

	//add html formatting for label
	public String processMessage( String message ){

	    //process new host
	    if ( message.matches(".*?New Host.*?at.*") == true ){
		message = message.replace
		    ("New Host" ,
		     "<html><FONT COLOR=\"FFFF00\">New Host</font><br><br>");
		return message;
	    }

	    if ( message.matches("Warning.*?changed MAC from.*") == true ){
		message = message.replace
		    ("Warning" ,"<html><FONT COLOR=\"FFFF00\">Warning</font><br>");
		message = message.replace
		    ("changed MAC from" ,"changed MAC from<br>");
		return message;
	    }
	    
	    message = wrapText( message );
	    return message;
	}

    }

    protected static final int ANIMATION_TIME = 500; 
    protected static final float ANIMATION_TIME_F = 
	(float) ANIMATION_TIME; 
    protected static final int ANIMATION_DELAY = 50; 
    JWindow window; 
    JComponent contents; 
    AnimatingSheet animatingSheet; 
    Rectangle desktopBounds; 
    Dimension tempWindowSize; 
    Timer animationTimer; 
    int showX, startY; 
    long animationStart; 

    Timer hideTimer;

    public SlideInNotification () { 
	initDesktopBounds(); 
    } 
    public SlideInNotification (String message) { 
	this();
	NotificationBox box = new NotificationBox( message );
	setContents ( box );

	window.setAlwaysOnTop(true);
	window.addMouseListener(new MouseAdapter(){
		public void mousePressed(MouseEvent e){
		    window.setVisible( false );
		    window.dispose();
		}
	    });

    }
    protected void initDesktopBounds() { 
	GraphicsEnvironment env = 
	    GraphicsEnvironment.getLocalGraphicsEnvironment(); 
	desktopBounds = env.getMaximumWindowBounds(); 
	//System.out.println ("max window bounds = " + desktopBounds);
    } 
    public void setContents (JComponent contents) { 
	this.contents = contents; 
	JWindow tempWindow = new JWindow(); 
	tempWindow.getContentPane().add (contents); 
	tempWindow.pack(); 
	tempWindowSize = tempWindow.getSize(); 
	tempWindow.getContentPane().removeAll(); 
	window = new JWindow(); 
	animatingSheet = new AnimatingSheet (); 
	animatingSheet.setSource (contents); 
	window.getContentPane().add (animatingSheet); 
    } 
    public void showAt (int x , int hideAfter ) { 
	// create a window with an animating sheet 
	// copy over its contents from the temp window 
	// animate it 
	// when done, remove animating sheet and add real contents 
	showX = x; 
	startY = desktopBounds.y + desktopBounds.height; 

	ActionListener animationLogic = new ActionListener() { 
		public void actionPerformed(ActionEvent e) { 
		    long elapsed = 
			System.currentTimeMillis() - animationStart;
		 
		    if (elapsed > ANIMATION_TIME) { 
			// put real contents in window and show 
			window.getContentPane().removeAll(); 
			window.getContentPane().add (contents); 
			window.pack(); 
			window.setLocation (showX, 
					    startY - window.getSize().height); 
			window.setVisible(true); 
			window.repaint(); 
			animationTimer.stop(); 
			animationTimer = null; 
		    } else { 
			// calculate % done 
			float progress = 
			    (float) elapsed / ANIMATION_TIME_F; 
			// get height to show 
			int animatingHeight = 
			    (int) (progress * tempWindowSize.getHeight()); 
			animatingHeight = Math.max (animatingHeight, 1); 
			animatingSheet.setAnimatingHeight (animatingHeight); 
			window.pack(); 
			window.setLocation (showX, 
					    startY - window.getHeight()); 
			window.setVisible(true); 
			window.repaint(); 
		    } 
		} 
	    }; 
	animationTimer = 
	    new Timer (ANIMATION_DELAY, animationLogic); 
	animationStart = System.currentTimeMillis(); 
	animationTimer.start(); 

	if ( hideAfter != 0 ){
	    ActionListener hideNotifier = new ActionListener() { 
		    public void actionPerformed(ActionEvent e) {
			window.setVisible( false );
			window.dispose();
			hideTimer.stop();
			hideTimer = null;
		    }};

	    hideTimer =  new Timer ( hideAfter , hideNotifier);
	    hideTimer.start(); 	    
	}

    } 
    // AnimatingSheet inner class listed below 
}

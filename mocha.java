import java.lang.String;
import java.util.logging.Logger;
import java.util.Vector;
import javax.swing.JFrame;
import java.lang.reflect.Constructor;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import java.util.prefs.Preferences;

import com.nakkaya.lib.arp.*;
import com.nakkaya.lib.tail.*;
import com.nakkaya.lib.network.*;
import com.nakkaya.lib.mailer.*;
import com.nakkaya.gui.*;
import com.nakkaya.lib.Defaults;

import com.nakkaya.gui.logHandler.*;

class mocha {

    static Preferences preferences = Preferences.userRoot();

    static Logger arpLogger = Logger.getLogger("ArpLog");
    static Logger firewallLogger = Logger.getLogger("FirewallLog");
    static Logger sysLogger = Logger.getLogger("SysLog");

    public static void detectOS(){
	if(System.getProperty("mrj.version") != null){

	    preferences.put( "mocha.operatingSystem", "OSX" );

	}else if (System.getProperty("os.name").equals("Linux") == true){

	    preferences.put( "mocha.operatingSystem", "Linux" );

	    //look and feel
	    try{
		UIManager.setLookAndFeel
		    ("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
	    }catch( Exception e ) { 
		sysLogger.warning(e.toString());
	    }
	}
    }


    static void setupFirewallTailer(){
	
	String tailFile = preferences.get
	    ("mocha.firewall.log" , Defaults.mocha_firewall_log );

	if ( tailFile.length() > 0 ){
	    Tail tailFirewall = new Tail( tailFile );
	    return;
	}

	if (preferences.get("mocha.operatingSystem" , 
			    Defaults.mocha_operatingSystem ).equals( "OSX" ) == true ){
	    Tail tailFirewall = new Tail("/var/log/appfirewall.log");
	}

	if (preferences.get("mocha.operatingSystem" , 
			    Defaults.mocha_operatingSystem).equals( "Linux" )==true ){
	    Tail tailFirewall = new Tail("/var/log/messages");
	    tailFirewall.grep("IPTABLES: ");
	}
	
    }

    static void setupMacApplication(ApplicationWindow applicationWindow){
	try{
	    Class klass = Class.forName("com.nakkaya.mac.MacApplication");
	    Class[] paramTypes = { JFrame.class };
	    Constructor cons = klass.getConstructor(paramTypes);
	    
	    Object[] args = { applicationWindow };		
	    Object theObject = cons.newInstance(args);
	    applicationWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}catch( Exception e ) {
	    sysLogger.warning( e.toString() );
	}
    }

    static void setupSysTray(ApplicationWindow applicationWindow){
	try{
	    //set to exit on unavaible tray
	    applicationWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	    Class klass = Class.forName("com.nakkaya.tray.SysTray");
	    Class[] paramTypes = { ApplicationWindow.class };
	    Constructor cons = klass.getConstructor(paramTypes);
	    
	    Object[] args = { applicationWindow };		
	    Object theObject = cons.newInstance(args);
	}catch( Exception e ) {
	    sysLogger.warning( e.toString() );
	}
    }


    public static void main(String[] argsCommand) {
	try{
	    arpLogger.setLevel(Level.ALL);
	    arpLogger.setUseParentHandlers(false);

	    firewallLogger.setLevel(Level.ALL);
	    firewallLogger.setUseParentHandlers(false);

	    detectOS();

	    ArpTable arpTable = new ArpTable();
	    //start arp watcher thread
	    ArpWatcher arpWatcher = new ArpWatcher( arpTable );


	    NetworkWatcher network = new NetworkWatcher();


	    ArpTableModel arpTableModel = new ArpTableModel( arpTable );

	    ApplicationWindow applicationWindow 
	    	= new ApplicationWindow( arpTableModel , network );

 	    applicationWindow.setLocationRelativeTo( null );
 	    applicationWindow.setVisible( true );

	    setupFirewallTailer();

	    //load OS specific features.
	    if (preferences.get("mocha.operatingSystem" ,Defaults.mocha_operatingSystem ).equals( "OSX" ) == true ){
	    	setupMacApplication(applicationWindow);
	    }

	    if (preferences.get("mocha.operatingSystem" ,Defaults.mocha_operatingSystem).equals( "Linux" ) == true ){
	    	setupSysTray(applicationWindow);
	    }

	    Thread thread = new Thread( arpWatcher , "ArpWathcer");
	    thread.setName("ArpWatcher");
	    thread.start();
	    
	}catch( Exception e ) { 
	    sysLogger.warning( e.toString() );
	}
    }
}

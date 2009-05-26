package com.nakkaya.lib.network;

import java.util.StringTokenizer;
import java.lang.Process;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.net.NetworkInterface;
import java.net.InetAddress;
import java.net.URLConnection;
import java.net.URL;
import java.util.prefs.Preferences;
import java.lang.Thread;
import java.util.logging.Logger;
import java.util.Observable;

import com.nakkaya.lib.Defaults;

public class NetworkWatcher extends Observable{

    Logger logger = Logger.getLogger("SysLog");
    Preferences preferences = Preferences.userRoot();    
    
    public String gateway = " - - - ";
    public String adapter = " - - - ";
    public String localIP = " - - - ";
    public String wanIP = " - - - ";

    public NetworkWatcher(){
	getNetworkInfo();
    }

    public void getNetworkInfo(){
	new Thread( new Runnable() {
		public void run() {
		    while(true){
			try{

			    //detects os needed by findIP
			    findGateway();
			    findIP();
			    findWanIP();

			    setChanged();
			    notifyObservers( );
			    
			    Thread.sleep(10000);
			}catch (Exception e) {
			    logger.warning( e.toString() );
			}
		    }
		    
		}}).start();
    }

    public void findWanIP(){
	try{

	    URL checkip = new URL("http://checkip.dyndns.org/");
	    URLConnection yc = checkip.openConnection();
	    yc.setConnectTimeout( 2000 );
	    yc.setReadTimeout( 2000 );
	    BufferedReader in = new BufferedReader
		( new InputStreamReader( yc.getInputStream()));

	    String inputLine;
	    String content = new String();
	    
	    while ((inputLine = in.readLine()) != null) 
		content = content + inputLine;

	    wanIP = content.substring
		(content.indexOf(": ")+2 , content.indexOf("</body>"));
	    in.close();
	    
	}catch( Exception e ) {
	    wanIP = " - - - ";
	    logger.warning( e.toString() );
	}
    }

    public void findIP(){
	try{

	    NetworkInterface nif = NetworkInterface.getByName( adapter );
	    Enumeration nifAddresses = nif.getInetAddresses();
	    InetAddress inet = (InetAddress)nifAddresses.nextElement();
	    
	    if ( preferences.get
		 ("mocha.operatingSystem",
		  Defaults.mocha_operatingSystem ).equals( "Linux" ) == true)
		inet = (InetAddress)nifAddresses.nextElement();

	    localIP = inet.getHostAddress();

	}catch( Exception e ) { 
	    logger.warning( e.toString() );
	    localIP = new String();
	}
    }

    public void findGateway(){
	try{
	    Process result = Runtime.getRuntime().exec("netstat -rn");
	    
	    BufferedReader output = new BufferedReader
		(new InputStreamReader(result.getInputStream()));
	    
	    String line = output.readLine();
	    while(line != null){
		//get default route depending on the os.
		if ( line.startsWith("default") == true )
		    break;		

		if ( line.startsWith("0.0.0.0") == true )
		    break;

		line = output.readLine();
	    }


 	    StringTokenizer st = new StringTokenizer( line );
 	    st.nextToken();
 	    gateway = st.nextToken();
	    
	    //skip enough tokens to grap adapter for local IP.
	    if ( preferences.get
		 ("mocha.operatingSystem",
		  Defaults.mocha_operatingSystem ).equals( "OSX" ) == true){
		st.nextToken();
		st.nextToken();
		st.nextToken();
	    }

	    if ( preferences.get
		 ("mocha.operatingSystem",
		  Defaults.mocha_operatingSystem ).equals( "Linux" )== true){
		st.nextToken();
		st.nextToken();
		st.nextToken();
		st.nextToken();
		st.nextToken();
	    }

	    adapter = st.nextToken();

	}catch( Exception e ) { 
	    logger.warning( e.toString() );
	    gateway = new String();
	    adapter = new String();
	}
    }
}

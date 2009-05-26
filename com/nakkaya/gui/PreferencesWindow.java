package com.nakkaya.gui;

import java.awt.Container;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
/*
 * Created by JFormDesigner on Wed May 20 19:11:55 EEST 2009
 */

import java.awt.Toolkit;
import java.net.URL;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;
import com.nakkaya.lib.arp.*;
import com.nakkaya.lib.Defaults;

/**
 * @author Nurullah Akkaya
 */
public class PreferencesWindow extends JFrame {

    Preferences preferences = Preferences.userRoot();

    public PreferencesWindow() {

	URL imageURL = 
	    this.getClass().getClassLoader().getResource( "icons/mocha25.png" );
	Image image = Toolkit.getDefaultToolkit().getImage(imageURL);
	setIconImage( image );

	initComponents();

	addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent e){
		    // your stuf here
		    savePreferences();
		}
	    });

    }

    public void savePreferences(){

	int arpWatchInterval = 5;
	try{
	    arpWatchInterval = Integer.parseInt( arpRefreshIntervalField.getText() );
	}catch( Exception ex ) { 
	    arpWatchInterval = 5;
	}
	preferences.putInt( "mocha.arp.interval", arpWatchInterval );

	preferences.putBoolean( "mocha.notify.firewall", 
				notifyFirewallCheckBox.isSelected());

	preferences.put( "mocha.arp.command", arpCommandField.getText() );

	preferences.put( "mocha.firewall.log", logFileField.getText() );
	
	int maxLogs = Defaults.mocha_log_size;
	try{
	    maxLogs = Integer.parseInt( numOfLogMessagesToShow.getText() );
	}catch( Exception ex ) { 
	    maxLogs = 5;
	}
	preferences.putInt( "mocha.log.size", maxLogs );

	preferences.putBoolean( "mocha.suppress.incomplete", 
				suppressIncompleteWarningCheckBox.isSelected() );

	preferences.putBoolean( "mocha.suppress.newhost", 
				suppressNewHostMessagesCheckBox.isSelected() );

	preferences.putBoolean( "mocha.notify.mail", 
				sendMailWarningCheckBox.isSelected() );

	preferences.put( "mocha.smtp.server", serverNameField.getText() );

	int serverPort = Defaults.mocha_smtp_port;
	try{
	    serverPort = Integer.parseInt( serverPortField.getText() );
	}catch( Exception ex ) { 
	    serverPort = Defaults.mocha_smtp_port;
	}
	preferences.putInt( "mocha.smtp.port", serverPort );

	preferences.put( "mocha.smtp.username", userNameField.getText() );

	preferences.put( "mocha.smtp.password", passWordField.getText() );

	preferences.put( "mocha.smtp.email", toFromField.getText() );

    }

    public void loadPreferences(){
	Integer tmpValue = preferences.getInt( "mocha.arp.interval", Defaults.mocha_arp_interval );
	arpRefreshIntervalField.setText( tmpValue.toString() );

	boolean bool = preferences.getBoolean
	    ("mocha.notify.firewall" , Defaults.mocha_notify_firewall);
	notifyFirewallCheckBox.setSelected( bool );

	String tmpString = preferences.get( "mocha.arp.command", Defaults.mocha_arp_command );
	arpCommandField.setText( tmpString );

	tmpString = preferences.get( "mocha.firewall.log", Defaults.mocha_firewall_log );
	logFileField.setText( tmpString );

	tmpValue = preferences.getInt
	    ( "mocha.log.size", Defaults.mocha_log_size );
	numOfLogMessagesToShow.setText( tmpValue.toString() );

	bool = preferences.getBoolean
	    ("mocha.suppress.incomplete" ,Defaults.mocha_suppress_incomplete);
	suppressIncompleteWarningCheckBox.setSelected( bool );

	bool = preferences.getBoolean
	    ("mocha.suppress.newhost",Defaults.mocha_suppress_newhost);
	suppressNewHostMessagesCheckBox.setSelected(bool);

	bool = preferences.getBoolean
	    ("mocha.notify.mail",Defaults.mocha_notify_mail);
	sendMailWarningCheckBox.setSelected(bool);

	tmpString = preferences.get( "mocha.smtp.server", Defaults.mocha_smtp_server );
	serverNameField.setText( tmpString );

	tmpValue = preferences.getInt
	    ( "mocha.smtp.port", Defaults.mocha_smtp_port );
	serverPortField.setText( tmpValue.toString() );

	tmpString = preferences.get( "mocha.smtp.username", Defaults.mocha_smtp_username );
	userNameField.setText( tmpString );

	tmpString = preferences.get( "mocha.smtp.password", Defaults.mocha_smtp_password );
	passWordField.setText( tmpString );

	tmpString = preferences.get( "mocha.smtp.email", Defaults.mocha_smtp_email );
	toFromField.setText( tmpString );
	
    }

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel8 = new JPanel();
		label9 = new JLabel();
		arpRefreshIntervalField = new JTextField();
		label16 = new JLabel();
		arpCommandField = new JTextField();
		label10 = new JLabel();
		label17 = new JLabel();
		logFileField = new JTextField();
		label12 = new JLabel();
		numOfLogMessagesToShow = new JTextField();
		label13 = new JLabel();
		panel7 = new JPanel();
		suppressIncompleteWarningCheckBox = new JCheckBox();
		suppressNewHostMessagesCheckBox = new JCheckBox();
		notifyFirewallCheckBox = new JCheckBox();
		panel1 = new JPanel();
		sendMailWarningCheckBox = new JCheckBox();
		checkBox2 = new JCheckBox();
		label1 = new JLabel();
		serverNameField = new JTextField();
		label3 = new JLabel();
		userNameField = new JTextField();
		label4 = new JLabel();
		passWordField = new JPasswordField();
		label2 = new JLabel();
		serverPortField = new JTextField();
		label5 = new JLabel();
		toFromField = new JTextField();

		//======== this ========
		setTitle("Settings");
		setResizable(false);
		Container contentPane = getContentPane();

		//======== panel8 ========
		{
			panel8.setBorder(new TitledBorder("General"));

			//---- label9 ----
			label9.setText("Arp Table Refresh Interval:");

			//---- label16 ----
			label16.setText("Arp Command:");

			//---- label10 ----
			label10.setText("seconds");

			//---- label17 ----
			label17.setText("Firewall Log:");

			//---- label12 ----
			label12.setText("Show Last");

			//---- label13 ----
			label13.setText("Log Messages");

			GroupLayout panel8Layout = new GroupLayout(panel8);
			panel8.setLayout(panel8Layout);
			panel8Layout.setHorizontalGroup(
				panel8Layout.createParallelGroup()
					.add(panel8Layout.createSequentialGroup()
						.addContainerGap()
						.add(panel8Layout.createParallelGroup()
							.add(label9)
							.add(label16)
							.add(label17)
							.add(label12))
						.addPreferredGap(LayoutStyle.RELATED)
						.add(panel8Layout.createParallelGroup()
							.add(panel8Layout.createSequentialGroup()
								.add(numOfLogMessagesToShow, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
								.add(18, 18, 18)
								.add(label13))
							.add(panel8Layout.createSequentialGroup()
								.add(arpRefreshIntervalField, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.RELATED)
								.add(label10))
							.add(logFileField, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
							.add(arpCommandField, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(26, Short.MAX_VALUE))
			);
			panel8Layout.setVerticalGroup(
				panel8Layout.createParallelGroup()
					.add(panel8Layout.createSequentialGroup()
						.add(5, 5, 5)
						.add(panel8Layout.createParallelGroup(GroupLayout.BASELINE)
							.add(label9)
							.add(arpRefreshIntervalField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.add(label10))
						.add(18, 18, 18)
						.add(panel8Layout.createParallelGroup(GroupLayout.BASELINE)
							.add(label16)
							.add(arpCommandField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.add(18, 18, 18)
						.add(panel8Layout.createParallelGroup(GroupLayout.BASELINE)
							.add(label17)
							.add(logFileField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.add(18, 18, 18)
						.add(panel8Layout.createParallelGroup(GroupLayout.BASELINE)
							.add(label12)
							.add(numOfLogMessagesToShow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.add(label13))
						.addContainerGap(31, Short.MAX_VALUE))
			);
		}

		//======== panel7 ========
		{
			panel7.setBorder(new TitledBorder("Notification"));

			//---- suppressIncompleteWarningCheckBox ----
			suppressIncompleteWarningCheckBox.setText("Suppress Incomplete Warnings");

			//---- suppressNewHostMessagesCheckBox ----
			suppressNewHostMessagesCheckBox.setText("Suppress New Host Messages");

			//---- notifyFirewallCheckBox ----
			notifyFirewallCheckBox.setText("Notify Firewall Activity");

			GroupLayout panel7Layout = new GroupLayout(panel7);
			panel7.setLayout(panel7Layout);
			panel7Layout.setHorizontalGroup(
				panel7Layout.createParallelGroup()
					.add(panel7Layout.createSequentialGroup()
						.addContainerGap()
						.add(panel7Layout.createParallelGroup()
							.add(notifyFirewallCheckBox)
							.add(suppressIncompleteWarningCheckBox)
							.add(suppressNewHostMessagesCheckBox))
						.addContainerGap(160, Short.MAX_VALUE))
			);
			panel7Layout.setVerticalGroup(
				panel7Layout.createParallelGroup()
					.add(panel7Layout.createSequentialGroup()
						.add(5, 5, 5)
						.add(notifyFirewallCheckBox)
						.addPreferredGap(LayoutStyle.UNRELATED)
						.add(suppressIncompleteWarningCheckBox)
						.addPreferredGap(LayoutStyle.UNRELATED)
						.add(suppressNewHostMessagesCheckBox)
						.addContainerGap())
			);
		}

		//======== panel1 ========
		{
			panel1.setBorder(new TitledBorder("SMTP Settings"));

			//---- sendMailWarningCheckBox ----
			sendMailWarningCheckBox.setText("Send Warnings via E-Mail");

			//---- checkBox2 ----
			checkBox2.setText("Use SSL");
			checkBox2.setSelected(true);
			checkBox2.setEnabled(false);

			//---- label1 ----
			label1.setText("Server:");

			//---- label3 ----
			label3.setText("Username:");

			//---- label4 ----
			label4.setText("Password:");

			//---- label2 ----
			label2.setText("Port:");

			//---- label5 ----
			label5.setText("To/From:");

			GroupLayout panel1Layout = new GroupLayout(panel1);
			panel1.setLayout(panel1Layout);
			panel1Layout.setHorizontalGroup(
				panel1Layout.createParallelGroup()
					.add(panel1Layout.createSequentialGroup()
						.addContainerGap()
						.add(panel1Layout.createParallelGroup()
							.add(sendMailWarningCheckBox)
							.add(checkBox2)
							.add(panel1Layout.createSequentialGroup()
								.add(panel1Layout.createParallelGroup()
									.add(label3)
									.add(label1))
								.addPreferredGap(LayoutStyle.RELATED)
								.add(panel1Layout.createParallelGroup()
									.add(panel1Layout.createParallelGroup(GroupLayout.TRAILING, false)
										.add(GroupLayout.LEADING, passWordField)
										.add(GroupLayout.LEADING, userNameField, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
									.add(GroupLayout.TRAILING, panel1Layout.createSequentialGroup()
										.add(panel1Layout.createParallelGroup(GroupLayout.TRAILING)
											.add(GroupLayout.LEADING, toFromField, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
											.add(serverNameField, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
										.addPreferredGap(LayoutStyle.RELATED)
										.add(label2)
										.addPreferredGap(LayoutStyle.RELATED)
										.add(serverPortField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.add(10, 10, 10))))
							.add(label4)
							.add(label5))
						.addContainerGap())
			);
			panel1Layout.setVerticalGroup(
				panel1Layout.createParallelGroup()
					.add(panel1Layout.createSequentialGroup()
						.add(sendMailWarningCheckBox)
						.addPreferredGap(LayoutStyle.UNRELATED)
						.add(checkBox2)
						.addPreferredGap(LayoutStyle.UNRELATED)
						.add(panel1Layout.createParallelGroup(GroupLayout.BASELINE)
							.add(label1)
							.add(serverNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.add(label2)
							.add(serverPortField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.RELATED)
						.add(panel1Layout.createParallelGroup(GroupLayout.BASELINE)
							.add(label3)
							.add(userNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.UNRELATED)
						.add(panel1Layout.createParallelGroup(GroupLayout.BASELINE)
							.add(label4)
							.add(passWordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.add(18, 18, 18)
						.add(panel1Layout.createParallelGroup(GroupLayout.BASELINE)
							.add(label5)
							.add(toFromField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(31, Short.MAX_VALUE))
			);
		}

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.add(GroupLayout.TRAILING, contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.add(contentPaneLayout.createParallelGroup(GroupLayout.TRAILING)
						.add(GroupLayout.LEADING, panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.add(GroupLayout.LEADING, panel8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.add(GroupLayout.LEADING, panel7, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.add(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.add(panel8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.RELATED)
					.add(panel7, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
					.add(18, 18, 18)
					.add(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel8;
	private JLabel label9;
	private JTextField arpRefreshIntervalField;
	private JLabel label16;
	private JTextField arpCommandField;
	private JLabel label10;
	private JLabel label17;
	private JTextField logFileField;
	private JLabel label12;
	private JTextField numOfLogMessagesToShow;
	private JLabel label13;
	private JPanel panel7;
	private JCheckBox suppressIncompleteWarningCheckBox;
	private JCheckBox suppressNewHostMessagesCheckBox;
	private JCheckBox notifyFirewallCheckBox;
	private JPanel panel1;
	private JCheckBox sendMailWarningCheckBox;
	private JCheckBox checkBox2;
	private JLabel label1;
	private JTextField serverNameField;
	private JLabel label3;
	private JTextField userNameField;
	private JLabel label4;
	private JPasswordField passWordField;
	private JLabel label2;
	private JTextField serverPortField;
	private JLabel label5;
	private JTextField toFromField;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

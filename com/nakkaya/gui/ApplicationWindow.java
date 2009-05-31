package com.nakkaya.gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import com.nakkaya.gui.beans.ImageButton;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
/*
 * Created by JFormDesigner on Wed May 20 16:21:34 EEST 2009
 */

import com.nakkaya.lib.Defaults;
import com.nakkaya.lib.arp.*;
import com.nakkaya.lib.network.*;
import com.nakkaya.lib.mailer.*;
import com.nakkaya.gui.logHandler.*;

import javax.swing.ListSelectionModel;
import java.awt.Dimension;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import java.util.Observer;
import java.util.Observable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.Toolkit;
import javax.swing.JFileChooser;
import java.net.URL;
import java.awt.Image;

import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import javax.swing.Action;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;

/**
 * @author Nurullah Akkaya
 */
public class ApplicationWindow extends JFrame implements Observer {


    Logger arpLogger = Logger.getLogger("ArpLog");
    Logger firewallLogger = Logger.getLogger("FirewallLog");

    Preferences preferences = Preferences.userRoot();

    PreferencesWindow preferencesWindow = new PreferencesWindow();
    SliderLogHandler sliderLogHandler = new SliderLogHandler();

    ArpTableModel arpTableModel;
    NetworkWatcher network;

    public ApplicationWindow(ArpTableModel model , NetworkWatcher ntwrk ) {

	URL imageURL = 
	    this.getClass().getClassLoader().getResource( "icons/mocha25.png" );
	Image image = Toolkit.getDefaultToolkit().getImage(imageURL);
	setIconImage( image );

	initComponents();
	initKeyBindings();

	arpTableModel = model;
	network = ntwrk;
	network.addObserver(this);
	
	setupNetworkPanel();
	setupArpTablePanel();
	setupArpLogPanel();
	setupFirewallLogPanel();
	
    }

    public void initKeyBindings(){

	//os specific modifier key
	int modifierKey = java.awt.event.InputEvent.ALT_MASK;
	if (preferences.get( "mocha.operatingSystem"
			     , Defaults.mocha_operatingSystem )
	    .equals("OSX") == true ){

	    modifierKey = java.awt.event.InputEvent.META_MASK;
	}

	//bind command + w
	KeyStroke altW = KeyStroke.getKeyStroke
	    (KeyEvent.VK_W,modifierKey,false);

	Action alt_W_Action = new AbstractAction(){
		public void actionPerformed(ActionEvent e){
		    setVisible( false );
		}
	    };
	tabbedPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(altW, "ALT_W");
	tabbedPane.getActionMap().put("ALT_W", alt_W_Action);
    }

    public void setupNetworkPanel(){
	updateNetworkPanel();

	final Component parent = (Component)this;
	ActionListener showPreferencesWindow = 	new ActionListener() {
		public void actionPerformed(ActionEvent event) {
		    preferencesWindow.loadPreferences();
		    preferencesWindow.setLocationRelativeTo(parent);
		    preferencesWindow.setVisible(true);
		}
	    };
	settingsButton.addActionListener(showPreferencesWindow);

	ActionListener copyIPToClibBoard = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
		    String ip = localIPLabel.getText();
		    StringSelection stringSelection = new StringSelection( ip );
		    Clipboard clipboard = 
			Toolkit.getDefaultToolkit().getSystemClipboard();
		    clipboard.setContents( stringSelection, null );
		}
	    };
	copyIPButton.addActionListener( copyIPToClibBoard );

	ActionListener copyWanIPToClibBoard = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
		    String ip = wanIPLabel.getText();
		    StringSelection stringSelection = new StringSelection( ip );
		    Clipboard clipboard = 
			Toolkit.getDefaultToolkit().getSystemClipboard();
		    clipboard.setContents( stringSelection, null );
		}
	    };
	copyWanIPButton.addActionListener( copyWanIPToClibBoard );

	ActionListener copyRouterIPToClibBoard = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
		    String ip = routerIPLabel.getText();
		    StringSelection stringSelection = new StringSelection( ip );
		    Clipboard clipboard = 
			Toolkit.getDefaultToolkit().getSystemClipboard();
		    clipboard.setContents( stringSelection, null );
		}
	    };
	copyRouterIPButton.addActionListener( copyRouterIPToClibBoard );

	ActionListener exportArpTable = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
		    JFileChooser chooser = new JFileChooser();
		    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    int returnVal = 
			chooser.showSaveDialog( parent );
		    if(returnVal == chooser.APPROVE_OPTION) {
			arpTableModel.exportToFile
			    (chooser.getSelectedFile().toString() );
		    }
		}
	    };
	exportArpTableButton.addActionListener(exportArpTable);

	ActionListener importArpTable = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
		    JFileChooser chooser = new JFileChooser();
		    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    int returnVal = 
			chooser.showOpenDialog( parent );
		    if(returnVal == chooser.APPROVE_OPTION) {
			arpTableModel.importFromFile
			    (chooser.getSelectedFile().toString() );
		    }
		}
	    };

	importArpTableButton.addActionListener( importArpTable );
    }

    public void setupArpTablePanel(){
	//setup arp table
	//set table
        arpTable.setIntercellSpacing(new Dimension());
	// Allow only single a selection
	arpTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	arpTable.setModel( arpTableModel );
	
	//arpTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	arpTable.getColumnModel().getColumn(0).setPreferredWidth(160);
	arpTable.getColumnModel().getColumn(1).setPreferredWidth(140);
	arpTable.getColumnModel().getColumn(2).setPreferredWidth(195);
    }

    public void setupArpLogPanel(){
	ListLogModel model = new ListLogModel();
	ListLogHandler handler = new ListLogHandler( model );
	arpLogList.setModel(model);
	arpLogger.addHandler( handler );
	arpLogger.addHandler( sliderLogHandler );

	if ( preferences.getBoolean
	     ("mocha.notify.mail" , Defaults.mocha_notify_mail)== true){

	    Mailer mailer = new Mailer( );
	    MailerLogHandler mailerLogHandler = new MailerLogHandler( mailer );
	    arpLogger.addHandler( mailerLogHandler );
	}
    }

    public void setupFirewallLogPanel(){
	ListLogModel model = new ListLogModel();
	ListLogHandler handler = new ListLogHandler( model );
	firewallLogList.setModel(model);
	firewallLogger.addHandler( handler );
	if ( preferences.getBoolean
	     ("mocha.notify.firewall" , Defaults.mocha_notify_firewall)== true)
	    firewallLogger.addHandler( sliderLogHandler );
    }

    public void update(Observable obj, Object arg){
	updateNetworkPanel();
    }

    public void updateNetworkPanel(){
	adapterLabel.setText( network.adapter );
	localIPLabel.setText( network.localIP );
	wanIPLabel.setText( network.wanIP );
	routerIPLabel.setText( network.gateway);
    }
    

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		tabbedPane = new JTabbedPane();
		panel1 = new JPanel();
		panel9 = new JPanel();
		panel6 = new JPanel();
		label1 = new JLabel();
		label2 = new JLabel();
		adapterLabel = new JLabel();
		localIPLabel = new JLabel();
		copyIPButton = new ImageButton();
		label3 = new JLabel();
		wanIPLabel = new JLabel();
		copyWanIPButton = new ImageButton();
		label4 = new JLabel();
		routerIPLabel = new JLabel();
		copyRouterIPButton = new ImageButton();
		panel5 = new JPanel();
		panel8 = new JPanel();
		importArpTableButton = new ImageButton();
		exportArpTableButton = new ImageButton();
		settingsButton = new ImageButton();
		panel2 = new JPanel();
		scrollPane1 = new JScrollPane();
		arpTable = new JTable();
		panel3 = new JPanel();
		scrollPane4 = new JScrollPane();
		arpLogList = new JList();
		panel4 = new JPanel();
		scrollPane2 = new JScrollPane();
		firewallLogList = new JList();

		//======== this ========
		setTitle("Mocha");
		Container contentPane = getContentPane();

		//======== tabbedPane ========
		{

			//======== panel1 ========
			{

				//======== panel9 ========
				{
					panel9.setLayout(new GridBagLayout());

					//======== panel6 ========
					{
						panel6.setBorder(new TitledBorder("Status"));

						//---- label1 ----
						label1.setText("Default Adapter:");
						label1.setFont(new Font("Arial", Font.PLAIN, 14));

						//---- label2 ----
						label2.setText("IP Address:");
						label2.setFont(new Font("Arial", Font.PLAIN, 14));

						//---- adapterLabel ----
						adapterLabel.setText("en1");
						adapterLabel.setFont(new Font("Arial", Font.PLAIN, 14));

						//---- localIPLabel ----
						localIPLabel.setText("192.168.10.2");
						localIPLabel.setFont(new Font("Arial", Font.PLAIN, 14));

						//---- copyIPButton ----
						copyIPButton.setIconPath("icons/copy.png");
						copyIPButton.setIconPressedPath("icons/copyPressed.png");
						copyIPButton.setIcon(new ImageIcon(getClass().getResource("/icons/copy.png")));
						copyIPButton.setToolTipText("Copy IP to Clipbard");

						//---- label3 ----
						label3.setText("WAN IP Address:");
						label3.setFont(new Font("Arial", Font.PLAIN, 14));

						//---- wanIPLabel ----
						wanIPLabel.setText("91.92.93.94");
						wanIPLabel.setFont(new Font("Arial", Font.PLAIN, 14));

						//---- copyWanIPButton ----
						copyWanIPButton.setIconPath("icons/copy.png");
						copyWanIPButton.setIconPressedPath("icons/copyPressed.png");
						copyWanIPButton.setIcon(new ImageIcon(getClass().getResource("/icons/copy.png")));
						copyWanIPButton.setToolTipText("Copy Wan IP to Clipbard");

						//---- label4 ----
						label4.setText("Router IP:");
						label4.setFont(new Font("Arial", Font.PLAIN, 14));

						//---- routerIPLabel ----
						routerIPLabel.setText("91.92.93.94");
						routerIPLabel.setFont(new Font("Arial", Font.PLAIN, 14));

						//---- copyRouterIPButton ----
						copyRouterIPButton.setIconPath("icons/copy.png");
						copyRouterIPButton.setIconPressedPath("icons/copyPressed.png");
						copyRouterIPButton.setIcon(new ImageIcon(getClass().getResource("/icons/copy.png")));
						copyRouterIPButton.setToolTipText("Copy Router IP to Clipbard");

						GroupLayout panel6Layout = new GroupLayout(panel6);
						panel6.setLayout(panel6Layout);
						panel6Layout.setHorizontalGroup(
							panel6Layout.createParallelGroup()
								.add(panel6Layout.createSequentialGroup()
									.addContainerGap()
									.add(panel6Layout.createParallelGroup()
										.add(panel6Layout.createSequentialGroup()
											.add(label1, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
											.add(18, 18, 18)
											.add(adapterLabel, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE))
										.add(panel6Layout.createSequentialGroup()
											.add(label2, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
											.add(18, 18, 18)
											.add(localIPLabel, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
											.add(18, 18, 18)
											.add(copyIPButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.add(panel6Layout.createSequentialGroup()
											.add(label3, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
											.add(18, 18, 18)
											.add(wanIPLabel, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
											.add(18, 18, 18)
											.add(copyWanIPButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.add(panel6Layout.createSequentialGroup()
											.add(label4, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
											.add(18, 18, 18)
											.add(routerIPLabel, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
											.add(18, 18, 18)
											.add(copyRouterIPButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
									.addContainerGap(9, Short.MAX_VALUE))
						);
						panel6Layout.setVerticalGroup(
							panel6Layout.createParallelGroup()
								.add(panel6Layout.createSequentialGroup()
									.add(panel6Layout.createParallelGroup(GroupLayout.BASELINE)
										.add(label1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
										.add(adapterLabel, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(LayoutStyle.RELATED)
									.add(panel6Layout.createParallelGroup(GroupLayout.BASELINE)
										.add(label2, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
										.add(localIPLabel, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
										.add(copyIPButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(LayoutStyle.RELATED)
									.add(panel6Layout.createParallelGroup(GroupLayout.BASELINE)
										.add(label3, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
										.add(wanIPLabel, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
										.add(copyWanIPButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(LayoutStyle.RELATED)
									.add(panel6Layout.createParallelGroup(GroupLayout.BASELINE)
										.add(label4, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
										.add(routerIPLabel, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
										.add(copyRouterIPButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addContainerGap(20, Short.MAX_VALUE))
						);
					}
					panel9.add(panel6, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 0), 0, 0));

					//======== panel5 ========
					{

						GroupLayout panel5Layout = new GroupLayout(panel5);
						panel5.setLayout(panel5Layout);
						panel5Layout.setHorizontalGroup(
							panel5Layout.createParallelGroup()
								.add(0, 349, Short.MAX_VALUE)
						);
						panel5Layout.setVerticalGroup(
							panel5Layout.createParallelGroup()
								.add(0, 20, Short.MAX_VALUE)
						);
					}
					panel9.add(panel5, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//======== panel8 ========
					{
						panel8.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

						//---- importArpTableButton ----
						importArpTableButton.setIconPath("icons/import.png");
						importArpTableButton.setIconPressedPath("icons/importPressed.png");
						importArpTableButton.setToolTipText("Import Arp Table");
						panel8.add(importArpTableButton);

						//---- exportArpTableButton ----
						exportArpTableButton.setIconPath("icons/export.png");
						exportArpTableButton.setIconPressedPath("icons/exportPressed.png");
						exportArpTableButton.setToolTipText("Export Current Arp Table");
						panel8.add(exportArpTableButton);

						//---- settingsButton ----
						settingsButton.setIconPath("icons/settings.png");
						settingsButton.setIconPressedPath("icons/settingsPressed.png");
						settingsButton.setToolTipText("Settings");
						panel8.add(settingsButton);
					}
					panel9.add(panel8, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
						new Insets(0, 0, 0, 0), 0, 0));
				}

				GroupLayout panel1Layout = new GroupLayout(panel1);
				panel1.setLayout(panel1Layout);
				panel1Layout.setHorizontalGroup(
					panel1Layout.createParallelGroup()
						.add(panel1Layout.createSequentialGroup()
							.addContainerGap()
							.add(panel9, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
							.addContainerGap())
				);
				panel1Layout.setVerticalGroup(
					panel1Layout.createParallelGroup()
						.add(panel1Layout.createSequentialGroup()
							.addContainerGap()
							.add(panel9, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
							.addContainerGap())
				);
			}
			tabbedPane.addTab("Network", panel1);


			//======== panel2 ========
			{

				//======== scrollPane1 ========
				{
					scrollPane1.setViewportView(arpTable);
				}

				GroupLayout panel2Layout = new GroupLayout(panel2);
				panel2.setLayout(panel2Layout);
				panel2Layout.setHorizontalGroup(
					panel2Layout.createParallelGroup()
						.add(panel2Layout.createSequentialGroup()
							.addContainerGap()
							.add(scrollPane1, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
							.addContainerGap())
				);
				panel2Layout.setVerticalGroup(
					panel2Layout.createParallelGroup()
						.add(panel2Layout.createSequentialGroup()
							.add(scrollPane1, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
							.addContainerGap())
				);
			}
			tabbedPane.addTab("Arp Table", panel2);


			//======== panel3 ========
			{

				//======== scrollPane4 ========
				{
					scrollPane4.setViewportView(arpLogList);
				}

				GroupLayout panel3Layout = new GroupLayout(panel3);
				panel3.setLayout(panel3Layout);
				panel3Layout.setHorizontalGroup(
					panel3Layout.createParallelGroup()
						.add(panel3Layout.createSequentialGroup()
							.addContainerGap()
							.add(scrollPane4, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
							.addContainerGap())
				);
				panel3Layout.setVerticalGroup(
					panel3Layout.createParallelGroup()
						.add(GroupLayout.TRAILING, panel3Layout.createSequentialGroup()
							.add(scrollPane4, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
							.addContainerGap())
				);
			}
			tabbedPane.addTab("Arp Log", panel3);


			//======== panel4 ========
			{

				//======== scrollPane2 ========
				{
					scrollPane2.setViewportView(firewallLogList);
				}

				GroupLayout panel4Layout = new GroupLayout(panel4);
				panel4.setLayout(panel4Layout);
				panel4Layout.setHorizontalGroup(
					panel4Layout.createParallelGroup()
						.add(panel4Layout.createSequentialGroup()
							.addContainerGap()
							.add(scrollPane2, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
							.addContainerGap())
				);
				panel4Layout.setVerticalGroup(
					panel4Layout.createParallelGroup()
						.add(GroupLayout.TRAILING, panel4Layout.createSequentialGroup()
							.add(scrollPane2, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
							.addContainerGap())
				);
			}
			tabbedPane.addTab("Firewall Log", panel4);

		}

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.add(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.add(tabbedPane, GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.add(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.add(tabbedPane)
					.addContainerGap())
		);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JTabbedPane tabbedPane;
	private JPanel panel1;
	private JPanel panel9;
	private JPanel panel6;
	private JLabel label1;
	private JLabel label2;
	private JLabel adapterLabel;
	private JLabel localIPLabel;
	private ImageButton copyIPButton;
	private JLabel label3;
	private JLabel wanIPLabel;
	private ImageButton copyWanIPButton;
	private JLabel label4;
	private JLabel routerIPLabel;
	private ImageButton copyRouterIPButton;
	private JPanel panel5;
	private JPanel panel8;
	private ImageButton importArpTableButton;
	private ImageButton exportArpTableButton;
	private ImageButton settingsButton;
	private JPanel panel2;
	private JScrollPane scrollPane1;
	private JTable arpTable;
	private JPanel panel3;
	private JScrollPane scrollPane4;
	private JList arpLogList;
	private JPanel panel4;
	private JScrollPane scrollPane2;
	private JList firewallLogList;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

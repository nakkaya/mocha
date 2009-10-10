package com.nakkaya.gui;

import java.awt.Container;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
/*
 * Created by JFormDesigner on Thu May 21 19:24:40 EEST 2009
 */



/**
 * @author Nurullah Akkaya
 */
public class AboutWindow extends JFrame {
	public AboutWindow() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		label4 = new JLabel();
		label5 = new JLabel();
		label6 = new JLabel();

		//======== this ========
		setResizable(false);
		setTitle("About");
		Container contentPane = getContentPane();

		//---- label1 ----
		label1.setIcon(new ImageIcon(getClass().getResource("/icons/aboutIcon.png")));
		label1.setVerticalAlignment(SwingConstants.BOTTOM);
		label1.setHorizontalAlignment(SwingConstants.LEFT);

		//---- label2 ----
		label2.setText("Mocha");
		label2.setFont(new Font("Arial", Font.BOLD, 20));

		//---- label3 ----
		label3.setText("v 1.1.1");
		label3.setFont(new Font("Arial", Font.PLAIN, 20));

		//---- label4 ----
		label4.setText("<html><a href=\"http://nakkaya.com/Mocha.html\">http://nakkaya.com/Mocha.html</a></html>");

		//---- label5 ----
		label5.setText("Nurullah Akkaya");

		//---- label6 ----
		label6.setText("<html><a href=\"mailto:nurullah@nakkaya.com\">nurullah@nakkaya.com</a></html>");

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.add(contentPaneLayout.createSequentialGroup()
					.add(contentPaneLayout.createParallelGroup()
						.add(contentPaneLayout.createSequentialGroup()
							.addContainerGap()
							.add(label1)
							.add(18, 18, 18)
							.add(contentPaneLayout.createParallelGroup()
								.add(label2)
								.add(label3)))
						.add(contentPaneLayout.createSequentialGroup()
							.add(73, 73, 73)
							.add(label5))
						.add(contentPaneLayout.createSequentialGroup()
							.add(33, 33, 33)
							.add(label4))
						.add(contentPaneLayout.createSequentialGroup()
							.add(52, 52, 52)
							.add(label6)))
					.addContainerGap(27, Short.MAX_VALUE))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.add(contentPaneLayout.createSequentialGroup()
					.add(30, 30, 30)
					.add(contentPaneLayout.createParallelGroup()
						.add(label2)
						.add(contentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
							.add(label1, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
							.add(label3)))
					.addPreferredGap(LayoutStyle.RELATED)
					.add(label4)
					.add(18, 18, 18)
					.add(label5)
					.addPreferredGap(LayoutStyle.RELATED)
					.add(label6)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

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

package com.nakkaya.gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
		panel1 = new JPanel();
		label4 = new JLabel();
		label5 = new JLabel();
		label6 = new JLabel();
		panel2 = new JPanel();
		label2 = new JLabel();
		label3 = new JLabel();

		//======== this ========
		setResizable(false);
		setTitle("About");
		Container contentPane = getContentPane();

		//---- label1 ----
		label1.setIcon(new ImageIcon(getClass().getResource("/icons/aboutIcon.png")));
		label1.setVerticalAlignment(SwingConstants.BOTTOM);
		label1.setHorizontalAlignment(SwingConstants.LEFT);

		//======== panel1 ========
		{
			panel1.setLayout(new FlowLayout());

			//---- label4 ----
			label4.setText("<html><a href=\"http://nakkaya.com/Mocha.html\">http://nakkaya.com/</a></html>");
			panel1.add(label4);

			//---- label5 ----
			label5.setText("Nurullah Akkaya");
			panel1.add(label5);

			//---- label6 ----
			label6.setText("<html><a href=\"mailto:nurullah@nakkaya.com\">nurullah@nakkaya.com</a></html>");
			panel1.add(label6);
		}

		//======== panel2 ========
		{
			panel2.setLayout(new FlowLayout());

			//---- label2 ----
			label2.setText("Mocha");
			label2.setFont(new Font("Arial", Font.BOLD, 20));
			panel2.add(label2);

			//---- label3 ----
			label3.setText("v 1.1.1");
			label3.setFont(new Font("Arial", Font.PLAIN, 20));
			panel2.add(label3);
		}

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.add(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.add(contentPaneLayout.createParallelGroup()
						.add(contentPaneLayout.createSequentialGroup()
							.add(24, 24, 24)
							.add(panel1, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE))
						.add(contentPaneLayout.createSequentialGroup()
							.add(label1)
							.addPreferredGap(LayoutStyle.RELATED)
							.add(panel2, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.add(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.add(contentPaneLayout.createParallelGroup()
						.add(label1, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
						.add(panel2, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.UNRELATED)
					.add(panel1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(19, Short.MAX_VALUE))
		);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel label1;
	private JPanel panel1;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	private JPanel panel2;
	private JLabel label2;
	private JLabel label3;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

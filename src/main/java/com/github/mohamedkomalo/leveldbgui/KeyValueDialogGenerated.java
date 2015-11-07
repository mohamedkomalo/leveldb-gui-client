package com.github.mohamedkomalo.leveldbgui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Mohamed Kamal on 10/26/2015.
 */
public class KeyValueDialogGenerated extends JDialog {
	protected JTextArea valueTextArea;
	protected JTextArea keyTextArea;
	protected JButton okButton;
	protected JButton cancelButton;

	public KeyValueDialogGenerated(JFrame owner) {
		super(owner, true);

		setBounds(100, 100, 566, 414);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new EmptyBorder(10, 10, 10, 10));
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			{
				JSplitPane splitPane = new JSplitPane();
				splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
				panel.add(splitPane);
				splitPane.setDividerSize(10);
				splitPane.setDividerLocation(100);
				{
					JPanel panel_2 = new JPanel();
					splitPane.setLeftComponent(panel_2);
					panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
					{
						JPanel panel_1 = new JPanel();
						FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
						flowLayout.setAlignment(FlowLayout.LEFT);
						panel_2.add(panel_1);
						{
							JLabel label = new JLabel("Key");
							panel_1.add(label);
						}
					}
					{
						keyTextArea = new JTextArea();
						keyTextArea.setFont(UIManager.getFont("TextField.font"));
						keyTextArea.setLineWrap(true);
						panel_2.add(keyTextArea);
					}
				}
				{
					JPanel panel_2 = new JPanel();
					splitPane.setRightComponent(panel_2);
					panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
					{
						JPanel panel_1 = new JPanel();
						FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
						flowLayout.setAlignment(FlowLayout.LEFT);
						panel_2.add(panel_1);
						{
							JLabel lblValue = new JLabel("Value");
							panel_1.add(lblValue);
						}
					}
					{
						valueTextArea = new JTextArea();
						valueTextArea.setFont(UIManager.getFont("TextField.font"));
						valueTextArea.setLineWrap(true);
						panel_2.add(valueTextArea);
					}
				}
			}
		}
	}


}

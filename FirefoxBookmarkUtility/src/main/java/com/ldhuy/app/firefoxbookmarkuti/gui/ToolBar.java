/**
 * 
 */
package com.ldhuy.app.firefoxbookmarkuti.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Observable;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javafx.scene.control.ComboBox;

/**
 * @author ldhuy
 *
 */
public class ToolBar extends Observable {
	private JPanel mainPanel;
	private JTextField searchBox;

	private final int SEARCHBOX_WIDTH = 20;

	public ToolBar() {
		this.searchBox = new JTextField(SEARCHBOX_WIDTH);
		this.searchBox = new JTextField(10);
		this.searchBox.setEnabled(false);
		this.searchBox.getDocument().addDocumentListener(new DocumentListener() {

			public void removeUpdate(DocumentEvent e) {
				changeUpdate(e);
			}

			public void insertUpdate(DocumentEvent e) {
				changeUpdate(e);
			}

			public void changedUpdate(DocumentEvent e) {
				changeUpdate(e);
			}

			private void changeUpdate(DocumentEvent e) {
				String text = searchBox.getText();

				// Notify observer
				setChanged();
				notifyObservers(text);
			}
		});

		this.searchBox.setBorder(null);
		PlaceholderComponent placeHolderComp = new PlaceholderComponent(searchBox, "Search", PlaceholderComponent.DISPLAY_MODE.NO_TEXT);
		this.mainPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		this.mainPanel.add(searchBox);
	}

	/**
	 * @return the mainPanel
	 */
	public JPanel getMainPanel() {
		return mainPanel;
	}

	/**
	 * @return the searchBox
	 */
	public JTextField getSearchBox() {
		return searchBox;
	}

}

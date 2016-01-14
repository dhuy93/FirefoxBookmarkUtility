/**
 * 
 */
package com.ldhuy.app.firefoxbookmarkuti.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

/**
 * @author ldhuy
 *
 */
public class PlaceholderComponent extends JLabel implements FocusListener, DocumentListener, PropertyChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6398893683085606411L;

	public static enum DISPLAY_MODE {
		ALWAYS, FOCUS_GAINED, FOCUS_LOST, NO_TEXT;
	}

	private JTextComponent textComponent;
	private Document document;
	private DISPLAY_MODE displayMode;
	private boolean showPromptOnce;
	private int focusLost;

	public PlaceholderComponent(JTextComponent textComponent, String placeHolder) {
		this(textComponent, placeHolder, DISPLAY_MODE.NO_TEXT);
	}

	public PlaceholderComponent(JTextComponent textComponent, String placeHolder, DISPLAY_MODE displayMode) {
		this.textComponent = textComponent;
		this.document = textComponent.getDocument();
		setDisplayMode(displayMode);

		setText(placeHolder);
		setFont(textComponent.getFont());
		setForeground(textComponent.getForeground());
		setBorder(textComponent.getBorder());
		setHorizontalAlignment(JLabel.LEADING);
		
		changeAlpha(0.5f);

		textComponent.addFocusListener(this);
		this.document.addDocumentListener(this);

		textComponent.setLayout(new BorderLayout());
		textComponent.add(this);

		checkForPrompt();
	}

	/**
	 * Convenience method to change the alpha value of the current foreground
	 * Color to the specifice value.
	 *
	 * @param alpha
	 *            value in the range of 0 - 1.0.
	 */
	public void changeAlpha(float alpha) {
		changeAlpha((int) (alpha * 255));
	}

	/**
	 * Convenience method to change the alpha value of the current foreground
	 * Color to the specifice value.
	 *
	 * @param alpha
	 *            value in the range of 0 - 255.
	 */
	public void changeAlpha(int alpha) {
		alpha = alpha > 255 ? 255 : alpha < 0 ? 0 : alpha;

		Color foreground = getForeground();
		int red = foreground.getRed();
		int green = foreground.getGreen();
		int blue = foreground.getBlue();

		Color withAlpha = new Color(red, green, blue, alpha);
		super.setForeground(withAlpha);
	}

	/**
	 * Convenience method to change the style of the current Font. The style
	 * values are found in the Font class. Common values might be: Font.BOLD,
	 * Font.ITALIC and Font.BOLD + Font.ITALIC.
	 *
	 * @param style
	 *            value representing the the new style of the Font.
	 */
	public void changeStyle(int style) {
		setFont(getFont().deriveFont(style));
	}

	/**
	 * Check whether the prompt should be visible or not. The visibility will
	 * change on updates to the Document and on focus changes.
	 */
	private void checkForPrompt() {
		// Text has been entered, remove the prompt
		if (document.getLength() > 0) {
			setVisible(false);
			return;
		}

		// Prompt has already been shown once, remove it
		if (showPromptOnce && focusLost > 0) {
			setVisible(false);
			return;
		}

		// Check the displayMode property and component focus to determine if
		// the placeholder should be displayed.
		if (this.displayMode == DISPLAY_MODE.NO_TEXT) {
			if (this.textComponent.getText().length() > 0) {
				setVisible(false);
			} else {
				setVisible(true);
			}
			return;
		}
		if (this.textComponent.hasFocus()) {
			if (this.displayMode == DISPLAY_MODE.ALWAYS || this.displayMode == DISPLAY_MODE.FOCUS_GAINED)
				setVisible(true);
			else
				setVisible(false);
		} else {
			if (this.displayMode == DISPLAY_MODE.ALWAYS || this.displayMode == DISPLAY_MODE.FOCUS_LOST)
				setVisible(true);
			else
				setVisible(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.
	 * PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.
	 * DocumentEvent)
	 */
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.
	 * DocumentEvent)
	 */
	public void insertUpdate(DocumentEvent arg0) {
		checkForPrompt();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.
	 * DocumentEvent)
	 */
	public void removeUpdate(DocumentEvent arg0) {
		checkForPrompt();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained(FocusEvent arg0) {
		checkForPrompt();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public void focusLost(FocusEvent arg0) {
		this.focusLost++;
		checkForPrompt();
	}

	/**
	 * @return the displayMode
	 */
	public DISPLAY_MODE getDisplayMode() {
		return displayMode;
	}

	/**
	 * @param displayMode
	 *            the displayMode to set
	 */
	public void setDisplayMode(DISPLAY_MODE displayMode) {
		this.displayMode = displayMode;
	}

}

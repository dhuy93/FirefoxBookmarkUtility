/**
 * 
 */
package com.ldhuy.app.firefoxbookmarkuti.model;

/**
 * @author ldhuy
 *
 */
public enum MenuCommand {
	OPENFILE("Open"), SAVEFILE("Save"), SAVEFILEAS("Save as...");
	private String value;
	
	
	private MenuCommand(String value) {
		this.value = value;
	}


	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	
}

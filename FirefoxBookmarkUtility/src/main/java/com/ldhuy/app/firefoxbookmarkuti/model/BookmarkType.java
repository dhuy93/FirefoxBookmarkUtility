/**
 * 
 */
package com.ldhuy.app.firefoxbookmarkuti.model;

/**
 * @author ldhuy
 *
 */
public enum BookmarkType {
	PLACE("text/x-moz-place"), CONTAINER("text/x-moz-place-container");
	private String value;

	private BookmarkType(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}

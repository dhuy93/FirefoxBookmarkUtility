/**
 * 
 */
package com.ldhuy.app.firefoxbookmarkuti.model;

import javax.swing.table.AbstractTableModel;

/**
 * @author ldhuy
 *
 */
public class TableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4614115231692427780L;
	private String[] columnNames;
	private Object[][] data;

	public TableModel(String[] columnNames) {
		this.columnNames = columnNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return columnNames.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return data.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}

	/**
	 * @return the columnNames
	 */
	public String[] getColumnNames() {
		return columnNames;
	}

	/**
	 * @param columnNames
	 *            the columnNames to set
	 */
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	/**
	 * @return the data
	 */
	public Object[][] getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object[][] data) {
		this.data = data;
		this.fireTableDataChanged();
	}
}

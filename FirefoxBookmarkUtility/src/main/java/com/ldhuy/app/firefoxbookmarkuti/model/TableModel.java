/**
 * 
 */
package com.ldhuy.app.firefoxbookmarkuti.model;

import javax.swing.table.DefaultTableModel;

/**
 * @author ldhuy
 *
 */
public class TableModel extends DefaultTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4614115231692427780L;
	private String[] columnNames;
	private Object[][] data;

	public TableModel(String[] columnNames) {
		this.columnNames = columnNames;
	}
	
	public TableModel() {
		this.columnNames = new String[] {""};
		this.data = new Object[0][];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		if (columnNames != null) {
			return columnNames.length;
		} else {
			return 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		if (data != null) {
			return data.length;
		} else {
			return 0;
		}

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

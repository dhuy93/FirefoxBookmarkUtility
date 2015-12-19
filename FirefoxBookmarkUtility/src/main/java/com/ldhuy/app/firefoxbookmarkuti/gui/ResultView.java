/**
 * 
 */
package com.ldhuy.app.firefoxbookmarkuti.gui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.ldhuy.app.firefoxbookmarkuti.model.TableModel;

/**
 * @author ldhuy
 *
 */
public class ResultView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8667721047894246147L;
	private JScrollPane scrollPane;
	private JTable table;
	private TableModel tableModel;

	public ResultView(TableModel tableModel) {
		this.tableModel = tableModel;
		this.table = new JTable(this.tableModel);
		this.scrollPane = new JScrollPane(this.table);
		this.add(scrollPane);

		this.table.setShowGrid(false);
		this.table.setIntercellSpacing(new Dimension(0, 0));
		this.table.setDefaultRenderer(Object.class, new RowSelectionRenderer());
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/**
	 * @return the scrollPane
	 */
	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	/**
	 * @param scrollPane
	 *            the scrollPane to set
	 */
	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	/**
	 * @return the table
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * @param table
	 *            the table to set
	 */
	public void setTable(JTable table) {
		this.table = table;
	}

	/**
	 * @return the tableModel
	 */
	public TableModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            the tableModel to set
	 */
	public void setTableModel(TableModel tableModel) {
		this.tableModel = tableModel;
	}

}
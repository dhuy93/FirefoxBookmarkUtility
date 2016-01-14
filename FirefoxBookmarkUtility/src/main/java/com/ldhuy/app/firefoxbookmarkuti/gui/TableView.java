/**
 * 
 */
package com.ldhuy.app.firefoxbookmarkuti.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.ldhuy.app.firefoxbookmarkuti.model.TableModel;

/**
 * @author ldhuy
 *
 */
public class TableView extends Observable {
	private JPanel mainPanel;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel tableModel;
	private ImageIcon bookmarkIcon;
	private ImageIcon folderIcon;

	public TableView() {
		this.bookmarkIcon = new ImageIcon("res/icon/bookmark.png");
		this.folderIcon = new ImageIcon("res/icon/folder.png");
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new GridLayout(1, 0, 10, 10));
		this.tableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Disable cell editing
				return false;
			}
		};
		this.table = new JTable(this.tableModel) {
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}

			 private boolean inLayout;

			 @Override
             public boolean getScrollableTracksViewportWidth()
             {
                 return getPreferredSize().width < getParent().getWidth();
             }

             @Override
             public void doLayout()
             {
                 TableColumn resizingColumn = null;

                 if (tableHeader != null)
                     resizingColumn = tableHeader.getResizingColumn();

                 //  Viewport size changed. May need to increase columns widths

                 if (resizingColumn == null)
                 {
                     setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
                     super.doLayout();
                 }

                 //  Specific column resized. Reset preferred widths

                 else
                 {
                     TableColumnModel tcm = getColumnModel();

                     for (int i = 0; i < tcm.getColumnCount(); i++)
                     {
                         TableColumn tc = tcm.getColumn(i);
                         tc.setPreferredWidth( tc.getWidth() );
                     }

                     // Columns don't fill the viewport, invoke default layout

                     if (tcm.getTotalColumnWidth() < getParent().getWidth())
                         setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
                         super.doLayout();
                 }

                 setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
             }
		};
		this.scrollPane = new JScrollPane(this.table);
		this.mainPanel.add(scrollPane);

		this.table.setShowGrid(false);
		this.table.setIntercellSpacing(new Dimension(0, 0));
		this.table.setDefaultRenderer(Object.class, new RowSelectionRenderer());
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		this.table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				if (e.getClickCount() == 2) {
					JTable table = (JTable) e.getSource();
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						// Notify observer
						setChanged();
						notifyObservers(selectedRow);
					}
				}
			}
		});
	}

	public void setColumnPreferredWidth(int column, int preferredWidth) {
		if (column >= 0 && column < this.tableModel.getColumnCount() && preferredWidth >= 0) {
			TableColumnModel columnModel = this.table.getColumnModel();
			TableColumn tableColumn = columnModel.getColumn(column);
			tableColumn.setPreferredWidth(preferredWidth);
		}
	}

	/**
	 * @return the mainPanel
	 */
	public JPanel getMainPanel() {
		return mainPanel;
	}

	/**
	 * @param mainPanel
	 *            the mainPanel to set
	 */
	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
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
	 * @return the tableModel
	 */
	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	/**
	 * @return the bookmarkIcon
	 */
	public ImageIcon getBookmarkIcon() {
		return bookmarkIcon;
	}

	/**
	 * @return the folderIcon
	 */
	public ImageIcon getFolderIcon() {
		return folderIcon;
	}

}

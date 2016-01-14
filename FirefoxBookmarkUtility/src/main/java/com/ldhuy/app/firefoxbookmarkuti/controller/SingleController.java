/**
 * 
 */
package com.ldhuy.app.firefoxbookmarkuti.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.tree.TreePath;

import com.ldhuy.app.firefoxbookmarkuti.gui.TableView;
import com.ldhuy.app.firefoxbookmarkuti.gui.ToolBar;
import com.ldhuy.app.firefoxbookmarkuti.gui.TreeView;
import com.ldhuy.app.firefoxbookmarkuti.model.BookmarkDTO;
import com.ldhuy.app.firefoxbookmarkuti.model.BookmarkType;
import com.ldhuy.app.firefoxbookmarkuti.service.BookmarkService;

/**
 * @author ldhuy
 *
 */
public class SingleController implements Observer {

	/**
	 * The table view
	 */
	private TableView tableView;

	/**
	 * The tree view to view folders in a tree structure
	 */
	private TreeView treeView;

	/**
	 * The toolbar
	 */
	private ToolBar toolbar;

	/**
	 * Columns of table view
	 */
	private String[] columnNames = { "Type", "Title", "Tags", "Location" };

	/**
	 * The root node
	 */
	private BookmarkDTO root;

	/**
	 * The selected folder
	 */
	private BookmarkDTO selectedFolder;

	/**
	 * @param tableView
	 * @param treeView
	 * @param toolbar
	 */
	public SingleController(TableView tableView, TreeView treeView, ToolBar toolbar) {
		super();
		this.tableView = tableView;
		this.treeView = treeView;
		this.toolbar = toolbar;
	}

	public void init(BookmarkDTO root) {
		this.root = root;
		this.selectedFolder = root;
		this.treeView.setRootNode(root);
		this.treeView.setSelectedNode(root);
		for (int i = 0; i < columnNames.length; i++) {
			this.tableView.getTableModel().addColumn(columnNames[i]);
		}

		// Initiate data in table view
		Object[][] data = convertToTableData(this.root);
		this.tableView.getTableModel().setDataVector(data, this.columnNames);

		// Set preferred width of table columns
		this.tableView.setColumnPreferredWidth(0, 10);
		this.tableView.setColumnPreferredWidth(1, 100);
		this.tableView.setColumnPreferredWidth(2, 200);
		// this.tableView.setColumnPreferredWidth(3, 500);

		this.tableView.getTable().setAutoCreateColumnsFromModel(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		if (o instanceof TreeView) {
			// Selected node in tree view was changed
			Object[][] data = convertToTableData((BookmarkDTO) arg);
			this.selectedFolder = (BookmarkDTO) arg;
			this.tableView.getTableModel().setDataVector(data, this.columnNames);
		} else if (o instanceof TableView) {
			int rowNum = (Integer) arg;
			if (this.selectedFolder != null && this.selectedFolder.getChildren() != null) {
				BookmarkDTO dbClickedNode = this.selectedFolder.getChildren().get(rowNum);
				if (dbClickedNode.getType().equals(BookmarkType.CONTAINER.getValue())) {
					this.selectedFolder = dbClickedNode;

					// Set new data for table view
					Object[][] data = convertToTableData((BookmarkDTO) dbClickedNode);
					this.tableView.getTableModel().setDataVector(data, this.columnNames);

					// Set respective selected node in tree view
					TreePath parentPath = treeView.getTree().getSelectionPath();
					TreePath currentTreePath = parentPath.pathByAddingChild(this.selectedFolder);
					this.treeView.getTree().setSelectionPath(currentTreePath);
					System.out.println(currentTreePath.toString());
				}
			}
		} else if (o instanceof ToolBar) {
			this.searchBookmark((String) arg);
		}
	}

	/**
	 * Convert children of BookmarkDTO object to data for table
	 * 
	 * @param bookmarkDTO
	 * @return Data for table model
	 */
	private Object[][] convertToTableData(BookmarkDTO bookmarkDTO) {
		if (bookmarkDTO != null) {
			List<BookmarkDTO> list = bookmarkDTO.getChildren();
			if (list == null) {
				return null;
			}

			Object[][] data = new Object[list.size()][4];
			for (int i = 0; i < list.size(); ++i) {
				data[i][0] = (list.get(i).getType().equals(BookmarkType.CONTAINER.getValue()))
						? this.tableView.getFolderIcon() : this.tableView.getBookmarkIcon();
				data[i][1] = (list.get(i).getTitle() != null) ? list.get(i).getTitle() : "";
				data[i][2] = (list.get(i).getTags() != null) ? list.get(i).getTags() : "";
				data[i][3] = (list.get(i).getUri() != null) ? list.get(i).getUri() : "";
			}

			return data;
		}
		return new Object[0][0];
	}

	private Object[][] convertToTableData(Collection<BookmarkDTO> collection) {
		if (collection != null) {
			if (collection.size() > 0) {
				Object[][] data = new Object[collection.size()][4];
				int i = 0;
				for (Iterator<BookmarkDTO> iterator = collection.iterator(); iterator.hasNext(); ++i) {
					BookmarkDTO bookmarkDTO = (BookmarkDTO) iterator.next();
					data[i][0] = (bookmarkDTO.getType().equals(BookmarkType.CONTAINER.getValue()))
							? this.tableView.getFolderIcon() : this.tableView.getBookmarkIcon();
					data[i][1] = (bookmarkDTO.getTitle() != null) ? bookmarkDTO.getTitle() : "";
					data[i][2] = (bookmarkDTO.getTags() != null) ? bookmarkDTO.getTags() : "";
					data[i][3] = (bookmarkDTO.getUri() != null) ? bookmarkDTO.getUri() : "";
				}
				return data;
			}
		}
		return new Object[0][0];
	}

	public void searchBookmark(String query) {
		if (query != null) {
			if (!query.equals("")) {
				Map<Long, BookmarkDTO> result = BookmarkService.find(this.treeView.getSelectedNode(), query);
				this.tableView.getTableModel().setDataVector(convertToTableData(result.values()), this.columnNames);
			} else {
				Object[][] data = convertToTableData(this.treeView.getSelectedNode());
				this.selectedFolder = this.treeView.getSelectedNode();
				this.tableView.getTableModel().setDataVector(data, this.columnNames);
			}
		}
	}

	/**
	 * @return the tableView
	 */
	public TableView getTableView() {
		return tableView;
	}

	/**
	 * @param tableView
	 *            the tableView to set
	 */
	public void setTableView(TableView tableView) {
		this.tableView = tableView;
	}

	/**
	 * @return the treeView
	 */
	public TreeView getTreeView() {
		return treeView;
	}

	/**
	 * @param treeView
	 *            the treeView to set
	 */
	public void setTreeView(TreeView treeView) {
		this.treeView = treeView;
	}

}

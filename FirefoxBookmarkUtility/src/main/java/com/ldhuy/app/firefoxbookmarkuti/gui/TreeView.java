/**
 * 
 */
package com.ldhuy.app.firefoxbookmarkuti.gui;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import com.ldhuy.app.firefoxbookmarkuti.App;
import com.ldhuy.app.firefoxbookmarkuti.model.BookmarkDTO;
import com.ldhuy.app.firefoxbookmarkuti.model.BookmarkType;
import com.ldhuy.app.firefoxbookmarkuti.model.TableModel;
import com.ldhuy.app.firefoxbookmarkuti.model.TreeViewModel;

/**
 * @author ldhuy
 *
 */
public class TreeView extends JPanel implements TreeSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4842193866029430484L;
	private JScrollPane scrollPane;
	private JTree tree;
	private TreeViewModel model;
	private BookmarkDTO selectedNode;
	private TableModel tableModel;

	public TreeView() {
		this.setLayout(new GridLayout(0, 1, 10, 10));
		this.tree = new JTree();
		this.scrollPane = new JScrollPane(this.tree);
		this.add(this.scrollPane);
		this.tree.setRootVisible(false);
		this.tree.setShowsRootHandles(false);
		this.tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.tree.addTreeSelectionListener(this);
	}

	private Object[][] convertToTableData(BookmarkDTO bookmarkDTO) {
		if (bookmarkDTO != null) {
			List<BookmarkDTO> list = bookmarkDTO.getChildren();
			if (list == null) {
				return null;
			}

			Object[][] data = new Object[list.size()][4];
			for (int i = 0; i < list.size(); ++i) {
				data[i][0] = (list.get(i).getType().equals(BookmarkType.CONTAINER.getValue())) ? "Folder" : "";
				data[i][1] = (list.get(i).getTitle() != null) ? list.get(i).getTitle() : "";
				data[i][2] = (list.get(i).getTags() != null) ? list.get(i).getTags() : "";
				data[i][3] = (list.get(i).getUri() != null) ? list.get(i).getUri() : "";
			}

			return data;
		}
		return new Object[0][0];
	}

	public void setModel(TreeViewModel model) {
		this.model = model;
		if (this.model != null) {
			this.selectedNode = (BookmarkDTO) this.model.getRoot();
			Object[][] data = convertToTableData(this.selectedNode);
			if (this.tableModel != null) {
				this.tableModel.setData(data);
			}
		}
		this.tree.setModel(this.model);
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
		this.remove(this.scrollPane);
		this.scrollPane = scrollPane;
		this.add(this.scrollPane);
	}

	/**
	 * @return the tree
	 */
	public JTree getTree() {
		return tree;
	}

	/**
	 * @param tree
	 *            the tree to set
	 */
	public void setTree(JTree tree) {
		this.scrollPane.remove(this.tree);
		this.tree = tree;
		this.scrollPane.add(this.tree);
	}

	public void valueChanged(TreeSelectionEvent e) {
		BookmarkDTO node = (BookmarkDTO) tree.getLastSelectedPathComponent();

		if (node != null) {
			App.selectedContainer = node;
			this.selectedNode = node;
			Object[][] data = convertToTableData(this.selectedNode);
			this.tableModel.setData(data);
			System.out.println("Select node \"" + node.getTitle() + "\"");
		} else {
			System.out.println("nothing selected");
		}
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
		if (this.tableModel != null) {
			Object[][] data = convertToTableData(this.selectedNode);
			this.tableModel.setData(data);
		}
	}
}

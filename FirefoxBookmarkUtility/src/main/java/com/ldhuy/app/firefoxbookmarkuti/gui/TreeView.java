/**
 * 
 */
package com.ldhuy.app.firefoxbookmarkuti.gui;

import java.awt.GridLayout;
import java.util.Observable;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.ldhuy.app.firefoxbookmarkuti.model.BookmarkDTO;
import com.ldhuy.app.firefoxbookmarkuti.model.TreeViewModel;

/**
 * @author ldhuy
 *
 */
public class TreeView extends Observable implements TreeSelectionListener {
	private JPanel mainPanel;
	private JScrollPane scrollPane;
	private JTree tree;
	private TreeViewModel treeModel;
	private BookmarkDTO selectedNode;

	public TreeView() {
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new GridLayout(0, 1, 10, 10));
		this.tree = new JTree();

		// Remove all default nodes
		DefaultTreeModel tempTreeModel = (DefaultTreeModel) this.tree.getModel();
		((DefaultMutableTreeNode) tempTreeModel.getRoot()).removeAllChildren();
		tempTreeModel.reload();

		this.scrollPane = new JScrollPane(this.tree);
		this.mainPanel.add(this.scrollPane);
		this.tree.setRootVisible(false);
		this.tree.setShowsRootHandles(false);
		this.tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.tree.addTreeSelectionListener(this);
	}

	public void setRootNode(BookmarkDTO root) {
		// TODO: handle root==null
		this.treeModel = new TreeViewModel(root);
		if (root != null) {
			this.selectedNode = root;
		}
		this.tree.setModel(this.treeModel);
		this.tree.setSelectionPath(new TreePath(this.treeModel.getRoot()));
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
		this.mainPanel.remove(this.scrollPane);
		this.scrollPane = scrollPane;
		this.mainPanel.add(this.scrollPane);
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

	/**
	 * @return the selectedNode
	 */
	public BookmarkDTO getSelectedNode() {
		return selectedNode;
	}

	/**
	 * @param selectedNode
	 *            the selectedNode to set
	 */
	public void setSelectedNode(BookmarkDTO selectedNode) {
		this.selectedNode = selectedNode;
	}

	/**
	 * @return the treeModel
	 */
	public TreeViewModel getTreeModel() {
		return treeModel;
	}

	public void valueChanged(TreeSelectionEvent e) {
		BookmarkDTO node = (BookmarkDTO) tree.getLastSelectedPathComponent();

		if (node != null) {
			// App.selectedContainer = node;
			this.selectedNode = node;
			// Notify observer
			setChanged();
			notifyObservers(node);
			System.out.println("Select node \"" + node.getTitle() + "\"");
		} else {
			System.out.println("nothing selected");
		}
	}
}

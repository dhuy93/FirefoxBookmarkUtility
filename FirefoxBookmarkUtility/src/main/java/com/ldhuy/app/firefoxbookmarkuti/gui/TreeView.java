/**
 * 
 */
package com.ldhuy.app.firefoxbookmarkuti.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import com.ldhuy.app.firefoxbookmarkuti.model.TreeViewModel;

/**
 * @author ldhuy
 *
 */
public class TreeView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4842193866029430484L;
	private JScrollPane scrollPane;
	private JTree tree;
	private TreeViewModel model;

	public TreeView() {
		this.tree = new JTree();
		this.scrollPane = new JScrollPane(this.tree);
		this.add(this.scrollPane);
		this.tree.setRootVisible(false);
		this.tree.setShowsRootHandles(false);
	}

	public void setModel(TreeViewModel model) {
		this.model = model;
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
}

/**
 * 
 */
package com.ldhuy.app.firefoxbookmarkuti.model;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * @author ldhuy
 *
 */
public class TreeViewModel implements TreeModel {
	private BookmarkDTO root;
	private static long nGetChildCountCalled = 0;

	/**
	 * @param root
	 */
	public TreeViewModel(BookmarkDTO root) {
		super();
		this.root = root;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#addTreeModelListener(javax.swing.event.
	 * TreeModelListener)
	 */
	public void addTreeModelListener(TreeModelListener arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
	 */
	public Object getChild(Object parent, int index) {
		if (index >= 0 && index < ((BookmarkDTO) parent).getChildren().size()) {
			return ((BookmarkDTO) parent).getChildren().get(index);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
	 */
	public int getChildCount(Object parent) {
		BookmarkDTO dto = (BookmarkDTO) parent;
		return (dto.getChildren() != null) ? dto.getChildren().size() : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object,
	 * java.lang.Object)
	 */
	public int getIndexOfChild(Object parent, Object child) {
		return ((BookmarkDTO) parent).getChildren().indexOf(child);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#getRoot()
	 */
	public Object getRoot() {
		return root;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
	 */
	public boolean isLeaf(Object node) {
		if (((BookmarkDTO) node).getType().equals(BookmarkType.PLACE)) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.tree.TreeModel#removeTreeModelListener(javax.swing.event.
	 * TreeModelListener)
	 */
	public void removeTreeModelListener(TreeModelListener arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath,
	 * java.lang.Object)
	 */
	public void valueForPathChanged(TreePath arg0, Object arg1) {

	}

}

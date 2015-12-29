/**
 * 
 */
package com.ldhuy.app.firefoxbookmarkuti.model;

import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * @author ldhuy
 *
 */
public class TreeViewModel implements TreeModel {
	private BookmarkDTO root;

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
		if (index >= 0) {
			BookmarkDTO dto = (BookmarkDTO) parent;
			List<BookmarkDTO> children = dto.getChildren();
			int idx = -1;
			if (children != null) {
				for (int i = 0; i < children.size(); ++i) {
					if (children.get(i).getType().equals(BookmarkType.CONTAINER.getValue())) {
						++idx;
						if (idx == index) {
							return children.get(i);
						}
					}
				}
			}
		}
		return null;
		// if (index >= 0 && index < ((BookmarkDTO)
		// parent).getChildren().size()) {
		// return ((BookmarkDTO) parent).getChildren().get(index);
		// }
		// return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
	 */
	public int getChildCount(Object parent) {
		BookmarkDTO dto = (BookmarkDTO) parent;
		int count = 0;
		if (dto.getChildren() != null) {
			for (BookmarkDTO b : dto.getChildren()) {
				if (b.getType().equals(BookmarkType.CONTAINER.getValue())) {
					++count;
				}
			}
			return count;
		} else {
			return 0;
		}
		// return (dto.getChildren() != null) ? dto.getChildren().size() : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object,
	 * java.lang.Object)
	 */
	public int getIndexOfChild(Object parent, Object child) {
		BookmarkDTO dto = (BookmarkDTO) parent;
		List<BookmarkDTO> children = dto.getChildren();
		int index = -1;
		if (children != null) {
			for (int i = 0; i < children.size(); ++i) {
				if (children.get(i).getType().equals(BookmarkType.CONTAINER.getValue())) {
					++index;
					if (children.get(i).equals(child)) {
						return index;
					}
				}
			}
		}
		return index;
		// return ((BookmarkDTO) parent).getChildren().indexOf(child);
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
		// if (((BookmarkDTO) node).getType().equals(BookmarkType.PLACE)) {
		// return true;
		// }
		// return false;
		BookmarkDTO dto = (BookmarkDTO) node;
		List<BookmarkDTO> children = dto.getChildren();
		if (children != null) {
			for (BookmarkDTO child : children) {
				if (child.getType().equals(BookmarkType.CONTAINER.getValue())) {
					return false;
				}
			}
			return true;
		} else {
			return true;
		}
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

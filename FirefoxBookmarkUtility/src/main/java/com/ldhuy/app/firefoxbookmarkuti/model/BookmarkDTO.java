/**
 * 
 */
package com.ldhuy.app.firefoxbookmarkuti.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ldhuy
 *
 */
public class BookmarkDTO {
	private String guid;
	private String title;
	private String index;
	private String dateAdded;
	private String lastModified;
	private String id;
	private String tags;
	private String type;
	private String uri;
	private List<BookmarkDTO> children;

	public Bookmark toBookmark() {
		Bookmark result = new Bookmark();
		result.setGuid(guid);
		result.setTitle(title);
		result.setIndex(Long.parseLong(index));
		result.setDateAdded(new Date(Long.parseLong(dateAdded)));
		result.setLastModified(new Date(Long.parseLong(lastModified)));
		result.setId(Long.parseLong(id));
		if (type.equals(BookmarkType.CONTAINER.getValue())) {
			result.setType(BookmarkType.CONTAINER);
		} else if (type.equals(BookmarkType.PLACE.getValue())) {
			result.setType(BookmarkType.PLACE);
		}
		result.setUri(uri);
		List<Bookmark> lChildren = new ArrayList<Bookmark>();
		if (children != null) {
			for (BookmarkDTO dto : children) {
				Bookmark b = dto.toBookmark();
				lChildren.add(b);
			}
		}
		result.setChildren(lChildren);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return title != null ? title : "(no title)";
	}

	/**
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * @param guid
	 *            the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @return the dateAdded
	 */
	public String getDateAdded() {
		return dateAdded;
	}

	/**
	 * @param dateAdded
	 *            the dateAdded to set
	 */
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	/**
	 * @return the lastModified
	 */
	public String getLastModified() {
		return lastModified;
	}

	/**
	 * @param lastModified
	 *            the lastModified to set
	 */
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri
	 *            the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the children
	 */
	public List<BookmarkDTO> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(List<BookmarkDTO> children) {
		this.children = children;
	}

}

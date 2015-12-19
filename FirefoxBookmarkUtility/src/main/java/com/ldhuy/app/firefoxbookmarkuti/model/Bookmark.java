/**
 * 
 */
package com.ldhuy.app.firefoxbookmarkuti.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ldhuy
 *
 */
public class Bookmark {
	private String guid;
	private String title;
	private Long index;
	private Date dateAdded;
	private Date lastModified;
	private Long id;
	private String tags;
	private BookmarkType type;
	private String uri;
	private List<Bookmark> children;

	public Map<Long, Bookmark> findByTitle(String query) {
		String[] subqueries = query.split(" ");
		Map<Long, Bookmark> result = new HashMap<Long, Bookmark>();
		boolean legit = true;
		for (String subquery : subqueries) {
			if (this.title == null || !StringUtils.containsIgnoreCase(this.title, subquery)) {
				legit = false;
				break;
			}
		}
		if (legit) {
			result.put(this.id, this);
		}

		for (Bookmark bm : children) {
			Map<Long, Bookmark> subResult = bm.findByTitle(query);
			if (subResult.size() > 0) {
				result.putAll(subResult);
			}
		}
		return result;
	}

	public Map<Long, Bookmark> findByURI(String query) {
		String[] subqueries = query.split(" ");
		Map<Long, Bookmark> result = new HashMap<Long, Bookmark>();
		boolean legit = true;
		for (String subquery : subqueries) {
			if (this.uri == null || !StringUtils.containsIgnoreCase(this.uri, subquery)) {
				legit = false;
				break;
			}
		}
		if (legit) {
			result.put(this.id, this);
		}

		for (Bookmark bm : children) {
			Map<Long, Bookmark> subResult = bm.findByURI(query);
			if (subResult.size() > 0) {
				result.putAll(subResult);
			}
		}
		return result;
	}

	public Map<Long, Bookmark> find(String query) {
		String[] subqueries = query.split(" ");
		Map<Long, Bookmark> result = new HashMap<Long, Bookmark>();

		// Find by title
		boolean legit = true;
		for (String subquery : subqueries) {
			if (this.title == null || !StringUtils.containsIgnoreCase(this.title, subquery)) {
				legit = false;
				break;
			}
		}
		if (legit) {
			result.put(this.id, this);
		}

		// Find by tags
		legit = true;
		for (String subquery : subqueries) {
			if (this.tags == null || !StringUtils.containsIgnoreCase(this.tags, subquery)) {
				legit = false;
				break;
			}
		}
		if (legit) {
			result.put(this.id, this);
		}

		// Find by URI
		legit = true;
		for (String subquery : subqueries) {
			if (this.uri == null || !StringUtils.containsIgnoreCase(this.uri, subquery)) {
				legit = false;
				break;
			}
		}
		if (legit) {
			result.put(this.id, this);
		}
		for (Bookmark bm : children) {
			Map<Long, Bookmark> subResult = bm.find(query);
			if (subResult.size() > 0) {
				result.putAll(subResult);
			}
		}

		return result;
	}
	
	/**
	 * @return: a BookmarkDTO object represent this Bookmark (with empty children)
	 */
	public BookmarkDTO toDTO() {
		BookmarkDTO dto = new BookmarkDTO();
		dto.setGuid(guid);
		dto.setTitle(title);
		dto.setIndex(index.toString());
		dto.setDateAdded(String.valueOf(dateAdded.getTime()));
		dto.setLastModified(String.valueOf(lastModified.getTime()));
		dto.setId(id.toString());
		dto.setTags(tags);
		dto.setType(type.toString());
		dto.setUri(uri);
		dto.setChildren(new ArrayList<BookmarkDTO>());
		return dto;
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
	public Long getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(Long index) {
		this.index = index;
	}

	/**
	 * @return the dateAdded
	 */
	public Date getDateAdded() {
		return dateAdded;
	}

	/**
	 * @param dateAdded
	 *            the dateAdded to set
	 */
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	/**
	 * @return the lastModified
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * @param lastModified
	 *            the lastModified to set
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the type
	 */
	public BookmarkType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(BookmarkType type) {
		this.type = type;
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
	public List<Bookmark> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(List<Bookmark> children) {
		this.children = children;
	}

}

/**
 * 
 */
package com.ldhuy.app.firefoxbookmarkuti.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ldhuy.app.firefoxbookmarkuti.model.Bookmark;
import com.ldhuy.app.firefoxbookmarkuti.model.BookmarkDTO;
import com.ldhuy.app.firefoxbookmarkuti.model.BookmarkType;

/**
 * @author ldhuy
 *
 */
public class BookmarkService {

	public static Map<Long, Bookmark> findByTitle(Bookmark root, String query) {
		String[] subqueries = query.split(" ");
		Map<Long, Bookmark> result = new HashMap<Long, Bookmark>();
		boolean legit = true;
		for (String subquery : subqueries) {
			if (root.getTitle() == null || !StringUtils.containsIgnoreCase(root.getTitle(), subquery)) {
				legit = false;
				break;
			}
		}
		if (legit) {
			result.put(root.getId(), root);
		}

		List<Bookmark> children = root.getChildren();
		for (Bookmark bm : children) {
			Map<Long, Bookmark> subResult = bm.findByTitle(query);
			if (subResult.size() > 0) {
				result.putAll(subResult);
			}
		}
		return result;
	}

	public static Map<Long, Bookmark> findByURI(Bookmark root, String query) {
		String[] subqueries = query.split(" ");
		Map<Long, Bookmark> result = new HashMap<Long, Bookmark>();
		boolean legit = true;
		for (String subquery : subqueries) {
			if (root.getUri() == null || !StringUtils.containsIgnoreCase(root.getUri(), subquery)) {
				legit = false;
				break;
			}
		}
		if (legit) {
			result.put(root.getId(), root);
		}

		List<Bookmark> children = root.getChildren();
		for (Bookmark bm : children) {
			Map<Long, Bookmark> subResult = bm.findByURI(query);
			if (subResult.size() > 0) {
				result.putAll(subResult);
			}
		}
		return result;
	}

	public static Map<Long, Bookmark> find(Bookmark root, String query) {
		String[] subqueries = query.split(" ");
		Map<Long, Bookmark> result = new HashMap<Long, Bookmark>();

		// Find by title
		if (root.getType() == BookmarkType.PLACE) {
			boolean legit = true;
			for (String subquery : subqueries) {
				if (root.getTitle() == null || !StringUtils.containsIgnoreCase(root.getTitle(), subquery)) {
					legit = false;
					break;
				}
			}
			if (legit) {
				result.put(root.getId(), root);
			}

			// Find by tags
			legit = true;
			for (String subquery : subqueries) {
				if (root.getTags() == null || !StringUtils.containsIgnoreCase(root.getTags(), subquery)) {
					legit = false;
					break;
				}
			}
			if (legit) {
				result.put(root.getId(), root);
			}

			// Find by URI
			legit = true;
			for (String subquery : subqueries) {
				if (root.getUri() == null || !StringUtils.containsIgnoreCase(root.getUri(), subquery)) {
					legit = false;
					break;
				}
			}
			if (legit) {
				result.put(root.getId(), root);
			}
		}

		if (root.getType() == BookmarkType.CONTAINER) {
			List<Bookmark> children = root.getChildren();
			for (Bookmark bm : children) {
				Map<Long, Bookmark> subResult = BookmarkService.find(bm, query);
				if (subResult.size() > 0) {
					result.putAll(subResult);
				}
			}
		}

		return result;
	}

	public static Map<Long, BookmarkDTO> find(BookmarkDTO root, String query) {
		String[] subqueries = query.split(" ");
		Map<Long, BookmarkDTO> result = new HashMap<Long, BookmarkDTO>();

		// Find by title
		if (root.getType().equals(BookmarkType.PLACE.getValue())) {
			boolean legit = true;
			for (String subquery : subqueries) {
				if (root.getTitle() == null || !StringUtils.containsIgnoreCase(root.getTitle(), subquery)) {
					legit = false;
					break;
				}
			}
			if (legit) {
				result.put(Long.valueOf(root.getId(), 10), root);
			}

			// Find by tags
			legit = true;
			for (String subquery : subqueries) {
				if (root.getTags() == null || !StringUtils.containsIgnoreCase(root.getTags(), subquery)) {
					legit = false;
					break;
				}
			}
			if (legit) {
				result.put(Long.valueOf(root.getId(), 10), root);
			}

			// Find by URI
			legit = true;
			for (String subquery : subqueries) {
				if (root.getUri() == null || !StringUtils.containsIgnoreCase(root.getUri(), subquery)) {
					legit = false;
					break;
				}
			}
			if (legit) {
				result.put(Long.valueOf(root.getId(), 10), root);
			}
		}

		if (root.getType().equals(BookmarkType.CONTAINER.getValue())) {
			List<BookmarkDTO> children = root.getChildren();
			if (children != null) {
				for (BookmarkDTO bm : children) {
					Map<Long, BookmarkDTO> subResult = BookmarkService.find(bm, query);
					if (subResult.size() > 0) {
						result.putAll(subResult);
					}
				}
			}
		}

		return result;
	}

}

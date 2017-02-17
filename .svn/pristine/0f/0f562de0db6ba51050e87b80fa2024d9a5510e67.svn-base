package com.injedu.bookproduce.core.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成书本数据
 *
 * @author joy.zhou
 * @date 2016年8月10日
 * @version 1.0
 *
 */
public class ProduceBookData {

	private ProduceBook book;

	private List<ProduceBookCatalog> catalogList;

	private Map<Long, List<ProduceBookArticle>> articles = new LinkedHashMap<>();

	public ProduceBookData(ProduceBook book, List<ProduceBookCatalog> catalogList) {
		this.book = book;
		this.catalogList = catalogList;
		this.initArticles(catalogList);
	}

	public ProduceBook getBook() {
		return book;
	}

	public List<ProduceBookCatalog> getCatalogList() {
		return catalogList;
	}

	public Map<Long, List<ProduceBookArticle>> getArticles() {
		return articles;
	}

	/**
	 * 初始化目录章节信息
	 * 
	 * @param catalogList
	 */
	private void initArticles(List<ProduceBookCatalog> catalogList) {

		if (catalogList == null || catalogList.isEmpty()) {
			return;
		}

		for (ProduceBookCatalog catalog : catalogList) {

			if (!catalog.getChilds().isEmpty()) {

				initArticles(catalog.getChilds());

			} else {

				if (catalog.getArticles().isEmpty()) {
					// 目录下没有章节
					continue;
				}

				this.articles.put(catalog.getId(), catalog.getArticles());

			}

		}

	}
}

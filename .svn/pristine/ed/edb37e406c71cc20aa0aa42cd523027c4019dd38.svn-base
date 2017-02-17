package com.injedu.bookproduce.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProduceBookCatalog implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 目录ID */
	private Long id;
	/** 目录名 */
	private String name;
	/** 子目录 */
	private List<ProduceBookCatalog> childs = new ArrayList<>(0);
	/** 书本章节内容 */
	private List<ProduceBookArticle> articles = new ArrayList<>(0);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProduceBookCatalog> getChilds() {
		return childs;
	}

	public void setChilds(List<ProduceBookCatalog> childs) {
		this.childs = childs;
	}

	public List<ProduceBookArticle> getArticles() {
		return articles;
	}

	public void setArticles(List<ProduceBookArticle> articles) {
		this.articles = articles;
	}

}

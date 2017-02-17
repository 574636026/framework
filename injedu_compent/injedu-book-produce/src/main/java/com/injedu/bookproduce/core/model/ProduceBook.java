package com.injedu.bookproduce.core.model;

import java.io.Serializable;

/**
 * 书本
 * 
 * @author joy.zhou
 * @date 2016年8月4日
 * @version 1.0
 *
 */
public class ProduceBook implements Serializable {

	private static final long serialVersionUID = 1L;
	/** ID */
	private Long id;
	/** 书名 */
	private String name;
	/** 书本封面 */
	private String cover;
	/** 简介 */
	private String brief;
	/** 作者 */
	private String author;
	/** 学科ID */
	private Integer subjectId;
	/** 学科名称 */
	private String subjectName;
	/** 最后更新时间 */
	private String updateTime;
	/** 压缩包版本 */
	private Integer zipVersion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getZipVersion() {
		return zipVersion;
	}

	public void setZipVersion(Integer zipVersion) {
		this.zipVersion = zipVersion;
	}

}

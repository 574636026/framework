package com.injedu.core.dto.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 菜单对象
 *
 * @author joy.zhou
 * @date 2015年11月24日
 * @version 1.0
 *
 */
public class MenuDto implements Serializable {

	private static final long serialVersionUID = 1L;
	/** ID */
	private Long id;
	/** 菜单名称 */
	private String name;
	/** 菜单地址 */
	private String url;
	/** 父ID */
	private Long parentId;
	/** 子菜单 */
	private List<MenuDto> childs = new ArrayList<MenuDto>(0);

	public MenuDto() {
	}

	public MenuDto(Long id, String name, String url, Long parentId) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.parentId = parentId;
	}

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<MenuDto> getChilds() {
		return childs;
	}

	public void setChilds(List<MenuDto> childs) {
		this.childs = childs;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public boolean getHasChilds() {

		return childs.size() > 0;
	}

	@Override
	public String toString() {
		return "MenuDto [id=" + id + ", name=" + name + ", url=" + url + ", parentId=" + parentId + ", childs=" + childs
				+ "]";
	}

}

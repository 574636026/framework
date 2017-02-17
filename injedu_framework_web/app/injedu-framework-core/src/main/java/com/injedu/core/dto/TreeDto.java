package com.injedu.core.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 树形实体类
 *
 * @author joy.zhou
 * @date 2016年4月27日
 * @version 1.0
 *
 */
public class TreeDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/** ID */
	private String id;
	/** 标题 */
	private String title;
	/** 类型 */
	private TYPE type = TYPE.folder;
	/** 附加信息 */
	private Map<String, Object> attrs = new HashMap<>(0);

	public TreeDto(String id, String title) {
		this.id = id;
		this.title = title;
	}

	public TreeDto(String id, String title, TYPE type) {
		this.id = id;
		this.title = title;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public Map<String, Object> getAttrs() {
		return attrs;
	}

	public void setAttrs(Map<String, Object> attrs) {
		this.attrs = attrs;
	}

	public static enum TYPE {
		folder, item
	}

}

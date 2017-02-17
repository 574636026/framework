package com.injedu.supports.push.entity;

/***
 * 
 * 推送标签信息
 *
 * @author joy.zhou
 * @date 2015年11月3日
 * @version 1.0
 *
 */
public class PushTag {

	/** 标签ID */
	private String tagId;
	/** 标签名称 */
	private String tagName;
	/** 用于进一步描述标签的附件信息 */
	private String info;
	/** 标签创建时间 */
	private String createTime;

	public PushTag() {
	}

	public PushTag(String tagId, String tagName, String info, String createTime) {
		this.tagId = tagId;
		this.tagName = tagName;
		this.info = info;
		this.createTime = createTime;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "PushTag [tagId=" + tagId + ", tagName=" + tagName + ", info=" + info + ", createTime=" + createTime
				+ "]";
	}

}

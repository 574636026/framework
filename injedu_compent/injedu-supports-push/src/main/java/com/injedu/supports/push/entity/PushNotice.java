package com.injedu.supports.push.entity;

import java.util.Map;

import com.injedu.supports.push.constant.NoticeType;

/**
 * 通知消息
 *
 * @author joy.zhou
 * @date 2015年11月2日
 * @version 1.0
 *
 */
public class PushNotice {

	/** 消息标题 (必须) */
	private String title;
	/** 消息类型(必须),默认为通知 */
	private NoticeType type = NoticeType.NOTIFICATION;
	/** 消息摘要 */
	private String description;
	/** 消息内容 */
	private String content;
	/** 推送时间 :格式 yyyy-MM-dd HH:mm:ss */
	private String startTime;
	/** 截止时间 :格式 yyyy-MM-dd HH:mm:ss */
	private String endTime;
	/** 自定义参数 */
	private Map<String, String> customs;

	/**
	 * 
	 * 初始化通知消息
	 * 
	 * @param title
	 *            消息标题
	 */
	public PushNotice(String title) {
		this.title = title;
	}

	/**
	 * 
	 * 初始化通知消息
	 * 
	 * @param title
	 *            消息标题
	 * @param description
	 *            消息摘要
	 */
	public PushNotice(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public NoticeType getType() {
		return type;
	}

	public void setType(NoticeType type) {
		this.type = type;
	}

	public String getDescription() {
		return description == null ? title : description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Map<String, String> getCustoms() {
		return customs;
	}

	public void setCustoms(Map<String, String> customs) {
		this.customs = customs;
	}

}

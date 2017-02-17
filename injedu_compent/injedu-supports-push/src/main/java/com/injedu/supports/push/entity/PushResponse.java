package com.injedu.supports.push.entity;

import com.injedu.utils.enums.common.DeviceType;

/**
 * 推送消息返回信息
 *
 * @author joy.zhou
 * @date 2015年11月2日
 * @version 1.0
 *
 */
public class PushResponse {

	/** 返回代码 */
	private ReturnCode returnCode = ReturnCode.ERROR;
	/** 操作消息 */
	private String returnMsg;
	/** 此次消息的ID识别码 */
	private String msgId;
	/** 任务ID */
	private String taskId;
	/** 发送时间 */
	private String sendTime;
	/** 设备类型 */
	private DeviceType deviceType;
	/** 请求ID */
	private String requestId;

	public PushResponse() {
	}

	public PushResponse(ReturnCode returnCode, String returnMsg) {
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
	}

	public PushResponse(ReturnCode returnCode, String returnMsg, String msgId) {
		this(returnCode, returnMsg);
		this.msgId = msgId;
	}

	public PushResponse(ReturnCode returnCode, String returnMsg, String msgId, String taskId, String sendTime) {
		this(returnCode, returnMsg, msgId);
		this.taskId = taskId;
		this.sendTime = sendTime;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public ReturnCode getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(ReturnCode returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public static enum ReturnCode {
		SUCCESS, ERROR
	}

	@Override
	public String toString() {
		return "PushResponse [requestId=" + requestId + ", msgId=" + msgId + ", taskId=" + taskId + ", sendTime="
				+ sendTime + ", returnCode=" + returnCode + ", returnMsg=" + returnMsg + ", deviceType=" + deviceType
				+ "]";
	}

}

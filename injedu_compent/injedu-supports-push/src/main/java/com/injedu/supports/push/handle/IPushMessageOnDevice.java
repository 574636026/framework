package com.injedu.supports.push.handle;

import com.injedu.supports.push.entity.PushMessageInfo;
import com.injedu.supports.push.entity.PushNotice;
import com.injedu.supports.push.entity.PushResponse;
import com.injedu.supports.push.exception.PushException;
import com.injedu.utils.enums.common.DeviceType;

/**
 * 
 * 消息推送接口（单个设备类型）
 * 
 * 比如只推送Android设备
 *
 * @author joy.zhou
 * @date 2015年11月5日
 * @version 1.0
 *
 */
public interface IPushMessageOnDevice {

	/**
	 * 单播推送
	 * 
	 * @param notice
	 *            通知消息
	 * @param deviceType
	 *            设备类型
	 * @param deviceToken
	 *            设备ID
	 * @throws PushException
	 */
	public PushResponse pushMessageUnicast(PushNotice notice, DeviceType deviceType, String deviceToken)
			throws PushException;

	/**
	 * 列播推送
	 * 
	 * @param notice
	 *            通知消息
	 * @param deviceType
	 *            设备类型
	 * @param deviceTokens
	 *            设备ID列表
	 * @throws PushException
	 */
	public PushResponse pushMessageListcast(PushNotice notice, DeviceType deviceType, String[] deviceTokens)
			throws PushException;

	/**
	 * 广播推送
	 * 
	 * @param notice
	 *            通知消息
	 * @param deviceType
	 *            设备类型
	 * @throws PushException
	 */
	public PushResponse pushMessageBroadcast(PushNotice notice, DeviceType deviceType) throws PushException;

	/**
	 * 
	 * 标签推送
	 * 
	 * @param notice
	 *            通知消息
	 * @param deviceType
	 *            设备类型
	 * @param tagName
	 *            标签名称
	 * @return
	 * @throws PushException
	 */
	public PushResponse pushMessageTagcast(PushNotice notice, DeviceType deviceType, String tagName)
			throws PushException;

	/**************************************************************************
	 * 
	 * 消息推送中关于统计操作
	 * 
	 ***************************************************************************/

	/**
	 * 查询消息状态
	 * 
	 * @param deviceType
	 *            设备类型
	 * @param taskId
	 *            消息ID
	 * @throws PushException
	 */
	public PushMessageInfo queryMessageStatus(DeviceType deviceType, String taskId) throws PushException;

}

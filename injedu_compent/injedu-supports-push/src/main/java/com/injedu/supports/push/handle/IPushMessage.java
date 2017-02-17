package com.injedu.supports.push.handle;

import java.util.List;

import com.injedu.supports.push.entity.PushNotice;
import com.injedu.supports.push.entity.PushResponse;
import com.injedu.supports.push.exception.PushException;

/**
 * 
 * 消息推送接口
 *
 * @author joy.zhou
 * @date 2015年11月2日
 * @version 1.0
 *
 */
public interface IPushMessage extends IPushMessageOnDevice {

	/**
	 * 推送消息给所有设备
	 * 
	 * @param notice
	 *            通知消息
	 * @throws PushException
	 */
	public List<PushResponse> pushMessageBroadcast(PushNotice notice) throws PushException;

	/**
	 * 推送消息给指定标签设备
	 * 
	 * @param notice
	 *            通知消息
	 * @param tagName
	 *            标签名(1-128字节,不允许为"default")
	 * @throws PushException
	 */
	public List<PushResponse> pushMessageTagcast(PushNotice notice, String tagName) throws PushException;

}

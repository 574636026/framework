package com.injedu.supports.push.handle.ym.ios;

import com.injedu.supports.push.exception.PushException;
import com.injedu.supports.push.handle.ym.IOSNotification;

/**
 * IOS 广播消息
 *
 * @author joy.zhou
 * @date 2016年1月14日
 * @version 1.0
 *
 */
public class IOSBroadcast extends IOSNotification {
	public IOSBroadcast(String appkey, String appMasterSecret) throws PushException {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "broadcast");

	}
}

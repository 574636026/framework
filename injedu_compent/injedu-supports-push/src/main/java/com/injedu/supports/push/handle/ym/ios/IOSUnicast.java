package com.injedu.supports.push.handle.ym.ios;

import com.injedu.supports.push.exception.PushException;
import com.injedu.supports.push.handle.ym.IOSNotification;

/**
 *
 * IOS单播
 *
 * @author joy.zhou
 * @date 2016年1月14日
 * @version 1.0
 *
 */
public class IOSUnicast extends IOSNotification {

	public IOSUnicast(String appkey, String appMasterSecret) throws PushException {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "unicast");
	}

	public void setDeviceToken(String token) throws PushException {
		setPredefinedKeyValue("device_tokens", token);
	}
}

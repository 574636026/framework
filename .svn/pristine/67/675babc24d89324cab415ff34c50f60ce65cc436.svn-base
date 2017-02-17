package com.injedu.supports.push.handle.ym.ios;

import org.apache.commons.lang3.StringUtils;

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
public class IOSListcast extends IOSNotification {

	public IOSListcast(String appkey, String appMasterSecret) throws PushException {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "listcast");
	}

	public void setDeviceToken(String[] token) throws PushException {
		setPredefinedKeyValue("device_tokens", StringUtils.join(token));
	}
}

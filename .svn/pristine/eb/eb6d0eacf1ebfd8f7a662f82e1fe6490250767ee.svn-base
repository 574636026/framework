package com.injedu.supports.push.handle.ym.android;

import org.apache.commons.lang3.StringUtils;

import com.injedu.supports.push.exception.PushException;
import com.injedu.supports.push.handle.ym.AndroidNotification;

/**
 * 
 * Android 多播消息
 *
 * @date 2016年1月14日
 * @version 1.0
 *
 */
public class AndroidListcast extends AndroidNotification {

	public AndroidListcast(String appkey, String appMasterSecret) throws PushException {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "listcast");
	}

	public void setDeviceToken(String[] token) throws PushException {
		setPredefinedKeyValue("device_tokens", StringUtils.join(token));
	}
}

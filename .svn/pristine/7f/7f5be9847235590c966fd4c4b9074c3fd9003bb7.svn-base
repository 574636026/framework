package com.injedu.supports.push.handle.ym.android;

import com.injedu.supports.push.exception.PushException;
import com.injedu.supports.push.handle.ym.AndroidNotification;

/**
 * 
 * Android 广播消息
 *
 * @date 2016年1月14日
 * @version 1.0
 *
 */
public class AndroidBroadcast extends AndroidNotification {

	public AndroidBroadcast(String appkey, String appMasterSecret) throws PushException {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "broadcast");
	}
}

package com.injedu.supports.push.handle.ym.android;

import com.alibaba.fastjson.JSONObject;
import com.injedu.supports.push.exception.PushException;
import com.injedu.supports.push.handle.ym.AndroidNotification;

/**
 * 
 * Android 组播消息
 *
 * @date 2016年1月14日
 * @version 1.0
 *
 */
public class AndroidGroupcast extends AndroidNotification {
	public AndroidGroupcast(String appkey, String appMasterSecret) throws PushException {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "groupcast");
	}

	public void setFilter(JSONObject filter) throws PushException {
		setPredefinedKeyValue("filter", filter);
	}
}

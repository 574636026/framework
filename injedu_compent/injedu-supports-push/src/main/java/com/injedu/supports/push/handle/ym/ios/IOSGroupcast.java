package com.injedu.supports.push.handle.ym.ios;

import com.alibaba.fastjson.JSONObject;
import com.injedu.supports.push.exception.PushException;
import com.injedu.supports.push.handle.ym.IOSNotification;

/**
 * IOS组播
 *
 * @author joy.zhou
 * @date 2016年1月14日
 * @version 1.0
 *
 */
public class IOSGroupcast extends IOSNotification {
	public IOSGroupcast(String appkey, String appMasterSecret) throws PushException {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "groupcast");
	}

	public void setFilter(JSONObject filter) throws PushException {
		setPredefinedKeyValue("filter", filter);
	}
}

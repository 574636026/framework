package com.injedu.supports.push.handle.ym.android;

import com.injedu.supports.push.exception.PushException;
import com.injedu.supports.push.handle.ym.AndroidNotification;

/**
 * 
 * Android 自定义(标签)消息
 *
 * @author joy.zhou
 * @date 2016年1月14日
 * @version 1.0
 *
 */
public class AndroidCustomizedcast extends AndroidNotification {

	public AndroidCustomizedcast(String appkey, String appMasterSecret) throws PushException {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "customizedcast");
	}

	public void setAlias(String alias, String aliasType) throws PushException {
		setPredefinedKeyValue("alias", alias);
		setPredefinedKeyValue("alias_type", aliasType);
	}

	public void setFileId(String fileId, String aliasType) throws PushException {
		setPredefinedKeyValue("file_id", fileId);
		setPredefinedKeyValue("alias_type", aliasType);
	}

}

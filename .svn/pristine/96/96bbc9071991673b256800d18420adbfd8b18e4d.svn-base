package com.injedu.supports.push.handle.ym.android;

import com.injedu.supports.push.exception.PushException;
import com.injedu.supports.push.handle.ym.AndroidNotification;

/**
 * 
 * Android 文件播
 *
 * @date 2016年1月14日
 * @version 1.0
 *
 */
public class AndroidFilecast extends AndroidNotification {
	public AndroidFilecast(String appkey, String appMasterSecret) throws PushException {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "filecast");
	}

	public void setFileId(String fileId) throws PushException {
		setPredefinedKeyValue("file_id", fileId);
	}
}
package com.injedu.supports.push.handle.ym.ios;

import com.injedu.supports.push.exception.PushException;
import com.injedu.supports.push.handle.ym.IOSNotification;

/**
 * 
 * IOS 文件播
 *
 * @author joy.zhou
 * @date 2016年1月14日
 * @version 1.0
 *
 */
public class IOSFilecast extends IOSNotification {
	public IOSFilecast(String appkey, String appMasterSecret) throws PushException {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "filecast");
	}

	public void setFileId(String fileId) throws PushException {
		setPredefinedKeyValue("file_id", fileId);
	}
}

package com.injedu.supports.push;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.injedu.supports.push.entity.PushResponse;
import com.injedu.supports.push.handle.ym.YMPushMessage;

/**
 * 友盟推送测试
 *
 * @author joy.zhou
 * @date 2016年1月18日
 * @version 1.0
 *
 */
public class YMPushMessageCommon {

	protected final Logger logger = LoggerFactory.getLogger(YMPushMessageAndroidTest.class);

	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String appkey_ios = "";
	private String appMasterSecret_ios = "";
	private String appkey_android = "";
	private String appMasterSecret_android = "";
	private boolean productionMode = false;

	protected YMPushMessage pushMessage = new YMPushMessage(appkey_ios, appMasterSecret_ios, appkey_android,
			appMasterSecret_android, productionMode);

	protected static final int RES_SUCCESS = 200;

	/**
	 * 创建消息
	 * 
	 * @param msg
	 * @param args
	 * @return
	 */
	protected String createMessage(String msg, Object... args) {

		String time = sf.format(new Date());

		return String.format(msg + "-" + time, args);

	}

	/**
	 * 校验返回结果
	 * 
	 * @param res
	 */
	protected void assertTrue(PushResponse res) {

		Assert.assertTrue(res.getReturnCode() == PushResponse.ReturnCode.SUCCESS);

	}
}

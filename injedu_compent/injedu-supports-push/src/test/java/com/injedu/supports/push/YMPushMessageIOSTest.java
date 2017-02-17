package com.injedu.supports.push;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.injedu.supports.push.entity.PushMessageInfo;
import com.injedu.supports.push.entity.PushNotice;
import com.injedu.supports.push.entity.PushResponse;
import com.injedu.supports.push.exception.PushException;
import com.injedu.utils.enums.common.DeviceType;

/**
 * 友盟推送Andrdoi测试
 *
 * @author joy.zhou
 * @date 2016年1月15日
 * @version 1.0
 *
 */
public class YMPushMessageIOSTest extends YMPushMessageCommon {

	// 设备号 44位
	private String deviceToken = "30e5755ac034098623a57da5fd70fc03e77a673238aaab5420eeb8b6392a8eec";

	private String title = "IOS";

	private DeviceType deviceType = DeviceType.IOS;

	private String tagName = "测试标签";

	@Test
	public void testEmpty() {

	}

	/**
	 * 广播推送
	 * 
	 * @throws PushException
	 */
	// @Test
	public void testPushMessageBroadcast() throws PushException {

		PushNotice notice = new PushNotice(createMessage("测试服务端%s广播推送", title));

		PushResponse res = pushMessage.pushMessageBroadcast(notice, deviceType);

		assertTrue(res);

	}

	/**
	 * 单播推送
	 * 
	 * @throws PushException
	 */
	// @Test
	public void testPushMessageUnicast() throws PushException {

		PushNotice notice = new PushNotice(createMessage("测试服务端%s单播推送", title));

		PushResponse res = pushMessage.pushMessageUnicast(notice, deviceType, deviceToken);

		assertTrue(res);
	}

	/**
	 * 列播推送
	 * 
	 * @throws PushException
	 */
	// @Test
	public void testPushMessageListcast() throws PushException {

		PushNotice notice = new PushNotice(createMessage("测试服务端%s列播推送", title));

		PushResponse res = pushMessage.pushMessageListcast(notice, deviceType, new String[] { deviceToken });

		assertTrue(res);
	}

	/**
	 * 标签推送
	 * 
	 * @throws PushException
	 */
	// @Test
	public void testPushMessageTagcast() throws PushException {

		PushNotice notice = new PushNotice(createMessage("测试服务端%s标签推送", title));

		PushResponse res = pushMessage.pushMessageTagcast(notice, deviceType, tagName);

		assertTrue(res);
	}

	/**
	 * 查询消息状态
	 * 
	 * @throws PushException
	 */
	// @Test
	public void testQueryMessageStatus() throws PushException {

		String taskId = "us92657145310685569601";

		PushMessageInfo info = pushMessage.queryMessageStatus(deviceType, taskId);

		logger.info(info.toString());
	}

	/**
	 * 添加设备标签
	 * 
	 * @throws PushException
	 */
	// @Test
	public void testTagAdd() throws PushException {

		PushResponse res = pushMessage.tagAdd(tagName, deviceType, new String[] { deviceToken });

		assertTrue(res);
	}

	/**
	 * 查询设备标签
	 * 
	 * @throws PushException
	 */
	// @Test
	public void testTagQueryList() throws PushException {
		List<String> tags = pushMessage.tagQueryList(deviceType, deviceToken);
		Assert.assertNotNull(tags);
		logger.info(tags.toString());
	}

	/**
	 * 删除设备指定标签
	 * 
	 * @throws PushException
	 */
	// @Test
	public void testTagDelete() throws PushException {

		PushResponse res = pushMessage.tagDelete(tagName, deviceType, deviceToken);
		assertTrue(res);
	}

	/**
	 * 清除设备所有标签
	 * 
	 * @throws PushException
	 */
	// @Test
	public void testTagClear() throws PushException {

		PushResponse res = pushMessage.tagClear(deviceType, deviceToken);
		assertTrue(res);
	}

}

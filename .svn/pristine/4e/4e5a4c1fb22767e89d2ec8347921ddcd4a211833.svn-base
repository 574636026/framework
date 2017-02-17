package com.injedu.supports.push.handle.ym;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.injedu.supports.push.constant.NoticeType;
import com.injedu.supports.push.entity.PushMessageInfo;
import com.injedu.supports.push.entity.PushNotice;
import com.injedu.supports.push.entity.PushResponse;
import com.injedu.supports.push.exception.PushException;
import com.injedu.supports.push.handle.IPushMessage;
import com.injedu.supports.push.handle.ym.AndroidNotification;
import com.injedu.supports.push.handle.ym.IOSNotification;
import com.injedu.supports.push.handle.ym.PushClient;
import com.injedu.supports.push.handle.ym.UmengNotification;
import com.injedu.supports.push.handle.ym.android.AndroidBroadcast;
import com.injedu.supports.push.handle.ym.android.AndroidGroupcast;
import com.injedu.supports.push.handle.ym.android.AndroidListcast;
import com.injedu.supports.push.handle.ym.android.AndroidUnicast;
import com.injedu.supports.push.handle.ym.constant.FilterType;
import com.injedu.supports.push.handle.ym.filter.FilterEntity;
import com.injedu.supports.push.handle.ym.filter.FiterQuery;
import com.injedu.supports.push.handle.ym.ios.IOSBroadcast;
import com.injedu.supports.push.handle.ym.ios.IOSGroupcast;
import com.injedu.supports.push.handle.ym.ios.IOSListcast;
import com.injedu.supports.push.handle.ym.ios.IOSUnicast;
import com.injedu.utils.enums.common.DeviceType;
import com.injedu.utils.supports.SecurityKey;

/**
 * 
 * 友盟消息推送实现
 * 
 * 对于没有上线的App(集成个数小于200)，没有限制
 * 
 * 对于已经上线的App,广播每天不超过3次,单播目前没有限制, 对于任务(非单播),每分钟不能超过5次。
 * 
 * 离线消息默认3天后失效
 *
 * @author joy.zhou
 * @date 2016年1月14日
 * @version 1.0
 *
 */
public class YMPushMessage implements IPushMessage {

	private Logger logger = LoggerFactory.getLogger(YMPushMessage.class);

	private PushClient client = new PushClient();
	/** ios key */
	private SecurityKey ios;
	/** android key */
	private SecurityKey android;
	/** true:正式模式,false:测试模式 */
	private boolean productionMode;
	/** 多播设备最大数量 */
	private static int MAX_LISTCAST = 500;

	public YMPushMessage() {
	};

	public YMPushMessage(String appkey_ios, String appMasterSecret_ios, String appkey_android,
			String appMasterSecret_android, boolean productionMode) {
		this.ios = new SecurityKey(appkey_ios, appMasterSecret_ios);
		this.android = new SecurityKey(appkey_android, appMasterSecret_android);
		this.productionMode = productionMode;
	}

	@Override
	public PushResponse pushMessageUnicast(PushNotice notice, DeviceType deviceType, String deviceToken)
			throws PushException {

		String res = null;

		if (DeviceType.ANDROID == deviceType) {
			// 构造消息体
			AndroidUnicast request = new AndroidUnicast(android.getAppkey(), android.getSecret());
			// 构建统一参数
			buildAndroidRequest(request, notice);
			// 设置设备号
			request.setDeviceToken(deviceToken);

			res = client.send(request);

		} else if (DeviceType.IOS == deviceType) {
			// 构造消息体
			IOSUnicast request = new IOSUnicast(ios.getAppkey(), ios.getSecret());
			// 构建统一参数
			buildIOSRequest(request, notice);
			// 设置设备号
			request.setDeviceToken(deviceToken);

			res = client.send(request);

		}

		return parsetResponse(res, deviceType);
	}

	@Override
	public PushResponse pushMessageListcast(PushNotice notice, DeviceType deviceType, String[] deviceTokens)
			throws PushException {

		String res = null;

		if (deviceTokens.length > MAX_LISTCAST) {
			throw new PushException("推送到设备数量不能超过" + MAX_LISTCAST);
		}

		if (DeviceType.ANDROID == deviceType) {
			// 构造消息体
			AndroidListcast request = new AndroidListcast(android.getAppkey(), android.getSecret());
			// 构建统一参数
			buildAndroidRequest(request, notice);
			// 设置设备号
			request.setDeviceToken(deviceTokens);

			res = client.send(request);

		} else if (DeviceType.IOS == deviceType) {
			// 构造消息体
			IOSListcast request = new IOSListcast(ios.getAppkey(), ios.getSecret());
			// 构建统一参数
			buildIOSRequest(request, notice);
			// 设置设备号
			request.setDeviceToken(deviceTokens);

			res = client.send(request);
		}
		return parsetResponse(res, deviceType);
	}

	@Override
	public PushResponse pushMessageBroadcast(PushNotice notice, DeviceType deviceType) throws PushException {

		String res = null;

		if (DeviceType.ANDROID == deviceType) {
			// 构造消息体
			AndroidBroadcast request = new AndroidBroadcast(android.getAppkey(), android.getSecret());
			// 构建统一参数
			buildAndroidRequest(request, notice);

			res = client.send(request);

		} else if (DeviceType.IOS == deviceType) {

			IOSBroadcast request = new IOSBroadcast(ios.getAppkey(), ios.getSecret());
			// 构建统一参数
			buildIOSRequest(request, notice);

			res = client.send(request);

		}

		return parsetResponse(res, deviceType);
	}

	/**
	 * 
	 * 当消息发送的类型为任务时，包括"broadcast","groupcast",
	 * "filecast","customizedcast(通过file_id传参)"时, 可以通过"task_id"来查询当前的消息状态。
	 * 注意,当次发送任务如果发送总量小于500个的话，后台会按照列播的方式推送，不再按照任务的方式来处理。 该接口会不生效。
	 * 
	 */
	@Override
	public PushMessageInfo queryMessageStatus(DeviceType deviceType, String taskId) throws PushException {

		String resJson = null;

		if (DeviceType.ANDROID == deviceType) {

			resJson = client.queryStatus(android.getAppkey(), android.getSecret(), taskId);

		} else if (DeviceType.IOS == deviceType) {
			resJson = client.queryStatus(ios.getAppkey(), ios.getSecret(), taskId);
		}

		logger.debug("res == " + resJson);

		PushMessageInfo res = new PushMessageInfo();

		if (StringUtils.isBlank(resJson)) {
			return res;
		}

		JSONObject result = JSONObject.parseObject(resJson);

		String ret = result.getString("ret");

		JSONObject data = result.getJSONObject("data");

		if ("FAIL".equals(ret)) {

			throw new PushException("查询错误,错误码 [" + data.getString("error_code") + "]");
		}

		res.setTaskId(taskId);
		res.setStatus(data.getInteger("status"));
		res.setPushCount(data.getInteger("accept_count"));
		res.setArriveCount(data.getInteger("sent_count"));
		res.setClickCount(data.getInteger("open_count"));
		res.setDelCount(data.getInteger("dismiss_count"));

		return res;
	}

	@Override
	public List<PushResponse> pushMessageBroadcast(PushNotice notice) throws PushException {

		List<PushResponse> res = new ArrayList<PushResponse>(2);

		res.add(this.pushMessageBroadcast(notice, DeviceType.ANDROID));
		res.add(this.pushMessageBroadcast(notice, DeviceType.IOS));

		return res;
	}

	@Override
	public List<PushResponse> pushMessageTagcast(PushNotice notice, String tagName) throws PushException {
		List<PushResponse> res = new ArrayList<PushResponse>(2);

		res.add(this.pushMessageTagcast(notice, DeviceType.ANDROID, tagName));
		res.add(this.pushMessageTagcast(notice, DeviceType.IOS, tagName));

		return res;
	}

	@Override
	public PushResponse pushMessageTagcast(PushNotice notice, DeviceType deviceType, String tagName)
			throws PushException {

		if (StringUtils.isBlank(tagName)) {
			throw new PushException("参数tagName不能为空!");
		}

		String res = null;

		FiterQuery filter = new FiterQuery();

		filter.and(new FilterEntity(FilterType.TAG, tagName));

		if (DeviceType.ANDROID == deviceType) {
			// 构造消息体
			AndroidGroupcast request = new AndroidGroupcast(android.getAppkey(), android.getSecret());
			// 构建统一参数
			buildAndroidRequest(request, notice);
			// 设置标签参数
			request.setFilter(filter.toJsonObject());

			res = client.send(request);

		} else if (DeviceType.IOS == deviceType) {

			IOSGroupcast request = new IOSGroupcast(ios.getAppkey(), ios.getSecret());
			// 构建统一参数
			buildIOSRequest(request, notice);
			// 设置标签参数
			request.setFilter(filter.toJsonObject());

			res = client.send(request);
		}

		return parsetResponse(res, deviceType);

	}

	/**************************************************************************
	 * 
	 * 消息推送中 关于标签的操作
	 * 
	 * 由于每个推送类实现方式不同,因此不作为接口方法
	 * 
	 ***************************************************************************/

	/**
	 * 添加设备标签
	 * 
	 * @param tagName
	 *            标签名称
	 * @param deviceTokens
	 *            设备列表
	 * @return
	 * @throws PushException
	 */
	public PushResponse tagAdd(String tagName, DeviceType deviceType, String[] deviceTokens) throws PushException {

		String res = null;

		if (DeviceType.ANDROID == deviceType) {
			res = client.addTags(android.getAppkey(), android.getSecret(), tagName, deviceTokens);
		} else if (DeviceType.IOS == deviceType) {
			res = client.addTags(ios.getAppkey(), ios.getSecret(), tagName, deviceTokens);
		}

		return parsetResponse(res, deviceType);
	}

	/**
	 * 删除设备标签
	 * 
	 * @param tagName
	 *            标签名称
	 * @param deviceToken
	 *            设备号
	 * @return
	 * @throws PushException
	 */
	public PushResponse tagDelete(String tagName, DeviceType deviceType, String deviceToken) throws PushException {
		String res = null;

		if (DeviceType.ANDROID == deviceType) {
			res = client.deleteTag(android.getAppkey(), android.getSecret(), tagName, deviceToken);
		} else if (DeviceType.IOS == deviceType) {
			res = client.deleteTag(ios.getAppkey(), ios.getSecret(), tagName, deviceToken);
		}
		return parsetResponse(res, deviceType);
	}

	/**
	 * 删除设备所有标签
	 * 
	 * @param deviceToken
	 * @return
	 * @throws PushException
	 */
	public PushResponse tagClear(DeviceType deviceType, String deviceToken) throws PushException {
		String res = null;
		if (DeviceType.ANDROID == deviceType) {
			res = client.clearTag(android.getAppkey(), android.getSecret(), deviceToken);
		} else if (DeviceType.IOS == deviceType) {
			res = client.clearTag(ios.getAppkey(), ios.getSecret(), deviceToken);
		}
		return parsetResponse(res, deviceType);
	}

	/**
	 * 查询设备所有标签
	 * 
	 * @param deviceToken
	 *            设备号
	 * @return
	 * @throws PushException
	 */
	public List<String> tagQueryList(DeviceType deviceType, String deviceToken) throws PushException {

		String resJson = null;

		if (DeviceType.ANDROID == deviceType) {
			resJson = client.getTagList(android.getAppkey(), android.getSecret(), deviceToken);
		} else if (DeviceType.IOS == deviceType) {
			resJson = client.getTagList(ios.getAppkey(), ios.getSecret(), deviceToken);
		}

		logger.debug("res == " + resJson);

		if (StringUtils.isBlank(resJson)) {
			throw new PushException("查询错误");
		}

		JSONObject result = JSONObject.parseObject(resJson);

		String ret = result.getString("ret");

		JSONObject data = result.getJSONObject("data");

		if ("FAIL".equals(ret)) {
			throw new PushException("查询错误,错误码 [" + data.getString("error_code") + "]");
		}

		String tags = data.getString("tags");

		if (StringUtils.isBlank(tags)) {
			return new ArrayList<String>();
		}

		return Arrays.asList(tags.split(","));
	}

	/**
	 * 构建Android统一消息格式
	 * 
	 * @param request
	 *            请求对象
	 * @param notice
	 *            通知内容
	 * @throws PushException
	 */
	protected void buildAndroidRequest(AndroidNotification request, PushNotice notice) throws PushException {

		buildRequest(request, notice);
		// 设置消息类型
		if (notice.getType() == NoticeType.MESSAGE) {
			request.setDisplayType(AndroidNotification.DisplayType.MESSAGE);
		} else {
			request.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
			request.goAppAfterOpen();
		}
		// 通知栏提示文字
		request.setTicker(notice.getTitle());
		// 通知栏标题
		request.setTitle(notice.getTitle());
		// 通知栏文字描述
		request.setText(notice.getDescription());
		// 自定义参数处理
		if (notice.getCustoms() != null) {
			for (Entry<String, String> entry : notice.getCustoms().entrySet()) {
				request.setExtraField(entry.getKey(), entry.getValue());
			}
		}

	}

	/**
	 * 构建IOS统一消息格式
	 * 
	 * @param request
	 *            请求对象
	 * @param notice
	 *            通知内容
	 * @throws PushException
	 */
	protected void buildIOSRequest(IOSNotification request, PushNotice notice) throws PushException {

		buildRequest(request, notice);
		// 消息标题
		request.setAlert(notice.getTitle());
		// badge样式
		request.setBadge(0);
		// 音乐
		request.setSound("default");
		// 自定义参数处理
		if (notice.getCustoms() != null) {
			for (Entry<String, String> entry : notice.getCustoms().entrySet()) {
				request.setCustomizedField(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * 构建统一消息参数
	 * 
	 * @param request
	 *            请求对象
	 * @throws PushException
	 */
	protected void buildRequest(UmengNotification request, PushNotice notice) throws PushException {
		// 设置开发模式
		if (this.productionMode) {
			request.setProductionMode();
		} else {
			request.setTestMode();
		}
		// 设置定时发送参数
		if (StringUtils.isNoneBlank(notice.getStartTime())) {
			request.setStartTime(notice.getStartTime());
		}
		if (StringUtils.isNoneBlank(notice.getEndTime())) {
			request.setExpireTime(notice.getStartTime());
		}
	}

	/**
	 * 消息内容校验
	 * 
	 * @param notice
	 * @throws PushException
	 */
	protected void validateNotice(PushNotice notice) throws PushException {

		if (notice == null) {
			throw new PushException("param notice is not null.");
		}

		if (StringUtils.isBlank(notice.getTitle())) {

			throw new PushException("param notice.title is required.");
		}
	}

	/**
	 * 
	 * 解析结果
	 * 
	 * @param resJson
	 * @return
	 */
	protected PushResponse parsetResponse(String resJson, DeviceType deviceType) {

		logger.debug("res == " + resJson);

		PushResponse res = new PushResponse();
		res.setDeviceType(deviceType);
		if (StringUtils.isBlank(resJson)) {
			return res;
		}

		JSONObject result = JSONObject.parseObject(resJson);

		String ret = result.getString("ret");

		if ("SUCCESS".equals(ret)) {
			res.setReturnCode(PushResponse.ReturnCode.SUCCESS);
		}

		JSONObject data = result.getJSONObject("data");

		if (data != null) {

			if (data.containsKey("error_code")) {
				res.setReturnMsg(data.getString("error_code"));
			}
			if (data.containsKey("msg_id")) {
				res.setMsgId(data.getString("msg_id"));
			}
			if (data.containsKey("task_id")) {
				res.setTaskId(data.getString("task_id"));
			}
			// 自定义ID,暂不使用
			// if(data.containsKey("thirdparty_id")){
			//
			// }
		}

		return res;
	}

	public SecurityKey getIos() {
		return ios;
	}

	public void setIos(SecurityKey ios) {
		this.ios = ios;
	}

	public SecurityKey getAndroid() {
		return android;
	}

	public void setAndroid(SecurityKey android) {
		this.android = android;
	}

	public boolean isProductionMode() {
		return productionMode;
	}

	public void setProductionMode(boolean productionMode) {
		this.productionMode = productionMode;
	}

}

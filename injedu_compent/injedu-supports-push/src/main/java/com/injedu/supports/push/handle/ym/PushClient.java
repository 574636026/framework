package com.injedu.supports.push.handle.ym;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.injedu.supports.push.exception.PushException;
import com.injedu.utils.http.HttpHelper;
import com.injedu.utils.http.ResponseContent;

/**
 * 
 *
 * @author joy.zhou
 * @date 2016年8月30日
 * @version 1.0
 *
 */
public class PushClient {

	private Logger logger = LoggerFactory.getLogger(PushClient.class);

	/** API接口地址 */
	private static final String host = "http://msg.umeng.com";

	/** 文件上传地址 */
	protected static final String uploadPath = host + "/upload";
	/** 发送消息地址 */
	protected static final String sendPath = host + "/api/send";
	/** 查询任务状态 */
	protected static final String statusPath = host + "/api/status";
	/** 标签地址 */
	private static final String tagPath = host + "/api/tag";
	/** 添加设备标签 (可添加多个设备) */
	protected static final String tagAddPath = tagPath + "/add";
	/** 获取设备标签列表 (单个设备) */
	protected static final String tagListPath = tagPath + "/list";
	/** 设置设备标签 (单个设备) */
	protected static final String tagSetPath = tagPath + "/set";
	/** 删除设备标签 (单个设备) */
	protected static final String tagDeletePath = tagPath + "/delete";
	/** 清除设备所有标签 (单个设备) */
	protected static final String tagClearPath = tagPath + "/clear";

	/**
	 * 
	 * 发送消息
	 * 
	 * @param msg
	 *            消息体
	 * @return
	 * @throws PushException
	 */
	public String send(UmengNotification msg) throws PushException {
		String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
		msg.setPredefinedKeyValue("timestamp", timestamp);
		// 提交数据
		return postData(msg.getAppMasterSecret(), sendPath, msg.getPostBody());

	}

	/**
	 * 
	 * 上传内容
	 * 
	 * @param appkey
	 * @param appMasterSecret
	 * @param contents
	 *            内容列表
	 * @return fileId(文件ID)
	 * @throws PushException
	 */
	public String uploadContents(String appkey, String appMasterSecret, String contents) throws PushException {
		// 构建请求参数
		JSONObject uploadJson = new JSONObject();
		uploadJson.put("appkey", appkey);
		String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
		uploadJson.put("timestamp", timestamp);
		uploadJson.put("content", contents);
		String postBody = uploadJson.toString();
		// 提交数据
		String res = postData(appMasterSecret, uploadPath, postBody);

		if (StringUtils.isBlank(res)) {
			return null;
		}
		// 获取文件ID
		JSONObject respJson = JSONObject.parseObject(res);
		String ret = respJson.getString("ret");
		if (!ret.equals("SUCCESS")) {
			throw new PushException("Failed to upload file");
		}
		JSONObject data = respJson.getJSONObject("data");

		return data.getString("file_id");
	}

	/**
	 * 添加标签
	 * 
	 * @param appkey
	 * @param appMasterSecret
	 * @param tag
	 *            标签名称
	 * @param device_tokens
	 *            设备列表
	 * @return
	 * @throws PushException
	 */
	public String addTags(String appkey, String appMasterSecret, String tag, String[] device_tokens)
			throws PushException {

		// 构建请求参数
		JSONObject postJson = new JSONObject();
		postJson.put("appkey", appkey);
		String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
		postJson.put("timestamp", timestamp);
		postJson.put("tag", tag);
		postJson.put("device_tokens", StringUtils.join(device_tokens));

		// 提交数据
		return postData(appMasterSecret, tagAddPath, postJson.toString());

	}

	/**
	 * 设置设备标签 (单个设备)
	 * 
	 * @param appkey
	 * @param appMasterSecret
	 * @param tag
	 *            标签名称
	 * @param device_tokens
	 *            设备列表
	 * @return
	 * @throws PushException
	 */
	public String setTag(String appkey, String appMasterSecret, String tag, String device_token) throws PushException {

		// 构建请求参数
		JSONObject postJson = new JSONObject();
		postJson.put("appkey", appkey);
		String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
		postJson.put("timestamp", timestamp);
		postJson.put("tag", tag);
		postJson.put("device_tokens", device_token);

		// 提交数据
		return postData(appMasterSecret, tagSetPath, postJson.toString());

	}

	/**
	 * 获取设备标签列表 (单个设备)
	 * 
	 * @param appkey
	 * @param appMasterSecret
	 * @param tag
	 *            标签名称
	 * @param device_tokens
	 *            设备列表
	 * @return
	 * @throws PushException
	 */
	public String getTagList(String appkey, String appMasterSecret, String device_token) throws PushException {

		// 构建请求参数
		JSONObject postJson = new JSONObject();
		postJson.put("appkey", appkey);
		String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
		postJson.put("timestamp", timestamp);
		postJson.put("device_tokens", device_token);

		// 提交数据
		return postData(appMasterSecret, tagListPath, postJson.toString());

	}

	/**
	 * 删除设备标签 (单个设备)
	 * 
	 * @param appkey
	 * @param appMasterSecret
	 * @param tag
	 *            标签名称
	 * @param device_tokens
	 *            设备列表
	 * @return
	 * @throws PushException
	 */
	public String deleteTag(String appkey, String appMasterSecret, String tag, String device_token)
			throws PushException {

		// 构建请求参数
		JSONObject postJson = new JSONObject();
		postJson.put("appkey", appkey);
		String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
		postJson.put("timestamp", timestamp);
		postJson.put("device_tokens", device_token);
		postJson.put("tag", tag);

		// 提交数据
		return postData(appMasterSecret, tagDeletePath, postJson.toString());

	}

	/**
	 * 清除设备所有标签 (单个设备)
	 * 
	 * @param appkey
	 * @param appMasterSecret
	 * @param tag
	 *            标签名称
	 * @param device_tokens
	 *            设备列表
	 * @return
	 * @throws PushException
	 */
	public String clearTag(String appkey, String appMasterSecret, String device_token) throws PushException {

		// 构建请求参数
		JSONObject postJson = new JSONObject();
		postJson.put("appkey", appkey);
		String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
		postJson.put("timestamp", timestamp);
		postJson.put("device_tokens", device_token);
		// 提交数据
		return postData(appMasterSecret, tagClearPath, postJson.toString());

	}

	/**
	 * 查询任务状态
	 * 
	 * @param appkey
	 * @param appMasterSecret
	 * @param tag
	 *            标签名称
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws PushException
	 */
	public String queryStatus(String appkey, String appMasterSecret, String taskId) throws PushException {

		// 构建请求参数
		JSONObject postJson = new JSONObject();
		postJson.put("appkey", appkey);
		String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
		postJson.put("timestamp", timestamp);
		postJson.put("taskId", taskId);
		// 提交数据
		return postData(appMasterSecret, statusPath, postJson.toString());

	}

	/**
	 * 提交数据
	 * 
	 * @param appMasterSecret
	 *            app secret
	 * @param url
	 *            请求路径
	 * @param postBody
	 *            请求实体
	 * @return
	 */
	protected String postData(String appMasterSecret, String url, String postBody) {

		try {

			String sign = DigestUtils.md5Hex(("POST" + url + postBody + appMasterSecret).getBytes("utf8"));
			url = url + "?sign=" + sign;

			if (logger.isDebugEnabled()) {
				logger.debug("url == " + url);
				logger.debug("postBody == " + postBody);
			}

			ResponseContent res = HttpHelper.postJsonEntity(url, postBody);

			return res.getContent();

		} catch (UnsupportedEncodingException e) {
			new PushException(e.getMessage(), e);
		}

		return null;
	}

}

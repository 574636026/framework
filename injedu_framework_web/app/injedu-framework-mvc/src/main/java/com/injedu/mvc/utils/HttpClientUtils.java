package com.injedu.mvc.utils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.injedu.utils.http.HttpHelper;

/**
 * 
 * httpclient工具类,基于httpclient 4.x
 * 
 * @author joy.zhou
 * @date 2016年8月19日
 * @version 1.0
 *
 */
public class HttpClientUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

	/**
	 * post请求
	 * 
	 * @param url
	 * @return
	 */
	public static String doPost(String url) {
		try {
			return doPost(url, null);
		} catch (Exception e) {
			LOGGER.error("POST请求出错：{}", url, e);
		}

		return null;
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param formParams
	 * @return
	 */
	public static String doPost(String url, Map<String, String> formParams) {

		return doPost(url, formParams, String.class);
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            地址
	 * @param formParams
	 *            参数
	 * @param responseType
	 *            返回类型
	 * @return
	 */
	public static <T> T doPost(String url, Map<String, String> formParams, Class<T> responseType) {

		if (formParams == null || formParams.isEmpty()) {
			return RestClient.getClient().postForObject(url, HttpEntity.EMPTY, responseType);
		}
		try {
			MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
			for (String key : formParams.keySet()) {
				requestEntity.add(key, formParams.get(key));
			}
			return RestClient.getClient().postForObject(url, requestEntity, responseType);
		} catch (Exception e) {
			LOGGER.error("POST请求出错：{}", url, e);
		}

		return null;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @return
	 */
	public static String doGet(String url) {
		try {
			return RestClient.getClient().getForObject(url, String.class);
		} catch (Exception e) {
			LOGGER.error("GET请求出错：{}", url, e);
		}

		return null;
	}

	/**
	 * get 请求
	 * 
	 * @param url
	 *            地址
	 * @param responseType
	 *            返回类型
	 * @return
	 */
	public static <T> T doGet(String url, Class<T> responseType) {

		return doGet(url, null, responseType);
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            地址
	 * @param formParams
	 *            参数
	 * @param responseType
	 *            返回类型
	 * @return
	 */
	public static <T> T doGet(String url, Map<String, String> formParams, Class<T> responseType) {

		if (formParams != null) {
			url = HttpHelper.makeURL(url, formParams);
		}
		try {
			return RestClient.getClient().getForObject(url, responseType);
		} catch (Exception e) {
			LOGGER.error("POST请求出错：{}", url, e);
		}

		return null;
	}

	private static class RestClient {

		private static RestTemplate restTemplate;

		private final static Object syncLock = new Object();

		private static void initRestTemplate() {
			// 长连接保持30秒
			PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager(30,
					TimeUnit.SECONDS);
			// 总连接数
			pollingConnectionManager.setMaxTotal(1000);
			// 同路由的并发数
			pollingConnectionManager.setDefaultMaxPerRoute(1000);

			HttpClientBuilder httpClientBuilder = HttpClients.custom();
			httpClientBuilder.setConnectionManager(pollingConnectionManager);
			// 重试次数，默认是3次，没有开启
			httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true));
			// 保持长连接配置，需要在头添加Keep-Alive
			httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());

			List<Header> headers = new ArrayList<>();
			headers.add(new BasicHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36"));
			headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
			headers.add(new BasicHeader("Accept-Language", "zh-CN"));
			headers.add(new BasicHeader("Connection", "Keep-Alive"));

			httpClientBuilder.setDefaultHeaders(headers);

			HttpClient httpClient = httpClientBuilder.build();

			// httpClient连接配置，底层是配置RequestConfig
			HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
					httpClient);
			// 连接超时
			clientHttpRequestFactory.setConnectTimeout(5000);
			// 数据读取超时时间，即SocketTimeout
			clientHttpRequestFactory.setReadTimeout(5000);
			// 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
			clientHttpRequestFactory.setConnectionRequestTimeout(200);
			// 缓冲请求数据，默认值是true。通过POST或者PUT大量发送数据时，建议将此属性更改为false，以免耗尽内存。
			// clientHttpRequestFactory.setBufferRequestBody(false);

			// 添加内容转换器
			List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
			messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
			messageConverters.add(new FormHttpMessageConverter());
			messageConverters.add(new FastJsonHttpMessageConverter());

			restTemplate = new RestTemplate(messageConverters);
			restTemplate.setRequestFactory(clientHttpRequestFactory);
			restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

		}

		private RestClient() {
		}

		public static RestTemplate getClient() {
			if (restTemplate == null) {
				synchronized (syncLock) {
					if (restTemplate == null) {
						initRestTemplate();
					}
				}
			}
			return restTemplate;
		}
	}

}

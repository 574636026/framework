package com.injedu.supports.payment.wxpay.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.injedu.supports.payment.wxpay.config.WxConfig;
import com.injedu.supports.payment.wxpay.sign.WxMD5;
import com.injedu.utils.log.LogUtils;

/**
 * 
 *
 * @author joy.zhou
 * @date 2016年2月2日
 * @version 1.0
 *
 */
public class WxCore {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected WxHttpClient client = new WxHttpClient();

	/**
	 * 
	 * 提交结果
	 * 
	 * @param url
	 *            地址
	 * @param params
	 *            参数
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	protected String post(String url, Map<String, String> params) throws HttpException, IOException {

		String xml = this.toParams(params);

		return client.postBody(url, xml).getContent();

	}

	/**
	 * 签名算法
	 * 
	 * @param o
	 *            要参与签名的数据对象
	 * @return 签名
	 * @throws IllegalAccessException
	 */
	public String getSign(Object o) throws IllegalAccessException {
		ArrayList<String> list = new ArrayList<String>();
		Class<?> cls = o.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
			if (f.get(o) != null && f.get(o) != "") {
				list.add(f.getName() + "=" + f.get(o) + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		return getSign(arrayToSort);
	}

	/**
	 * 签名算法
	 * 
	 * @param map
	 * @return
	 */
	public String getSign(Map<String, String> map) {
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (entry.getValue() != null && entry.getValue() != "") {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		return getSign(arrayToSort);
	}

	/**
	 * 签名算法
	 * 
	 * @param arrayToSort
	 *            参数数组
	 * @return
	 */
	private String getSign(String[] arrayToSort) {
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arrayToSort.length; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + WxConfig.getKey();
		result = WxMD5.MD5Encode(result).toUpperCase();
		return result;
	}

	/**
	 * 从API返回的XML数据里面重新计算一次签名
	 * 
	 * @param responseString
	 *            API返回的XML数据
	 * @return 新鲜出炉的签名
	 * @throws DocumentException
	 * @throws IllegalAccessException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public String getSignFromResponseString(String responseString) throws DocumentException {
		Map<String, String> map = XMLParse.getMapFromXML(responseString);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		map.put("sign", "");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		return this.getSign(map);
	}

	/**
	 * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
	 * 
	 * @param responseString
	 *            API返回的XML数据字符串
	 * @return API签名是否合法
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws IllegalAccessException
	 */
	public boolean checkIsSignValidFromResponseString(Map<String, String> params) throws IllegalAccessException {

		String signFromAPIResponse = params.get("sign");
		if (signFromAPIResponse == "" || signFromAPIResponse == null) {
			LogUtils.i("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
			return false;
		}
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		params.put("sign", "");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		String signForAPIResponse = this.getSign(params);

		if (!signForAPIResponse.equals(signFromAPIResponse)) {
			// 签名验不过，表示这个API返回的数据有可能已经被篡改了
			LogUtils.i("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
			return false;
		}
		LogUtils.i("恭喜，API返回的数据签名验证通过!!!");
		return true;
	}

	/**
	 * 将对象转为XML
	 * 
	 * @param obj
	 * @return
	 */
	protected String toParams(Map<String, String> obj) {

		if (obj == null) {
			return null;
		}

		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("xml");

		for (Entry<String, String> entry : obj.entrySet()) {

			String value = entry.getValue();

			if (value == null) {
				continue;
			}

			Element e = root.addElement(entry.getKey());

			e.setText(value);
		}

		String result = document.asXML();

		// try {
		// return new String(result.getBytes(), CHARSET);
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }

		return result;
	}
}

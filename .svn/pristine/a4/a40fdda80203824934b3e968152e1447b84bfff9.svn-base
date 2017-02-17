package com.injedu.supports.payment.wxpay.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.xml.sax.SAXException;

import com.injedu.supports.payment.wxpay.entity.PayResult;
import com.injedu.supports.payment.wxpay.entity.PayUnified;
import com.injedu.utils.tools.ConvertUtils;

/**
 * 
 * 微信支付XML解析器
 *
 * @author joy.zhou
 * @date 2016年1月8日
 * @version 1.0
 * 
 */
public class XMLParse {

	/**
	 * 
	 * 解析支付回调信息
	 * 
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	public static PayResult parsePaidResult(String xml) throws DocumentException {

		PayResult result = new PayResult();

		Map<String, String> params = getMapFromXML(xml);

		result.setReturnCode(params.get("return_code"));
		result.setReturnMsg(params.get("return_msg"));
		result.setAppid(params.get("appid"));
		result.setMchId(params.get("mch_id"));
		result.setDeviceInfo(params.get("device_info"));
		result.setNonceStr(params.get("nonce_str"));
		result.setSign(params.get("sign"));
		result.setResultCode(params.get("result_code"));
		result.setErrCode(params.get("err_code"));
		result.setErrCodeDes(params.get("err_code_des"));
		result.setOpenid(params.get("openid"));
		result.setIsSubscribe(params.get("is_subscribe"));
		result.setTradeType(params.get("trade_type"));
		result.setBankType(params.get("bank_type"));
		result.setTotalFee(ConvertUtils.toInt(params.get("total_fee"), 0));
		result.setFeeType(params.get("fee_type"));
		result.setCashFee(ConvertUtils.toInt(params.get("cash_fee"), 0));
		result.setCashFeeType(params.get("cash_fee_type"));
		// 优惠卷信息
		result.setCouponFee(ConvertUtils.toInt(params.get("coupon_fee"), 0));
		Integer couponCount = (ConvertUtils.toInt(params.get("coupon_count"), 0));
		if (couponCount != null && couponCount > 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < couponCount; i++) {
				sb.append(params.get("coupon_id_" + i)).append("-").append(params.get("coupon_fee_" + i)).append(",");
			}
			if (sb.length() > 1) {
				sb.delete(sb.length() - 1, sb.length());
			}
			result.setCouponInfo(sb.toString());
		}
		result.setTransactionId(params.get("transaction_id"));
		result.setOutTradeNo(params.get("out_trade_no"));
		result.setAttach(params.get("attach"));
		result.setTimeEnd(params.get("time_end"));
		return result;

	}

	/**
	 * 解析预支付信息
	 * 
	 * @param params
	 * @throws DocumentException
	 */
	public static PayUnified parsePayUnified(String xml) throws DocumentException {

		PayUnified result = new PayUnified();

		Map<String, String> params = getMapFromXML(xml);

		result.setReturnCode(params.get("return_code"));
		result.setReturnMsg(params.get("return_msg"));
		result.setAppid(params.get("appid"));
		result.setMchId(params.get("mch_id"));
		result.setDeviceInfo(params.get("device_info"));
		result.setNonceStr(params.get("nonce_str"));
		result.setSign(params.get("sign"));
		result.setResultCode(params.get("result_code"));
		result.setErrCode(params.get("err_code"));
		result.setErrCodeDes(params.get("err_code_des"));

		result.setTradeType(params.get("trade_type"));
		result.setPrepayId(params.get("prepay_id"));
		result.setCodeUrl(params.get("code_url"));

		return result;
	}

	/**
	 * 
	 * Xml解析工具类
	 * 
	 * @param xml
	 * @return
	 * @throws DocumentException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static Map<String, String> getMapFromXML(String xml) throws DocumentException {

		Document document = DocumentHelper.parseText(xml);
		// 获取到document里面的全部结点
		Element root = document.getRootElement();

		Map<String, String> map = new HashMap<String, String>();

		for (Object temp : root.elements()) {
			Element node = (Element) temp;
			map.put(node.getName(), node.getText());
		}
		return map;

	}

	/**
	 * 
	 * Xml解析工具类
	 * 
	 * @param is
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws DocumentException
	 * @throws SAXException
	 */
	public static Map<String, String> getMapFromXML(InputStream is)
			throws ParserConfigurationException, IOException, DocumentException {

		if (is == null) {
			return null;
		}
		// 这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
		return getMapFromXML(IOUtils.toString(is));
	}

	/**
	 * 将对象转为XML
	 * 
	 * 根据腾讯开发的字段规则string类型字段加上CDATA
	 * 
	 * @param obj
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static String toXml(Object obj)
			throws ParserConfigurationException, IllegalArgumentException, IllegalAccessException {

		if (obj == null) {
			return null;
		}
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("xml");
		Class<?> cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
			Object value = f.get(obj);
			if (value != null) {
				Element e = root.addElement(f.getName());
				if (value instanceof String) {
					e.addCDATA(String.valueOf(value));
				} else {
					e.setText(String.valueOf(value));
				}
			}
		}
		return document.asXML();
	}

}

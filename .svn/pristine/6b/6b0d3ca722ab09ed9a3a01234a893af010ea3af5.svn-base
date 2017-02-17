package com.injedu.supports.payment.wxpay;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.http.HttpException;
import org.dom4j.DocumentException;

import com.injedu.supports.payment.exception.PaymentException;
import com.injedu.supports.payment.wxpay.config.WxConfig;
import com.injedu.supports.payment.wxpay.constant.TradeType;
import com.injedu.supports.payment.wxpay.entity.PayUnified;
import com.injedu.supports.payment.wxpay.utils.WxCore;
import com.injedu.supports.payment.wxpay.utils.XMLParse;
import com.injedu.utils.encrypt.RandomUtils;

/**
 * 微信submit
 *
 * @author joy.zhou
 * @date 2016年2月1日
 * @version 1.0
 *
 */
public class WxSubmit extends WxCore {

	private static final String PACKAGE_VALUE = "Sign=WXPay";

	/**
	 * 生成预支付订单
	 * 
	 * @param orderCode
	 *            订单号
	 * @param amount
	 *            订单金额
	 * @param body
	 *            订单描述
	 * @return
	 * @throws PaymentException
	 */
	public PayUnified payOrder(String orderCode, Integer amount, String body, TradeType tradeType) {

		Map<String, String> params = new HashMap<String, String>();
		// 公众账号ID
		params.put("appid", WxConfig.getAppid());
		// 商户号
		params.put("mch_id", WxConfig.getMchid());
		// 字符集
		params.put("input_charset", "UTF-8");
		// 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
		params.put("notify_url", WxConfig.getNotifyUrl());
		// 订单生成的机器
		params.put("spbill_create_ip", WxConfig.getIP());
		// 交易类型
		params.put("trade_type", tradeType.name());
		// 随机字符串
		params.put("nonce_str", genNonceStr());
		// 商品描述
		params.put("body", body);
		// 商户订单号
		params.put("out_trade_no", orderCode);
		// 总金额以分为单位，不带小数点
		params.put("total_fee", String.valueOf(amount));
		// 交易类型
		params.put("trade_type", "APP");
		// 签名
		params.put("sign", this.getSign(params));

		try {

			String xml = this.post(WxConfig.PAY_UNIFIED_API, params);
			
			logger.debug(xml);

			return XMLParse.parsePayUnified(xml);

		} catch (HttpException | IOException | DocumentException e) {
			throw new PaymentException(e);
		}
	}

	/**
	 * 根据预知付信息，生成调用支付参数
	 * 
	 * @param payUnified
	 */
	public Map<String, String> genCallPayParams(PayUnified payUnified) {

		Map<String, String> params = new HashMap<String, String>();
		// 应用ID
		params.put("appid", payUnified.getAppid());
		// 商户号
		params.put("partnerid", payUnified.getMchId());
		// 预知付ID
		params.put("prepayid", payUnified.getPrepayId());
		// 固定值Sign=WXPay
		params.put("package", PACKAGE_VALUE);
		// 随机字符串
		params.put("noncestr", genNonceStr());
		// 时间戳
		params.put("timestamp", getTimeStamp());
		// 签名
		params.put("sign", this.getSign(params));
		// java package为关键字,所以接口返回packageValue参数
		params.remove("package");
		params.put("packageValue", PACKAGE_VALUE);

		return params;
	}

	/**
	 * 获取随机串
	 * 
	 * @return
	 */
	private String genNonceStr() {
		return RandomUtils.radom(12);
	}

	private String getTimeStamp() {

		Calendar date = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));

		return String.valueOf(date.getTimeInMillis() / 1000);
	}

}

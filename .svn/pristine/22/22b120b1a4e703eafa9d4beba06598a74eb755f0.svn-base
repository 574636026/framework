package com.injedu.supports.payment.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.injedu.utils.log.LogUtils;
import com.injedu.supports.payment.alipay.config.AlipayConfig;
import com.injedu.supports.payment.alipay.entity.AliPayMobile;
import com.injedu.supports.payment.alipay.sign.RSA;

/**
 * 
 * 微信支付
 *
 * @author joy.zhou
 * @date 2016年5月30日
 * @version 1.0
 *
 */
public class AlipaySubmit {

	/**
	 * 生成支付订单信息
	 * 
	 * @param orderCode
	 *            订单编号
	 * @param subject
	 *            商品名称
	 * @param body
	 *            商品详情
	 * @param price
	 *            商品价格
	 * @return
	 */
	public AliPayMobile payOrder(String orderCode, String subject, String body, String price) {

		AliPayMobile entity = new AliPayMobile();
		entity.setPaymentType(AlipayConfig.payment_type);
		entity.setCharset(AlipayConfig.input_charset);
		entity.setService(AlipayConfig.SERVICE_MOBILE_PAY);
		entity.setPartner(AlipayConfig.getPartner());
		entity.setSellerId(AlipayConfig.getPartner());
		entity.setNotifyUrl(AlipayConfig.getNotifyURL());
		entity.setItBPay(AlipayConfig.getItBPay());
		entity.setOutTradeNo(orderCode);
		entity.setSubject(subject);
		entity.setBody(body);
		entity.setTotalFee(price);
		entity.setSign(this.getSign(entity));
		entity.setSignType(AlipayConfig.sign_type);

		return entity;
	}

	private String getSign(AliPayMobile entity) {

		Map<String, String> params = new LinkedHashMap<String, String>();
		// 签约合作者身份ID
		params.put("partner", entity.getPartner());
		// 签约卖家支付宝账号或者签约合作者身份ID
		params.put("seller_id", entity.getSellerId());
		// 商户网站唯一订单号
		params.put("out_trade_no", entity.getOutTradeNo());
		// 商品名称
		params.put("subject", entity.getSubject());
		// 商品详情
		params.put("body", entity.getBody());
		// 商品金额
		params.put("total_fee", entity.getTotalFee());
		// 服务器异步通知页面路径
		params.put("notify_url", entity.getNotifyUrl());
		// 服务接口名称, 固定值
		params.put("service", entity.getService());
		// 支付类型,固定值
		params.put("payment_type", entity.getPaymentType());
		// 参数编码, 固定值
		params.put("_input_charset", entity.getCharset());
		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		params.put("it_b_pay", entity.getItBPay());

		try {
			return URLEncoder.encode(RSA.sign(makeParams(params), AlipayConfig.getPrivateKey(), entity.getCharset()),
					"utf-8");
		} catch (UnsupportedEncodingException e) {
			LogUtils.e(e.getMessage(), e);
		}
		return "";
	}

	private String makeParams(Map<String, String> params) {

		StringBuilder result = new StringBuilder();

		for (Entry<String, String> entry : params.entrySet()) {
			result.append("&").append(entry.getKey()).append("=").append("\"").append(entry.getValue()).append("\"");
		}

		if (result.length() > 0) {
			result.deleteCharAt(0);
		}

		return result.toString();
	}

}

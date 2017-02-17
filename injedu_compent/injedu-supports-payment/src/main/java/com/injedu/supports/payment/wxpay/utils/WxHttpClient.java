package com.injedu.supports.payment.wxpay.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

import com.injedu.supports.payment.wxpay.config.WxConfig;
import com.injedu.utils.http.HttpClientWrapper;

/**
 * 微信http请求
 *
 * @author joy.zhou
 * @date 2016年2月1日
 * @version 1.0
 *
 */
public class WxHttpClient extends HttpClientWrapper {

	public WxHttpClient() {
		super();
	}

	public WxHttpClient(Integer connectionRequestTimeout, Integer connectTimeout, Integer socketTimeout) {
		super(connectionRequestTimeout, connectTimeout, socketTimeout);
	}

	@Override
	protected void init() {

		FileInputStream instream = null;

		try {

			// SSL配置
			SSLContext sslContext = null;
			// 指定读取证书格式为PKCS12
			KeyStore trustStore = KeyStore.getInstance("PKCS12");
			instream = new FileInputStream(WxConfig.getCertLocalPath());
			// 指定PKCS12的密码
			trustStore.load(instream, WxConfig.getCertPassword().toCharArray());

			sslContext = SSLContexts.custom().useProtocol("TLS").loadTrustMaterial(trustStore, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();

			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
					.register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", new SSLConnectionSocketFactory(sslContext)).build();
			connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
			connManager.setDefaultSocketConfig(socketConfig);
			MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200)
					.setMaxLineLength(2000).build();
			ConnectionConfig connectionConfig = ConnectionConfig.custom()
					.setMalformedInputAction(CodingErrorAction.IGNORE)
					.setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
					.setMessageConstraints(messageConstraints).build();
			connManager.setDefaultConnectionConfig(connectionConfig);
			connManager.setMaxTotal(200);
			connManager.setDefaultMaxPerRoute(20);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (instream != null) {
				try {
					instream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

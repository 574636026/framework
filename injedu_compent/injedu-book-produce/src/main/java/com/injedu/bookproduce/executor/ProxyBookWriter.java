package com.injedu.bookproduce.executor;

import org.apache.commons.lang3.StringUtils;

import com.injedu.bookproduce.core.ProduceFileEntity;
import com.injedu.bookproduce.exception.WriterException;
import com.injedu.bookproduce.transfer.IBookTransfer;

/**
 * 图片下载代理
 *
 * @author joy.zhou
 * @date 2016年8月26日
 * @version 1.0
 *
 */
public class ProxyBookWriter extends DefaultBookWriter {
	/** 域名 */
	private String domain;
	/** 代理 */
	private String proxy;

	public ProxyBookWriter() {
	}

	public ProxyBookWriter(IBookTransfer transfer) {
		super(transfer);
	}

	public ProxyBookWriter(IBookTransfer transfer, String domain, String proxy) {
		super(transfer);
		this.domain = domain;
		this.proxy = proxy;
	}

	@Override
	protected void writeImage(ProduceFileEntity entity) throws WriterException {

		if (StringUtils.isNotBlank(this.proxy)) {
			// 网络优化,使用内网地址下载
			String path = entity.getPath();
			if (path.startsWith(this.domain)) {
				entity.setPath(path.replace(this.domain, this.proxy));
			}
		}

		super.writeImage(entity);
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getProxy() {
		return proxy;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

}

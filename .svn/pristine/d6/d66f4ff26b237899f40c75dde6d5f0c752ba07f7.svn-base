package com.injedu.bookproduce.executor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.injedu.bookproduce.core.ProduceFileEntity;
import com.injedu.bookproduce.exception.WriterException;
import com.injedu.bookproduce.transfer.IBookTransfer;

/**
 * 
 * 默认书本下载器
 *
 * @author joy.zhou
 * @date 2016年8月11日
 * @version 1.0
 *
 */
public class DefaultBookWriter extends BookWriter {

	public DefaultBookWriter() {
	}

	public DefaultBookWriter(IBookTransfer transfer) {
		super(transfer);
	}

	/**
	 * 校验文件
	 * 
	 * @param t
	 * @throws WriterException
	 */
	@Override
	protected boolean hasValid(ProduceFileEntity t) throws WriterException {
		switch (t.getFileType()) {
		case IMAGE:
			if (StringUtils.isBlank(t.getPath())) {
				throw new WriterException(String.format("url 为空", t.getPath()), t.getMessage());
			}
			if (!t.getPath().startsWith("http://") && !t.getPath().startsWith("https://")) {
				throw new WriterException(String.format("url[%s] 不支持", t.getPath()), t.getMessage());
			}
			break;
		case FILE:
			if (StringUtils.isBlank(t.getContent())) {
				throw new WriterException(String.format("文件[%s] 为空", t.getPath()), t.getMessage());
			}
			break;
		}

		return true;
	}

	/**
	 * 写入文件
	 * 
	 * @param entity
	 * @throws IOException
	 */
	protected void writeFile(ProduceFileEntity entity) throws WriterException {
		if (StringUtils.isBlank(entity.getContent())) {
			return;
		}
		try (FileOutputStream out = new FileOutputStream(new File(entity.getDir(), entity.getPath()))) {
			IOUtils.write(entity.getContent(), out, this.getCharset());
		} catch (IOException e) {
			throw fileException(e, entity);
		}
	}

	/**
	 * 写入图片
	 * 
	 * @throws IOException
	 */
	protected void writeImage(ProduceFileEntity entity) throws WriterException {

		String url = entity.getPath();
		if (StringUtils.isBlank(url)) {
			return;
		}
		String fileName = entity.getFileName();
		if (StringUtils.isBlank(fileName)) {
			fileName = url.substring(url.lastIndexOf("/"));
		}
		// 打开连接
		URLConnection conn = this.getUrlConnect(entity);
		try (InputStream in = conn.getInputStream();
				FileOutputStream file = new FileOutputStream(new File(entity.getDir(), fileName))) {
			IOUtils.copy(in, file);
		} catch (IOException e) {
			throw fileException(e, entity);
		}

	}

	private URLConnection getUrlConnect(ProduceFileEntity entity) throws WriterException {
		URLConnection conn = null;
		try {
			conn = new URL(entity.getPath()).openConnection();
			// 设置请求超时为10s
			conn.setConnectTimeout(10 * 1000);
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322)");
		} catch (IOException e) {
			throw fileException(e, entity);
		}

		return conn;
	}

	private WriterException fileException(Throwable e, ProduceFileEntity entity) {
		return new WriterException(entity.getPath() + "," + e.getMessage(), entity.getMessage(), e);
	}

}

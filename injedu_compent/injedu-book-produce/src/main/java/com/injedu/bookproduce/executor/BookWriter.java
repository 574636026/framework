package com.injedu.bookproduce.executor;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.injedu.bookproduce.core.ProduceFileEntity;
import com.injedu.bookproduce.exception.WriterException;
import com.injedu.bookproduce.transfer.CompressBookTransfer;
import com.injedu.bookproduce.transfer.IBookTransfer;
import com.injedu.utils.fileupload.model.FileEntity;

import rx.Subscriber;

/**
 * 
 * 书本执行器
 *
 * @author joy.zhou
 * @date 2016年8月11日
 * @version 1.0
 *
 */
public abstract class BookWriter extends Subscriber<ProduceFileEntity> {

	protected static final Logger logger = LoggerFactory.getLogger(BookWriter.class);
	/** 字符集 */
	private String charset = "utf8";
	/** 错误信息 */
	private StringBuilder error = new StringBuilder();
	/** 书本传输器 */
	private IBookTransfer transfer;
	/***/
	private BookWriterLinster bookWriterLinster;
	/** 书本根目录 */
	private File rootDir;
	/** 结果 */
	private FileEntity result;

	public BookWriter() {
		this.transfer = new CompressBookTransfer();
	}

	public BookWriter(IBookTransfer transfer) {
		this.transfer = transfer;
	}

	/**
	 * 写入文件
	 * 
	 * @param entity
	 * @throws IOException
	 */
	protected abstract void writeFile(ProduceFileEntity entity) throws WriterException;

	/**
	 * 写入图片
	 * 
	 * @throws IOException
	 */
	protected abstract void writeImage(ProduceFileEntity entity) throws WriterException;

	/**
	 * 
	 * @param result
	 */
	private void completeOnSuccess(FileEntity result) {
		if (bookWriterLinster != null) {
			bookWriterLinster.onSuccess(result);
		}
	}

	/**
	 * 
	 * @param error
	 */
	private void completeOnError(String error, Throwable e) {
		if (bookWriterLinster != null) {
			bookWriterLinster.onError(error, e);
		}
	}

	@Override
	public void onCompleted() {
		result = transfer.transfer(rootDir);
		this.completeOnSuccess(this.result);
	}

	@Override
	public void onError(Throwable e) {
		error.append(e.getMessage()).append(",");
		this.completeOnError(error.toString(), e);
		this.unsubscribe();
	}

	@Override
	public void onNext(ProduceFileEntity t) {

		this.hasValid(t);

		switch (t.getFileType()) {
		case FILE:
			logger.debug(String.format("start to write file [%s]", t.getPath()));
			writeFile(t);
			break;
		case IMAGE:
			logger.debug(String.format("start to write image [%s]", t.getPath()));
			writeImage(t);
			break;
		}

	}

	/**
	 * 校验文件
	 * 
	 * @param t
	 * @throws WriterException
	 */
	protected boolean hasValid(ProduceFileEntity t) throws WriterException {

		return true;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getCharset() {
		return charset;
	}

	public void setRootDir(File rootDir) {
		this.rootDir = rootDir;
	}

	public void setTransfer(IBookTransfer transfer) {
		this.transfer = transfer;
	}

	public void setBookWriterLinster(BookWriterLinster bookWriterLinster) {
		this.bookWriterLinster = bookWriterLinster;
	}

	/**
	 * 
	 * 书本输出监听器
	 *
	 * @author joy.zhou
	 * @date 2016年8月26日
	 * @version 1.0
	 *
	 */
	public static interface BookWriterLinster {
		/**
		 * 生成成功
		 * 
		 * @param result
		 *            书本结果
		 */
		public void onSuccess(FileEntity result);

		/**
		 * 生成失败
		 * 
		 * @param error
		 *            错误信息
		 */
		public void onError(String error, Throwable e);

	}

}

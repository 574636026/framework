package com.injedu.bookproduce.transfer;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.injedu.bookproduce.exception.TransferException;
import com.injedu.utils.file.ZipUtils;
import com.injedu.utils.fileupload.model.FileEntity;

/**
 * 
 * 文件压缩传输
 *
 * @author joy.zhou
 * @date 2016年8月11日
 * @version 1.0
 *
 */
public class CompressBookTransfer implements IBookTransfer {

	protected Logger logger = LoggerFactory.getLogger(CompressBookTransfer.class);

	/** 传输成功是否删除 */
	private boolean remove = false;

	public CompressBookTransfer() {
	}

	/**
	 * 
	 * @param isRemove
	 *            是否删除
	 */
	public CompressBookTransfer(boolean isRemove) {
		this.remove = isRemove;
	}

	@Override
	public FileEntity transfer(File book) throws TransferException {

		File zip = null;
		try {
			zip = this.compress(book);
			return this.compressAfter(zip);
		} finally {
			logger.debug(
					String.format("start to delete gen file :[%s,%s]", book.getAbsolutePath(), zip.getAbsolutePath()));
			this.deleteFile(book, zip);
		}
	}

	/**
	 * 压缩文件
	 * 
	 * @param file
	 * @return
	 */
	public File compress(File file) throws TransferException {

		String name = file.getName() + ".zip";

		logger.debug(String.format("compress the file %s to %s", file.getName(), name));

		File zipFile = new File(file.getParentFile(), name);

		try {
			ZipUtils.zip(file, zipFile);
		} catch (IOException e) {
			throw new TransferException(e.getMessage(), e);
		}

		return zipFile;
	}

	/**
	 * 压缩文件之后执行操作
	 * 
	 * @param file
	 */
	public FileEntity compressAfter(File file) {
		return new FileEntity(file.getName(), file.length(), file.getAbsolutePath());
	}

	/**
	 * 删除文件
	 * 
	 * @param files
	 * @throws TransferException
	 */
	protected void deleteFile(File... files) throws TransferException {

		if (!this.remove) {
			return;
		}
		if (files.length == 0) {
			return;
		}
		try {
			for (File file : files) {
				if (!file.exists()) {
					continue;
				}
				if (file.isDirectory()) {
					FileUtils.deleteDirectory(file);
				} else {
					file.delete();
				}
			}
		} catch (IOException e) {
			throw new TransferException(e.getMessage(), e);
		}
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

}

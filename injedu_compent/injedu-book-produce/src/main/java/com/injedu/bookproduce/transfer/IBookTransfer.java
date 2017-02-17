package com.injedu.bookproduce.transfer;

import java.io.File;

import com.injedu.bookproduce.exception.TransferException;
import com.injedu.utils.fileupload.model.FileEntity;

/**
 * 
 * 书本传输
 *
 * @author joy.zhou
 * @date 2016年8月11日
 * @version 1.0
 *
 */
public interface IBookTransfer {

	/**
	 * 
	 * 传输书本内容
	 * 
	 * @param book
	 *            书本目录
	 * 
	 * @return FileEntity
	 */
	public FileEntity transfer(File book) throws TransferException;

}

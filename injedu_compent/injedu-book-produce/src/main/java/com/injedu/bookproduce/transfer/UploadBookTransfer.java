package com.injedu.bookproduce.transfer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.injedu.utils.fileupload.IFileUpload;
import com.injedu.utils.fileupload.model.FileEntity;
import com.injedu.utils.fileupload.model.FileUploadConfig;

/**
 * FTP 上传
 *
 * @author joy.zhou
 * @date 2016年8月26日
 * @version 1.0
 *
 */
public class UploadBookTransfer extends CompressBookTransfer {

	private IFileUpload fileUpload;
	/** 上传目录 */
	private String dir;
	/** 是否重命名 */
	private Boolean rename = false;

	@Override
	public FileEntity compressAfter(File file) {

		if (this.fileUpload == null) {
			logger.warn("the fileUpload is null,will not upload the file.");
			return super.compressAfter(file);
		}

		FileEntity entity = null;

		try {
			entity = fileUpload.upload(dir, file.getName(), file.length(), new FileInputStream(file),
					new FileUploadConfig(rename));
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}

		return entity;
	}

	public void setFileUpload(IFileUpload fileUpload) {
		this.fileUpload = fileUpload;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public void setRename(Boolean rename) {
		this.rename = rename;
	}

}

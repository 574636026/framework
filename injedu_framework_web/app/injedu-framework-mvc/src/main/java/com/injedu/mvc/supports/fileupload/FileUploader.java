package com.injedu.mvc.supports.fileupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.injedu.core.exception.constant.ExceptionConstants;
import com.injedu.core.tools.MessageTools;
import com.injedu.utils.date.DateUtils;
import com.injedu.utils.fileupload.IFileUpload;
import com.injedu.utils.fileupload.model.FileEntity;
import com.injedu.utils.fileupload.model.FileUploadConfig;
import com.injedu.utils.fileupload.model.FileUploadDir;

/**
 * 
 * web 文件上传工具
 *
 * @author joy.zhou
 * @date 2016年1月21日
 * @version 1.0
 *
 */
public class FileUploader {

	/** 图片类型适配 */
	private static final Pattern IMAGE = Pattern.compile("^\\.(gif|jpe?g|png|bmp)$");
	/** spring auto inject */
	private IFileUpload client;
	/** 访问根路径 */
	private String webRoot;

	public FileUploader() {
	}

	public FileUploader(IFileUpload client) {
		this.client = client;
	}

	/**
	 * 文件上传
	 * 
	 * @param dir
	 *            上传目录
	 * @param file
	 *            文件
	 * @return
	 * @throws IOException
	 */
	public FileEntity upload(FileUploadDir dir, MultipartFile file) {

		FileEntity res = null;
		try {
			res = upload(dir, file.getOriginalFilename(), file.getSize(), file.getInputStream(),
					new FileUploadConfig());
		} catch (IOException e) {
			return new FileEntity(file.getOriginalFilename(), file.getSize(), "",
					MessageTools.message(ExceptionConstants.SYSTEM_FILEUPLOAD_ERROR, ""));
		}

		return res;
	}

	/**
	 * 文件上传
	 * 
	 * @param dir
	 *            上传目录
	 * @param file
	 *            文件
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public FileEntity upload(FileUploadDir dir, File file) {

		try {
			return upload(dir, file.getName(), file.length(), new FileInputStream(file), new FileUploadConfig());
		} catch (FileNotFoundException e) {
			return new FileEntity(file.getName(), file.length(), "",
					MessageTools.message(ExceptionConstants.SYSTEM_FILEUPLOAD_ERROR, ""));
		}

	}

	/**
	 * 上传图片并生成缩略图
	 * 
	 * 注：缩略图名称规则：mini_原图名称.jpg
	 * 
	 * @param dir
	 *            上传目录
	 * @param subDir
	 *            子目录
	 * @param file
	 *            文件
	 * @return
	 */
	public List<FileEntity> uploadImageAndThumb(FileUploadDir dir, String subDir, MultipartFile file) {

		List<FileEntity> list = new ArrayList<>();
		FileUploadConfig noCompress = new FileUploadConfig();
		noCompress.setCompress(false);

		// 上传原图片
		FileEntity source = this.uploadImage(dir, subDir, file.getOriginalFilename(), file, noCompress);
		// 上传缩略图
		FileEntity thumb = this.uploadImage(dir, subDir, "mini_" + source.getName(), file, new FileUploadConfig(false));

		list.add(thumb);
		list.add(source);

		return list;
	}

	/**
	 * 上传并直接替换图片
	 * 
	 * @param dir
	 *            目录地址
	 * @param name
	 *            图片名称
	 * @param file
	 *            文件
	 * @return
	 */
	public FileEntity uploadImageForReplace(FileUploadDir dir, String name, MultipartFile file) {
		return uploadImage(dir, "", name, file, new FileUploadConfig(false));

	}

	/**
	 * 
	 * 上传图片
	 * 
	 * @param dir
	 *            上传目录
	 * @param subDir
	 *            子目录
	 * @param file
	 *            文件
	 * @param config
	 *            上传配置
	 * @return
	 */
	private FileEntity uploadImage(FileUploadDir dir, String subDir, String name, MultipartFile file,
			FileUploadConfig config) {

		long size = file.getSize();

		FileEntity res = null;
		try {
			res = upload(dir, subDir, name, size, file.getInputStream(), config);
		} catch (IOException e) {
			return new FileEntity(file.getOriginalFilename(), size, "",
					MessageTools.message(ExceptionConstants.SYSTEM_FILEUPLOAD_ERROR, ""));
		}

		return res;
	}

	/**
	 * 文件上传
	 * 
	 * @param dir
	 *            上传目录
	 * @param files
	 *            文件组
	 * @return
	 * @throws IOException
	 */
	public List<FileEntity> upload(FileUploadDir dir, MultipartFile[] files) {

		List<FileEntity> list = new ArrayList<FileEntity>();

		for (MultipartFile file : files) {

			list.add(upload(dir, file));
		}

		return list;
	}

	/**
	 * 文件上传(默认使用当前日期yyyy/MM/dd为子目录)
	 * 
	 * @param dir
	 *            根目录
	 * @param fileName
	 *            文件名
	 * @param size
	 *            文件大小
	 * @param is
	 *            文件流
	 * @param config
	 *            上传配置
	 * @return
	 */
	public FileEntity upload(FileUploadDir dir, String fileName, Long size, InputStream is, FileUploadConfig config) {

		return upload(dir, DateUtils.dateToString(new Date(), "yyyy/MM"), fileName, size, is, config);
	}

	/**
	 * 文件上传
	 * 
	 * @param dir
	 *            根目录
	 * @param subDir
	 *            子目录
	 * @param fileName
	 *            文件名
	 * @param size
	 *            文件大小
	 * @param is
	 *            文件流
	 * @param isRename
	 *            是否重命名
	 * @param isCompress
	 *            是否压缩
	 * @return
	 */
	public FileEntity upload(FileUploadDir dir, String subDir, String fileName, Long size, InputStream is,
			FileUploadConfig config) {

		if (dir == null) {
			return new FileEntity(fileName, size, "",
					MessageTools.message(ExceptionConstants.SYSTEM_FILEUPLOAD_DIR_UNSUPPORT));
		}

		String path = dir.getPath();

		if (StringUtils.isBlank(path)) {
			return new FileEntity(fileName, size, "",
					MessageTools.message(ExceptionConstants.SYSTEM_FILEUPLOAD_DIR_UNKNOW, dir));
		}

		if (StringUtils.isNotBlank(subDir)) {
			path += "/" + subDir;
		}

		FileEntity result = null;

		String type = getFileSuiff(fileName);

		if (isImage(type)) {

			result = client.uploadImage(path, fileName, size, is, config);

		} else {

			result = client.upload(path, fileName, size, is, config);
		}

		result.setUrl(this.getAddress(path, result.getName()));

		return result;

	}

	protected String getAddress(String dir, String fileName) {
		return this.webRoot + dir + "/" + fileName;
	}

	/**
	 * 判断文件是否是图片
	 * 
	 * @return
	 */
	protected boolean isImage(String suiff) {

		return IMAGE.matcher(suiff).matches();
	}

	/**
	 * 获取文件后缀
	 * 
	 * @param name
	 * @return
	 */
	protected String getFileSuiff(String name) {

		String suiff = "";
		int idx = name.lastIndexOf(".");
		if (idx > -1) {
			suiff = name.substring(idx);
		}
		return suiff;
	}

	public void setClient(IFileUpload client) {
		this.client = client;
	}

	public void setWebRoot(String webRoot) {
		this.webRoot = webRoot;
	}

}

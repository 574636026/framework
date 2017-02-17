package com.injedu.bookproduce.core;

import java.io.File;

/**
 * 文件内容实体
 *
 * @author joy.zhou
 * @date 2016年8月13日
 * @version 1.0
 *
 */
public class ProduceFileEntity {
	/** 文件类型 */
	private FileType fileType;
	/** 下载目录 */
	private File dir;
	/** 路径 */
	private String path;
	/** 文件名称 */
	private String fileName;
	/** 文件内容 */
	private String content;
	/** 文件消息 */
	private String message;

	public ProduceFileEntity(FileType fileType, File dir, String path) {
		this.fileType = fileType;
		this.dir = dir;
		this.path = path;
	}

	public ProduceFileEntity(FileType fileType, File dir, String path, String message) {
		this(fileType, dir, path);
		this.message = message;
	}

	public ProduceFileEntity(FileType fileType, File dir, String path, String content, String message) {
		this(fileType, dir, path, message);
		this.content = content;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public File getDir() {
		return dir;
	}

	public void setDir(File dir) {
		this.dir = dir;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 文件类型
	 *
	 * @author joy.zhou
	 * @date 2016年8月15日
	 * @version 1.0
	 *
	 */
	public static enum FileType {
		FILE, IMAGE
	}
}

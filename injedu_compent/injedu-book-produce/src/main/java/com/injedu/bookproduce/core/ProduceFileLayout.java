package com.injedu.bookproduce.core;

import java.io.File;

/**
 * 文件格式
 *
 * @author joy.zhou
 * @date 2016年8月13日
 * @version 1.0
 *
 */
public class ProduceFileLayout {
	/** 根目录 */
	private File rootDir;
	/** 章节目录 */
	private File articleDir;
	/** 图片目录 */
	private File imageDir;

	public ProduceFileLayout() {
	}

	public ProduceFileLayout(File rootDir, File articleDir, File imageDir) {
		this.rootDir = rootDir;
		this.articleDir = articleDir;
		this.imageDir = imageDir;
	}

	public File getRootDir() {
		return rootDir;
	}

	public void setRootDir(File rootDir) {
		this.rootDir = rootDir;
	}

	public File getArticleDir() {
		return articleDir;
	}

	public void setArticleDir(File articleDir) {
		this.articleDir = articleDir;
	}

	public File getImageDir() {
		return imageDir;
	}

	public void setImageDir(File imageDir) {
		this.imageDir = imageDir;
	}

}

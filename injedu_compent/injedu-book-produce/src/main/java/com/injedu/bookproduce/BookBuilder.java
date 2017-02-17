package com.injedu.bookproduce;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.injedu.bookproduce.core.ProduceFileEntity;
import com.injedu.bookproduce.core.ProduceFileLayout;
import com.injedu.bookproduce.core.adapter.IBookDataAdapter;
import com.injedu.bookproduce.core.constant.BookBuilderConstant;
import com.injedu.bookproduce.core.model.ProduceBookData;
import com.injedu.bookproduce.executor.BookArchive;
import com.injedu.bookproduce.executor.BookWriter;
import com.injedu.bookproduce.executor.DefaultBookArchive;

import rx.Observable;

/**
 * 
 * 书本创建器
 * 
 * @author joy.zhou
 * @date 2016年8月4日
 * @version 1.0
 *
 */
public class BookBuilder {

	protected static final Logger logger = LoggerFactory.getLogger(BookBuilder.class);
	/** 重试次数 */
	private static int RETRY_COUNT = 3;
	/** serialName */
	private String serialName;
	/** 根目录 */
	private String dir;

	public BookBuilder() {
	}

	public BookBuilder(String dir) {
		this.dir = dir;
	}

	/**
	 * 执行创建
	 * 
	 * 1.初始换参数信息
	 * 
	 * 2.格式化书本数据
	 * 
	 * 3.生成书本
	 * 
	 * @throws IOException
	 */
	public void execute(ProduceBookData data, BookWriter executor) {

		// 初始化序列名称
		this.serialName = initSerialName(data);
		logger.info(String.format("书本[%s]开始生成..", this.serialName));
		// 初始化目录
		ProduceFileLayout layout = this.initDirs(this.dir);
		logger.info(String.format("书本[%s]内容归档..", this.serialName));
		/** 书本转换器 */
		DefaultBookArchive bookArchive = new DefaultBookArchive();

		BookArchive.ArchiveFile archiveFile = bookArchive.archive(layout, data);
		// 执行写入(合并处理)
		List<ProduceFileEntity> list = archiveFile.toAll();
		archiveFile = null;
		executor.setRootDir(layout.getRootDir());
		logger.info(String.format("书本[%s]写入,文件、图片数:[%d]...", this.serialName, list.size()));
		Observable.from(list).retry(RETRY_COUNT).subscribe(executor);

	}

	/**
	 * 执行创建
	 * 
	 * @throws IOException
	 */
	public void execute(IBookDataAdapter adapter, BookWriter executor) {

		this.execute(adapter.getData(), executor);
	}

	/**
	 * 创建书本文件夹
	 */
	protected ProduceFileLayout initDirs(String dir) {

		File rootDir = null;
		if (StringUtils.isBlank(dir)) {
			rootDir = new File(this.serialName);
		} else {
			rootDir = new File(this.dir, this.serialName);
		}
		File artilesDir = new File(rootDir, BookBuilderConstant.BookFolder.ARTILES);
		File imageDir = new File(rootDir, BookBuilderConstant.BookFolder.IMAGES);

		if (!rootDir.exists()) {
			rootDir.mkdirs();
		}
		if (!artilesDir.exists()) {
			artilesDir.mkdirs();
		}
		if (!imageDir.exists()) {
			imageDir.mkdirs();
		}

		return new ProduceFileLayout(rootDir, artilesDir, imageDir);
	}

	/**
	 * 初始化序列名称(默认使用书本ID)
	 * 
	 * @param data
	 * @return
	 */
	protected String initSerialName(ProduceBookData data) {

		return String.valueOf(data.getBook().getId());
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

}

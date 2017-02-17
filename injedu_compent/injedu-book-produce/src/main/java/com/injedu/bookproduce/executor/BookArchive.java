package com.injedu.bookproduce.executor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.injedu.bookproduce.core.ProduceFileEntity;
import com.injedu.bookproduce.core.ProduceFileLayout;
import com.injedu.bookproduce.core.model.ProduceBook;
import com.injedu.bookproduce.core.model.ProduceBookArticle;
import com.injedu.bookproduce.core.model.ProduceBookCatalog;
import com.injedu.bookproduce.core.model.ProduceBookData;

/**
 * 书本文件归档器
 *
 * @author joy.zhou
 * @date 2016年8月13日
 * @version 1.0
 *
 */
public abstract class BookArchive {

	protected ProduceFileLayout layout;

	/**
	 * 归档书本
	 * 
	 * @param data
	 *            书本数据
	 */
	public ArchiveFile archive(ProduceFileLayout layout, ProduceBookData data) {

		this.layout = layout;

		ArchiveFile archiveFile = new ArchiveFile();
		archiveFile.add(this.archiveBookInfo(data.getBook()));
		archiveFile.add(this.archiveCatalog(data.getCatalogList()));
		archiveFile.add(this.archiveArticle(data.getArticles()));

		return archiveFile;
	}

	/**
	 * 书本基础信息归档
	 * 
	 * @param book
	 *            书本信息
	 */
	protected abstract ArchiveFile archiveBookInfo(ProduceBook book);

	/**
	 * 书本目录归档
	 * 
	 * @param catalogList
	 *            目录信息
	 */
	protected abstract ArchiveFile archiveCatalog(List<ProduceBookCatalog> catalogList);

	/**
	 * 书本章节归档
	 * 
	 * @param articles
	 *            章节信息
	 */
	protected abstract ArchiveFile archiveArticle(Map<Long, List<ProduceBookArticle>> articles);

	public static class ArchiveFile {
		/** 归档文件 */
		private Collection<ProduceFileEntity> files = new ArrayList<>();
		/** 归档图片 */
		protected Collection<ProduceFileEntity> images = new HashSet<>();

		public Collection<ProduceFileEntity> getFiles() {
			return files;
		}

		public Collection<ProduceFileEntity> getImages() {
			return images;
		}

		public void add(ArchiveFile archiveFile) {
			this.files.addAll(archiveFile.getFiles());
			this.images.addAll(archiveFile.getImages());
		}

		public List<ProduceFileEntity> toAll() {
			List<ProduceFileEntity> list = new LinkedList<>();
			list.addAll(this.files);
			list.addAll(this.images);
			return list;
		}
	}

}

package com.injedu.bookproduce.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.injedu.bookproduce.core.ProduceFileEntity;
import com.injedu.bookproduce.core.ProduceFileEntity.FileType;
import com.injedu.bookproduce.core.constant.BookBuilderConstant;
import com.injedu.bookproduce.core.constant.BookBuilderConstant.BookSuffix;
import com.injedu.bookproduce.core.convert.IBookConvert;
import com.injedu.bookproduce.core.convert.IBookImageConvert;
import com.injedu.bookproduce.core.convert.IBookImageConvert.ArticleImage;
import com.injedu.bookproduce.core.convert.JSONBookConvert;
import com.injedu.bookproduce.core.model.ProduceBook;
import com.injedu.bookproduce.core.model.ProduceBookArticle;
import com.injedu.bookproduce.core.model.ProduceBookCatalog;

/**
 * 默认文件归档器
 *
 * @author joy.zhou
 * @date 2016年8月13日
 * @version 1.0
 *
 */
public class DefaultBookArchive extends BookArchive {

	/** 内容转换器 */
	private IBookConvert bookConvert = new JSONBookConvert();

	public DefaultBookArchive() {
	}

	public DefaultBookArchive(IBookConvert bookConvert) {
		this.bookConvert = bookConvert;
	}

	/**
	 * 书本基础信息归档
	 * 
	 * @param book
	 *            书本信息
	 */
	@Override
	protected ArchiveFile archiveBookInfo(ProduceBook book) {

		ArchiveFile archiveFile = new ArchiveFile();

		String content = bookConvert.convertBook(book);

		archiveFile.getFiles().add(new ProduceFileEntity(FileType.FILE, this.layout.getRootDir(),
				BookBuilderConstant.BOOK_DEFAULT_NAME + BookSuffix.TIT, content, this.getBookMessage()));
		// 修改： 不打包书本封面
		// if (bookConvert instanceof IBookImageConvert) {
		//
		// String coverPath = ((IBookImageConvert) bookConvert).getCoverImage();
		//
		// if (StringUtils.isNotBlank(coverPath)) {
		//
		// ProduceFileEntity cover = new ProduceFileEntity(FileType.IMAGE,
		// this.layout.getRootDir(), coverPath,
		// this.getBookMessage());
		// // 使用图片ID作书本封面
		// cover.setFileName(book.getId() +
		// coverPath.substring(coverPath.lastIndexOf(".")));
		//
		// archiveFile.getImages().add(cover);
		//
		// }
		//
		// }

		return archiveFile;

	}

	/**
	 * 书本目录归档
	 * 
	 * @param catalogList
	 *            目录信息
	 */
	@Override
	protected ArchiveFile archiveCatalog(List<ProduceBookCatalog> catalogList) {

		ArchiveFile archiveFile = new ArchiveFile();

		archiveFile.getFiles()
				.add(new ProduceFileEntity(FileType.FILE, this.layout.getRootDir(),
						BookBuilderConstant.BOOK_DEFAULT_NAME + BookSuffix.CATALOG,
						bookConvert.convertCatalog(catalogList), this.getBookMessage()));

		return archiveFile;
	}

	/**
	 * 书本章节归档
	 * 
	 * @param articles
	 *            章节信息
	 */
	@Override
	protected ArchiveFile archiveArticle(Map<Long, List<ProduceBookArticle>> articles) {

		ArchiveFile archiveFile = new ArchiveFile();

		for (Entry<Long, List<ProduceBookArticle>> entry : articles.entrySet()) {

			String article_path = entry.getKey() + BookSuffix.TIT;

			Long catalogId = entry.getKey();

			ProduceFileEntity entity = new ProduceFileEntity(FileType.FILE, this.layout.getArticleDir(), article_path,
					bookConvert.convertArticle(entry.getValue()), this.getBookMessage(catalogId));

			if (bookConvert instanceof IBookImageConvert) {
				IBookImageConvert convert = ((IBookImageConvert) bookConvert);

				for (ArticleImage articleImage : convert.getArticleImages()) {

					archiveFile.getImages().addAll(getImageFileEntity(catalogId, articleImage));

				}
				convert.getArticleImages().clear();
			}
			archiveFile.getFiles().add(entity);
		}

		return archiveFile;

	}

	private List<ProduceFileEntity> getImageFileEntity(Long catalogId, ArticleImage articleImage) {

		List<ProduceFileEntity> list = new ArrayList<>();

		for (Entry<String, List<String>> entry : articleImage.getImages().entrySet()) {

			String field = entry.getKey();

			for (String url : entry.getValue()) {
				list.add(new ProduceFileEntity(FileType.IMAGE, this.layout.getImageDir(), url,
						this.getBookMessage(catalogId, articleImage.getId(), field)));
			}

		}

		return list;

	}

	/**
	 * 获取 书本消息
	 * 
	 * @return
	 */
	protected String getBookMessage() {

		return String.format("book:%s", this.layout.getRootDir().getPath());
	}

	/**
	 * 获取 书本消息
	 * 
	 * @param catalogId
	 *            目录ID
	 * @return
	 */
	protected String getBookMessage(Long catalogId) {

		return String.format("书本:%s,目录:%d", this.layout.getRootDir().getPath(), catalogId);
	}

	/**
	 * 获取 书本消息
	 * 
	 * @param catalogId
	 *            目录ID
	 * @param articleId
	 *            文章ID
	 * @param articleTitle
	 *            文章标题
	 * @param field
	 *            文章字段
	 * @return
	 */
	protected String getBookMessage(Long catalogId, Long articleId, String field) {

		String cnfield = JSONBookConvert.CNField.get(field);

		return String.format("书本:%s,目录:%d,文章:%d,属性:%s", this.layout.getRootDir().getPath(), catalogId, articleId,
				StringUtils.isNotBlank(cnfield) ? cnfield : field);
	}

	public void setBookConvert(IBookConvert bookConvert) {
		this.bookConvert = bookConvert;
	}

}

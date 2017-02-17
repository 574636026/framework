package com.injedu.bookproduce.core.convert;

import java.util.List;

import com.injedu.bookproduce.core.model.ProduceBook;
import com.injedu.bookproduce.core.model.ProduceBookArticle;
import com.injedu.bookproduce.core.model.ProduceBookCatalog;

/**
 * 
 * 书本转换器
 * 
 * B<书本转换对象>,C<目录转换对象>,AR<章节转换对象>
 *
 * @author joy.zhou
 * @date 2016年8月5日
 * @version 1.0
 *
 */
public interface IBookConvert {

	/**
	 * 转换书本
	 * 
	 * @param book
	 *            书本
	 * @return
	 */
	public String convertBook(ProduceBook book);

	/**
	 * 转换目录
	 * 
	 * @param catalog
	 *            目录
	 * @return
	 */
	public String convertCatalog(List<ProduceBookCatalog> catalog);

	/**
	 * 转换章节
	 * 
	 * @param article
	 *            章节
	 * @return
	 */
	public String convertArticle(List<ProduceBookArticle> article);
}

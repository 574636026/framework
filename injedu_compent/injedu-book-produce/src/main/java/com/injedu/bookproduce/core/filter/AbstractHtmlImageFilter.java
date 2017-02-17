package com.injedu.bookproduce.core.filter;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;

import com.injedu.bookproduce.core.constant.BookBuilderConstant.BookFolder;

/**
 * 
 * html 图片过滤器
 *
 * @author joy.zhou
 * @date 2016年8月4日
 * @version 1.0
 *
 */
public abstract class AbstractHtmlImageFilter implements IHtmlFilter {

	private List<String> images;

	@Override
	public void doFilter(Document doc) {
		this.images = this.doImageFilter(doc);
	}

	protected abstract List<String> doImageFilter(Document doc);

	public List<String> getImages() {
		return images;
	}

	/**
	 * 图片相对路径
	 * 
	 * @param url
	 *            图片地址
	 * @return
	 */
	protected String getRelativePath(String url) {

		String fileName = url.substring(url.lastIndexOf("/") + 1);

		return BookFolder.IMAGES + "/" + fileName;
	}

	/**
	 * URL补丁
	 * 
	 * \"
	 * 
	 * @param url
	 * @return
	 */
	protected String urlPatch(String url) {

		if (StringUtils.isBlank(url)) {
			return url;
		}

		return url.replaceAll("\\\\\"", "");
	}

}

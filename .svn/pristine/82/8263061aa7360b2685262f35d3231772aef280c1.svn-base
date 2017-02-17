package com.injedu.bookproduce.core.convert;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public interface IBookImageConvert extends IBookConvert {

	/**
	 * 获取封面图片地址
	 */
	public String getCoverImage();

	/**
	 * 获取文章提取出的图片地址
	 */
	public Collection<ArticleImage> getArticleImages();

	/**
	 * 文章图片
	 *
	 * @author joy.zhou
	 * @date 2016年9月21日
	 * @version 1.0
	 *
	 */
	public static class ArticleImage {
		/** 文章ID */
		private Long id;
		/** 文章标题 */
		private String title;
		/** 图片地址 */
		private Map<String, List<String>> images = new HashMap<>(0);

		public ArticleImage() {
		}

		public ArticleImage(Long id, String title, Map<String, List<String>> images) {
			this.id = id;
			this.title = title;
			this.images = images;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Map<String, List<String>> getImages() {
			return images;
		}

		public void setImages(Map<String, List<String>> images) {
			this.images = images;
		}

	}
}

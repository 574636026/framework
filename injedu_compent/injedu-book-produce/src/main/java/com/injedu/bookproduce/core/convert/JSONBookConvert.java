package com.injedu.bookproduce.core.convert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.injedu.bookproduce.core.filter.AbstractHtmlImageFilter;
import com.injedu.bookproduce.core.filter.BackgroundFilter;
import com.injedu.bookproduce.core.filter.ImgSrcAndWidthFilter;
import com.injedu.bookproduce.core.json.JSONFilterObject;
import com.injedu.bookproduce.core.model.ProduceBook;
import com.injedu.bookproduce.core.model.ProduceBookArticle;
import com.injedu.bookproduce.core.model.ProduceBookCatalog;
import com.injedu.bookproduce.core.model.ProduceBookKnowledge;
import com.injedu.bookproduce.core.model.ProduceBookTopic;

/**
 * 
 * 书本数据转换器
 *
 * @author joy.zhou
 * @date 2016年8月5日
 * @version 1.0
 *
 */
public class JSONBookConvert implements IBookImageConvert {

	/** html 过滤器 */
	private AbstractHtmlImageFilter[] filters;
	/** 封面图片 */
	private String coverImage;
	/** 章节图片 */
	private Collection<ArticleImage> articleImages = new ArrayList<>(0);

	public static final Map<String, String> CNField = new HashMap<>();

	static {
		CNField.put("title", "标题");
		CNField.put("subjectContent", "题干");
		CNField.put("subjectOpation", "题目选项");
		CNField.put("subjectAnswer", "题目答案");
		CNField.put("subjectAnalysis", "题目解析");
		CNField.put("subjectDirect", "题目点拨");
		CNField.put("subjectKnowledge", "知识点");
		CNField.put("body", "文章内容");
	}

	public JSONBookConvert() {
		this.filters = this.getDefaultHtmlFilter();
	}

	public JSONBookConvert(AbstractHtmlImageFilter... filters) {
		this.filters = filters;
	}

	/**
	 * 获取默认的html过滤器
	 * 
	 * @return
	 */
	private AbstractHtmlImageFilter[] getDefaultHtmlFilter() {
		return new AbstractHtmlImageFilter[] { new BackgroundFilter(), new ImgSrcAndWidthFilter() };
	}

	@Override
	public String convertBook(ProduceBook book) {
		if (StringUtils.isNotBlank(book.getCover())) {
			this.coverImage = book.getCover();
			book.setCover(book.getCover().substring(book.getCover().lastIndexOf("/")));
		}
		return JSON.toJSONString(book);
	}

	@Override
	public String convertCatalog(List<ProduceBookCatalog> catalogList) {

		JSONArray array = new JSONArray();
		for (ProduceBookCatalog catalog : catalogList) {
			JSONObject entity = convertCatalog(catalog);
			array.add(entity);
		}
		return array.toJSONString();
	}

	private JSONObject convertCatalog(ProduceBookCatalog catalog) {

		JSONObject entity = new JSONObject();
		entity.put("id", catalog.getId());
		entity.put("title", Jsoup.clean(catalog.getName(), Whitelist.none()));
		// 子目录
		JSONArray childs = new JSONArray();
		if (!catalog.getChilds().isEmpty()) {
			for (ProduceBookCatalog child : catalog.getChilds()) {
				childs.add(convertCatalog(child));
			}
		}
		entity.put("child", childs);
		// 章节目录处理
		entity.put("count", catalog.getArticles().size());

		return entity;
	}

	/**
	 * 章节目录
	 * 
	 * @param catalogList
	 * @return
	 */
	// private JSONArray convertAcl(List<ProduceBookArticle> catalogList) {
	//
	// JSONArray array = new JSONArray();
	// for (ProduceBookArticle catalog : catalogList) {
	// JSONObject entity = convertAcl(catalog);
	// array.add(entity);
	// }
	// return array;
	// }
	//
	// private JSONObject convertAcl(ProduceBookArticle article) {
	//
	// JSONObject entity = new JSONObject();
	// entity.put("id", article.getId());
	// entity.put("type", article.getArticleType());
	// entity.put("title", Jsoup.clean(article.getTitle(), Whitelist.none()));
	// entity.put("child", new JSONArray());
	// entity.put("count", 0);
	//
	// return entity;
	// }

	@Override
	public String convertArticle(List<ProduceBookArticle> articles) {

		JSONArray array = new JSONArray();

		for (ProduceBookArticle article : articles) {
			JSONObject entity = null;
			if (article instanceof ProduceBookKnowledge) {
				entity = convertKnowledge((ProduceBookKnowledge) article);
			} else if (article instanceof ProduceBookTopic) {
				entity = convertTopic((ProduceBookTopic) article);
			}

			if (entity instanceof JSONFilterObject) {
				this.articleImages.add(
						new ArticleImage(article.getId(), article.getTitle(), ((JSONFilterObject) entity).getImages()));
				// 子题目
				JSONArray sub = entity.getJSONArray("sub");
				if (sub != null && sub.size() > 0) {
					for (int i = 0; i < sub.size(); i++) {
						this.articleImages.add(new ArticleImage(article.getId(),
								article.getTitle() + "(" + entity.getString("title") + ")",
								((JSONFilterObject) sub.get(i)).getImages()));

					}
				}

			}
			array.add(entity);
		}

		return array.toJSONString();
	}

	/**
	 * 转换知识点章节
	 * 
	 * @param entity
	 * @return
	 */
	private JSONObject convertKnowledge(ProduceBookKnowledge entity) {

		JSONObject map = new JSONFilterObject(this.filters);
		map.put("id", entity.getId());
		map.put("type", entity.getArticleType());
		map.put("title", entity.getTitle());
		map.put("body", entity.getBody());
		map.put("knowledge", entity.getKnowledge());

		return map;
	}

	/**
	 * 获取书本题目章节内容
	 * 
	 * @param dir
	 *            根目录
	 * @param catalogId
	 *            目录ID
	 * @param entity
	 *            题目数据
	 * @return
	 * @throws IOException
	 */
	private JSONObject convertTopic(ProduceBookTopic entity) {
		JSONObject map = convertToBookTopic(entity);
		if (!entity.getChilds().isEmpty()) {
			JSONArray jsonarry = new JSONArray();
			for (ProduceBookTopic topic : entity.getChilds()) {
				JSONObject mapsub = convertToBookTopic(topic);
				jsonarry.add(mapsub);
			}
			map.put("sub", jsonarry);
		}
		return map;
	}

	/**
	 * 转换题目数据
	 * 
	 * @return
	 * @throws IOException
	 */
	private JSONObject convertToBookTopic(ProduceBookTopic entity) {
		JSONObject map = new JSONFilterObject(this.filters);
		map.put("id", entity.getId());
		map.put("type", entity.getArticleType());
		map.put("title", entity.getTitle());
		map.put("subjectType", entity.getTopicTypeName());
		map.put("subjectContent", entity.getStem());
		map.put("subjectOpation", entity.getOption());
		map.put("subjectAnswer", entity.getResult());
		map.put("subjectAnalysis", entity.getAnalysis());
		map.put("subjectDirect", entity.getPoint());
		map.put("subjectKnowledge", entity.getKnowledge());

		return map;
	}

	public String getCoverImage() {
		return coverImage;
	}

	@Override
	public Collection<ArticleImage> getArticleImages() {
		return articleImages;
	}

}

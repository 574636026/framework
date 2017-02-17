package com.injedu.bookproduce.core.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import com.alibaba.fastjson.JSONObject;
import com.injedu.bookproduce.core.filter.AbstractHtmlImageFilter;
import com.injedu.bookproduce.core.filter.IHtmlFilter;

/**
 * 
 *
 * @author joy.zhou
 * @date 2016年8月5日
 * @version 1.0
 *
 */
/**
 *
 * @author joy.zhou
 * @date 2016年9月23日
 * @version 1.0
 *
 */
public class JSONFilterObject extends JSONObject {

	private static final long serialVersionUID = 1L;
	/** html 过滤器 */
	private AbstractHtmlImageFilter[] filters;
	/** 图片地址 */
	private Map<String, List<String>> images = new HashMap<>();

	public JSONFilterObject(AbstractHtmlImageFilter... filters) {
		this.filters = filters;
	}

	@Override
	public Object put(String key, Object value) {

		Object result = value;

		if (value instanceof String) {
			result = this.getFilterValue(key, (String) value);
		} else if (value instanceof String[]) {
			result = this.getFilterValue(key, (String[]) value);
		}

		return super.put(key, result);
	}

	public Map<String, List<String>> getImages() {
		return images;
	}

	/**
	 * 是否过滤 value
	 * 
	 * 1. 非空
	 * <p>
	 * 2. html格式
	 * 
	 * @param value
	 */
	protected Object putFilterValue(String key, String value) {

		return super.put(key, getFilterValue(key, value));

	}

	/*
	 * 是否过滤 value
	 * 
	 * 1. 非空 <p> 2. html格式
	 * 
	 * @param key
	 * 
	 * @param value
	 * 
	 * @return
	 */
	protected String[] getFilterValue(String key, String[] values) {

		String[] result = new String[values.length];

		int i = 0;
		for (String value : values) {
			result[i] = getFilterValue(key, value);
			i++;
		}

		return result;
	}

	private String getFilterValue(String key, String value) {

		if (Jsoup.isValid((String) value, Whitelist.basicWithImages())) {
			return value;
		}

		Document doc = Jsoup.parseBodyFragment((String) value);
		Document.OutputSettings settings = new Document.OutputSettings();
		settings.prettyPrint(false);
		doc.outputSettings(settings);

		for (IHtmlFilter filter : this.filters) {
			filter.doFilter(doc);
			if (filter instanceof AbstractHtmlImageFilter) {
				if (this.images.get(key) == null) {
					this.images.put(key, new ArrayList<String>());
				}
				this.images.get(key).addAll(((AbstractHtmlImageFilter) filter).getImages());
			}
		}
		return doc.body().html();
	}

}

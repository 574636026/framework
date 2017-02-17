package com.injedu.bookproduce.core.filter;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * img src and width filter
 * 
 * 1. src地址替换为相对路径
 * <p>
 * 2. width > 340 替换为 100%
 *
 * @author joy.zhou
 * @date 2016年8月4日
 * @version 1.0
 *
 */
public class BackgroundFilter extends AbstractHtmlImageFilter {

	private static final String ATTR = "background";

	@Override
	protected List<String> doImageFilter(Document doc) {
		List<String> result = new ArrayList<>();

		Elements elements = doc.getElementsByAttribute(ATTR);

		if (elements == null || elements.isEmpty()) {
			return result;
		}
		for (Element element : elements) {
			String url = this.urlPatch(element.attr(ATTR));
			element.attr(ATTR, this.getRelativePath(url));
			result.add(url);
		}

		return result;
	}

}

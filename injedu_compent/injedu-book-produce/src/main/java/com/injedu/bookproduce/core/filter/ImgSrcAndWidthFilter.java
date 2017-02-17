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
public class ImgSrcAndWidthFilter extends AbstractHtmlImageFilter {

	private static final String TAG_IMG = "img";

	private static final String ATTR_SRC = "src";

	private static final String ATTR_WIDTH = "width";

	@Override
	protected List<String> doImageFilter(Document doc) {

		List<String> result = new ArrayList<>();

		Elements elements = doc.getElementsByTag(TAG_IMG);

		if (elements == null || elements.isEmpty()) {
			return result;
		}

		for (Element element : elements) {
			// 图片地址处理
			if (element.hasAttr(ATTR_SRC)) {
				String url = this.urlPatch(element.attr(ATTR_SRC));
				element.attr(ATTR_SRC, this.getRelativePath(url));
				result.add(url);
			}
			// 图片大小处理
			if (element.hasAttr(ATTR_WIDTH)) {
				try {
					Integer width = Integer.parseInt(element.attr(ATTR_WIDTH));
					// px?
					if (width > 340) {
						element.attr(ATTR_WIDTH, "100%");
					}
				} catch (Exception e) {
				}
			}
		}

		return result;
	}

}

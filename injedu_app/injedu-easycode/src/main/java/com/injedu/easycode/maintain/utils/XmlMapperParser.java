package com.injedu.easycode.maintain.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * XML配置文件解析类
 *
 * User: liyd Date: 13-11-20 Time: 下午2:53
 */
public class XmlMapperParser {

	private static final String REPLACE_MAP_TAG = "resultMap";
	private static final String REPLACE_SAVE_TAG = "insert";
	private static final String REPLACE_UPDATE_TAG = "update";
	private static final String REPLACE_SQL_TAG = "sql";

	private static final String REPLACE_MAP_BASE_ID = "BaseResultMap";
	private static final String REPLACE_MAP_ENTITY_ID = "EntityResultMap";
	private static final String REPLACE_SAVE_ID = "save";
	private static final String REPLACE_UPDATE_ID = "update";
	private static final String REPLACE_SQL_ORDER_ID = "orderInclude";
	private static final String REPLACE_SAVE_BATH = "saveBath";

	public static String parseReplaceXml(String source, String template) {

		List<ReplaceTag> tags = new ArrayList<ReplaceTag>();
		tags.add(new ReplaceTag(REPLACE_MAP_TAG, REPLACE_MAP_ENTITY_ID));
		tags.add(new ReplaceTag(REPLACE_SAVE_TAG, REPLACE_SAVE_ID));
		tags.add(new ReplaceTag(REPLACE_UPDATE_TAG, REPLACE_UPDATE_ID));
		tags.add(new ReplaceTag(REPLACE_SQL_TAG, REPLACE_SQL_ORDER_ID));
		tags.add(new ReplaceTag(REPLACE_SAVE_TAG, REPLACE_SAVE_BATH));

		String result = source;

		for (ReplaceTag tag : tags) {

			result = replaceTagContent(tag.name, tag.id, result, template);

		}

		return result;
	}

	private static String replaceTagContent(String tag, String id, String source, String target) {

		String regex = getRegex(tag, id);

		if (id.equals(REPLACE_MAP_ENTITY_ID) && !isMatchTag(REPLACE_MAP_TAG, REPLACE_MAP_ENTITY_ID, source)) {
			regex = getRegex(tag, REPLACE_MAP_BASE_ID);
		}
		
		System.out.println(regex);

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(source);

		if (matcher.matches() && matcher.groupCount() > 0) {

			String content = getTagContent(tag, id, target);

			System.out.println(content);
			
			if (content == null) {
				return source;
			}

			StringBuffer result = new StringBuffer();

			result.append(source.substring(0, matcher.start(1)));
			result.append(content);
			result.append(source.substring(matcher.end(1)));

			return result.toString();

		}

		return source;

	}

	private static boolean isMatchTag(String tag, String id, String source) {

		String regex = getRegex(tag, id);

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(source);

		return matcher.matches() && matcher.groupCount() > 0;

	}

	private static String getRegex(String tag, String id) {

		StringBuffer regex = new StringBuffer("[\\w\\W]*<");
		regex.append(tag);
		if (StringUtils.isNotBlank(id)) {
			regex.append("[^>]*").append("id=\"" + id).append("\"");
		}
		regex.append("[^>]*?>").append("([\\w\\W]*)").append("<\\/").append(tag).append(">").append("[\\w\\W]*");

		return regex.toString();

	}

	private static String getTagContent(String tag, String id, String content) {

		Pattern pattern = Pattern.compile(getRegex(tag, id));

		Matcher matcher = pattern.matcher(content);

		if (matcher.matches()) {

			return matcher.group(1);
		}
		return null;
	}

	public static void main(String[] args) throws FileNotFoundException {

		String dic = "F:\\EclipseWorkPlace\\WebWorkPlace\\injedu_framework\\app\\framework-easycode\\src\\test\\resources";

		File sourceFile = new File(dic + "\\ResCourseMapper.xml");

		String source = FileUtils.readInStream(new FileInputStream(sourceFile));

		String template = FileUtils.readInStream(new FileInputStream(dic + "\\ResCourseMapper_replace.xml"));

		System.out.println(XmlMapperParser.parseReplaceXml(source, template));

	}

}

/**
 * 替换标签
 *
 * @author joy.zhou
 * @date 2015年12月28日
 * @version 1.0
 *
 */
class ReplaceTag {
	/** 标签名称 */
	String name;
	/** 标签ID */
	String id;

	public ReplaceTag(String name, String id) {
		this.name = name;
		this.id = id;
	}

}

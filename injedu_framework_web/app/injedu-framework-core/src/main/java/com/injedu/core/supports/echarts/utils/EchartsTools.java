package com.injedu.core.supports.echarts.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.feature.Feature;
import com.github.abel533.echarts.series.Line;
import com.injedu.core.supports.echarts.data.CategoryData;

/**
 * 
 * 生成Echarts options
 *
 * @author joy.zhou
 * @date 2016年4月12日
 * @version 1.0
 *
 */
public class EchartsTools {

	/** 默认工具组 */
	private static Object[] DEFAULT_TOOLS = { Feature.dataView, Feature.restore, Feature.saveAsImage };

	/**
	 * 
	 * 生成折线图
	 * 
	 * @param orient
	 *            方向
	 * @param datas
	 *            值
	 * @return
	 */
	public static Option transCategoryLine(Title title, LinkedHashMap<String, List<CategoryData>> datas) {

		Option option = new Option();
		// 设置工具栏
		option.title(title);
		option.toolbox().feature(DEFAULT_TOOLS);
		option.tooltip(Trigger.axis);
		// 设置值域
		if (datas == null || datas.isEmpty()) {
			return option;
		}
		option.yAxis(new ValueAxis());
		Legend legend = new Legend();
		for (Entry<String, List<CategoryData>> data : datas.entrySet()) {
			String key = data.getKey();
			legend.data(key);
			CategoryAxis categoryAxis = new CategoryAxis();
			Line line = new Line();
			for (CategoryData categoryData : data.getValue()) {
				categoryAxis.data(categoryData.getName());
				line.name(key).data(categoryData.getValue());
			}
			option.series(line);
			option.xAxis(categoryAxis);
		}
		option.legend(legend);

		return option;
	}

}

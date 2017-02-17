package com.injedu.supports.push.handle.ym.filter;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 配合Groupcast使用的条件生成器
 *
 * @author joy.zhou
 * @date 2016年1月15日
 * @version 1.0
 *
 */
public class FiterQuery {

	JSONArray conditions = new JSONArray();

	private static final String AND = "and";
	private static final String OR = "or";
	private static final String NOT = "not";
	private static final String WHERE = "where";

	/**
	 * 添加 and 条件
	 * 
	 * @param entity
	 * @return
	 */
	public FiterQuery and(FilterEntity entity) {
		List<FilterEntity> entitys = new ArrayList<FilterEntity>(1);
		entitys.add(entity);
		return by(AND, entitys);
	}

	/**
	 * 添加 or 条件
	 * 
	 * @param entity
	 * @return
	 */
	public FiterQuery or(FilterEntity entity) {
		List<FilterEntity> entitys = new ArrayList<FilterEntity>(1);
		entitys.add(entity);
		return by(OR, entitys);
	}

	/**
	 * 添加 and 条件
	 * 
	 * @param entity
	 * @return
	 */
	public FiterQuery and(List<FilterEntity> entity) {
		return by(AND, entity);
	}

	/**
	 * 添加 or 条件
	 * 
	 * @param entity
	 * @return
	 */
	public FiterQuery or(List<FilterEntity> entity) {
		return by(OR, entity);
	}

	/**
	 * 
	 * 添加过滤条件
	 * 
	 * @param operator
	 *            过滤类型
	 * @param entitys
	 *            过滤条件
	 * @return
	 */
	private FiterQuery by(String operator, List<FilterEntity> entitys) {

		JSONArray array = new JSONArray();
		for (FilterEntity entity : entitys) {

			JSONObject object = new JSONObject();
			object.put(entity.getType().getName(), entity.getValue());
			if (entity.isNot()) {
				JSONObject notObject = new JSONObject();
				notObject.put(NOT, object);
				array.add(notObject);
			} else {
				array.add(object);
			}
		}
		JSONObject op = new JSONObject();
		op.put(operator, array);
		conditions.add(op);
		return this;
	}

	public JSONObject toJsonObject() {
		JSONObject filter = new JSONObject();
		// 顶层一定要是and
		JSONObject andWhere = new JSONObject();
		andWhere.put(AND, conditions);
		filter.put(WHERE, andWhere);
		return filter;
	}

	@Override
	public String toString() {
		return toJsonObject().toString();
	}

}

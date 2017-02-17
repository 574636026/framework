package com.injedu.mvc.supports.fastjson;

import java.util.Set;

import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.google.common.collect.Sets;
import com.injedu.core.domain.BaseDomain;

/**
 * 
 * fast json默认忽略字段
 * 
 * id,createBy,createTime,lastUpdateBy,lastUpdateTime,isDelete
 *
 * @author joy.zhou
 * @date 2016年3月21日
 * @version 1.0
 *
 */
public class BaseDomainPropertyFilter extends SimplePropertyPreFilter {

	private final Set<String> defaultExculeds = Sets.newHashSet("createBy", "createTime", "lastUpdateBy",
			"lastUpdateTime", "isDelete");

	public BaseDomainPropertyFilter() {
		super(BaseDomain.class);
		this.getExcludes().addAll(defaultExculeds);
	}

}

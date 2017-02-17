package com.injedu.mvc.servlet.init;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

/**
 * 
 * 初始化业务静态变量
 *
 * @author joy.zhou
 * @date 2016年2月1日
 * @version 1.0
 *
 */
public class InitStaticConfigFactory implements InitializingBean {

	private List<InitStaticConfig> configs;

	@Override
	public void afterPropertiesSet() throws Exception {

		if (configs == null) {
			return;
		}
		for (InitStaticConfig config : configs) {
			config.execute();
		}

	}

	public void setConfigs(List<InitStaticConfig> configs) {
		this.configs = configs;
	}

}
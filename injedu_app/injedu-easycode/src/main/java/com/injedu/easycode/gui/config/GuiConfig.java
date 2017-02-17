package com.injedu.easycode.gui.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.injedu.easycode.gui.model.DataModel;

/**
 * 
 * 配置文件
 *
 * @author joy.zhou
 * @date 2016年2月24日
 * @version 1.0
 *
 */
public class GuiConfig {

	protected static final Logger LOG = LoggerFactory.getLogger(GuiConfig.class);

	public static final String CONFIG_FILE = "easycode.ini";

	private Properties pro = new Properties();

	/**
	 * 初始化
	 */
	public DataModel init() {

		File file = new File(getConfigPath());

		if (file.exists()) {
			try {
				pro.load(new FileInputStream(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
			DataModel model = new DataModel();
			model.setDbType(pro.getProperty("dbtype"));
			model.setDburl(pro.getProperty("dburl"));
			model.setDbuser(pro.getProperty("dbuser"));
			model.setDbpwd(pro.getProperty("dbpwd"));
			model.setOverride(Boolean.valueOf(pro.getProperty("override", "true")));
			model.setTargetPath(pro.getProperty("targetPath"));
			model.setSrcmainjava("src/main/java");
			model.setSrcmainresourse("src/main/resources");
			model.setSrctestjava("src/test/java");
			model.setSrctestresourse("src/test/resources");
			return model;
		}

		return null;
	}

	protected String getConfigPath() {

		return System.getProperty("user.dir") + File.separator + CONFIG_FILE;
	}

	/**
	 * 获取配置文件路径
	 * 
	 * @return
	 */
	protected String getConfigPath(String fileName) {

		return System.getProperty("user.dir") + File.separator + fileName;
	}

	/**
	 * 保存配置文件
	 * 
	 * @param model
	 */
	public void saveConfig(String fileName, DataModel model) {

		Properties config = new Properties();

		config.setProperty("dbtype", model.getDbType());
		config.setProperty("dburl", model.getDburl());
		config.setProperty("dbuser", model.getDbuser());
		config.setProperty("dbpwd", model.getDbpwd());
		config.setProperty("override", String.valueOf(model.isOverride()));
		config.setProperty("targetPath", model.getTargetPath());

		try {

			String filePath = getConfigPath(fileName);

			LOG.info("保存配置文件：" + filePath);

			config.store(new FileOutputStream(filePath), "---- easycode config ----");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

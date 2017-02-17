package com.injedu.easycode.gui;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTabbedPane;

import com.injedu.easycode.gui.config.GuiConfig;
import com.injedu.easycode.gui.model.DataModel;
import com.injedu.easycode.gui.window.LayerAbout;
import com.injedu.easycode.gui.window.LayerBase;
import com.injedu.easycode.gui.window.LayerCode;
import com.injedu.easycode.gui.window.LayerConfig;

public class PanelMain extends JTabbedPane {

	private static final long serialVersionUID = -7036337490758041471L;

	protected DataModel model;

	/**
	 * Create the panel.
	 */
	public PanelMain() {

		this.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		this.addTab("程序说明", null, new LayerAbout(), null);
		this.addTab("项目配置", null, new LayerConfig(), null);
		this.addTab("代码生成", null, new LayerCode(), null);

		this.setSelectedIndex(2);

		model = new GuiConfig().init();
	}

	/**
	 * 
	 * 收集所有数据
	 * 
	 * @return
	 */
	public void dataModelChange() {

		if (model == null) {
			model = new DataModel();
		}

		for (Component c : this.getComponents()) {
			((LayerBase) c).setDataModel(model);
		}
	}

	public DataModel getDataModel() {
		return model;
	}

}

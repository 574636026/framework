package com.injedu.easycode.gui.window;

import javax.swing.JPanel;

import com.injedu.easycode.gui.PanelMain;
import com.injedu.easycode.gui.model.DataModel;

public abstract class LayerBase extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * 设置数据
	 * 
	 * @param model
	 */
	public abstract void setDataModel(DataModel model);

	/**
	 * 获取配置数据
	 * 
	 * @return
	 */
	protected DataModel getDataModel() {

		return ((PanelMain) this.getParent()).getDataModel();
	}

	/**
	 * 配置数据变更
	 */
	protected void dataModelChange() {

		((PanelMain) this.getParent()).dataModelChange();
	}

}

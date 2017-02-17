package com.injedu.easycode.gui.window;

import java.awt.SystemColor;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import com.injedu.easycode.gui.model.DataModel;

/**
 * 
 * 项目说明
 *
 * @author joy.zhou
 * @date 2016年2月26日
 * @version 1.0
 *
 */
public class LayerAbout extends LayerBase {

	private static final long serialVersionUID = -4365881653353737637L;

	public LayerAbout() {
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u4F7F\u7528\u8BF4\u660E", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(53)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
					.addGap(49))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(27)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 263, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JTextArea txtrcomprotest = new JTextArea();
		txtrcomprotest.setBackground(SystemColor.menu);
		txtrcomprotest.setEditable(false);
		txtrcomprotest.setLineWrap(true);
		txtrcomprotest.setText("\r\n1.首次使用,先进入项目配置,填写必需的数据,保存配置后下一次可自动获取。\r\n\r\n2.\"目标路径\"表示代码生成位置,也就是需要生成代码的项目文件路径。\r\n\r\n3.\"基础包名\"表示项目的基础包,代码会生成在此包下,例:com.pro.test。\r\n\r\n4.\"表名\"表示需要生成代码的表。\r\n\r\n5.\"表别名\"如果不填写,则默认按照驼峰式命名风格生成类名。\r\n\r\n6.\"表说明\"表示实体的注释,如果不填写则读取表的注释。\r\n\r\n7.\"生成代码\"会跟据勾选的任务进行生成。\r\n\r\n8.\"更新实体\"只会更新实体文件与xmlMapper文件(insert、update与字段相关的内容,不改变其他已添加到方法)");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtrcomprotest, GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(txtrcomprotest, GroupLayout.PREFERRED_SIZE, 237, Short.MAX_VALUE)
					.addGap(6))
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}

	@Override
	public void setDataModel(DataModel model) {

	}
}

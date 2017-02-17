package com.injedu.easycode.gui.window;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

import com.injedu.easycode.gui.config.GuiConfig;
import com.injedu.easycode.gui.model.DataModel;

public class LayerConfig extends LayerBase implements ActionListener {

	private static final long serialVersionUID = 3743584025316223166L;

	private GuiConfig guiConfig = new GuiConfig();

	/** 数据库类型 */
	private JComboBox<String> comboBoxDbType;
	/** 数据库地址 */
	private JTextField txtDburl;
	/** 数据库用户 */
	private JTextField txtDbuser;
	/** 数据库密码 */
	private JTextField txtDbpwd;
	/** 覆盖源代码 */
	private ButtonGroup btnGroupOverride;
	private JRadioButton rdbtnOverrideTrue;
	private JRadioButton rdbtnOverrideFalse;
	/** 目标路径 */
	private JTextField txtTargetPath;
	/** 源代码目录 */
	private JTextField txtSrcmainjava;
	/** 测试代码目录 */
	private JTextField txtSrctestjava;
	/** 保存配置 */
	private JButton btnSaveConfig;

	/**
	 * Create the panel.
	 */
	public LayerConfig() {

		JPanel panelDbConfig = new JPanel();
		panelDbConfig.setBorder(new TitledBorder(null, "\u6570\u636E\u5E93\u914D\u7F6E", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		JPanel panelCodeConfig = new JPanel();
		panelCodeConfig.setBorder(new TitledBorder(null, "\u4EE3\u7801\u751F\u6210\u914D\u7F6E", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
										.addComponent(panelDbConfig, GroupLayout.PREFERRED_SIZE, 239,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(panelCodeConfig, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)))
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE, false)
								.addComponent(panelDbConfig, GroupLayout.PREFERRED_SIZE, 176,
										GroupLayout.PREFERRED_SIZE)
						.addComponent(panelCodeConfig, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE).addGap(20)));

		btnSaveConfig = new JButton("保存配置");
		btnSaveConfig.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(102)
						.addComponent(btnSaveConfig).addContainerGap(231, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addGap(27).addComponent(btnSaveConfig).addContainerGap(29, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		btnSaveConfig.addActionListener(this);

		btnGroupOverride = new ButtonGroup();

		rdbtnOverrideTrue = new JRadioButton("是");
		rdbtnOverrideTrue.setSelected(true);

		rdbtnOverrideFalse = new JRadioButton("否");

		btnGroupOverride.add(rdbtnOverrideTrue);
		btnGroupOverride.add(rdbtnOverrideFalse);

		txtTargetPath = new JTextField();
		txtTargetPath.setColumns(10);

		JLabel label_4 = new JLabel("覆盖源码");

		JLabel label_2 = new JLabel("目标路径");

		JLabel lblSrc = new JLabel("src目录");

		JLabel lblTest = new JLabel("test目录");

		txtSrcmainjava = new JTextField();
		txtSrcmainjava.setEditable(false);
		txtSrcmainjava.setText("src/main/java");
		txtSrcmainjava.setColumns(10);

		txtSrctestjava = new JTextField();
		txtSrctestjava.setEditable(false);
		txtSrctestjava.setText("src/test/java");
		txtSrctestjava.setColumns(10);

		GroupLayout gl_panelCodeConfig = new GroupLayout(panelCodeConfig);
		gl_panelCodeConfig.setHorizontalGroup(gl_panelCodeConfig.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCodeConfig.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelCodeConfig.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelCodeConfig.createSequentialGroup()
										.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 60,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(rdbtnOverrideTrue)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(rdbtnOverrideFalse))
						.addGroup(
								gl_panelCodeConfig.createSequentialGroup()
										.addComponent(lblTest, GroupLayout.PREFERRED_SIZE, 48,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_panelCodeConfig.createParallelGroup(Alignment.LEADING)
												.addComponent(txtSrctestjava, GroupLayout.DEFAULT_SIZE, 107,
														Short.MAX_VALUE)
												.addComponent(txtSrcmainjava).addComponent(txtTargetPath)))
						.addGroup(gl_panelCodeConfig.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(label_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblSrc, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 60,
										GroupLayout.PREFERRED_SIZE)))
						.addGap(13)));
		gl_panelCodeConfig.setVerticalGroup(gl_panelCodeConfig.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCodeConfig.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelCodeConfig.createParallelGroup(Alignment.BASELINE).addComponent(label_4)
								.addComponent(rdbtnOverrideTrue).addComponent(rdbtnOverrideFalse))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_panelCodeConfig.createParallelGroup(Alignment.BASELINE).addComponent(label_2)
								.addComponent(txtTargetPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_panelCodeConfig.createParallelGroup(Alignment.BASELINE).addComponent(lblSrc)
								.addComponent(txtSrcmainjava, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(15)
						.addGroup(gl_panelCodeConfig.createParallelGroup(Alignment.BASELINE).addComponent(lblTest)
								.addComponent(txtSrctestjava, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(29, Short.MAX_VALUE)));
		panelCodeConfig.setLayout(gl_panelCodeConfig);

		JLabel lblNewLabel = new JLabel("数据库类型");

		JLabel label = new JLabel("数据库地址");

		txtDburl = new JTextField();
		txtDburl.setColumns(10);

		JLabel label_1 = new JLabel("数据库用户");

		txtDbuser = new JTextField();
		txtDbuser.setColumns(10);

		JLabel label_3 = new JLabel("数据库密码");

		txtDbpwd = new JTextField();
		txtDbpwd.setColumns(10);

		comboBoxDbType = new JComboBox<String>();
		comboBoxDbType.setModel(new DefaultComboBoxModel<String>(new String[] { "mysql" }));
		GroupLayout gl_panelDbConfig = new GroupLayout(panelDbConfig);
		gl_panelDbConfig.setHorizontalGroup(gl_panelDbConfig.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDbConfig.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelDbConfig.createParallelGroup(Alignment.LEADING, false)
								.addComponent(label_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(label_1, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panelDbConfig.createParallelGroup(Alignment.LEADING, false).addComponent(txtDbpwd)
								.addComponent(txtDburl).addComponent(comboBoxDbType, 0, 105, Short.MAX_VALUE)
								.addComponent(txtDbuser))
						.addGap(58)));
		gl_panelDbConfig.setVerticalGroup(gl_panelDbConfig.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDbConfig.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelDbConfig.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel)
								.addComponent(comboBoxDbType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_panelDbConfig.createParallelGroup(Alignment.BASELINE).addComponent(label)
								.addComponent(txtDburl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_panelDbConfig.createParallelGroup(Alignment.BASELINE).addComponent(label_1)
								.addComponent(txtDbuser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_panelDbConfig.createParallelGroup(Alignment.BASELINE).addComponent(label_3)
								.addComponent(txtDbpwd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(51, Short.MAX_VALUE)));
		panelDbConfig.setLayout(gl_panelDbConfig);
		setLayout(groupLayout);

		loadConfig();
	}

	/**
	 * 加载配置信息
	 */
	private void loadConfig() {

		DataModel model = new GuiConfig().init();

		if (model == null) {
			return;
		}

		comboBoxDbType.setSelectedItem(model.getDbType());
		txtDburl.setText(model.getDburl());
		txtDbuser.setText(model.getDbuser());
		txtDbpwd.setText(model.getDbpwd());
		if (model.isOverride()) {
			rdbtnOverrideTrue.setSelected(true);
		} else {
			rdbtnOverrideFalse.setSelected(true);
		}
		txtTargetPath.setText(model.getTargetPath());
		txtSrcmainjava.setText(model.getSrcmainjava());
		txtSrctestjava.setText(model.getSrctestjava());

	}

	@Override
	public void setDataModel(DataModel model) {

		model.setDbType(String.valueOf(comboBoxDbType.getSelectedItem()));
		model.setDburl(txtDburl.getText());
		model.setDbuser(txtDbuser.getText());
		model.setDbpwd(txtDbpwd.getText());
		if (rdbtnOverrideTrue.isSelected()) {
			model.setOverride(true);
		} else {
			model.setOverride(false);
		}
		model.setTargetPath(txtTargetPath.getText());
		model.setSrcmainjava(txtSrcmainjava.getText());
		model.setSrctestjava(txtSrctestjava.getText());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		this.dataModelChange();
		// 保存配置
		if (e.getSource() == btnSaveConfig) {

			guiConfig.saveConfig(GuiConfig.CONFIG_FILE, this.getDataModel());
		}

	}
}

package com.injedu.easycode.gui.window;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.injedu.easycode.gui.logger.JListAppender;
import com.injedu.easycode.gui.model.DataModel;
import com.injedu.easycode.service.EasyCodeService;

/**
 * 
 * 代码生成窗体
 *
 * @author joy.zhou
 * @date 2016年2月19日
 * @version 1.0
 *
 */
public class LayerCode extends LayerBase implements ActionListener {

	protected static final Logger LOG = LoggerFactory.getLogger(LayerCode.class);

	private static final long serialVersionUID = 3936687231660286728L;
	/** 基础包名 */
	private JTextField txtBasePackage;
	/** 表名 */
	private JTextField txtTable;
	/** 表别名 */
	private JTextField txtAlais;
	/** 表说明 */
	private JTextField txtDesc;
	/** 任务组 */
	private List<JCheckBox> chkTaskGroup;
	/** 生成代码按钮 */
	private JButton btnGenCode;
	/** 更新实体按钮 */
	private JButton btnUpdateEntity;
	/** 日志列表数据 */
	private DefaultListModel<String> listLogModel = new DefaultListModel<String>();
	/** 代码生成service */
	private EasyCodeService easyCodeService = new EasyCodeService();

	/**
	 * Create the panel.
	 */
	public LayerCode() {

		JPanel panelSourceConfig = new JPanel();
		panelSourceConfig.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"\u6267\u884C\u914D\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JPanel panelLog = new JPanel();
		panelLog.setBorder(
				new TitledBorder(null, "\u6267\u884C\u65E5\u5FD7", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JPanel panelActions = new JPanel();
		panelActions.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panelActions, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(panelSourceConfig, GroupLayout.PREFERRED_SIZE, 285,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelLog, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(panelLog, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
						.addComponent(panelSourceConfig, GroupLayout.PREFERRED_SIZE, 279, Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(panelActions, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE).addGap(29)));

		JLabel label_3 = new JLabel("基础包名");

		txtBasePackage = new JTextField();
		txtBasePackage.setColumns(10);

		chkTaskGroup = new ArrayList<JCheckBox>();

		JCheckBox chkDomain = new JCheckBox("domain");
		chkDomain.setSelected(true);
		chkTaskGroup.add(chkDomain);

		JCheckBox chkMapper = new JCheckBox("javaMapper");
		chkMapper.setSelected(true);
		chkTaskGroup.add(chkMapper);

		JCheckBox chkXmlmapper = new JCheckBox("xmlMapper");
		chkXmlmapper.setSelected(true);
		chkTaskGroup.add(chkXmlmapper);

		JCheckBox chkDao = new JCheckBox("daoImpl");
		chkDao.setSelected(true);
		chkTaskGroup.add(chkDao);

		JCheckBox chkService = new JCheckBox("service");
		chkService.setSelected(true);
		chkTaskGroup.add(chkService);

		JCheckBox chkServiceimpl = new JCheckBox("serviceImpl");
		chkServiceimpl.setSelected(true);
		chkTaskGroup.add(chkServiceimpl);

		JLabel lblNewLabel = new JLabel("生成代码");

		JLabel lblNewLabel_1 = new JLabel("表名");

		txtTable = new JTextField();
		txtTable.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("表别名");

		txtAlais = new JTextField();
		txtAlais.setColumns(10);

		JLabel label = new JLabel("表说明");

		txtDesc = new JTextField();
		txtDesc.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panelSourceConfig);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_panel.createSequentialGroup()
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
												.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(txtAlais, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
										.addComponent(txtTable, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)))
						.addGroup(gl_panel.createSequentialGroup().addComponent(lblNewLabel).addGap(18)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(chkDomain, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(chkMapper, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(chkDao, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(chkService, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(chkXmlmapper, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(chkServiceimpl)))
						.addGroup(gl_panel.createSequentialGroup()
								.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(txtBasePackage,
										GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)))
						.addGap(253))
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtDesc, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(253, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGap(13)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(label_3).addComponent(
								txtBasePackage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_1).addComponent(
						txtTable, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_2).addComponent(
						txtAlais, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(13)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(label).addComponent(txtDesc,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addComponent(chkDomain)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(chkMapper)
										.addComponent(chkXmlmapper))
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(chkDao)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(chkService)
										.addComponent(chkServiceimpl)))
						.addComponent(lblNewLabel)).addGap(23)));
		panelSourceConfig.setLayout(gl_panel);
		JList<String> listLog = new JList<String>();
		listLog.setModel(listLogModel);
		JScrollPane scrollPaneListLog = new JScrollPane(listLog);
		GroupLayout gl_panelLog = new GroupLayout(panelLog);
		gl_panelLog.setHorizontalGroup(gl_panelLog.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelLog.createSequentialGroup().addContainerGap()
						.addComponent(scrollPaneListLog, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panelLog.setVerticalGroup(gl_panelLog.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelLog.createSequentialGroup().addGap(5)
						.addComponent(scrollPaneListLog, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
						.addContainerGap()));
		panelLog.setLayout(gl_panelLog);

		btnGenCode = new JButton("生成代码");
		btnGenCode.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btnGenCode.addActionListener(this);

		btnUpdateEntity = new JButton("更新实体");
		btnUpdateEntity.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btnUpdateEntity.addActionListener(this);
		GroupLayout gl_panelActions = new GroupLayout(panelActions);
		gl_panelActions
				.setHorizontalGroup(gl_panelActions.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelActions.createSequentialGroup().addGap(197).addComponent(btnGenCode)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnUpdateEntity)
								.addGap(195)));
		gl_panelActions.setVerticalGroup(gl_panelActions.createParallelGroup(Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				gl_panelActions.createSequentialGroup().addContainerGap(28, Short.MAX_VALUE).addGroup(gl_panelActions
						.createParallelGroup(Alignment.BASELINE).addComponent(btnGenCode).addComponent(btnUpdateEntity))
						.addGap(21)));
		panelActions.setLayout(gl_panelActions);
		setLayout(groupLayout);

		initLog();
	}

	private void initLog() {
		try {
			new JListAppender(listLogModel).start();
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	@Override
	public void setDataModel(DataModel model) {
		model.setBasePackage(txtBasePackage.getText());
		model.setTable(txtTable.getText());
		model.setAlais(txtAlais.getText());
		model.setDesc(txtDesc.getText());

		for (JCheckBox chk : chkTaskGroup) {
			if (chk.isSelected()) {
				model.getTasks().add(chk.getText());
			}

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// 清空日志
		listLogModel.clear();

		enableCompent(false);

		dataModelChange();

		DataModel dataModel = this.getDataModel();
		// 数据校验
		if (dataModel.validation() != null && dataModel.validation().size() > 0) {
			for (String error : dataModel.validation()) {
				LOG.error(error);
			}
			enableCompent(true);
			return;
		}

		if (e.getSource() == btnGenCode) {
			doCodeGenerate(dataModel);
		} else if (e.getSource() == btnUpdateEntity) {
			doUpdateEntity(dataModel);
		}
	}

	/**
	 * 生成代码
	 * 
	 * @param dataModel
	 */
	private void doCodeGenerate(final DataModel dataModel) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				easyCodeService.codeGenerate(dataModel);
				enableCompent(true);
			}
		});

	}

	/**
	 * 更新实体
	 * 
	 * @param dataModel
	 */
	private void doUpdateEntity(final DataModel dataModel) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				easyCodeService.updateEntity(dataModel);
				enableCompent(true);
			}
		});
	}

	/**
	 * 
	 * @param enabled
	 */
	private void enableCompent(boolean enabled) {
		btnGenCode.setEnabled(enabled);
		btnUpdateEntity.setEnabled(enabled);
	}
}

package com.injedu.easycode.gui.logger;

import java.io.IOException;
import java.util.Scanner;

import javax.swing.DefaultListModel;

/**
 * 
 *
 * @author joy.zhou
 * @date 2016年2月24日
 * @version 1.0
 *
 */
public class JListAppender extends GuiLogAppender {

	private DefaultListModel<String> list;

	/** 日志扫描器 */
	protected Scanner scanner;

	public JListAppender(DefaultListModel<String> list) throws IOException {
		super("gui");
		this.list = list;
	}

	public void setList(DefaultListModel<String> list) {
		this.list = list;
	}

	@Override
	public void run() {

		try {
			scanner = new Scanner(reader);
			// 将扫描到的字符流显示在指定的GUI上
			while (scanner.hasNextLine()) {
				list.addElement(scanner.nextLine());
			}
		} catch (Exception e) {

		}

	}
}

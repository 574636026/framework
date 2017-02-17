package com.injedu.easycode.gui;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import com.injedu.easycode.EasyCodeGui;

public class FrameMain extends JFrame {

	private static final long serialVersionUID = 2276196310194340081L;

	private PanelMain contentPane;

	/**
	 * Create the frame.
	 */
	public FrameMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("代码生成器-" + EasyCodeGui.VERSION);
		setBounds(100, 100, 630, 440);
		contentPane = new PanelMain();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}

}

package com.java.swing.ref;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class ShockDemo extends JFrame {


	private static final long serialVersionUID = 6575066362918582694L;

	private JPanel contentPane;

	/**
	 * 
	 * Launch the application.
	 */

	public static void main(String[] args) {

		try {

			UIManager

			.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		} catch (Throwable e) {

			e.printStackTrace();

		}

		EventQueue.invokeLater(new Runnable() {

			public void run() {

				try {

					ShockDemo frame = new ShockDemo();

					frame.setVisible(true);

				} catch (Exception e) {

					e.printStackTrace();

				}

			}

		});

	}

	/**
	 * 
	 * Create the frame.
	 */

	public ShockDemo() {

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowActivated(WindowEvent e) {

				do_this_windowActivated(e);

			}

		});

		setTitle("\u9707\u52A8\u6548\u679C\u7684\u7A97\u4F53");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 250, 100);

		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		contentPane.setLayout(new BorderLayout(0, 0));

		setContentPane(contentPane);

		JLabel lblJava = new JLabel("天气冷了，注意多件衣服哦~");

		contentPane.add(lblJava, BorderLayout.CENTER);

	}

	protected void do_this_windowActivated(WindowEvent e) {

		new Thread() {

			@Override
			public void run() {

				for (int i = 0; i < 20; i++) {// 使用for循环让窗体震动20次

					int newPoint = (int) (100 + Math.pow(-1, i) * 10);// 根据循环次数计算新点的位置

					setLocation(newPoint, newPoint);// 设置窗体的显示位置

					try {

						Thread.sleep(50);// 线程休眠0.05秒来实现动态效果

					} catch (InterruptedException e) {

						e.printStackTrace();

					}

				}

			}

		}.start();// 启动新线程

	}

}
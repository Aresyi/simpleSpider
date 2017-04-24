package com.browe;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

class Util {
	private Toolkit tool;
	private int width;
	private int height;
	private int screenWidth;
	private int screenHeight;
	private int leftMargin;
	private int topMargin;
	private static JFrame win;

	enum X {
		LEFT, RIGHT
	};

	enum Y {
		UP, DOWN
	};

	public Util(JFrame win, int width, int height) {
		Util.win = win;
		this.width = width;
		this.height = height;
		tool = win.getToolkit();
		this.screenWidth = tool.getScreenSize().width;
		this.screenHeight = tool.getScreenSize().height;
		Util.win.setVisible(true);
		Util.win.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// 按ESC键退出程序
				if (e.getKeyCode() == 27) {
					System.exit(0);
				}
			}
		});
	}

	/**
	 * @param speed
	 *            移动速度
	 *
	 **/
	public void fly(int speed) {
		// 每次移动距离
		int offset = 2;
		// 左边距
		int x = this.leftMargin;
		// 除去窗口的屏幕宽度
		int width = this.screenWidth - this.width;
		// 上边距
		int y = this.topMargin;
		// 除去窗口的屏幕高度
		int height = this.screenHeight - this.height;
		// 运动方向
		X directX = X.LEFT;
		Y directY = Y.UP;
		while (true) {
			if (x < 0)
				directX = X.RIGHT;
			else if (x >= width)
				directX = X.LEFT;
			if (y < 0)
				directY = Y.DOWN;
			else if (y >= height)
				directY = Y.UP;
			if (directX == X.RIGHT)
				x += offset;
			else
				x -= offset;
			if (directY == Y.DOWN)
				y += offset;
			else
				y -= offset;
			win.setBounds(x, y, this.width, this.height);
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
			}
		}
	}

	public static void main(String[] args) {
		// 创建实例
		new Util(new JFrame(), 300, 200).fly(10);
	}

}

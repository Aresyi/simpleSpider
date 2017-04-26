package com.java.swing.ref;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class TestFrame {
	public static void main(String[] args) {
		MFrame frame = new MFrame();

	}
}

class MFrame extends JFrame {
	public MFrame() {
		JLabel jl = new JLabel("欢迎注册", SwingUtilities.CENTER);
		Font font = new Font("宋体", Font.BOLD, 24);
		jl.setFont(font);
		jl.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.add(jl, BorderLayout.NORTH);
		font = new Font("宋体", Font.PLAIN, 12);

		JLabel jl_name = new JLabel("用户名：", SwingUtilities.RIGHT);
		jl_name.setFont(font);

		JLabel jl_id = new JLabel("身份证号：", SwingUtilities.RIGHT);
		jl_id.setFont(font);

		JLabel jl_pass1 = new JLabel("密码：", SwingUtilities.RIGHT);
		jl_pass1.setFont(font);

		JLabel jl_pass2 = new JLabel("确认密码：", SwingUtilities.RIGHT);
		jl_pass2.setFont(font);

		JLabel jl_count = new JLabel("存储金额：", SwingUtilities.RIGHT);
		jl_count.setFont(font);

		JPanel jp_center_left = new JPanel();
		jp_center_left.setLayout(new GridLayout(5, 1));
		jp_center_left.add(jl_name);
		jp_center_left.add(jl_id);
		jp_center_left.add(jl_pass1);
		jp_center_left.add(jl_pass2);
		jp_center_left.add(jl_count);

		JTextField jt_name = new JTextField();
		JTextField jt_id = new JTextField("                     ");
		JPasswordField jt_pass1 = new JPasswordField();
		JPasswordField jt_pass2 = new JPasswordField();
		jt_pass1.setEchoChar('*');
		jt_pass2.setEchoChar('*');
		JTextField jt_count = new JTextField();

		JPanel jp_center_right = new JPanel();
		jp_center_right.setLayout(new GridLayout(5, 1));
		jp_center_right.add(jt_name);
		jp_center_right.add(jt_id);
		jp_center_right.add(jt_pass1);
		jp_center_right.add(jt_pass2);
		jp_center_right.add(jt_count);

		JPanel jp_center = new JPanel();
		jp_center.setLayout(new GridLayout(1, 2));
		jp_center.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 60));
		jp_center.add(jp_center_left);
		jp_center.add(jp_center_right);

		JButton jb1 = new JButton("确认");
		JButton jb2 = new JButton("返回");

		JPanel jp_south = new JPanel();
		jp_south.add(jb1);
		jp_south.add(jb2);
		jp_south.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add(jp_center);
		this.add(jp_south, BorderLayout.SOUTH);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(370, 280);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
}
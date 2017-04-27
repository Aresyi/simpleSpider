package com.ydj.zhuaqu.ali1688.ui;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.ydj.common.kit.MyLog;
import com.ydj.common.kit.Toolbox;

/**
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42
 * @version : 1.0
 * @description :
 *
 */
public class CheckCodeDialog extends Dialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	JLabel jLabel_checkCode = new JLabel("请输入验证码:");
	JTextField field_checkCode = new JTextField(50);
	JLabel jLabel_checkCodeImg = new javax.swing.JLabel();
	JLabel jLabel_msg = new javax.swing.JLabel();

	JButton ok = new JButton("确  定");
	JButton cancel = new JButton("取  消");
	JButton exit = new JButton("哥，不玩了");

	
	String sessionid = Constant.ali1688CheckCodeFormData.getSessionid();
	String identity =  Constant.ali1688CheckCodeFormData.getIdentity();
	
	private boolean isOK = false;
	
	
	CheckCodeDialog(JFrame parent, boolean modal) {
		super(parent, modal);
		
		
		setTitle("验证码又来了...");
		setSize(500, 140);
		setResizable(false);
		setLayout(null);

		add(jLabel_checkCode);
		jLabel_checkCode.setBounds(50, 50, 100, 30);

		add(field_checkCode);
		field_checkCode.setBounds(150, 50, 90, 30);
		
		
		field_checkCode.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				MyLog.logInfo("keyTyped:"+e.getKeyCode());
				 String s = field_checkCode.getText();  
				 if(s.length() >= 6){
					 e.consume();  
				 }
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				MyLog.logInfo("keyReleased:"+e.getKeyCode());
				if(e.getKeyCode() == 10){
					submit();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				MyLog.logInfo("keyPressed:"+e.getKeyCode());
			}
		});
		

		jLabel_checkCodeImg.setText("<html><img src='https://pin.aliyun.com/get_img?sessionid="+sessionid+"&identity="+identity+"&type=default&r="+ System.currentTimeMillis() + "' /><html>");
		add(jLabel_checkCodeImg);
		jLabel_checkCodeImg.setBounds(280, 50, 100, 30);
		
		
		add(jLabel_msg);
		jLabel_msg.setBounds(400, 50, 80, 30);

		jLabel_checkCodeImg.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				MyLog.logInfo(e);
				jLabel_checkCodeImg.setText("<html><img src='https://pin.aliyun.com/get_img?sessionid="+sessionid+"&identity="+identity+"&type=default&r="+ System.currentTimeMillis() + "' /><html>");

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		add(ok);
		add(cancel);
		add(exit);
		ok.setBounds(100, 100, 60, 25);
		cancel.setBounds(220, 100, 60, 25);
		exit.setBounds(330, 100, 100, 25);
		
		
		ok.addActionListener(this);
		cancel.addActionListener(this);
		exit.addActionListener(this);

		this.setIconImage(Toolkit.getDefaultToolkit().createImage(CategorySpiderJFrame.class.getResource("logo.png")));
		this.setLocationRelativeTo(null);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok) {
			submit();
		}else if (e.getSource() == exit){
			MyLog.logError("exit");
			System.exit(-1);
		}else{
			dispose();
		}
		
	}
	
	
	private void submit(){
		String checkCode = field_checkCode.getText();
		MyLog.logInfo("chceckCode:"+checkCode);
		
		if(Toolbox.isNotEmpty(checkCode)){
			try {
				String msg = SpiderAli1688.submitCheckCode(checkCode);
				
				if(Toolbox.isNotEmpty(msg) && "SUCCESS".equals(msg)){
					this.isOK = true;
					dispose();
				}else{
					jLabel_checkCodeImg.setText("<html><img src='https://pin.aliyun.com/get_img?sessionid="+sessionid+"&identity="+identity+"&type=default&r="+ System.currentTimeMillis() + "' /><html>");
					jLabel_msg.setText("验证码错误");
					jLabel_msg.setForeground(Color.red);
					field_checkCode.setText("");
				}
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	

	public boolean isOK() {
		return isOK;
	}
	
	
}
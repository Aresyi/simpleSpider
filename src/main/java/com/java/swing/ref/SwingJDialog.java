package com.java.swing.ref;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
  
/** 
 * This program demonstrates the creation of a JDialog from a super-window. 
 * The created dialog is on the mode "Modal". 
 * @author han 
 * 
 */  
public class SwingJDialog {  
    public SwingJDialog(){  
        final JFrame jf=new JFrame("弹出窗体实验");  
        // Some methods defined by Toolkit query the native operating system directly.  
        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();   
        int Swing1x=500;  
        int Swing1y=300;  
        jf.setBounds(screensize.width/2-Swing1x/2,screensize.height/2-Swing1y/2,Swing1x,Swing1y);  
        jf.setVisible(true);  
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        Container c=jf.getContentPane();  
        c.setBackground(Color.pink);  
        c.setLayout(null);  
  
        Dimension Swing1size=jf.getSize();  
        JButton jb=new JButton("弹出对话窗");  
        int jbx=100;  
        int jby=30;  
        jb.setBounds(Swing1size.width/2-jbx/2,Swing1size.height/2-jby/2,jbx,jby);  
        //jb.setBounds(Swing1x/2-jbx/2,Swing1y/2-jby/2,jbx,jby);  
        c.add(jb);  
  
        class Dialog1 {  
            JDialog jd=new JDialog(jf,"JDialog窗体",true);  
            Dialog1(){  
  
                jd.setSize(300,200);  
                Container c2=jd.getContentPane();  
                c2.setLayout(null);  
                JLabel jl=new JLabel("只是一个对话框");  
                jl.setBounds(0,-20,100,100);  
                JButton jbb=new JButton("确定");  
                jbb.setBounds(100,100,60,30);  
                c2.add(jl);  
                c2.add(jbb);  
                jbb.addActionListener(new ActionListener(){  
                    @Override  
                    public void actionPerformed(ActionEvent e) {  
                        jd.dispose();  
                        //System.exit(0);  
                    }  
  
                });  
                System.out.println("OK");  
  
                jd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);  
  
            }  
        }  
  
        jb.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                new Dialog1().jd.setVisible(true);//弹出对话框  
                System.out.println("OK2");  
            }  
        });  
        System.out.println("OK3");  
    }  
  
    public static void main(String[] args){  
        new SwingJDialog();  
    }  
}  
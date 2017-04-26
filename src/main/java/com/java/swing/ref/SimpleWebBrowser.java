package com.java.swing.ref;
import java.awt.EventQueue;
import java.awt.Frame;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
  
/** 
 * 简单的Web浏览器 
 * 
 */  
public class SimpleWebBrowser {  
    public static void main(String[] args) {  
        String initialPage = "https://www.baidu.com/";  
          
        JEditorPane jep = new JEditorPane();  
        jep.setEditable(false);  
        jep.addHyperlinkListener(new LinkFollower(jep));  
          
        try {  
            jep.setPage(initialPage);  
        } catch (IOException e) {  
            System.err.println("Usage: java simpleWebBrowser url");  
            System.err.println(e);  
            System.exit(1);  
        }  
          
        JScrollPane scrollPane = new JScrollPane(jep);  
        JFrame f = new JFrame("Simple Web Browser");  
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);  
        f.setContentPane(scrollPane);  
        f.setSize(512,342);  
          
        EventQueue.invokeLater(new FrameShower(f));  
    }  
      
    private static class FrameShower implements Runnable{  
  
        private final Frame frame ;  
        public FrameShower(Frame frame){  
            this.frame = frame;  
        }  
        public void run() {  
            frame.setVisible(true);  
        }  
          
    }  
}  
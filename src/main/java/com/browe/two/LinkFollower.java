package com.browe.two;
import java.io.IOException;  

import javax.swing.JEditorPane;  
import javax.swing.event.HyperlinkEvent;  
import javax.swing.event.HyperlinkListener;  

/** 
 * 处理超链接 
 * @author Vic 
 * 
 */  
public class LinkFollower implements HyperlinkListener{  

    private JEditorPane pane;  
      
    public LinkFollower(JEditorPane pane){  
        this.pane = pane;  
    }  
      
    public void hyperlinkUpdate(HyperlinkEvent evt) {  
        if(evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED){  
            try {  
                pane.setPage(evt.getURL());  
            } catch (IOException e) {  
                pane.setText("<html>could not load "+evt.getURL()+"</html>");  
            }  
        }  
    }  

}  
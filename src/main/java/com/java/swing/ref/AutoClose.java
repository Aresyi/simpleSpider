package com.java.swing.ref;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * 第一次做GUI项目，有一个功能是要检查计算机信息，如果出现异常信息 会自动弹出窗口，显示警告，用户可以主动去关闭，如果用户没有主动关闭窗口，该窗口30秒后自动关闭。
 * 因为只是简单的提示，相对应JDialog，JOptionPane可以省很多代码，而JOptionPane，没有发现怎样做到自动关闭。
 * 看了一遍API发现可以利用JOptionPane来创建JDialog。这样就省去很多事了。
 * @author 
 * @since 
 */
public class AutoClose {

    /**
     * 测试对话框自动关闭
     * 
     * @param args
     */
    public static void main(String[] args) {

        JOptionPane op = new JOptionPane("本对话框将在30秒后关闭",JOptionPane.INFORMATION_MESSAGE);
        final JDialog dialog = op.createDialog("服务器自检异常");
        
        // 创建一个新计时器
        Timer timer = new Timer();

        // 30秒 后执行该任务
        timer.schedule(new TimerTask() {
            public void run() {
                dialog.setVisible(false);
                dialog.dispose();
            }
        }, 30000);

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setAlwaysOnTop(true);
        dialog.setModal(false);
        dialog.setVisible(true);
    }

}
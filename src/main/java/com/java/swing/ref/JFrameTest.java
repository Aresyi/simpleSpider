package com.java.swing.ref;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class JFrameTest {
    private JFrame frame = null;

    public JFrameTest() {
        frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowDeactivated(WindowEvent e) {
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        new JFrameTest();
    }
}
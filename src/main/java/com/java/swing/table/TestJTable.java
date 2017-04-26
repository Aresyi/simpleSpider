package com.java.swing.table;
public class TestJTable {

       //程序入口

       public static void main(String[] args) {

              javax.swing.JFrame jf=new javax.swing.JFrame("表格测试");

              jf.setSize(300,400);

              java.awt.FlowLayout fl=new java.awt.FlowLayout();

              jf.setLayout(fl);

              //1.创建一个默认的简单表格对像:

              javax.swing.JTable table=new javax.swing.JTable();

              //2.创建我们自定义的TableModel对象

              MyTableModelV1   tm=new MyTableModelV1();

              //3.将其设置为Table的Model

              table.setModel(tm);

              jf.add(table);

              jf.setDefaultCloseOperation(3);

              jf.setVisible(true);

       }

}
package com.zhuaqu.ali1688.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.sf.json.JSONObject;

import com.ydj.common.dao.DaoFactory;
import com.zhuaqu.ali1688.ui.MainJFrame;

/**
*
* @author : Ares.yi
* @createTime : 2014-11-10 上午11:13:42 
* @version : 1.0 
* @description : 
*
 */
public class ReportJFrame extends javax.swing.JFrame{

	private static final long serialVersionUID = 1L;

	public ReportJFrame (){
		initUI();
	}
	
    private void initUI(){
    	
    	this.setTitle("抓 抓 抓...抓个蛋蛋----抓取报表");

//       java.awt.FlowLayout fl=new java.awt.FlowLayout();
//       this.setLayout(fl);
       

       //创建一个表格对象
       final javax.swing.JTable userSpiderInfoReportTable = new javax.swing.JTable();
       
       List<JSONObject> userSpiderInfoReportData;
       try {
    	   userSpiderInfoReportData =  DaoFactory.getMyDao().getUserSpiderInfoReport();
       } catch (Exception e) {
    	   e.printStackTrace();
    	   userSpiderInfoReportData = new ArrayList<JSONObject>();
       }


        UserSpiderInfoReportTableModel tm=new UserSpiderInfoReportTableModel(userSpiderInfoReportData);

        userSpiderInfoReportTable.setModel(tm);//将模型设给表格
        
        userSpiderInfoReportTable.setPreferredScrollableViewportSize(new Dimension(200, 70));
        
        for(int i=0;i<userSpiderInfoReportTable.getColumnCount();i++){

            userSpiderInfoReportTable.getColumnModel().getColumn(i).setPreferredWidth(100);

        }

        userSpiderInfoReportTable.setRowHeight(20);//设定每一列的高度
        
       
        
        
        
      //创建一个表格对象
        final javax.swing.JTable categorySpiderInfoReportTable = new javax.swing.JTable();
        
        List<JSONObject> categorySpiderInfoReportData;
        try {
        	categorySpiderInfoReportData = DaoFactory.getMyDao().getCategorySpiderReport();
        } catch (Exception e) {
     	   e.printStackTrace();
     	  categorySpiderInfoReportData = new ArrayList<JSONObject>();
        }


        CategorySpiderInfoReportTableModel tm2 = new CategorySpiderInfoReportTableModel(categorySpiderInfoReportData);

         categorySpiderInfoReportTable.setModel(tm2);//将模型设给表格
         
         categorySpiderInfoReportTable.setPreferredScrollableViewportSize(new Dimension(200, 70));
         
         for(int i=0;i<categorySpiderInfoReportTable.getColumnCount();i++){

        	 categorySpiderInfoReportTable.getColumnModel().getColumn(i).setPreferredWidth(100);

         }

         categorySpiderInfoReportTable.setRowHeight(20);//设定每一列的高度
        
        
        
         
       //创建一个表格对象
         final javax.swing.JTable sumInfoReportTable = new javax.swing.JTable();
         
         List<JSONObject> sumInfoReportTableData;
         try {
        	 sumInfoReportTableData = DaoFactory.getMyDao().getReport();
         } catch (Exception e) {
      	     e.printStackTrace();
      	     sumInfoReportTableData = new ArrayList<JSONObject>();
         }


         SumInfoReportTableModel tm3 = new  SumInfoReportTableModel(sumInfoReportTableData);

         sumInfoReportTable.setModel(tm3);//将模型设给表格
          
         sumInfoReportTable.setPreferredScrollableViewportSize(new Dimension(200, 70));
          
          for(int i=0;i<sumInfoReportTable.getColumnCount();i++){

        	  sumInfoReportTable.getColumnModel().getColumn(i).setPreferredWidth(100);

          }

          sumInfoReportTable.setRowHeight(20);//设定每一列的高度
        
        
        
        
        Container cont = getContentPane();
        cont.setLayout(null);
        
        JPanel pan1 = new JPanel();
        pan1.setLayout(new GridLayout(1, 1));
        pan1.setBorder(BorderFactory.createTitledBorder("抓客信息"));
        pan1.setBounds(30, 30,400, 400);
//        pan1.setBackground(Color.red);
        pan1.add(new JScrollPane(userSpiderInfoReportTable));//将scrollPane对象加到界面上

        
        JPanel pan2 = new JPanel();
        pan2.setLayout(new GridLayout(1, 1));
        pan2.setBorder(BorderFactory.createTitledBorder("分类信息"));
        pan2.setBounds(450, 30,400, 400);
//        pan2.setBackground(Color.blue);
        pan2.add(new JScrollPane(categorySpiderInfoReportTable));

        
        
        JPanel pan3 = new JPanel();
        pan3.setLayout(new GridLayout(1, 1));
        pan3.setBorder(BorderFactory.createTitledBorder("汇总信息"));
        pan3.setBounds(30, 450,820, 100);
//        pan3.setBackground(Color.blue);
        pan3.add(new JScrollPane(sumInfoReportTable));

        
        cont.add(pan3);
        cont.add(pan2);
        cont.add(pan1);


        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        pack();
        
        this.setIconImage(Toolkit.getDefaultToolkit().createImage(MainJFrame.class.getResource("logo.png"))); 
        this.setResizable(false);//设置不可以最大化
        this.setLocationRelativeTo(null);
        
        this.setSize(900,630);
        
        this.setVisible(true);

    }

    
    public static void main(String[] args) {

        ReportJFrame lu=new ReportJFrame();

        lu.initUI();

     }
}
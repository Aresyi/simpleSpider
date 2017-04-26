package com.ydj.zhuaqu.ali1688.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.sf.json.JSONObject;

import com.ydj.common.dao.DaoFactory;

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
	
	List<JSONObject> userSpiderInfoReportData;
	List<JSONObject> categorySpiderInfoReportData;
	List<JSONObject> myCategoryReportTableData;
	List<JSONObject> sumInfoReportTableData;

	public ReportJFrame (){
		initData();
		initUI();
	}
	
	
	private void initData(){
		try {
	    	   userSpiderInfoReportData =  DaoFactory.getMyDao().getUserSpiderInfoReport();
	    	   categorySpiderInfoReportData = DaoFactory.getMyDao().getCategorySpiderReport();
	    	   myCategoryReportTableData = DaoFactory.getMyDao().getMyCategoryReport();
	    	   sumInfoReportTableData = DaoFactory.getMyDao().getReport();
	     } catch (Exception e) {
	    	userSpiderInfoReportData = new ArrayList<JSONObject>();
	    	categorySpiderInfoReportData = new ArrayList<JSONObject>();
	    	myCategoryReportTableData = new ArrayList<JSONObject>();
	    	sumInfoReportTableData = new ArrayList<JSONObject>();
	    }
	}
	
	
    private void initUI(){
    	
    	this.setTitle("抓 抓 抓...抓个蛋蛋----抓取报表");

//       java.awt.FlowLayout fl=new java.awt.FlowLayout();
//       this.setLayout(fl);
       

       //1 创建一个表格对象
       final javax.swing.JTable userSpiderInfoReportTable = new javax.swing.JTable();
       

       UserSpiderInfoReportTableModel tm=new UserSpiderInfoReportTableModel(userSpiderInfoReportData);

        userSpiderInfoReportTable.setModel(tm);//将模型设给表格
        
        userSpiderInfoReportTable.setPreferredScrollableViewportSize(new Dimension(200, 70));
        
        for(int i=0;i<userSpiderInfoReportTable.getColumnCount();i++){

            userSpiderInfoReportTable.getColumnModel().getColumn(i).setPreferredWidth(100);

        }

        userSpiderInfoReportTable.setRowHeight(20);//设定每一列的高度
        
       
        
        
        
      //2 创建一个表格对象
        final javax.swing.JTable categorySpiderInfoReportTable = new javax.swing.JTable();
        

        CategorySpiderInfoReportTableModel tm2 = new CategorySpiderInfoReportTableModel(categorySpiderInfoReportData);

         categorySpiderInfoReportTable.setModel(tm2);//将模型设给表格
         
         categorySpiderInfoReportTable.setPreferredScrollableViewportSize(new Dimension(200, 70));
         
         for(int i=0;i<categorySpiderInfoReportTable.getColumnCount();i++){

        	 categorySpiderInfoReportTable.getColumnModel().getColumn(i).setPreferredWidth(100);

         }

         categorySpiderInfoReportTable.setRowHeight(20);//设定每一列的高度
        
        
       //3 创建一个表格对象
         final javax.swing.JTable myCategoryReportTable = new javax.swing.JTable();
         

         MyCategoryReportTableModel tm3 = new  MyCategoryReportTableModel(myCategoryReportTableData);

         myCategoryReportTable.setModel(tm3);//将模型设给表格
          
         myCategoryReportTable.setPreferredScrollableViewportSize(new Dimension(200, 70));
          
          for(int i=0;i<myCategoryReportTable.getColumnCount();i++){

        	  myCategoryReportTable.getColumnModel().getColumn(i).setPreferredWidth(100);

          }

          myCategoryReportTable.setRowHeight(20);//设定每一列的高度
        
         
       //4 创建一个表格对象
         final javax.swing.JTable sumInfoReportTable = new javax.swing.JTable();
         

         SumInfoReportTableModel tm4 = new  SumInfoReportTableModel(sumInfoReportTableData);

         sumInfoReportTable.setModel(tm4);//将模型设给表格
          
         sumInfoReportTable.setPreferredScrollableViewportSize(new Dimension(200, 70));
          
          for(int i=0;i<sumInfoReportTable.getColumnCount();i++){

        	  sumInfoReportTable.getColumnModel().getColumn(i).setPreferredWidth(100);

          }

          sumInfoReportTable.setRowHeight(20);//设定每一列的高度
        
        
        
        
        Container cont = getContentPane();
        cont.setLayout(null);
        
        JPanel pan1 = new JPanel();
        pan1.setLayout(new GridLayout(1, 1));
        pan1.setBorder(BorderFactory.createTitledBorder("抓客统计信息"));
        pan1.setBounds(30, 30,400, 300);
//        pan1.setBackground(Color.red);
        pan1.add(new JScrollPane(userSpiderInfoReportTable));//将scrollPane对象加到界面上

        
        JPanel pan2 = new JPanel();
        pan2.setLayout(new GridLayout(1, 1));
        pan2.setBorder(BorderFactory.createTitledBorder("关键词统计信息"));
        pan2.setBounds(450, 30,400, 300);
//        pan2.setBackground(Color.blue);
        pan2.add(new JScrollPane(categorySpiderInfoReportTable));

        
        JPanel pan3 = new JPanel();
        pan3.setLayout(new GridLayout(1, 1));
        pan3.setBorder(BorderFactory.createTitledBorder("分类统计信息"));
        pan3.setBounds(30, 350,820, 300);
//        pan3.setBackground(Color.blue);
        pan3.add(new JScrollPane(myCategoryReportTable));
        
        
        JPanel pan4 = new JPanel();
        pan4.setLayout(new GridLayout(1, 1));
        pan4.setBorder(BorderFactory.createTitledBorder("汇总信息"));
        pan4.setBounds(30, 660,820, 100);
//        pan4.setBackground(Color.blue);
        pan4.add(new JScrollPane(sumInfoReportTable));

        
        JButton jButton_makeZero = new JButton("3次都未抓取成功的归零，重新抓取");
        jButton_makeZero.setBounds(400, 700, 200, 50);
        jButton_makeZero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               DaoFactory.getMyDao().updateZero();
//               initData();
               JOptionPane.showMessageDialog(null, "3次都未抓取成功的已全部归零，后续每条会再有3次重新抓取机会","提示",JOptionPane.INFORMATION_MESSAGE); 
            }
        });
        
        pan4.add(jButton_makeZero);
        
        cont.add(pan4);
        cont.add(pan3);
        cont.add(pan2);
        cont.add(pan1);


        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        pack();
        
        this.setIconImage(Toolkit.getDefaultToolkit().createImage(MainJFrame.class.getResource("logo.png"))); 
        this.setResizable(false);//设置不可以最大化
        this.setLocationRelativeTo(null);
        
        this.setSize(900,830);
        
        this.setVisible(true);

    }

    
    public static void main(String[] args) {

//        ReportJFrame lu=new ReportJFrame();

     }
}
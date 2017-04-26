package com.zhuaqu.ali1688.ui;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import net.sf.json.JSONObject;



public class SumInfoReportTableModel implements TableModel{

       private List<JSONObject> dataList; 


      public SumInfoReportTableModel(List<JSONObject> userList){

          this.dataList=userList;

      }


     public int getRowCount(){
        return dataList.size();
     }


     public int getColumnCount(){
        return 2;
     }

     public Class<?> getColumnClass(int columnIndex){
        return String.class;
     }


     public Object getValueAt(int rowIndex, int columnIndex){


    	 JSONObject one= dataList.get(rowIndex);

        if(columnIndex==0){
            return one.getString("总信息数");
        }else if(columnIndex==1){
            return one.getString("总有效数");
        }else{
            return "出错!";
        }

     }

     //界面数据有变化时，模型对象的这个方法会被调用，暂时弹出说明框
     @Override
	 public void setValueAt(Object aValue, int rowIndex, int columnIndex){
	
	 String info=rowIndex+"行"+columnIndex+"列的值改变: "+aValue.toString();
	
	     javax.swing.JOptionPane.showMessageDialog(null,info);
	
	 }

     //指定某单元格是否可编辑:第一列不可编缉，第一列是ID，是每个对象的唯一识别号
     @Override
     public boolean isCellEditable(int rowIndex, int columnIndex){
        return false;

     }



	@Override
	public String getColumnName(int columnIndex) {
		if(columnIndex==0){
            return "总信息数(总记录数)";
         }else if(columnIndex==1){
            return "总有效数(有联系电话)";
         }else{
            return "出错!";

        }
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}}
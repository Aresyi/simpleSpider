package com.ydj.zhuaqu.ali1688.ui;
import java.util.List;

import javax.swing.table.TableModel;

import net.sf.json.JSONObject;

import com.ydj.common.kit.Toolbox;


/**
*
* @author : Ares.yi
* @createTime : 2014-11-10 上午11:13:42 
* @version : 1.0 
* @description : 
*
 */
public abstract class MyTableModel implements TableModel{

      protected List<JSONObject> dataList; 


      public MyTableModel(List<JSONObject> userList){
          this.dataList=userList;
      }

     @Override
     public int getRowCount(){
        return dataList.size();
     }


     @Override
     public Class<?> getColumnClass(int columnIndex){
        return String.class;
     }

     @Override
     public Object getValueAt(int rowIndex, int columnIndex){

    	JSONObject one= dataList.get(rowIndex);

        if(columnIndex==0){
            return one.getString("category");
        }else if(columnIndex==1){
            return one.getString("sum");
        }else if(columnIndex==2){
            return Toolbox.getDateString("yyyy-MM-dd HH:mm",one.getLong("updateTime"));
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

}
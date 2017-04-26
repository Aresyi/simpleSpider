package com.ydj.zhuaqu.ali1688.ui;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import net.sf.json.JSONObject;

import com.ydj.zhuaqu.ali1688.IndustryNewCache;



public class MyCategoryReportTableModel implements TableModel{

       private List<JSONObject> dataList; 


      public MyCategoryReportTableModel(List<JSONObject> userList){

          this.dataList=userList;

      }


     public int getRowCount(){
        return dataList.size();
     }


     public int getColumnCount(){
        return 5;
     }

     public Class<?> getColumnClass(int columnIndex){
        return String.class;
     }


     public Object getValueAt(int rowIndex, int columnIndex){


    	 JSONObject one= dataList.get(rowIndex);

        if(columnIndex==0){
            String code = one.getString("行业名称");
            return IndustryNewCache.getName(code);
        }else if(columnIndex==1){
            return one.getString("总数");
        }else if(columnIndex==2){
            return one.getString("有效数");
        }else if(columnIndex==3){
            return one.getString("3次都未抓取成功数");
        }else if(columnIndex==4){
        	try {
        		return Toolbox.getDateString("yyyy-MM-dd HH:mm",one.getLong("最后抓取时间"));
			} catch (Exception e) {
				return "1970-01-01";
			}
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
	            return "行业名称";
	        }else if(columnIndex==1){
	            return "总数";
	        }else if(columnIndex==2){
	            return "有效数";
	        }else if(columnIndex==3){
	            return "3次都未抓取成功数";
	        }else if(columnIndex==4){
	            return "最后更新时间";
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
		
	}
	

}



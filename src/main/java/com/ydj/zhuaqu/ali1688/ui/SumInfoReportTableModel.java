package com.ydj.zhuaqu.ali1688.ui;

import java.util.List;

import javax.swing.event.TableModelListener;

import net.sf.json.JSONObject;

/**
*
* @author : Ares.yi
* @createTime : 2014-11-10 上午11:13:42 
* @version : 1.0 
* @description : 
*
 */
public class SumInfoReportTableModel extends MyTableModel {

	private List<JSONObject> dataList;

	public SumInfoReportTableModel(List<JSONObject> userList) {
		super(userList);
	}

	public int getColumnCount() {
		return 3;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		JSONObject one = dataList.get(rowIndex);

		if (columnIndex == 0) {
			return one.getString("总信息数");
		} else if (columnIndex == 1) {
			return one.getString("总有效数");
		} else if (columnIndex == 2) {
			return one.getString("3次都未抓取成功数");
		} else {
			return "出错!";
		}

	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == 0) {
			return "总信息数(总记录数)";
		} else if (columnIndex == 1) {
			return "总有效数(有联系电话)";
		} else if (columnIndex == 2) {
			return "3次都未抓取成功数";
		} else {
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

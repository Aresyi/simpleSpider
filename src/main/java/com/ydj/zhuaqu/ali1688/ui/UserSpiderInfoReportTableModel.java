package com.ydj.zhuaqu.ali1688.ui;

import java.util.List;

import javax.swing.event.TableModelListener;

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
public class UserSpiderInfoReportTableModel extends MyTableModel {

	private List<JSONObject> dataList;

	public UserSpiderInfoReportTableModel(List<JSONObject> userList) {
		super(userList);
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		JSONObject one = dataList.get(rowIndex);

		if (columnIndex == 0) {
			String spider = one.getString("spider");
			if ("0".equals(spider)) {
				spider = "系统";
			}
			return spider;
		} else if (columnIndex == 1) {
			return one.getString("sum");
		} else if (columnIndex == 2) {
			return Toolbox.getDateString("yyyy-MM-dd HH:mm",
					one.getLong("updateTime"));
		} else {
			return "出错!";

		}

	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == 0) {
			return "抓客";
		} else if (columnIndex == 1) {
			return "抓取数";

		} else if (columnIndex == 2) {
			return "最后抓取时间";
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
package com.ydj.zhuaqu.ali1688.ui;

import java.util.List;

import javax.swing.event.TableModelListener;

import net.sf.json.JSONObject;

import com.ydj.common.kit.Toolbox;
import com.ydj.zhuaqu.ali1688.IndustryCache;

/**
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42
 * @version : 1.0
 * @description :
 *
 */
public class MyCategoryReportTableModel extends MyTableModel {

	public MyCategoryReportTableModel(List<JSONObject> userList) {
		super(userList);
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		JSONObject one = dataList.get(rowIndex);

		if (columnIndex == 0) {
			String code = one.getString("行业名称");
			return IndustryCache.getName(code);
		} else if (columnIndex == 1) {
			return one.getString("总数");
		} else if (columnIndex == 2) {
			return one.getString("有效数");
		} else if (columnIndex == 3) {
			return one.getString("3次都未抓取成功数");
		} else if (columnIndex == 4) {
			try {
				return Toolbox.getDateString("yyyy-MM-dd HH:mm",
						one.getLong("最后抓取时间"));
			} catch (Exception e) {
				return "1970-01-01";
			}
		} else {
			return "出错!";
		}

	}

	@Override
	public String getColumnName(int columnIndex) {

		if (columnIndex == 0) {
			return "行业名称";
		} else if (columnIndex == 1) {
			return "总数";
		} else if (columnIndex == 2) {
			return "有效数";
		} else if (columnIndex == 3) {
			return "3次都未抓取成功数";
		} else if (columnIndex == 4) {
			return "最后更新时间";
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

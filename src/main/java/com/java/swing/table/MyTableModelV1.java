package com.java.swing.table;

import javax.swing.event.TableModelListener;

/** 自定义的tableModel实现类 */

public class MyTableModelV1 implements javax.swing.table.TableModel {

	// 多少行:

	public int getRowCount() {

		return 10;

	}

	// 多少列

	public int getColumnCount() {

		return 5;

	}

	// 取得列名

	public String getColumnName(int columnIndex) {

		return " 第" + columnIndex + "列名";

	}

	// 每一列的数据类型:我们这里显示的都是String型

	public Class<?> getColumnClass(int columnIndex) {

		return String.class;

	}

	// 指定的单元格是否可从界面上编辑

	public boolean isCellEditable(int rowIndex, int columnIndex) {

		if (columnIndex == 0) {

			return false;

		}

		return false;

	}

	// 取得指定单元格的值

	public Object getValueAt(int rowIndex, int columnIndex) {

		return rowIndex + "--" + columnIndex;

	}

	// 从表格界面上改变了某个单元格的值后会调用这个方法

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		String s = "Change at: " + rowIndex + "--- " + columnIndex
				+ " newValue: " + aValue;

		System.out.println(s);

	}

	// 这两个接口方法暂无用

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

}
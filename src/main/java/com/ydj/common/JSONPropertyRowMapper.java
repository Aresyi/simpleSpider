package com.ydj.common;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;


public class JSONPropertyRowMapper implements RowMapper<JSONObject> {

	public JSONPropertyRowMapper() {
	}

	public JSONObject mapRow(ResultSet rs, int rowNumber) throws SQLException {

		JSONObject json = new JSONObject();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();

		for (int index = 1; index <= columnCount; index++) {
			String column = JdbcUtils.lookupColumnName(rsmd, index);
			Object value = rs.getObject(column);
			if (null != value && !"".equals(value.toString()))
				json.put(column, "null".equalsIgnoreCase(value.toString()) ? "": value);
		}

		return json;
	}

	
	
}

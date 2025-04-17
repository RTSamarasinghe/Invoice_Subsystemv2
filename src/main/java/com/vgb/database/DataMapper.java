package com.vgb.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataMapper <T> {
	T map(ResultSet rs, Connection conn) throws SQLException;
	
}

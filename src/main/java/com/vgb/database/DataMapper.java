package com.vgb.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataMapper <T> {
	T map(ResultSet rs) throws SQLException;
}

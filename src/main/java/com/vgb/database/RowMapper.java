package com.vgb.database;

import java.sql.Connection;

public interface RowMapper {
	<T> T loadRow(int id, Connection conn);
}

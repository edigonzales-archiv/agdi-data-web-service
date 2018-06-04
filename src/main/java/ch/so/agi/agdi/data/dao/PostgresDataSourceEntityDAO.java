package ch.so.agi.agdi.data.dao;

import java.util.List;

import ch.so.agi.agdi.data.entity.PostgresDataSourceEntity;

public interface PostgresDataSourceEntityDAO {
	public List<PostgresDataSourceEntity> select(String dbUrl, String dbUsr, String dbPwd);
}

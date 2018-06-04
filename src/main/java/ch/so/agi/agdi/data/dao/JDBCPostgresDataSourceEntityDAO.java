package ch.so.agi.agdi.data.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.so.agi.agdi.data.db.ConnectionFactory;
import ch.so.agi.agdi.data.db.DbUtil;
import ch.so.agi.agdi.data.entity.PostgresDataSourceEntity;

public class JDBCPostgresDataSourceEntityDAO implements PostgresDataSourceEntityDAO {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private Connection connection;
	private Statement statement;

	@Override
	public List<PostgresDataSourceEntity> select(String dbUrl, String dbUsr, String dbPwd) {
		List<PostgresDataSourceEntity> postgresDataSourceEntities = new ArrayList<PostgresDataSourceEntity>();
		try {
			connection = ConnectionFactory.getConnection(dbUrl, dbUsr, dbPwd);
			statement = connection.createStatement();
			
			String sql = "SELECT DISTINCT ON (f_table_schema, f_table_name) f_table_schema, f_table_name FROM geometry_columns ORDER BY f_table_schema, f_table_name ASC;";
			ResultSet resultSet = statement.executeQuery(sql);
			
			PostgresDataSourceEntity postgresDataSourceEntity = null;
			while(resultSet.next()){
				postgresDataSourceEntity = new PostgresDataSourceEntity();
				postgresDataSourceEntity.setSchemaName(resultSet.getString("f_table_schema"));
				postgresDataSourceEntity.setTableName(resultSet.getString("f_table_name"));
                 
				postgresDataSourceEntities.add(postgresDataSourceEntity);
            }
            resultSet.close();
			
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			DbUtil.close(statement);
			DbUtil.close(connection);
		}
		return postgresDataSourceEntities;
	}
}

package ch.so.agi.agdi.geodb.model;

public class PostgresDataSourceEntity {
	
	private String schemaName;
	private String tableName;

	public String getSchemaName() {
		return schemaName;
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
}

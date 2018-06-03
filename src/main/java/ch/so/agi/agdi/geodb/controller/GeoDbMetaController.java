package ch.so.agi.agdi.geodb.controller;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ch.so.agi.agdi.geodb.model.PostgresDataSourceEntity;

@RestController
public class GeoDbMetaController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@ResponseBody
	@GetMapping("/tables")
	public List<PostgresDataSourceEntity> listDataSourceTables(@RequestParam(value = "data_source_id", required = true) String dataSourceId) {
		String dbUrl = dataSourceId;
		DataSource dataSource = dataSource("jdbc:postgresql://192.168.50.6/pub");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);

		String sql = "SELECT DISTINCT ON (f_table_schema, f_table_name) f_table_schema, f_table_name FROM geometry_columns ORDER BY f_table_schema, f_table_name ASC;";

		List<PostgresDataSourceEntity> postgresEntities = new ArrayList<PostgresDataSourceEntity>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map row : rows) {
			PostgresDataSourceEntity postgresEntity = new PostgresDataSourceEntity();
			postgresEntity.setSchemaName(row.get("f_table_schema").toString());
			postgresEntity.setTableName(row.get("f_table_name").toString());
			postgresEntities.add(postgresEntity);
		}
		return postgresEntities;
	}

	@Bean
	public DataSource dataSource(@Value("") String dbUrl) {
		log.info(dbUrl); 
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		//dataSourceBuilder.url("jdbc:postgresql://192.168.50.6/pub");
		dataSourceBuilder.url(dbUrl);
		dataSourceBuilder.username("ddluser");
		dataSourceBuilder.password("ddluser");
		return dataSourceBuilder.build();
	}
}

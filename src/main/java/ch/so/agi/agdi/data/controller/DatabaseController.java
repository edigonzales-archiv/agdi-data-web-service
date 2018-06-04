package ch.so.agi.agdi.data.controller;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ch.so.agi.agdi.data.dao.JDBCPostgresDataSourceEntityDAO;
import ch.so.agi.agdi.data.entity.PostgresDataSourceEntity;

@RestController
public class DatabaseController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private Connection connection;
	
	@ResponseBody
	@GetMapping("/tables")
	public List<PostgresDataSourceEntity> listDataSourceTables(@RequestParam(value = "dburl", required = true) String dbUrl,
			@RequestParam(value = "dbusr", required = true) String dbUsr,
			@RequestParam(value = "dbpwd", required = true) String dbPwd) {
		
		JDBCPostgresDataSourceEntityDAO dao = new JDBCPostgresDataSourceEntityDAO();

		return dao.select(dbUrl, dbUsr, dbPwd);		
	}
}

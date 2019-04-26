package com.greenleaves.application.tools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public class Mysql {
	
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.driver-class-name}")
	private String driver;
	
	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private String password;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private boolean init = false;
	
	private void init() {
		if(!init) {
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName(driver);
	        dataSource.setUrl(url);
	        dataSource.setUsername(username);
	        dataSource.setPassword(password);
	        jdbcTemplate = new JdbcTemplate(dataSource);
	        init = true;
		}
	}
 
	public void update(String sql, Object[] data) {
		init();
		jdbcTemplate.update(sql, data);
		// mysql.update("update fun_user set username = ? where username = ?", new Object[] {"admin123", "admin"});
	}

	public Object queryOne(String sql, RowMapper<?> rm, Object[] data) {
		init();
		Object res = jdbcTemplate.queryForObject(sql, rm, data);
		//Member m = (Member) mysql.queryOne("select * from gl_member where membername = ?", new BeanPropertyRowMapper<Member>(Member.class), new Object[] {cookie});
		return res;
	}
	
	public List<?> query(String sql, RowMapper<?> rm, Object[] data) {
		init();
		List<?> res = jdbcTemplate.query(sql, rm, data);
		return res;
	}
	
}

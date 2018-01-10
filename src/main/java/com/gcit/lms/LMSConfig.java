package com.gcit.lms;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.*;
import com.gcit.lms.service.AdminService;

@Configuration
public class LMSConfig {
	
	public final String driver = "com.mysql.cj.jdbc.Driver";
	public final String url = "jdbc:mysql://localhost/library";
	public final String username = "root";
	public final String password = "";//password
	
	@Bean
	public BasicDataSource dataSource(){
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		return ds;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(){
		return new JdbcTemplate(dataSource());
	}
	
	@Bean
	public AuthorDAO adao(){
		return new AuthorDAO();
	}
		
	@Bean
	public BookDAO bdao(){
		return new BookDAO();
	}
	
	@Bean
	public BookCopiesDAO bcdao(){
		return new BookCopiesDAO();
	}
	
	@Bean
	public BookLoansDAO bldao(){
		return new BookLoansDAO();
	}
	
	@Bean
	public BorrowerDAO borrowerdao(){
		return new BorrowerDAO();
	}
	
	@Bean
	public BranchDAO brdao(){
		return new BranchDAO();
	}
	
	@Bean
	public GenreDAO gdao(){
		return new GenreDAO();
	}
	
	@Bean
	public PublisherDAO pdao(){
		return new PublisherDAO();
	}
	
	@Bean
	public AdminService adminService(){
		return new AdminService();
	}

	@Bean
	public PlatformTransactionManager txManager(){
		return new DataSourceTransactionManager(dataSource());
	}
}

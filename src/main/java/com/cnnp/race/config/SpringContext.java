package com.cnnp.race.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@ComponentScan(basePackages= {"com.cnnp.race"})
@MapperScan(basePackages= {"com.cnnp.race.*.mapper"})
@EnableTransactionManagement
public class SpringContext {
	
	@Bean
	public DruidDataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/race2019?useUnicode=true&characterEncoding=utf-8");
		dataSource.setUsername("zhuangshaoqing");
		dataSource.setPassword("zhuangshaoqing");
		return dataSource;
	}
	
	@Bean
	public SqlSessionFactoryBean sessionFactory() {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		return sessionFactory;
	}
	
	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource());	
		return transactionManager;
	}

}

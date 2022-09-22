package com.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.*", entityManagerFactoryRef = "entityManagerFactoryBean", transactionManagerRef = "transactionManagerBean")
public class AppContext {
	
	@Autowired
	public Environment environment;

	//mặc định Spring Data JPA sẽ tìm LocalContainerEntityManagerFactoryBean có tên là "entityManagerFactory" để @Autowired
	//trong trường hợp này đang tạo Spring BEAN tên là entityManagerFactoryBean -->ko tìm thấy
	//muốn đổi tên Spring Data JPA sẽ tìm kiếm thì khai báo entityManagerFactoryRef = "entityManagerFactoryBean"
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(new String[] {"com.entity"});
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(hibernateProperties());
        return entityManagerFactoryBean;
	}
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();//javax.sql.DataSource
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		return dataSource;
	}

	public Properties hibernateProperties() {
		Properties properties = new Properties();//java.util.Properties
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
		return properties;
	}

	//mặc định Spring Data JPA sẽ tìm JpaTransactionManager có tên là "transactionManager" để @Autowired
	//trong trường hợp này đang tạo Spring BEAN tên là transactionManagerBean -->ko tìm thấy
	//muốn đổi tên Spring Data JPA sẽ tìm kiếm thì khai báo transactionManagerRef = "transactionManagerBean"
	@Bean
	public JpaTransactionManager transactionManagerBean() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
		return transactionManager;
	}
	
}
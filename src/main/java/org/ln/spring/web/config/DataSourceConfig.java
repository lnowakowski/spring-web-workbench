package org.ln.spring.web.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.ln.spring.web.jpa.repositories.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "org.ln.spring.web.jpa", includeFilters = @Filter(TestRepository.class))
@PropertySource("classpath:db.properties")
@EnableCaching
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = "org.ln.spring.web.jpa.repositories")
public class DataSourceConfig {
	@Autowired
	private Environment env;

	@Value("classpath:db-schema.sql")
	private Resource schemaScript;

	@Value("classpath:db-test-data.sql")
	private Resource dataScript;

	// @Bean(destroyMethod = "shutdown")
	public EmbeddedDatabase _dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
				.addScripts("db-schema.sql", "db-test-data.sql").build();
	}

	/*
	 * public DataSourceInitializer dbInitializer(DataSource dataSource) {
	 * DataSourceInitializer initializer = new DataSourceInitializer();
	 * 
	 * initializer.setDataSource(dataSource);
	 * initializer.setDatabasePopulator(databasePopulator());
	 * 
	 * return initializer; }
	 */

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource(
				env.getRequiredProperty("jdbc.url"),
				env.getRequiredProperty("jdbc.user"),
				env.getRequiredProperty("jdbc.pass"));

		dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driver"));

		databasePopulator(dataSource);

		return dataSource;
	}

	// @Bean
	// @Autowired
	private DatabasePopulator databasePopulator(DataSource dataSource) {
		final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

		populator.setSqlScriptEncoding("UTF-8");
		populator.setIgnoreFailedDrops(true);
		populator.setScripts(schemaScript, dataScript);

		try {
			populator.populate(dataSource.getConnection());
		}
		catch (ScriptException | SQLException e) {
			// ignore
		}

		return populator;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

		factory.setDataSource(dataSource());

		return factory;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager(entityManagerFactory().getObject());
	}

	@Bean
	public HibernateExceptionTranslator exceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager();
	}
}

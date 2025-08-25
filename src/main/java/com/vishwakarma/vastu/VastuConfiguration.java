package com.vishwakarma.vastu;


import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.googlecode.flyway.core.Flyway;


/**
 * The application Spring Framework configuration class.
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@PropertySource({"classpath:com/vishwakarma/vastu/db.properties",
	"classpath:com/vishwakarma/vastu/email.properties"})
@ImportResource({"classpath:com/vishwakarma/vastu/jpa.xml",
	"classpath:com/vishwakarma/vastu/spring-security.xml"})
@ComponentScan(basePackageClasses=VastuConfiguration.class)
@EnableAsync
@EnableScheduling
public class VastuConfiguration extends WebMvcConfigurerAdapter{
	@Resource private Environment env;
	
    @Configuration
    @Profile("default")
    public static class DevelopmentProfileConfiguration {

        @Bean public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript("classpath:com/vishwakarma/vastu/dev-schema.sql")
                    .addScript("classpath:com/vishwakarma/vastu/dev-test-data.sql")
                    .build();
        }

        @Bean public JpaVendorAdapter jpaVendorAdapter() {
            final HibernateJpaVendorAdapter ret =
                    new HibernateJpaVendorAdapter();
            ret.setDatabase(Database.H2);
            return ret;
        }
    }

    @Configuration
    @Profile("postgresql")
    public static class PostgreSQLProfileConfiguration {

        @Resource private Environment env;

        @Bean public DataSource dataSource() {
            final PGPoolingDataSource ret = new PGPoolingDataSource();
            ret.setServerName(env.getProperty("db.servername"));
            ret.setDatabaseName(env.getProperty("db.databasename"));
            ret.setUser(env.getProperty("db.user"));
            ret.setPassword(env.getProperty("db.password"));
            return ret;
        }

        @Bean public JpaVendorAdapter jpaVendorAdapter() {
            final HibernateJpaVendorAdapter ret =
                    new HibernateJpaVendorAdapter();
            ret.setDatabase(Database.POSTGRESQL);
            return ret;
        }
    }

    @Configuration
    @Profile("mysql")
    public static class MySQLProfileConfiguration {

        @Resource private Environment env;

        @Bean public DataSource dataSource() {
            final BasicDataSource ret = new BasicDataSource();
            ret.setUrl(env.getProperty("db.url"));
            ret.setDriverClassName(
                    env.getProperty("db.driverClass", "com.mysql.jdbc.Driver"));
            ret.setUsername(env.getProperty("db.username"));
            ret.setPassword(env.getProperty("db.password"));
            ret.setValidationQuery("SELECT 1");
            return ret;
        }

        @Bean public JpaVendorAdapter jpaVendorAdapter() {
            final HibernateJpaVendorAdapter ret =
                    new HibernateJpaVendorAdapter();
            ret.setDatabase(Database.MYSQL);
            ret.setShowSql(Boolean.parseBoolean(env.getProperty("jdbc.showSql","true")));
            ret.setGenerateDdl(Boolean.parseBoolean(env.getProperty("jdbc.generateDdl","true")));
            return ret;
        }
    }
    
    @Bean 
    //@DependsOn(value="flyway")
    public FactoryBean<EntityManagerFactory> entityManagerFactory(
            final DataSource ds, final JpaVendorAdapter jva)
    {
        final LocalContainerEntityManagerFactoryBean ret =
                new LocalContainerEntityManagerFactoryBean();
        ret.setPackagesToScan("com.vishwakarma.vastu");
        ret.setDataSource(ds);
        ret.setJpaVendorAdapter(jva);
        ret.afterPropertiesSet();
        if ("true".equals(env.getProperty("jdbc.populateData"))) {
			final ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
			rdp.addScript(new ClassPathResource(
					"com/vishwakarma/vastu/dev-schema.sql"));
			try {
				rdp.populate(ds.getConnection());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
    }

    @Bean public PlatformTransactionManager transactionManager(
            final EntityManagerFactory emf)
    {
        final JpaTransactionManager ret = new JpaTransactionManager();
        ret.setEntityManagerFactory(emf);
        return ret;
    }

    @Bean public ViewResolver internalResourceViewResolver() {
        final InternalResourceViewResolver ret =
                new InternalResourceViewResolver();
        ret.setOrder(1);
        ret.setViewClass(JstlView.class);
        ret.setPrefix("/WEB-INF/pages/");
        ret.setSuffix(".jsp");
        return ret;
    }
    
    @Bean public HandlerExceptionResolver simpleMappingExceptionResolver() {
        final SimpleMappingExceptionResolver ret =  new SimpleMappingExceptionResolver();
        ret.setDefaultErrorView("error");
        Properties props = new Properties();
        props.put("com.vishwakarma.vastu.exception.vastuException", "vastuError");
        ret.setExceptionMappings(props);
        return ret;
    }
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    	super.addArgumentResolvers(argumentResolvers);
    	argumentResolvers.add(new AtmoSpringControllerResolver());
    }
    
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
	}
    
    @Bean public CommonsMultipartResolver multipartResolver() {
    		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
    		resolver.setMaxUploadSize(5000000L);
    		return resolver;
    }
    
    @Bean public JavaMailSenderImpl javaMailSenderImpl(){
    	
    	final JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
    	javaMailSenderImpl.setHost(env.getProperty("email.host"));
    	javaMailSenderImpl.setPort(Integer.parseInt(env.getProperty("email.portnumber")));
    	javaMailSenderImpl.setUsername(env.getProperty("email.username"));
    	javaMailSenderImpl.setPassword(env.getProperty("email.password"));
    	javaMailSenderImpl.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
    	javaMailSenderImpl.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
    	return javaMailSenderImpl;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    	converters.add(new MappingJackson2HttpMessageConverter());
    	super.configureMessageConverters(converters);
    }

    //@Bean
    public Flyway flyway(DataSource dataSource) {
    	System.out.println("%%%%%%%%% FlyWay Init ....");
    	Flyway flyway = new Flyway();
    	flyway.setDataSource(dataSource);
    	flyway.setLocations("com.vishwakarma.vastu.migration");
    	flyway.migrate();
    	return flyway;
    }

//    @Bean
//    @Scope(value="singleton")
//    public RoleCache roleCache() {
//    		return new RoleCache();
//    }
}

package com.wizard.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = {"com.wizard.persistence.trade.**"}, sqlSessionTemplateRef = "tradeSqlSessionTemplate")
public class DataSourceTradeConfig {

    @Bean(name = "tradeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.trade")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "tradeSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("tradeDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        org.apache.ibatis.session.Configuration cf = new org.apache.ibatis.session.Configuration();
        cf.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(cf);
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/trade/**/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "tradeTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("tradeDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "tradeSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier("tradeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
package com.sangjae.springmybatis.config;

import com.sangjae.springmybatis.section01.factorybean.MenuDTO;
import com.sangjae.springmybatis.section01.factorybean.MenuMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;


    @Value("${spring.datasource.jdbc-url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;


    // HikariDataSource : 커넥션 , 커넥션 풀 등 연결정보 수정

    // SqlSessionFactory
    //  MyBatis에서 SQL 세션(SqlSession)을 생성하는 팩토리이다. SQL 쿼리 실행 및 트랜잭션 관리의 시작점 역할을 한다. 여러 스레드가 안전하게 데이터베이스와 소통할 수 있도록 SQL 세션을 생성해주는 핵심 컴포넌트이다.
    // SqlSessionFactoryBean
    //  Spring 환경에서 SqlSessionFactory를 생성하기 위한 팩토리 빈이다. DataSource와 MyBatis 설정(타입 별칭, 매퍼 등록 등)을 받아 구성한다.

    // SqlSessionTemplate : SqlSessionTemplate = MyBatis의 SqlSession을 스프링에서 안전하게 쓰기 위한 “대체품”
    @Bean
    public HikariDataSource dataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        // 커넥션 획득 대기 시간
        dataSource.setConnectionTimeout(30000);

        // 풀에서 동시에 유지 가능한 최대 커넥션 수
        dataSource.setMaximumPoolSize(50);

        // 사용하지 않는 커넥션의 유휴 시간
        dataSource.setIdleTimeout(600000);

        // 커넥션의 최대 생명 주기 (오래 된 커넥션 주기적 교체)
        dataSource.setMaxLifetime(1800000);

        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        org.apache.ibatis.session.Configuration configuration
                = new org.apache.ibatis.session.Configuration();

        configuration.setMapUnderscoreToCamelCase(true);
        configuration.getTypeAliasRegistry().registerAlias("MenuDTO", MenuDTO.class);
        configuration.addMapper(MenuMapper.class);

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setConfiguration(configuration);

        return sqlSessionFactoryBean.getObject();
    }


    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
      return new SqlSessionTemplate(sqlSessionFactory());
    }



}

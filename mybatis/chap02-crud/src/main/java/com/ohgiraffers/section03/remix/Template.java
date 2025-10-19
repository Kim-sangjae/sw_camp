package com.ohgiraffers.section03.remix;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class Template {

    private static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String URL = "jdbc:mysql://localhost:3306/menudb";
    private static String USER = "swcamp";
    private static String PASSWORD = "swcamp";

    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSession getSqlSession() {

        if (sqlSessionFactory == null) {
            Environment environment = new Environment(
                    "development",
                    new JdbcTransactionFactory(),
                    new PooledDataSource(DRIVER,URL,USER,PASSWORD)
            );

            Configuration configuration = new Configuration(environment);
            configuration.addMapper(MenuMapper.class);
            configuration.setMapUnderscoreToCamelCase(true); // 컬럼은 _ 표기 , 필드명은 카멜케이스
                                                                // 해당 규칙에 맞추어 컬럼명을 필드명으로 매핑
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        }
        // SqlSession : Thread에 안전하지 않고 공유 되지않아야 하므로 요청시 마다 생성
        return sqlSessionFactory.openSession(false);
    }//getSession()



}// class

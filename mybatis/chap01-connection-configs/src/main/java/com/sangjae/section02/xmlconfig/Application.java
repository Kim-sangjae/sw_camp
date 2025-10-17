package com.sangjae.section02.xmlconfig;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class Application {
    public static void main(String[] args) {
        String resource = "mybatis-config.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession(false);

            // selectOne : 조회 결과가 1행인 경우 사용하는 메서드
            // xml파일에 "namespace.id" 의 형태로 수행할 구문 전달
            Date now = sqlSession.selectOne("mapper.selectDate");
            System.out.println("now : "+ now);

            sqlSession.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

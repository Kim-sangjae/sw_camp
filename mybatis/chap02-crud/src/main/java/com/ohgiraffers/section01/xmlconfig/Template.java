package com.ohgiraffers.section01.xmlconfig;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Template {

    // SqlSessionFactory : 애플리케이션이 실행 되는 동안 존재해야 하며 여러차례 다시
    // 빌드 되지 않는것이 좋다 singleton 패턴을 화룡ㅇ하는것이 좋다( 단 하나의 인스턴스 )
    private static SqlSessionFactory sqlSessionFactory;

    // 한번의 요청 당 1개의 sqlSession 객체가 필요하므로 필요 시 호출할 메서드 정의
    public static SqlSession getSqlSession() {

        if (sqlSessionFactory == null) {

            String resource = "com/ohgiraffers/section01/xmlconfig/mybatis-config.xml";

            try {

                InputStream inputStream = Resources.getResourceAsStream(resource);
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
        // SqlSession : Thread에 안전하지 않고 공유 되지않아야 하므로 요청시 마다 생성
        return sqlSessionFactory.openSession(false);
    }//getSession()



}// class

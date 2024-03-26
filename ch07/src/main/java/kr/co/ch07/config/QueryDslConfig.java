package kr.co.ch07.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {

    // build.gradle 설정 후 JAPQueryFactory 빈 설정을 해야 에러 안남

    @PersistenceContext
    private EntityManager entityManager;

    @Bean // QueryDsl로 SQL을 실행하는 객체
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }



}

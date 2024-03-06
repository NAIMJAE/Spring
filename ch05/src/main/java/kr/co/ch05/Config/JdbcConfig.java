package kr.co.ch05.Config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

    // 커넥션 풀
    @Bean
    public DataSource dataSource(){

        // DriverManagerDataSource dataSource = new DriverManagerDataSource();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/studydb");
        dataSource.setUsername("naimjae");
        dataSource.setPassword("abc1234");
        
        // DBCP2 옵션 설정
        dataSource.setMaxTotal(13); // 최대 유휴 풀 크기 설정
        dataSource.setMaxIdle(13); // 최대 유휴 연결 풀 크기 설정
        
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

}

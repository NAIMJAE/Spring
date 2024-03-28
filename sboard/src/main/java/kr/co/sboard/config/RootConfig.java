package kr.co.sboard.config;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// 전역설정
@Getter
@Setter
@Configuration
@EnableAspectJAutoProxy
public class RootConfig {
    @Autowired
    private BuildProperties buildProperties;

    @Bean
    public AppInfo appInfo(){
        String name = buildProperties.getName();
        String version = buildProperties.getVersion();
        return new AppInfo(name, version);
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        // Entity에 setter를 주지 않고 ModelMapper를 사용하기 위한 설정
        modelMapper.getConfiguration()
                // 엔터티 클래스의 필드 접근 레벨을 PRIVATE로 설정 , setter 생략 가능
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                // 매핑할 때 엔티티와 DTO 사이의 속성 이름과 타입이 완전히 일치해야 함
                .setMatchingStrategy(MatchingStrategies.STRICT)
                // 필드 매칭을 활성화, ModelMapper가 매핑할 때 필드 이름이 일치하는 경우에만 매핑
                .setFieldMatchingEnabled(true);
        return modelMapper;
    }
}

package kr.co.oauth.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // 인증 설정 (로그인)
        httpSecurity.formLogin(login -> login
                                        .loginPage("/user/login")               // login 호출 URL
                                        .defaultSuccessUrl("/")     // login 성공 주소
                                        .failureUrl("/user/login?success=100")  // login 실패 주소
                                        .usernameParameter("uid")               // login시 사용할 name 파라미터
                                        .passwordParameter("pass"));            // login시 사용할 password 파라미터
        // 로그아웃 설정
        httpSecurity.logout(logout -> logout
                                .invalidateHttpSession(true)            // session 무효화 -> logout 후 새로운 session 시작
                                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // logout 호출 URL
                                .logoutSuccessUrl("/user/login?success=300"));                     // logout 성공 주소
        
        // Oauth 설정
        httpSecurity.oauth2Login(config -> config.loginPage("/user/login")
                                            .defaultSuccessUrl("/"));
        
        // 인가 설정
        httpSecurity.authorizeHttpRequests(authorize -> authorize
                                    .requestMatchers("/article/**").permitAll()
                                    .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                                    .requestMatchers("/manager/**").hasAnyAuthority("ADMIN", "MANAGER")
                                    .anyRequest().permitAll()
        );

        // 사이트 위변조 방지 설정
        httpSecurity.csrf(CsrfConfigurer::disable);

        // 위 설정들을 return
        return httpSecurity.build();
    }

    // Security 로그인 인증 암호화 인코더 설정
    @Bean
    public PasswordEncoder passwordEncoder(){
        // 같은 평문이라도 서로 다른 암호문 생성
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}

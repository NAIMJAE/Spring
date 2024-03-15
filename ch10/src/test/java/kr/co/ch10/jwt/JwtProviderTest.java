package kr.co.ch10.jwt;

import io.jsonwebtoken.Claims;
import kr.co.ch10.entity.User;
import kr.co.ch10.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JwtProviderTest {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("generateTokenTest()... 토큰 생성 테스트")
    public void generateTokenTest() {

        // given - 테스트 수행 전 테스트에 필요한 환경을 설정하는 단계
        User testUser = User.builder()
                .uid("a101")
                .name("김유신")
                .hp("chhak0503@gmail.com")
                .age(23)
                .role("USER")
                .build();

        // when - 핵심 테스트를 수행하는 단계
        String token = jwtProvider.createToken(testUser, 1000 * 60 * 3);

        // 토큰 확인 - JWT 디버거(https://jwt.io/) 확인
        System.out.println("generateTokenTest token : " + token);

        // then - 테스트의 결과를 검증하는 단계로 AssertJ 검증문 이용
        Claims claims = jwtProvider.getClaims(token);
        String uid = (String) claims.get("uid");
        System.out.println("generateTokenTest uid : " + uid);

        // AssertJ 검증문을 이용해 테스트 결과 가독성 향상
        assertThat(uid).isEqualTo(testUser.getUid());
    }

    @Test
    @DisplayName("validTokenTest1()... 만료한 토큰 테스트")
    public void validTokenTest1() {

        // given
        User testUser = User.builder()
                .uid("a101")
                .name("김유신")
                .hp("chhak0503@gmail.com")
                .age(23)
                .role("USER")
                .build();

        long millis = new Date().getTime() - Duration.ofDays(1).toMillis();
        Date yesterday = new Date(millis);

        String token = JwtMock.builder()
                .issuer(jwtProvider.getIssuer())
                .secretKey(jwtProvider.getSecretKey())
                .expireDate(yesterday)
                .build()
                .createToken(testUser);

        // 만료된 토큰 확인 - JWT 디버거(https://jwt.io/) 확인
        System.out.println("validTokenTest token : " + token);

        // when
        boolean result = jwtProvider.validateToken(token);
        System.out.println("validTokenTest result : " + result);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("validTokenTest2()... 유효한 토큰 테스트")
    public void validTokenTest2() {

        // given
        User testUser = User.builder()
                .uid("a101")
                .name("김유신")
                .hp("chhak0503@gmail.com")
                .age(23)
                .role("USER")
                .build();

        String token = JwtMock.builder()
                .issuer(jwtProvider.getIssuer())
                .secretKey(jwtProvider.getSecretKey())
                .build()
                .createToken(testUser);

        System.out.println("validTokenTest2 token : " + token);

        // when
        boolean result = jwtProvider.validateToken(token);
        System.out.println("validTokenTest2 result : " + result);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("authenticationTest() Test... 토큰 인증 정보 테스트")
    public void authenticationTest() {

        // given
        User testUser = User.builder()
                .uid("a101")
                .name("김유신")
                .hp("chhak0503@gmail.com")
                .age(23)
                .role("USER")
                .build();

        String token = jwtProvider.createToken(testUser, 1000 * 60 * 60 * 24 * 1); // 1일


        // when
        Authentication authentication = jwtProvider.getAuthentication(token);
        UserDetails user = (UserDetails) authentication.getPrincipal();

        System.out.println("user.getUsername : " + user.getUsername());
        System.out.println("user.getAuthorities : " + user.getAuthorities());

        // then
        assertThat(user.getUsername()).isEqualTo(testUser.getUid());
    }
}
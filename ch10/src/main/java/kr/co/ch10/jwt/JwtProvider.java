package kr.co.ch10.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kr.co.ch10.entity.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//
@Slf4j
@Getter
@Component
public class JwtProvider {
    private String issuer;
    private SecretKey secretKey;

    // 생성자
    // 설정 파일 참고해 issuer, secret 초기화
    public JwtProvider(@Value("${jwt.issuer}") String issuer,
                       @Value("${jwt.secret}") String secretKey){
        this.issuer = issuer;
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());

        log.info("JwtProvider...1");
    }

    // 토큰 생성 메서드
    public String createToken(User user, int days){
        log.info("createToken 시작");

        // 발급일, 만료일 생성
        Date issuedDate = new Date();
        Date expireDate = new Date(issuedDate.getTime() + Duration.ofDays(days).toMillis());

        // 클레임 생성
        Claims claims = Jwts.claims();
        claims.put("username", user.getUid());
        claims.put("role", user.getRole());

        // 토큰 생성
        String token = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(issuer)
                .setIssuedAt(issuedDate)
                .setExpiration(expireDate)
                .addClaims(claims)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        log.info("createToken token : " + token);
        log.info("createToken 토큰 생성 완료");
        log.info("createToken 끝");
        return token;
    }

    // 토큰에 있는 Claim(정보)을 가져오는 메서드
    public Claims getClaims(String token){
        log.info("getClaims 토큰 정보 가져오는 메서드");
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)   // 토큰 확인을 위한 대조
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰을 이용해 사용자 인증하는 메서드
    public Authentication getAuthentication(String token){
        log.info("getAuthentication 토큰 인증 메서드 시작");
        // 클레임에서 사용자, 권한 가져오기
        Claims claims = getClaims(token);
        String uid = (String) claims.get("username");
        String role = (String) claims.get("role");

        log.info("getAuthentication-1 uid : " + uid);
        log.info("getAuthentication-2 role : " + role);

        // 권한 목록 생성
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        
        // 사용자 인증 객체 생성
        User principal = User.builder()
                            .uid(uid)
                            .role(role)
                            .build();

        log.info("getAuthentication-3 principal : " + principal);
        log.info("getAuthentication 토큰 인증 메서드 끝");
        
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token){
        log.info("validateToken 토큰 유효성 검사 메서드");
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            log.info("validateToken 토큰 이상 없음");
            return true;

        }catch (SecurityException | MalformedJwtException e){
            // 잘못된 JWT 서명
            log.info("MalformedJwtException..." + e.getMessage());
            throw new JwtMyException(JwtMyException.JWT_ERROR.MALFORN);

        }catch (ExpiredJwtException e){
            // 만료된 JWT
            log.info("ExpiredJwtException..." + e.getMessage());
            throw new JwtMyException(JwtMyException.JWT_ERROR.EXOIRED);

        }catch (UnsupportedJwtException e){
            // 지원되지 않는 JWT
            log.info("UnsupportedJwtException..." + e.getMessage());
            throw new JwtMyException(JwtMyException.JWT_ERROR.BADTYPE);

        }catch (IllegalArgumentException e){
            // 잘못된 JWT
            log.info("IllegalArgumentException..." + e.getMessage());
            throw new JwtMyException(JwtMyException.JWT_ERROR.BADSIGN);

        }
    }
}

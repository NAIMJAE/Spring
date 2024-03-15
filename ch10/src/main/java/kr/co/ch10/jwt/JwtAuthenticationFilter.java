package kr.co.ch10.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.ch10.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

// 클라이언트에서 들어오는 요청 객체의 Header에서 토큰을 추출하는 Filter
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        // 요청 주소에서 마지막 문자열 추출
        String uri = request.getRequestURI();
        int i = uri.lastIndexOf("/");
        String path = uri.substring(i);
        log.info("JWT FILTER-1 path : " + path);

        // 토큰 추출
        String header = request.getHeader(AUTH_HEADER);
        log.info("JWT FILTER-2 header : " + header);

        String token = null;
        log.info("JWT FILTER-3 token : " + token);


        if (header != null && header.startsWith(TOKEN_PREFIX)){
            token = header.substring(TOKEN_PREFIX.length());
            log.info("JWT FILTER-2(header != null) token : " + token);
        }

        // 토큰 검사
        if (token != null){

            log.info("JWT FILTER-4 토큰 검사 ");

            try {
                log.info("JWT FILTER-5 try문");

                jwtProvider.validateToken(token);
                
                // refresh 요청 - accessToken이 만료되어 새 accessToken 발급
                if(path.equals("/refresh")){
                    log.info("JWT FILTER-6 만료된 토큰인 경우");
                    Claims claims = jwtProvider.getClaims(token);
                    String uid = (String) claims.get("username");
                    String role = (String) claims.get("role");

                    log.info("JWT FILTER-7 uid,role : " + uid + role);

                    User user = User.builder()
                            .uid(uid)
                            .role(role)
                            .build();
                    log.info("JWT FILTER-8 user : " + user);

                    // 새 accessToken 토큰 생성
                    String accessToken = jwtProvider.createToken(user, 1);

                    log.info("JWT FILTER-9 accessToken : " + accessToken);
                    log.info("JWT FILTER-9 새 access 토큰 생성");

                    response.setStatus(HttpServletResponse.SC_CREATED);
                    response.getWriter().println(accessToken);
                    return; // 새 accessToken 토큰을 리턴
                }

            }catch(JwtMyException e){
                e.sendResponseError(response);
                return; // Token에 대한 예외 발생으로 리턴
            } // try ~ catch

            log.info("JWT FILTER-10 토큰 인증 처리 ");
            // 토큰 인증 처리 (SecurityContextHolder에 등록)
            Authentication authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
        } // 토큰 검사 if문

        log.info("JWT FILTER-11 다음 필터 이동(controller) ");
        // 다음 필터 이동
        filterChain.doFilter(request, response);
        
    } // doFilterInternal 메서드

} // JwtAuthenticationFilter 클래스

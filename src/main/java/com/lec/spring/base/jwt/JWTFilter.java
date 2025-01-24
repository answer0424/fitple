package com.lec.spring.base.jwt;

import com.lec.spring.base.config.PrincipalDetails;
import com.lec.spring.base.domain.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JWTFilter.doFilterInternal() 호출");

        // request 의 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");

        // Authorization 헤더 검증
        if(authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("token is not correct");
            filterChain.doFilter(request, response);    // 다음 필터로 전달
            return;
        }

        // "Bearer " 부분을 제거 후 순수 토큰만 회득
        String token = authorization.split(" ")[1];
        System.out.println("authorization now, get token : " + token);

        // 토큰 expire 시간이 만료되었는지 검증
        Long id = jwtUtil.getId(token);
        String username = jwtUtil.getUsername(token);
        String authority = jwtUtil.getAuthority(token);

        // User 생성하여 값 세팅 이 정보를 session 에 담음
        User user = User.builder()
                .id(id)
                .username(username)
                .password("jwtwithfitple")    // 임시 비밀번호
                .authority(authority)
                .build();

        // UserDetails 에 회원 정보 객체 담기
        PrincipalDetails userDetails = new PrincipalDetails(user);

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // 인증 진행 -> session 에 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 다음 필터로 전달
        filterChain.doFilter(request, response);
    }
}

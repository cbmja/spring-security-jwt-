package com.campusMatch.CM.config;

import com.campusMatch.CM.jwt.JWTFilter;
import com.campusMatch.CM.jwt.JWTUtil;
import com.campusMatch.CM.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    //기존 security 처럼 폼을 통해 로그인 하고 세션에 로그인 정보를 저장하는 방식이 아닌 jwt를 활용해 토큰을 통해 로그인 정보를 가지고 있는 방식임

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    //세션 방식과 다르게 jwt 방식은 csrf 공격에 대하여 비교적 안전하기 때문에 csrf disable
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.csrf(auth -> auth.disable()); //csrf disable
        http.formLogin(auth -> auth.disable()); //form login 방식 disable
        http.httpBasic(auth -> auth.disable()); //http basic 인증 방식 disable

        //접근 권한 설정
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/login" , "/" , "join").permitAll()
                                               .requestMatchers("/admin").hasRole("ADMIN")
                                               .anyRequest().authenticated());
        //Login 필터 앞에 등록
        http.addFilterBefore(new JWTFilter(jwtUtil) , LoginFilter.class);
        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration) , jwtUtil) , UsernamePasswordAuthenticationFilter.class);

        //세션 사용 안함
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}

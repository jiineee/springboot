package com.coding404.jwt.security.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.coding404.jwt.security.filter.CustomLoginFilter;
import com.coding404.jwt.security.filter.FilterOne;
import com.coding404.jwt.security.filter.FilterTwo;
import com.coding404.jwt.security.filter.JWTAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	//비밀번호 암호화객체
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		//기본로그인 방식, 세션, 베이직 인증 , csrf토큰 전부 사용하지 X
		http.csrf().disable();
		http.formLogin().disable(); //form기반 로그인을 사용하지 X
		http.httpBasic().disable(); //Authorization : 아이디형식으로 넘어오는 basic인증을 사용하지 X
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //세션인증 기반을 사용하지 않고 JWT사용해서 인증
		
		http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); //모든 요청은 전부 허용
		
		//1. 크로스오리진 필터 생성 cors
		http.cors(Customizer.withDefaults());
		
		//2.필터 체이닝 연습
		//http.addFilter(new FilterOne()); //시큐리티타입의 필터를 등록할 때 
		
		//http.addFilterBefore(new FilterOne(),UsernamePasswordAuthenticationFilter.class);
		//http.addFilterBefore(new FilterTwo(), FilterOne.class); //FilterOne보다 이전에 실행
		//http.addFilterAfter(new FilterTwo(), FilterOne.class); //FilterOne보다 이후에 실행
		
		
		//3.로그인 시도에 AuthenticationManger 이 필요합니다
		//++UserDetailService객체 and PasswordEncoder가 반드시 필요
		AuthenticationManager authenticationManager =
				authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));
		
		
		
		//System.out.println(authenticationManager);
		
		//4.로그인 필터를 등록 
		//http.addFilterBefore(new CustomLoginFilter(), UsernamePasswordAuthenticationFilter.class);아래랑 똑같음
		//http.addFilter(new CustomLoginFilter(authenticationManager));
		
		//5.jwt검증필터를 등록
		//http.addFilterBefore(new JWTAuthorizationFilter(authenticationManager),BasicAuthenticationFilter.class);
		//http.addFilter(new JWTAuthorizationFilter(authenticationManager));
		
		//6.요청별로 필터실행시키기
		//login 요청에만 CustomLoginFilter가 실행됨
		http.requestMatchers()
			.antMatchers("/login")
			.and()
			.addFilter(new CustomLoginFilter(authenticationManager));
		
		//api로 시작하는 요청에만 JWT 필터가 실행됨
		http.requestMatchers()
			.antMatchers("/api/v1/**")
			.antMatchers("/api/v2/**")
			.and()
			.addFilter(new JWTAuthorizationFilter(authenticationManager));
		

		return http.build();
	}
	
	//로그인 시도에 필요한 AuthenticationManager객체
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	

    	
    	//1. 크로스 오리진 필터등록
    	@Bean
    	CorsConfigurationSource corsConfigurationSource() {
    		
    		CorsConfiguration configuration = new CorsConfiguration();
    		configuration.setAllowedOrigins(Arrays.asList("*")); //모든요청 주소를 허용함
    		configuration.setAllowedMethods(Arrays.asList("*")); //모든요청 메서드를 허용
    		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    		source.registerCorsConfiguration("/**", configuration);//모든요청에 대해서
    		
    		return source;
    		
    	}
    
	
	
	
}

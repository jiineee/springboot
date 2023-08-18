package com.coding404.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity5.dialect.processor.AuthorizeAclAttrProcessor;

@Configuration //설정파일
@EnableWebSecurity //이 설정파일을 시큐리티 필터에 추가
public class SecurityConfig {
	
	
	@Bean
	public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception{
		
		//crsf토큰 X
		http.csrf().disable();
		
		//권한설정
		//http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
		
		//모든페이지에 대해서 거부
		//http.authorizeHttpRequests(authorize -> authorize.anyRequest().denyAll());

		//유저로 시작하는 모든 경로에 대해 인증이 필요
//		http.authorizeHttpRequests(authorize -> authorize.
//				                                antMatchers("/user/**").
//				                                authenticated());
		
		
		//user패이지에 대해서 권한이 필요
		//http.authorizeHttpRequests(authorize -> authorize.antMatchers("/user/**").hasRole("USER"));
		
		//user페이지는 user권한이 필요 admin페이지는 admin권한이 필요
//		http.authorizeHttpRequests(authorize -> authorize.antMatchers("/user/**").hasRole("USER")
//														 .antMatchers("/admin/**").hasRole("ADMIN"));
		
		
		//all 페이지는 인증만 되면 됨 , user페이지는 user권한 , admin페이지는 admin권한 , 나머지 모든 페이지는 접근이 가능함
//		http.authorizeHttpRequests(authorize -> authorize.antMatchers("/all").authenticated()
//							                             .antMatchers("/user/**").hasRole("USER")
//							                             .antMatchers("/admin/**").hasRole("ADMIN")
//							                             .anyRequest().permitAll());
		
		//all 페이지는 인증만 되면 됨 ,user페이지는 3중 1개의 권한을 가지면 됨, 
		http.authorizeHttpRequests(authorize->authorize.antMatchers("/all").authenticated()
											                .antMatchers("/user/**").hasAnyRole("USER","ADMIN","TESTER")
											                .antMatchers("/admin/**").hasRole("ADMIN")
											                .anyRequest().permitAll());
							                             
		//시큐리티 설정파일을 만들면 시큐리티가 제공하는 기본 로그인페이지가 보이지 않게 됩니다
		//시큐리티가 사용하는 기본 로그인 페이지를 사용함 
		//권한 or인증이 되지 않으면 기본으로 선언된 로그인페이지를 보여주게 됩니다
		//http.formLogin(Customizer.withDefaults()); //기본 로그인 페이지 사용
		
		//사용자가 제공하는 폼기반 로그인기능을 사용할 수 있습니다.
		http.formLogin().loginPage("/login");
		
		
		
		
		return http.build();
	}
	
	
}

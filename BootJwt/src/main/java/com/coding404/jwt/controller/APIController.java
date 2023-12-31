package com.coding404.jwt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coding404.jwt.command.UserVO;
import com.coding404.jwt.security.config.JWTService;

@RestController //rest
public class APIController {
	
//	//로그인기능 가정 
//	@PostMapping("/login")
//	public ResponseEntity<String> login(@RequestBody UserVO vo) {
//		
//		System.out.println(vo.toString());
//		//로그인 시도 ~~> 성공이라고 가정
//		String token = JWTService.createToken(vo.getUsername());
//		
//		
//		return new ResponseEntity<>(token,HttpStatus.OK);
//	}
//	
//	//사용자정보확인
//	//토큰을 헤더에 담아서 사용자 정보와 함께 요청
//	@PostMapping("/api/v1/getInfo")
//	public ResponseEntity<Object> getInfo(HttpServletRequest request){
//		
//		//헤더에 담긴 토큰
//		String token = request.getHeader("Authorization");
//		
//		//토큰의 무결성 검사
//		
//		try {
//			boolean result = JWTService.validateToken(token);	
//			System.out.println("토큰무결성:" + result);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>("토큰위조",HttpStatus.UNAUTHORIZED);
//		}
//		
//	
//		return new ResponseEntity<>("success getInfo~",HttpStatus.OK);
//	}
	
	
	//시큐리티 요청테스트
	@GetMapping("/api/v1/hello")
	public String hello() {
		return "<h3>헬로</h3>";
	}
	
	//토큰 기반으로 한 사용자요청정보 반환 기능
	@PostMapping("/api/v1/getInfo")
	public ResponseEntity<Object> getInfo(){
		System.out.println("토큰이 있으면 호출됨(데이터 베이스 연결처리~)");
		
		return new ResponseEntity<>("데이터",HttpStatus.OK);
	}
	
	//회원가입
	@PostMapping("/join")
	public ResponseEntity<Object> join(){
		return new ResponseEntity<>("가입성공",HttpStatus.OK);
	}
	
	
	
	
}



















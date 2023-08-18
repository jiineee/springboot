package com.simple.basic.controller;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.simple.basic.command.SimpleVO;

@RestController //@ResponseBody + @Controller
public class RestBasicController {
	
	@GetMapping("/basic")
	public String basic() {
		return "<h3>hello world</h3>";
	}
	
	//데이터를 보내는 방법 -> @ResponseBody = 자바의 객체를 JSON형식으로 자동변환
	@GetMapping("/getObject")
	public SimpleVO getObject() {
		SimpleVO vo = new SimpleVO(1,"홍","길동",LocalDateTime.now());
		return vo;
	}
	
	@GetMapping("/getMap")
	public Map<String, Object> getMap(){
		Map<String,Object> map = new HashMap<>();
		map.put("name", "홍길동");
		return map;
	}
	
	@GetMapping("/getList")
	public List<SimpleVO> getList(){
		List<SimpleVO> list = new ArrayList<>();
		list.add(new SimpleVO(1,"홍","길동",LocalDateTime.now()));
		list.add(new SimpleVO(2,"이","길동",LocalDateTime.now()));
		return list;
	}
	
	//데이터를 받는 방법
	//get => 쿼리스트링 or 쿼리파라미터 이용해서 넘겨줌
	//post => 폼형식 or json을 이용해서 body에 담아서 넘김
	
	//http://localhost:8181/getData?sno=1&first=홍길동
	@GetMapping("/getData")
	public SimpleVO getData(SimpleVO vo) {
		System.out.println(vo.toString());	
		return new SimpleVO(2,"이","길동",LocalDateTime.now());
	}
	
	//http://localhost:8181/getData2?sno=1&first=홍길동
	//sno, first는 필수값이 됩니다.
	@GetMapping("/getData2") 
	public SimpleVO getData2(@RequestParam("sno") int sno,
							 @RequestParam("first") String first) {
		
		System.out.println(sno);
		System.out.println(first);
		return new SimpleVO(2,"이","길동",LocalDateTime.now());
	}	
	
	
	@GetMapping("/getData3/{sno}/{first}")
	public SimpleVO getData3(@PathVariable("sno") int sno,
							 @PathVariable("first") String first) {
		System.out.println(sno);
		System.out.println(first);
		
		return new SimpleVO(2,"이","길동",LocalDateTime.now());
	}
	
	
	//////////////////////////////////////
	//post방식의 데이터받기
	
	//보내는 입장에서 form 형식의 데이터를 써줘야함 
	@PostMapping("/getForm")
	public SimpleVO getForm(SimpleVO vo) {
		System.out.println(vo.toString());
		return new SimpleVO(2,"이","길동",LocalDateTime.now());
	}
	
	//보내는 입장에서는 JSON형식의 데이터를 써줘야함
	//{ "sno":"1", "first":"홍길동", "last":"이순신"}
	@PostMapping("/getJSON")
	public SimpleVO getJSON(@RequestBody SimpleVO vo) {
		System.out.println(vo.toString());
		return new SimpleVO(2,"이","길동",LocalDateTime.now());
	}
	
	@PostMapping("/getJSON2")
	public SimpleVO getJSON2(@RequestBody Map<String, Object> map) {
		System.out.println(map.toString());
		return new SimpleVO(2,"이","길동",LocalDateTime.now());
	}
	////////////////////////////////////////
	//consumer =이 타입으로 보내라 ! 명시
	//producer =내가 이타입으로 줄게! 명시 (default = json) ,xml을 사용하려면 xml데이터바인딩 라이브러리 필욧
	
	//보내는 타입은 text로 줄게 너는 json데이터로 보내라
	@PostMapping(value="/getResult", produces = "text/plain", consumes = "application/json")
	public String getResult(@RequestBody String str) {
		System.out.println(str);
		return "<h3>문자열</h3>";
	}
	
	
	//응답문서 직접작성하기 = RespnseEntity<보낼데이터타입>
	@PostMapping("/createResponse")
	public ResponseEntity<SimpleVO> createResponse(){
		
		SimpleVO vo = new SimpleVO(1,"홍","길동",LocalDateTime.now());
		
		//1st
		//ResponseEntity<SimpleVO> result = new ResponseEntity<>(vo	, HttpStatus.OK); //데이터, 상태코드
		
		//2nd
		//헤더에 대한 내용정의
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "JSON WEB TOKEN");
		header.add("Content-Type", "application/json");
		header.add("Access-Control-Allow-Origin", "*");
		
		ResponseEntity<SimpleVO> result = new ResponseEntity<>(vo, header, HttpStatus.OK); //데이터, 헤더, 상태코드
		
		return result;
	}
	
	///////////////
	/*
	 	클라이언트 fetch
	 	요청주소: /api/v1/getData
	 	메서드:get
	 	클라이언트에서 보낼데이터는 :num,name
	 	서버에서 보낼 데이터는: SimpleVO
	 	받을 수 있는 restAPI를 생성
	*/

//	@GetMapping("/api/v1/getData")
//	public SimpleVO api(@RequestParam("num") int num,
//					    @RequestParam("name") String name) {	
//		System.out.println(num);
//		System.out.println(name);
//		
//		return new SimpleVO(2,"이","길동",LocalDateTime.now());
//	}
	@CrossOrigin("*")
	@GetMapping("/api/v1/getData/{num}/{name}")
	public ResponseEntity<SimpleVO> getFetch(@PathVariable("num") int num,
											 @PathVariable("name") String name){
		
		System.out.println(num); //데이터베이스에서 활용가능할 수 있다는
		System.out.println(name);
		
				
		return new ResponseEntity<>(new SimpleVO(1,"kim","hong",LocalDateTime.now()), HttpStatus.OK);
	}
	

	/*
	 	클라이언트 fetch
	 	요청주소 /api/v1/getInfo
	 	메서드:post
	 	보낼데이터는 num name
	 	서버에서 보낼 데이터는: SimpleVO
	 	받을 수 있는 restAPI를 생성
	 	ResponseEntity로 응답
	 */
	
	//@CrossOrigin("http://localhost:3000")
	@CrossOrigin("*") //모든 서버에 대한 요청을 허용한다
	
	@PostMapping("/api/v1/getInfo")
	public ResponseEntity<List<SimpleVO>> getInfo(@RequestBody Map<String, Object> map){
		
		System.out.println(map.toString());
		List<SimpleVO> list = new ArrayList<>();
		list.add(new SimpleVO(2,"이","길동",LocalDateTime.now()));
		
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	
	
	
	
}

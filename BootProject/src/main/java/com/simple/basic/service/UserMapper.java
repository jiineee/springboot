package com.simple.basic.service;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.simple.basic.command.UserVO;

@Mapper	
public interface UserMapper {	
	public int join(UserVO userVO);
	
//	public boolean idcheck(String user_id);
//	public boolean emailcheck(String user_email);
	
	public UserVO login(UserVO userVO) ;
	
	public int idCheck(String user_id) throws Exception;
	public int emailCheck(String user_email) throws Exception;
	public void logout(HttpSession session);
	
	public int login_id(String user_id) throws Exception;
	
	public ArrayList<UserVO> getUser_id();
}
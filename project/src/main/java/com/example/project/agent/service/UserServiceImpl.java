package com.example.project.agent.service;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.example.project.command.*;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public int join(UserVO userVO) {
		return userMapper.join(userVO);
	}

	@Override
	public UserVO login(UserVO userVO) {
		return userMapper.login(userVO);
	}

	@Override
	public int idCheck(String user_id) throws Exception {
		return userMapper.idCheck(user_id);
	}

	@Override
	public int emailCheck(String user_email) throws Exception {
		return userMapper.emailCheck(user_email);
	}

	@Override
	public void logout(HttpSession session) {
		session.invalidate();
		
	}

	@Override
	public int login_id(String user_id) throws Exception {
		return userMapper.login_id(user_id);
	}

	@Override
	public ArrayList<UserVO> getUser_id() {
		return userMapper.getUser_id();
	}

	@Override
	public RoomVO getRoomData(int room_id) {
		
		return userMapper.getRoomData(room_id);
	}

	@Override
	public ArrayList<RoomVO> getList(String yn) {
	
		return userMapper.getList(yn);
		
	}

	@Override
	public ArrayList<PhotoVO> getAjaxImg(String room_writer) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	






	


}

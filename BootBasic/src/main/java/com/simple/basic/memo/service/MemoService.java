package com.simple.basic.memo.service;

import java.util.ArrayList;

import com.simple.basic.command.MemoVO;

public interface MemoService {
	public void memoWrite(MemoVO vo);
	public ArrayList<MemoVO> getList();
	
}

package com.simple.basic.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simple.basic.command.MemoVO;
import com.simple.basic.memo.service.MemoService;

@Controller
@RequestMapping("/memo")
public class MemoController {
	
	@Autowired
	@Qualifier("memoService")
	private MemoService memoService;
	
	//목록화면
	@GetMapping("/memoList")
	public String memoList(Model model) {
		return "memo/memoList";
	}
	
	@PostMapping("/memoForm")
	public String memoWrite(@Valid @ModelAttribute("vo") MemoVO vo,
							Errors erros, Model model) {
		
		if(erros.hasErrors()) {
			List<FieldError> list = erros.getFieldErrors();
			for(FieldError err :list) {
				if(err.isBindingFailure()) {
					model.addAttribute("Valid_" + err.getField(),"잘못입력");
				}else {
					model.addAttribute("valid_" + err.getField(), err.getDefaultMessage());
				}
			}
			return "memo/memoWrite";
		}
		
		
		return "memo/memoList";
	}
}

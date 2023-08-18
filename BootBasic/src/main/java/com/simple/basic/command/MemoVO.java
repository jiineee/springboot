package com.simple.basic.command;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemoVO {
	private Integer mno;
	@NotEmpty(message = "5글자이상작성해주세요")
	private String memo;
	
	@Pattern(message = "전화번호를 입력해주세요" , regexp ="[0-9]{3}-[0-9]{4}-[0-9]{4}" )
	private String phone;
	@Pattern(message = "비밀번호는 4자리여야합니다" , regexp ="[0-9]{4}" )
	private String pw;
	private String secret;
	
}

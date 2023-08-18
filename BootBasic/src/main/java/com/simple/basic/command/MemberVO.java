package com.simple.basic.command;

import javax.validation.constraints.NotBlank;
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
public class MemberVO {
	
	@NotEmpty(message="공백일 수 없습니다")
	@Pattern(message ="아이디형식을 지켜주세요", regexp = "[a-zA-Z0-9]{8,}")
	private String id;
	@NotEmpty(message="공백일 수 없습니다")
	@Pattern(message="비밀번호형식을 지켜주세요" , 
			regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
	private String pw;
}

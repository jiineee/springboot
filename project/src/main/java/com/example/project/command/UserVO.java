package com.example.project.command;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.apache.ibatis.type.Alias;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO {
	private Integer user_num;
	
	@Pattern(message = "이메일 형식이 올바르지 않습니다.",regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$")
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	private String user_email;

	@NotBlank(message = "아이디는 필수 입력 값입니다.")
	private String user_id;
	
	@Pattern(message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.",regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}")
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	private String user_pw;
	
	@Pattern(message = "000-0000-0000형식 이어야합니다", regexp = "[0-9]{3}-[0-9]{4}-[0-9]{4}")
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	private String user_phone;
	
	private LocalDateTime user_regdate;
	
	
}

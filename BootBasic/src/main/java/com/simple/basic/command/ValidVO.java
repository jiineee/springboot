package com.simple.basic.command;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidVO {
	
	/*
	 * @NotNull = null허용X (String Integer Double등등)
	 * @NotEmpty = null허용X ,공백허용X (String적용)
	 * @NotBlank = null, 공백, 화이트스페이스 허용X (String적용)
	 * 
	 * @Pattern = 정규표현식에 일치하지 않으면 err
	 */
	
	//기본타입 => 래퍼타입 작성
	@NotEmpty(message = "이름은 필수 입니다")
	private String name;
			
	@NotNull(message = "급여는 숫자로 입력하세요")
	private Integer salary;
	
	@NotBlank(message = "공백일 수 없습니다")
	@Email(message = "이메일 형식이어야 합니다") //이메일은 공백이 통과
	private String email;
	
	@Pattern(message = "000-0000-0000형식 이어야합니다", regexp = "[0-9]{3}-[0-9]{4}-[0-9]{4}")
	private String phone;
}



















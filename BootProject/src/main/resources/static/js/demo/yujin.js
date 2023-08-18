$(document).ready(function(){
	
	//상단 메뉴
	$("nav li>a").on("mouseenter click" , function(){
		$("nav nav").stop().slideDown();
	});
	$("nav ul").mouseleave(function(){
		$("nav nav").stop().slideUp();
	});
	$(".last").blur(function(){
		$("nav nav").stop().slideUp();
	});
	
	//페이드 슬라이드
	setInterval(fading, 2800);
	let i = 2;
	function fading(){	
		console.log(i);// 2,1,0,2,1,0......
		console.log(i==0);//false,false,true.....
		if( i == 0 ){
				$("#slide a").fadeIn();
				i=2;
		}
		else {
			$("#slide a").eq(i).fadeOut();
			i--;
		}		
	}
	
});//////////////all end








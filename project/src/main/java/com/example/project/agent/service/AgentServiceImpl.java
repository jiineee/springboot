package com.example.project.agent.service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.project.command.AgentMemberVO;
import com.example.project.command.PhotoVO;
import com.example.project.command.RoomVO;
import com.example.project.util.Criteria;

@Service("agentService")
public class AgentServiceImpl implements AgentService {

	@Autowired
	private AgentMapper agentMapper;
	
	@Value("${project.upload.path}")
	private String uploadPath;
	
	// 폴더 생성 함수
	public String makeFolder() {

		String path = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		File file = new File(uploadPath + "/" + path);

		if (file.exists() == false) { // 존재하면 true, 존재하지 않으면 false
			file.mkdirs();
		}

		return path; // 날짜폴더명 반환
	}
	
	@Override
	public AgentMemberVO agentLogin(AgentMemberVO vo) {
		return agentMapper.agentLogin(vo);
	}
	
	@Override
	public int agentRegist(AgentMemberVO vo) {
		
		return agentMapper.agentRegist(vo);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int roomRegist(RoomVO vo, List<MultipartFile> list) {
		
		 int result = agentMapper.roomRegist(vo);
		 
		// 업로드처리
		for (MultipartFile file : list) {

			// 파일 이름을 받습니다.
			String originName = file.getOriginalFilename();
			// 브라우저 별로 파일의 경로가 다를 수 있기 때문에 \\ 기준으로 파일명만 잘라서 다시 저장
			String filename = originName.substring(originName.lastIndexOf("\\") + 1);
			// 파일사이즈
			long size = file.getSize();
			// 동일한 파일을 재업로드 시 기존 파일을 덮어버리기 때문에, 난수 이름으로 파일명을 바꿔서 올림
			String uuid = UUID.randomUUID().toString();
			// 날짜 별로 폴더 생성
			String filepath = makeFolder();
			// 세이브할 경로
			String savepath = uploadPath + "/" + filepath + "/" + uuid + "_" + filename;

			// System.out.println(originName);
			// System.out.println(size);
			// 데이터베이스 추후에 저장
			System.out.println("실제파일명:" + filename);
			System.out.println("난수값:" + uuid);
			System.out.println("날짜폴더경로:" + filepath);
			System.out.println("세이브할 경로:" + savepath);
			System.out.println("===============================================");

			try {
				File saveFile = new File(savepath);
				file.transferTo(saveFile); // 파일업로드를 진행
			} catch (Exception e) {
				System.out.println("파일업로드중 error발생");
				e.printStackTrace();
				return 0; // 실패의 의미
			}

			// productUpload테이블에 파일의 경로 인서트
			// prod_id는 insert전에 selectKey를 통해서 얻습니다
			agentMapper.roomFileRegist(PhotoVO.builder().filename(filename).filepath(filepath)
					.uuid(uuid).room_writer(vo.getRoom_writer()).build());

		} // end for
			
		return result;
	}

	@Override
	public ArrayList<RoomVO> getList(String a_name) {
		System.out.println("SERVICE==================" + a_name);
		return agentMapper.getList(a_name);
	}
	
	//수정 화면 데이터
	@Override
	public RoomVO getRoomData(int room_id) {
		return agentMapper.getRoomData(room_id);
	}

	//수정
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int roomUpdate(RoomVO vo/* , int r_id, List<MultipartFile> list */) {
		
		 int result = agentMapper.roomUpdate(vo);
		 
		// 업로드처리
//		for (MultipartFile file : list) {
//
//			// 파일 이름을 받습니다.
//			String originName = file.getOriginalFilename();
//			// 브라우저 별로 파일의 경로가 다를 수 있기 때문에 \\ 기준으로 파일명만 잘라서 다시 저장
//			String filename = originName.substring(originName.lastIndexOf("\\") + 1);
//			// 파일사이즈
//			long size = file.getSize();
//			// 동일한 파일을 재업로드 시 기존 파일을 덮어버리기 때문에, 난수 이름으로 파일명을 바꿔서 올림
//			String uuid = UUID.randomUUID().toString();
//			// 날짜 별로 폴더 생성
//			String filepath = makeFolder();
//			// 세이브할 경로
//			String savepath = uploadPath + "/" + filepath + "/" + uuid + "_" + filename;

			// System.out.println(originName);
			// System.out.println(size);
			// 데이터베이스 추후에 저장
//			System.out.println("실제파일명:" + filename);
//			System.out.println("난수값:" + uuid);
//			System.out.println("날짜폴더경로:" + filepath);
//			System.out.println("세이브할 경로:" + savepath);
//			System.out.println("===============================================");
//
//			try {
//				File saveFile = new File(savepath);
//				file.transferTo(saveFile); // 파일업로드를 진행
//			} catch (Exception e) {
//				System.out.println("파일업로드중 error발생");
//				e.printStackTrace();
//				return 0; // 실패의 의미
//			}

			// productUpload테이블에 파일의 경로 인서트
			// prod_id는 insert전에 selectKey를 통해서 얻습니다
//			agentMapper.roomFileUpdate(PhotoVO.builder().filename(filename).filepath(filepath)
//					.uuid(uuid).room_writer(vo.getRoom_writer()).build(), r_id);

//		} // end for
			
		return result;
	}

	@Override
	public RoomVO getOk(String room_writer) {
		
		return agentMapper.getOk(room_writer);
	}
	@Override
	public ArrayList<PhotoVO> getAjaxImg(String room_writer) {
		return agentMapper.getAjaxImg(room_writer);
	}

	@Override
	public ArrayList<PhotoVO> getAjaxUpdateImg(int room_id) {
		return agentMapper.getAjaxUpdateImg(room_id);
	}
	

}

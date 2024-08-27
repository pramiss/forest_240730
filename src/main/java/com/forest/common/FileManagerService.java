package com.forest.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileManagerService {
	
	// 파일경로 - 로컬서버
	public static final String FILE_UPLOAD_PATH = "C:\\github\\Marondal\\7_forest\\clone\\src\\main\\resources\\images/"; //-- 노트북
//	public static final String FILE_UPLOAD_PATH = "C:\\github\\Marondal\\7_forest\\workspace\\forest\\src\\main\\resources\\images/"; //-- 데스크탑
//	public static final String FILE_UPLOAD_PATH = "D:\\배진하\\7_forest_project\\clone\\src\\main\\resources\\images/"; //-- 데탑2
	
	// 파일 추가
	public String uploadFile(MultipartFile file, String isbn) {
		String directoryName = isbn + "_" + System.currentTimeMillis();
		String filePath = FILE_UPLOAD_PATH + directoryName + "/";
		
		File directory = new File(filePath);
		if (directory.mkdir() == false) {
			return null;
		}
		
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath + file.getOriginalFilename());
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return "/images/" + directoryName + "/" + file.getOriginalFilename();
	} //-- 로컬경로생성 및 이미지 저장
	
	// 파일 삭제
	// input: imageUrl, output: X
	public void deleteFile(String imageUrl) {

		// 1. path 가져오기
		Path path = Paths.get(FILE_UPLOAD_PATH + imageUrl.replace("/images/", "")); 
		
		// 2. 파일(이미지) 삭제
		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				log.warn("[FileManagerService 파일삭제] 이미지 파일 삭제 실패, path:{}", path.toString());
				return;
			}
		}
		
		// 3. 파일(디렉토리) 삭제
		path = path.getParent();
		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				log.warn("[FileManagerService 파일삭제] 디렉토리 파일 삭제 실패, path:{}", path.toString());
			}
		}
		
	} // 파일(이미지) 삭제
}

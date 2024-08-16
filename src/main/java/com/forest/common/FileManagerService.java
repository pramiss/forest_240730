package com.forest.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManagerService {
	
	// 파일경로 - 로컬서버
	public static final String FILE_UPLOAD_PATH = "C:\\github\\Marondal\\7_forest\\clone\\src\\main\\resources\\images/"; // 노트북
	
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
}

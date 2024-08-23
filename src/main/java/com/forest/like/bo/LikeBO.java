package com.forest.like.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.forest.like.entity.LikeEntity;
import com.forest.like.entity.LikeId;
import com.forest.like.repository.LikeRepository;

@Service
public class LikeBO {

	private final LikeRepository likeRepository;
	
	public LikeBO(LikeRepository likeRepository) {
		this.likeRepository = likeRepository;
	}
	
	// like 토글
	public void likeToggle(int userId, String isbn) {
		
		// 1. 먼저 (userId, isbn)으로 해당 like가 존재하는지 확인
		LikeId likeId = new LikeId(userId, isbn);
		LikeEntity likeEntity = likeRepository.findById(likeId).orElse(null);
		
		// 2. 없으면 추가, 3. 존재하면 삭제
		if (likeEntity == null) {
			likeEntity = LikeEntity.builder().id(likeId).build();
			likeRepository.save(likeEntity);
		} else {
			likeRepository.delete(likeEntity);
		}
		
	} //-- like 토글
	
	// List<LikeEntity> 가져오기 (by userId)
	public List<LikeEntity> getLikeListByUserId(int userId) {
		return likeRepository.findAllByIdUserId(userId);
	}
	
	// 좋아요 여부 확인 (단건)
	public boolean isLikeById(int userId, String isbn) {
		
		// 1. 먼저 PK(userId, isbn)으로 해당 like가 존재하는지 확인
		LikeId likeId = new LikeId(userId, isbn);
		LikeEntity likeEntity = likeRepository.findById(likeId).orElse(null);
		
		// 있으면 return true, 없으면 return false
		return likeEntity != null;
	}
}

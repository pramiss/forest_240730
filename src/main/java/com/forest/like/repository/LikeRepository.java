package com.forest.like.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forest.like.entity.LikeEntity;
import com.forest.like.entity.LikeId;

public interface LikeRepository extends JpaRepository<LikeEntity, LikeId> {

	public List<LikeEntity> findAllByIdUserId(int userId);
}

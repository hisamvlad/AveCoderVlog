package com.avecoder.avecoder.avecoderblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avecoder.avecoder.avecoderblog.model.Post;


public interface PostRepository extends JpaRepository<Post, Long> {

	

}

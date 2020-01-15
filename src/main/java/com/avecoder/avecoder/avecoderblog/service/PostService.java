package com.avecoder.avecoder.avecoderblog.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avecoder.avecoder.avecoderblog.dto.PostDto;
import com.avecoder.avecoder.avecoderblog.exception.PostNotFoundException;
import com.avecoder.avecoder.avecoderblog.model.Post;
import com.avecoder.avecoder.avecoderblog.model.User;
import com.avecoder.avecoder.avecoderblog.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
    private AuthService authService;
    @Autowired
    private PostRepository postRepository;

    @Transactional
    public List<PostDto> showAllPosts() {
        List<Post> posts = postRepository.findAll();
        return (List<PostDto>) posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    private Collector toList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
    public void createPost(PostDto postDto) {
        Post post = mapFromDtoToPost(postDto);
        postRepository.save(post);
    }

    @Transactional
    public PostDto readSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromPostToDto(post);
    }

    private PostDto mapFromPostToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUsername(post.getUsername());
        return postDto;
    }

    private Post mapFromDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        org.springframework.security.core.userdetails.User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        post.setCreatedOn(Instant.now());
        post.setUsername(loggedInUser.getUsername());
        post.setUpdatedOn(Instant.now());
        return post;
    }
}

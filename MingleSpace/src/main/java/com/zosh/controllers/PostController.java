package com.zosh.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.models.Post;
import com.zosh.models.User;
import com.zosh.response.ApiResponse;
import com.zosh.service.PostService;
import com.zosh.service.UserService;

@RestController
public class PostController {
	
	@Autowired
	PostService postService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/api/posts")
	public ResponseEntity<Post>createPost(@RequestHeader("Authorization")String jwt , @RequestBody Post post) throws Exception{
		
		User reqUser=userService.findUserByJwt(jwt);
		Post createdPost =postService.createNewPost(post,reqUser.getId());
		return new ResponseEntity<>(createdPost,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/api/posts/delete/{postId}")
	public ResponseEntity<ApiResponse> deletePost( @RequestHeader("Authorization")String jwt ,@PathVariable Integer postId) throws Exception{
		
		User reqUser=userService.findUserByJwt(jwt);
		String message=postService.deletePost(postId, reqUser.getId());
		
		ApiResponse response=new ApiResponse(message,true);
		return new ResponseEntity<ApiResponse>(response ,HttpStatus.OK);
	}
	
	@GetMapping("/api/posts/{postId}")
	public ResponseEntity<Post> findPostByIdHandler(@PathVariable Integer postId) throws Exception{
		Post post =postService.findPostById(postId);
		
		 return new ResponseEntity<Post>(post,HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping("/api/posts/user/{userId}")
	public ResponseEntity<List<Post>> findUsersPost(@PathVariable Integer userId) throws Exception{
		List<Post> posts=postService.findPostByUserId(userId);
		
		return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<List<Post>> findAllPost() throws Exception{
		List<Post> posts=postService.findAllPost();
		
		return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
	}
	
	
	@PutMapping("/api/post/save/{postId}")
	public ResponseEntity<Post> savedPostIdHandler(  @RequestHeader("Authorization")String jwt ,@PathVariable Integer postId) throws Exception{
		
		User reqUser=userService.findUserByJwt(jwt);
		Post post =postService.savedPost(postId, reqUser.getId());
		
		 return new ResponseEntity<Post>(post,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/api/post/like/{postId}")
	public ResponseEntity<Post> likePostIdHandler(@RequestHeader("Authorization")String jwt, @PathVariable Integer postId) throws Exception{
		
		User reqUser=userService.findUserByJwt(jwt);
		Post post =postService.likePost(postId, reqUser.getId());
		
		 return new ResponseEntity<Post>(post,HttpStatus.ACCEPTED);
	}
	
}





package com.sopromadze.blogapi.controller;

import com.sopromadze.blogapi.model.Post;
import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.payload.PostRequest;
import com.sopromadze.blogapi.payload.PostResponse;
import com.sopromadze.blogapi.security.CurrentUser;
import com.sopromadze.blogapi.security.UserPrincipal;
import com.sopromadze.blogapi.service.PostService;
import com.sopromadze.blogapi.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
/**
 * REST controller for managing blog post operations.
 *
 * <p>Provides endpoints for creating, reading, updating, and deleting blog posts,
 * as well as retrieving posts by category or tag with pagination support.</p>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;

	/**
	 * Retrieves all posts with pagination support
	 *
	 * @param page Page number (default: 0)
	 * @param size Page size (default: 10)
	 * @return ResponseEntity containing paginated posts
	 */
	@GetMapping
	public ResponseEntity<PagedResponse<Post>> getAllPosts(
			@RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
		PagedResponse<Post> response = postService.getAllPosts(page, size);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Retrieves posts by category ID with pagination
	 *
	 * @param page Page number (default: 0)
	 * @param size Page size (default: 10)
	 * @param id Category ID to filter by
	 * @return ResponseEntity containing paginated posts for the category
	 */
	@GetMapping("/category/{id}")
	public ResponseEntity<PagedResponse<Post>> getPostsByCategory(
			@RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
			@PathVariable(name = "id") Long id) {
		PagedResponse<Post> response = postService.getPostsByCategory(id, page, size);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Retrieves posts by tag ID with pagination
	 *
	 * @param page Page number (default: 0)
	 * @param size Page size (default: 10)
	 * @param id Tag ID to filter by
	 * @return ResponseEntity containing paginated posts for the tag
	 */
	@GetMapping("/tag/{id}")
	public ResponseEntity<PagedResponse<Post>> getPostsByTag(
			@RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
			@PathVariable(name = "id") Long id) {
		PagedResponse<Post> response = postService.getPostsByTag(id, page, size);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Creates a new blog post
	 *
	 * @param postRequest Post data to create
	 * @param currentUser Authenticated user creating the post
	 * @return ResponseEntity with the created post
	 */
	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<PostResponse> addPost(
			@Valid @RequestBody PostRequest postRequest,
			@CurrentUser UserPrincipal currentUser) {
		PostResponse postResponse = postService.addPost(postRequest, currentUser);
		return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
	}

	/**
	 * Retrieves a specific post by ID
	 *
	 * @param id ID of the post to retrieve
	 * @return ResponseEntity with the requested post
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Post> getPost(
			@PathVariable(name = "id") Long id) {
		Post post = postService.getPost(id);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	/**
	 * Updates an existing post
	 *
	 * @param id ID of the post to update
	 * @param newPostRequest Updated post data
	 * @param currentUser Authenticated user making the request
	 * @return ResponseEntity with the updated post
	 */
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Post> updatePost(
			@PathVariable(name = "id") Long id,
			@Valid @RequestBody PostRequest newPostRequest,
			@CurrentUser UserPrincipal currentUser) {
		Post post = postService.updatePost(id, newPostRequest, currentUser);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	/**
	 * Deletes a post
	 *
	 * @param id ID of the post to delete
	 * @param currentUser Authenticated user making the request
	 * @return ResponseEntity with operation status
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deletePost(
			@PathVariable(name = "id") Long id,
			@CurrentUser UserPrincipal currentUser) {
		ApiResponse apiResponse = postService.deletePost(id, currentUser);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
}
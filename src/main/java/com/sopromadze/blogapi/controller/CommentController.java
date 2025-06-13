package com.sopromadze.blogapi.controller;

import com.sopromadze.blogapi.model.Comment;
import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.CommentRequest;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.security.CurrentUser;
import com.sopromadze.blogapi.security.UserPrincipal;
import com.sopromadze.blogapi.service.CommentService;
import com.sopromadze.blogapi.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for managing comments on blog posts.
 *
 * <p>Provides REST endpoints for CRUD operations on comments associated with specific posts.
 * All endpoints are scoped to a particular post identified by postId.</p>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;

	/**
	 * Retrieves paginated comments for a specific post
	 *
	 * @param postId The ID of the post to get comments for
	 * @param page Page number (default: 0)
	 * @param size Page size (default: 10)
	 * @return ResponseEntity containing paginated comments
	 */
	@GetMapping
	public ResponseEntity<PagedResponse<Comment>> getAllComments(
			@PathVariable(name = "postId") Long postId,
			@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {

		PagedResponse<Comment> allComments = commentService.getAllComments(postId, page, size);
		return new ResponseEntity<>(allComments, HttpStatus.OK);
	}

	/**
	 * Adds a new comment to a post
	 *
	 * @param commentRequest The comment data to add
	 * @param postId The ID of the post to comment on
	 * @param currentUser The authenticated user making the comment
	 * @return ResponseEntity with the created comment
	 */
	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Comment> addComment(
			@Valid @RequestBody CommentRequest commentRequest,
			@PathVariable(name = "postId") Long postId,
			@CurrentUser UserPrincipal currentUser) {

		Comment newComment = commentService.addComment(commentRequest, postId, currentUser);
		return new ResponseEntity<>(newComment, HttpStatus.CREATED);
	}

	/**
	 * Retrieves a specific comment
	 *
	 * @param postId The ID of the post the comment belongs to
	 * @param id The ID of the comment to retrieve
	 * @return ResponseEntity with the requested comment
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Comment> getComment(
			@PathVariable(name = "postId") Long postId,
			@PathVariable(name = "id") Long id) {

		Comment comment = commentService.getComment(postId, id);
		return new ResponseEntity<>(comment, HttpStatus.OK);
	}

	/**
	 * Updates an existing comment
	 *
	 * @param postId The ID of the post the comment belongs to
	 * @param id The ID of the comment to update
	 * @param commentRequest The updated comment data
	 * @param currentUser The authenticated user making the request
	 * @return ResponseEntity with the updated comment
	 */
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Comment> updateComment(
			@PathVariable(name = "postId") Long postId,
			@PathVariable(name = "id") Long id,
			@Valid @RequestBody CommentRequest commentRequest,
			@CurrentUser UserPrincipal currentUser) {

		Comment updatedComment = commentService.updateComment(postId, id, commentRequest, currentUser);
		return new ResponseEntity<>(updatedComment, HttpStatus.OK);
	}

	/**
	 * Deletes a comment
	 *
	 * @param postId The ID of the post the comment belongs to
	 * @param id The ID of the comment to delete
	 * @param currentUser The authenticated user making the request
	 * @return ResponseEntity with operation status
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteComment(
			@PathVariable(name = "postId") Long postId,
			@PathVariable(name = "id") Long id,
			@CurrentUser UserPrincipal currentUser) {

		ApiResponse response = commentService.deleteComment(postId, id, currentUser);
		HttpStatus status = response.getSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(response, status);
	}
}
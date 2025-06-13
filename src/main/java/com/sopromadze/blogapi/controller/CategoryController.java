package com.sopromadze.blogapi.controller;

import com.sopromadze.blogapi.exception.UnauthorizedException;
import com.sopromadze.blogapi.model.Category;
import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.security.CurrentUser;
import com.sopromadze.blogapi.security.UserPrincipal;
import com.sopromadze.blogapi.service.CategoryService;
import com.sopromadze.blogapi.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for managing blog post categories.
 *
 * <p>Provides REST endpoints for CRUD operations on blog categories.
 * Handles category creation, retrieval, updating and deletion with proper authorization checks.</p>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * Retrieves all categories with pagination support
	 *
	 * @param page Page number to retrieve (default: 0)
	 * @param size Number of items per page (default: 10)
	 * @return PagedResponse containing the list of categories
	 */
	@GetMapping
	public PagedResponse<Category> getAllCategories(
			@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
		return categoryService.getAllCategories(page, size);
	}

	/**
	 * Creates a new category
	 *
	 * @param category Category data to create
	 * @param currentUser Authenticated user creating the category
	 * @return ResponseEntity with the created category
	 */
	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Category> addCategory(
			@Valid @RequestBody Category category,
			@CurrentUser UserPrincipal currentUser) {
		return categoryService.addCategory(category, currentUser);
	}

	/**
	 * Retrieves a specific category by ID
	 *
	 * @param id ID of the category to retrieve
	 * @return ResponseEntity with the requested category
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategory(
			@PathVariable(name = "id") Long id) {
		return categoryService.getCategory(id);
	}

	/**
	 * Updates an existing category
	 *
	 * @param id ID of the category to update
	 * @param category Updated category data
	 * @param currentUser Authenticated user making the request
	 * @return ResponseEntity with the updated category
	 * @throws UnauthorizedException if user is not authorized to update the category
	 */
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Category> updateCategory(
			@PathVariable(name = "id") Long id,
			@Valid @RequestBody Category category,
			@CurrentUser UserPrincipal currentUser) throws UnauthorizedException {
		return categoryService.updateCategory(id, category, currentUser);
	}

	/**
	 * Deletes a category
	 *
	 * @param id ID of the category to delete
	 * @param currentUser Authenticated user making the request
	 * @return ResponseEntity with operation status
	 * @throws UnauthorizedException if user is not authorized to delete the category
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteCategory(
			@PathVariable(name = "id") Long id,
			@CurrentUser UserPrincipal currentUser) throws UnauthorizedException {
		return categoryService.deleteCategory(id, currentUser);
	}
}
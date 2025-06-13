package com.sopromadze.blogapi.controller;

import com.sopromadze.blogapi.model.Todo;
import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.security.CurrentUser;
import com.sopromadze.blogapi.security.UserPrincipal;
import com.sopromadze.blogapi.service.TodoService;
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
 * REST controller for managing Todo operations.
 *
 * <p>Provides endpoints for creating, reading, updating, and deleting Todo items,
 * as well as marking them as complete/uncomplete. All operations require USER role.</p>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@RestController
@RequestMapping("/api/todos")
public class TodoController {

	@Autowired
	private TodoService todoService;

	/**
	 * Retrieves all todos for the current user with pagination support
	 *
	 * @param currentUser The authenticated user
	 * @param page Page number (default: 0)
	 * @param size Page size (default: 10)
	 * @return ResponseEntity containing paginated todos
	 */
	@GetMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<PagedResponse<Todo>> getAllTodos(
			@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {

		PagedResponse<Todo> response = todoService.getAllTodos(currentUser, page, size);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Creates a new todo item
	 *
	 * @param todo The todo item to create
	 * @param currentUser The authenticated user
	 * @return ResponseEntity with the created todo
	 */
	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Todo> addTodo(
			@Valid @RequestBody Todo todo,
			@CurrentUser UserPrincipal currentUser) {

		Todo newTodo = todoService.addTodo(todo, currentUser);
		return new ResponseEntity<>(newTodo, HttpStatus.CREATED);
	}

	/**
	 * Retrieves a specific todo item by ID
	 *
	 * @param id ID of the todo to retrieve
	 * @param currentUser The authenticated user
	 * @return ResponseEntity with the requested todo
	 */
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Todo> getTodo(
			@PathVariable(value = "id") Long id,
			@CurrentUser UserPrincipal currentUser) {

		Todo todo = todoService.getTodo(id, currentUser);
		return new ResponseEntity<>(todo, HttpStatus.OK);
	}

	/**
	 * Updates an existing todo item
	 *
	 * @param id ID of the todo to update
	 * @param newTodo Updated todo data
	 * @param currentUser The authenticated user
	 * @return ResponseEntity with the updated todo
	 */
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Todo> updateTodo(
			@PathVariable(value = "id") Long id,
			@Valid @RequestBody Todo newTodo,
			@CurrentUser UserPrincipal currentUser) {

		Todo updatedTodo = todoService.updateTodo(id, newTodo, currentUser);
		return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
	}

	/**
	 * Deletes a todo item
	 *
	 * @param id ID of the todo to delete
	 * @param currentUser The authenticated user
	 * @return ResponseEntity with operation status
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ApiResponse> deleteTodo(
			@PathVariable(value = "id") Long id,
			@CurrentUser UserPrincipal currentUser) {

		ApiResponse apiResponse = todoService.deleteTodo(id, currentUser);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	/**
	 * Marks a todo item as complete
	 *
	 * @param id ID of the todo to complete
	 * @param currentUser The authenticated user
	 * @return ResponseEntity with the completed todo
	 */
	@PutMapping("/{id}/complete")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Todo> completeTodo(
			@PathVariable(value = "id") Long id,
			@CurrentUser UserPrincipal currentUser) {

		Todo todo = todoService.completeTodo(id, currentUser);
		return new ResponseEntity<>(todo, HttpStatus.OK);
	}

	/**
	 * Marks a todo item as uncomplete
	 *
	 * @param id ID of the todo to uncomplete
	 * @param currentUser The authenticated user
	 * @return ResponseEntity with the uncompleted todo
	 */
	@PutMapping("/{id}/unComplete")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Todo> unCompleteTodo(
			@PathVariable(value = "id") Long id,
			@CurrentUser UserPrincipal currentUser) {

		Todo todo = todoService.unCompleteTodo(id, currentUser);
		return new ResponseEntity<>(todo, HttpStatus.OK);
	}
}

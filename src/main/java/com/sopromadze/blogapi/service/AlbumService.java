package com.sopromadze.blogapi.service;

import com.sopromadze.blogapi.model.Album;
import com.sopromadze.blogapi.payload.AlbumResponse;
import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.payload.request.AlbumRequest;
import com.sopromadze.blogapi.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

/**
 * Service interface for managing Album operations.
 *
 * <p>Defines the contract for Album business logic including CRUD operations,
 * pagination support, and user-specific functionality.</p>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
public interface AlbumService {

	/**
	 * Retrieves all albums with pagination support
	 *
	 * @param page Page number (0-based)
	 * @param size Number of items per page
	 * @return PagedResponse containing album data and pagination metadata
	 */
	PagedResponse<AlbumResponse> getAllAlbums(int page, int size);

	/**
	 * Creates a new album for the authenticated user
	 *
	 * @param albumRequest Album data to create
	 * @param currentUser Currently authenticated user
	 * @return ResponseEntity with the created album
	 */
	ResponseEntity<Album> addAlbum(AlbumRequest albumRequest, UserPrincipal currentUser);

	/**
	 * Retrieves a specific album by ID
	 *
	 * @param id Album ID to retrieve
	 * @return ResponseEntity with the requested album
	 */
	ResponseEntity<Album> getAlbum(Long id);

	/**
	 * Updates an existing album
	 *
	 * @param id Album ID to update
	 * @param newAlbum Updated album data
	 * @param currentUser Currently authenticated user
	 * @return ResponseEntity with the updated album
	 */
	ResponseEntity<AlbumResponse> updateAlbum(Long id, AlbumRequest newAlbum, UserPrincipal currentUser);

	/**
	 * Deletes an album
	 *
	 * @param id Album ID to delete
	 * @param currentUser Currently authenticated user
	 * @return ResponseEntity with operation result
	 */
	ResponseEntity<ApiResponse> deleteAlbum(Long id, UserPrincipal currentUser);

	/**
	 * Retrieves albums belonging to a specific user
	 *
	 * @param username Username of the album owner
	 * @param page Page number (0-based)
	 * @param size Number of items per page
	 * @return PagedResponse containing user's albums
	 */
	PagedResponse<Album> getUserAlbums(String username, int page, int size);
}
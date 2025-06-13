package com.sopromadze.blogapi.controller;

import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.payload.PhotoRequest;
import com.sopromadze.blogapi.payload.PhotoResponse;
import com.sopromadze.blogapi.security.CurrentUser;
import com.sopromadze.blogapi.security.UserPrincipal;
import com.sopromadze.blogapi.service.PhotoService;
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
 * @file PhotoController.java
 * @brief Controlador REST para manejar operaciones con fotos
 *
 * @author Hamilton Jumbo
 * @created 12 de junio de 2025
 * @version 1.0
 *
 * @class PhotoController
 * @brief Controlador para gestionar fotos
 *
 * Este controlador proporciona endpoints para operaciones CRUD con fotos,
 * incluyendo permisos basados en roles.
 */
@RestController
@RequestMapping("/api/photos")
public class PhotoController {
	/**
	 * @brief Servicio de fotos inyectado
	 */
	@Autowired
	private PhotoService photoService;

	/**
	 * @brief Obtiene todas las fotos paginadas
	 * @param page Número de página (opcional, por defecto 0)
	 * @param size Tamaño de página (opcional, por defecto 30)
	 * @return PagedResponse<PhotoResponse> Respuesta paginada de fotos
	 */
	@GetMapping
	public PagedResponse<PhotoResponse> getAllPhotos(
			@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
		return photoService.getAllPhotos(page, size);
	}

	/**
	 * @brief Añade una nueva foto
	 * @param photoRequest Datos de la foto a crear
	 * @param currentUser Usuario autenticado
	 * @return ResponseEntity<PhotoResponse> Respuesta con la foto creada
	 * @note Requiere rol USER
	 */
	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<PhotoResponse> addPhoto(@Valid @RequestBody PhotoRequest photoRequest,
												  @CurrentUser UserPrincipal currentUser) {
		PhotoResponse photoResponse = photoService.addPhoto(photoRequest, currentUser);
		return new ResponseEntity<>(photoResponse, HttpStatus.OK);
	}

	/**
	 * @brief Obtiene una foto por ID
	 * @param id ID de la foto a buscar
	 * @return ResponseEntity<PhotoResponse> Respuesta con la foto encontrada
	 */
	@GetMapping("/{id}")
	public ResponseEntity<PhotoResponse> getPhoto(@PathVariable(name = "id") Long id) {
		PhotoResponse photoResponse = photoService.getPhoto(id);
		return new ResponseEntity<>(photoResponse, HttpStatus.OK);
	}

	/**
	 * @brief Actualiza una foto existente
	 * @param id ID de la foto a actualizar
	 * @param photoRequest Nuevos datos de la foto
	 * @param currentUser Usuario autenticado
	 * @return ResponseEntity<PhotoResponse> Respuesta con la foto actualizada
	 * @note Requiere rol USER o ADMIN
	 */
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<PhotoResponse> updatePhoto(@PathVariable(name = "id") Long id,
													 @Valid @RequestBody PhotoRequest photoRequest, @CurrentUser UserPrincipal currentUser) {
		PhotoResponse photoResponse = photoService.updatePhoto(id, photoRequest, currentUser);
		return new ResponseEntity<>(photoResponse, HttpStatus.OK);
	}

	/**
	 * @brief Elimina una foto existente
	 * @param id ID de la foto a eliminar
	 * @param currentUser Usuario autenticado
	 * @return ResponseEntity<ApiResponse> Respuesta con el resultado de la operación
	 * @note Requiere rol USER o ADMIN
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deletePhoto(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
		ApiResponse apiResponse = photoService.deletePhoto(id, currentUser);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
}
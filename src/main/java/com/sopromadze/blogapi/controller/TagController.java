package com.sopromadze.blogapi.controller;

import com.sopromadze.blogapi.model.Tag;
import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.security.CurrentUser;
import com.sopromadze.blogapi.security.UserPrincipal;
import com.sopromadze.blogapi.service.TagService;
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
 * Controlador REST para gestión de etiquetas (tags)
 *
 * <p>Maneja todas las operaciones CRUD para etiquetas del sistema de blog</p>
 *
 * @author Hamilton Jumbo
 * @since 1.0
 * @version 1.4
 * @created 12 de junio de 2025
 */
@RestController
@RequestMapping("/api/tags")
public class TagController {

	/**
	 * Servicio para manejo de operaciones de etiquetas
	 */
	@Autowired
	private TagService tagService;

	/**
	 * Obtiene todas las etiquetas con paginación
	 *
	 * @param page Número de página (por defecto: 0)
	 * @param size Tamaño de página (por defecto: 30)
	 * @return ResponseEntity con respuesta paginada de etiquetas
	 */
	@GetMapping
	public ResponseEntity<PagedResponse<Tag>> getAllTags(
			@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {

		PagedResponse<Tag> response = tagService.getAllTags(page, size);

		return new ResponseEntity< >(response, HttpStatus.OK);
	}

	/**
	 * Agrega una nueva etiqueta al sistema
	 *
	 * @param tag Datos de la nueva etiqueta
	 * @param currentUser Usuario autenticado actual
	 * @return ResponseEntity con la etiqueta creada
	 */
	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Tag> addTag(@Valid @RequestBody Tag tag, @CurrentUser UserPrincipal currentUser) {
		Tag newTag = tagService.addTag(tag, currentUser);

		return new ResponseEntity< >(newTag, HttpStatus.CREATED);
	}

	/**
	 * Obtiene una etiqueta específica por su ID
	 *
	 * @param id Identificador único de la etiqueta
	 * @return ResponseEntity con la etiqueta solicitada
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Tag> getTag(@PathVariable(name = "id") Long id) {
		Tag tag = tagService.getTag(id);

		return new ResponseEntity< >(tag, HttpStatus.OK);
	}

	/**
	 * Actualiza una etiqueta existente
	 *
	 * @param id Identificador de la etiqueta a actualizar
	 * @param tag Datos actualizados de la etiqueta
	 * @param currentUser Usuario autenticado actual
	 * @return ResponseEntity con la etiqueta actualizada
	 */
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Tag> updateTag(@PathVariable(name = "id") Long id, @Valid @RequestBody Tag tag, @CurrentUser UserPrincipal currentUser) {

		Tag updatedTag = tagService.updateTag(id, tag, currentUser);

		return new ResponseEntity< >(updatedTag, HttpStatus.OK);
	}

	/**
	 * Elimina una etiqueta del sistema
	 *
	 * @param id Identificador de la etiqueta a eliminar
	 * @param currentUser Usuario autenticado actual
	 * @return ResponseEntity con confirmación de eliminación
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteTag(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
		ApiResponse apiResponse = tagService.deleteTag(id, currentUser);

		return new ResponseEntity< >(apiResponse, HttpStatus.OK);
	}

}
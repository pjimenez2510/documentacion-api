package com.sopromadze.blogapi.service.impl;

import com.sopromadze.blogapi.exception.ResourceNotFoundException;
import com.sopromadze.blogapi.exception.UnauthorizedException;
import com.sopromadze.blogapi.model.Category;
import com.sopromadze.blogapi.model.role.RoleName;
import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.repository.CategoryRepository;
import com.sopromadze.blogapi.security.UserPrincipal;
import com.sopromadze.blogapi.service.CategoryService;
import com.sopromadze.blogapi.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Implementación del servicio de gestión de categorías.
 *
 * <p>Proporciona la lógica de negocio para operaciones CRUD de categorías,
 * incluyendo validación de permisos y soporte para paginación.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Operaciones CRUD completas para categorías</li>
 *   <li>Control de permisos basado en propietario y roles</li>
 *   <li>Soporte para paginación y ordenamiento por fecha de creación</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	/**
	 * Repositorio para operaciones de persistencia de categorías.
	 */
	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * Obtiene todas las categorías del sistema con paginación.
	 *
	 * @param page número de página (base 0)
	 * @param size tamaño de la página
	 * @return respuesta paginada con lista de categorías
	 * @throws BadRequestException si los parámetros de paginación son inválidos
	 */
	@Override
	public PagedResponse<Category> getAllCategories(int page, int size) {
		AppUtils.validatePageNumberAndSize(page, size);

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

		Page<Category> categories = categoryRepository.findAll(pageable);

		List<Category> content = categories.getNumberOfElements() == 0 ? Collections.emptyList() : categories.getContent();

		return new PagedResponse<>(content, categories.getNumber(), categories.getSize(), categories.getTotalElements(),
				categories.getTotalPages(), categories.isLast());
	}

	/**
	 * Obtiene una categoría específica por su identificador.
	 *
	 * @param id identificador único de la categoría
	 * @return respuesta con la categoría solicitada
	 * @throws ResourceNotFoundException si la categoría no existe
	 */
	@Override
	public ResponseEntity<Category> getCategory(Long id) {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	/**
	 * Crea una nueva categoría en el sistema.
	 *
	 * @param category datos de la nueva categoría
	 * @param currentUser usuario que crea la categoría
	 * @return respuesta con la categoría creada y código de estado 201
	 */
	@Override
	public ResponseEntity<Category> addCategory(Category category, UserPrincipal currentUser) {
		Category newCategory = categoryRepository.save(category);
		return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
	}

	/**
	 * Actualiza los datos de una categoría existente.
	 *
	 * @param id identificador único de la categoría a actualizar
	 * @param newCategory datos actualizados de la categoría
	 * @param currentUser usuario que realiza la actualización
	 * @return respuesta con la categoría actualizada
	 * @throws ResourceNotFoundException si la categoría no existe
	 * @throws UnauthorizedException si el usuario no tiene permisos
	 */
	@Override
	public ResponseEntity<Category> updateCategory(Long id, Category newCategory, UserPrincipal currentUser) {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		if (category.getCreatedBy().equals(currentUser.getId()) || currentUser.getAuthorities()
				.contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
			category.setName(newCategory.getName());
			Category updatedCategory = categoryRepository.save(category);
			return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
		}

		throw new UnauthorizedException("You don't have permission to edit this category");
	}

	/**
	 * Elimina una categoría del sistema.
	 *
	 * @param id identificador único de la categoría a eliminar
	 * @param currentUser usuario que realiza la eliminación
	 * @return respuesta de confirmación de la operación
	 * @throws ResourceNotFoundException si la categoría no existe
	 * @throws UnauthorizedException si el usuario no tiene permisos
	 */
	@Override
	public ResponseEntity<ApiResponse> deleteCategory(Long id, UserPrincipal currentUser) {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category", "id", id));
		if (category.getCreatedBy().equals(currentUser.getId()) || currentUser.getAuthorities()
				.contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
			categoryRepository.deleteById(id);
			return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted category"), HttpStatus.OK);
		}
		throw new UnauthorizedException("You don't have permission to delete this category");
	}
}























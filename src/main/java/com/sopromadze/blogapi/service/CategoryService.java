package com.sopromadze.blogapi.service;

import com.sopromadze.blogapi.exception.UnauthorizedException;
import com.sopromadze.blogapi.model.Category;
import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

/**
 * Interfaz de servicio para gestionar operaciones de Categorías.
 *
 * <p>Define el contrato para la lógica de negocio de categorías incluyendo operaciones CRUD,
 * soporte de paginación y funcionalidad específica del usuario.</p>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
public interface CategoryService {

	/**
	 * Obtiene todas las categorías con soporte de paginación
	 *
	 * @param page Número de página (base 0)
	 * @param size Número de elementos por página
	 * @return PagedResponse con los datos de categorías y metadatos de paginación
	 */
	PagedResponse<Category> getAllCategories(int page, int size);

	/**
	 * Obtiene una categoría específica por ID
	 *
	 * @param id ID de la categoría a obtener
	 * @return ResponseEntity con la categoría solicitada
	 */
	ResponseEntity<Category> getCategory(Long id);

	/**
	 * Crea una nueva categoría
	 *
	 * @param category Datos de la categoría a crear
	 * @param currentUser Usuario autenticado actualmente
	 * @return ResponseEntity con la categoría creada
	 */
	ResponseEntity<Category> addCategory(Category category, UserPrincipal currentUser);

	/**
	 * Actualiza una categoría existente
	 *
	 * @param id ID de la categoría a actualizar
	 * @param newCategory Datos actualizados de la categoría
	 * @param currentUser Usuario autenticado actualmente
	 * @return ResponseEntity con la categoría actualizada
	 * @throws UnauthorizedException si el usuario no tiene permisos para actualizar
	 */
	ResponseEntity<Category> updateCategory(Long id, Category newCategory, UserPrincipal currentUser)
			throws UnauthorizedException;

	/**
	 * Elimina una categoría
	 *
	 * @param id ID de la categoría a eliminar
	 * @param currentUser Usuario autenticado actualmente
	 * @return ResponseEntity con el resultado de la operación
	 * @throws UnauthorizedException si el usuario no tiene permisos para eliminar
	 */
	ResponseEntity<ApiResponse> deleteCategory(Long id, UserPrincipal currentUser) throws UnauthorizedException;
}
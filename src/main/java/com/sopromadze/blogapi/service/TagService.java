package com.sopromadze.blogapi.service;

import com.sopromadze.blogapi.model.Tag;
import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.security.UserPrincipal;

/**
 * Interfaz de servicio para gestionar operaciones de Etiquetas (Tag).
 *
 * <p>Define el contrato para la lógica de negocio de etiquetas incluyendo operaciones CRUD,
 * soporte de paginación y funcionalidad específica del usuario.</p>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
public interface TagService {

	/**
	 * Obtiene todas las etiquetas del sistema con paginación
	 *
	 * @param page número de página solicitada
	 * @param size tamaño de página para la paginación
	 * @return respuesta paginada con todas las etiquetas disponibles
	 */
	PagedResponse<Tag> getAllTags(int page, int size);

	/**
	 * Obtiene una etiqueta específica por su identificador
	 *
	 * @param id identificador único de la etiqueta
	 * @return etiqueta solicitada
	 */
	Tag getTag(Long id);

	/**
	 * Agrega una nueva etiqueta al sistema
	 *
	 * @param tag entidad de etiqueta a crear
	 * @param currentUser principal del usuario autenticado que crea la etiqueta
	 * @return etiqueta creada con información actualizada
	 */
	Tag addTag(Tag tag, UserPrincipal currentUser);

	/**
	 * Actualiza una etiqueta existente
	 *
	 * @param id identificador único de la etiqueta a actualizar
	 * @param newTag datos actualizados de la etiqueta
	 * @param currentUser principal del usuario autenticado que realiza la actualización
	 * @return etiqueta actualizada con nueva información
	 */
	Tag updateTag(Long id, Tag newTag, UserPrincipal currentUser);

	/**
	 * Elimina una etiqueta del sistema
	 *
	 * @param id identificador único de la etiqueta a eliminar
	 * @param currentUser principal del usuario autenticado que realiza la eliminación
	 * @return respuesta de la API indicando el resultado de la operación
	 */
	ApiResponse deleteTag(Long id, UserPrincipal currentUser);
}
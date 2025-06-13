package com.sopromadze.blogapi.service;

import com.sopromadze.blogapi.model.Comment;
import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.CommentRequest;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.security.UserPrincipal;

/**
 * Interfaz de servicio para gestionar operaciones de Comentarios.
 *
 * <p>Define el contrato para la lógica de negocio de comentarios incluyendo operaciones CRUD,
 * soporte de paginación y funcionalidad específica asociada a publicaciones.</p>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
public interface CommentService {

	/**
	 * Obtiene todos los comentarios de una publicación específica con soporte de paginación
	 *
	 * @param postId ID de la publicación para obtener sus comentarios
	 * @param page Número de página (base 0)
	 * @param size Número de elementos por página
	 * @return PagedResponse con los comentarios y metadatos de paginación
	 */
	PagedResponse<Comment> getAllComments(Long postId, int page, int size);

	/**
	 * Añade un nuevo comentario a una publicación
	 *
	 * @param commentRequest Datos del comentario a crear
	 * @param postId ID de la publicación a comentar
	 * @param currentUser Usuario autenticado actualmente
	 * @return Comentario creado
	 */
	Comment addComment(CommentRequest commentRequest, Long postId, UserPrincipal currentUser);

	/**
	 * Obtiene un comentario específico por ID
	 *
	 * @param postId ID de la publicación que contiene el comentario
	 * @param id ID del comentario a obtener
	 * @return Comentario solicitado
	 */
	Comment getComment(Long postId, Long id);

	/**
	 * Actualiza un comentario existente
	 *
	 * @param postId ID de la publicación que contiene el comentario
	 * @param id ID del comentario a actualizar
	 * @param commentRequest Datos actualizados del comentario
	 * @param currentUser Usuario autenticado actualmente
	 * @return Comentario actualizado
	 */
	Comment updateComment(Long postId, Long id, CommentRequest commentRequest, UserPrincipal currentUser);

	/**
	 * Elimina un comentario
	 *
	 * @param postId ID de la publicación que contiene el comentario
	 * @param id ID del comentario a eliminar
	 * @param currentUser Usuario autenticado actualmente
	 * @return Respuesta con el resultado de la operación
	 */
	ApiResponse deleteComment(Long postId, Long id, UserPrincipal currentUser);
}
package com.sopromadze.blogapi.service;

import com.sopromadze.blogapi.exception.BadRequestException;
import com.sopromadze.blogapi.exception.ResourceNotFoundException;
import com.sopromadze.blogapi.exception.UnauthorizedException;
import com.sopromadze.blogapi.model.Post;
import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.payload.PostRequest;
import com.sopromadze.blogapi.payload.PostResponse;
import com.sopromadze.blogapi.security.UserPrincipal;

/**
 * Interfaz de servicio para gestionar operaciones de posts.
 *
 * <p>Define el contrato para la lógica de negocio de posts incluyendo operaciones CRUD,
 * soporte de paginación y funcionalidad específica del usuario.</p>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
public interface PostService {

	/**
	 * Obtiene todos los posts con paginación.
	 *
	 * @param page número de página (base 0)
	 * @param size tamaño de la página
	 * @return respuesta paginada con lista de posts
	 */
	PagedResponse<Post> getAllPosts(int page, int size);

	/**
	 * Obtiene todos los posts creados por un usuario específico con paginación.
	 *
	 * @param username nombre de usuario del creador
	 * @param page número de página (base 0)
	 * @param size tamaño de la página
	 * @return respuesta paginada con posts del usuario
	 * @throws ResourceNotFoundException si el usuario no existe
	 */
	PagedResponse<Post> getPostsByCreatedBy(String username, int page, int size);

	/**
	 * Obtiene todos los posts de una categoría específica con paginación.
	 *
	 * @param id identificador único de la categoría
	 * @param page número de página (base 0)
	 * @param size tamaño de la página
	 * @return respuesta paginada con posts de la categoría
	 * @throws ResourceNotFoundException si la categoría no existe
	 */
	PagedResponse<Post> getPostsByCategory(Long id, int page, int size);

	/**
	 * Obtiene todos los posts asociados a una etiqueta específica con paginación.
	 *
	 * @param id identificador único de la etiqueta
	 * @param page número de página (base 0)
	 * @param size tamaño de la página
	 * @return respuesta paginada con posts de la etiqueta
	 * @throws ResourceNotFoundException si la etiqueta no existe
	 */
	PagedResponse<Post> getPostsByTag(Long id, int page, int size);

	/**
	 * Actualiza los datos de un post existente.
	 *
	 * @param id identificador único del post a actualizar
	 * @param newPostRequest datos actualizados del post
	 * @param currentUser usuario que realiza la operación
	 * @return datos del post actualizado
	 * @throws ResourceNotFoundException si el post no existe
	 * @throws UnauthorizedException si el usuario no tiene permisos
	 */
	Post updatePost(Long id, PostRequest newPostRequest, UserPrincipal currentUser);

	/**
	 * Elimina un post del sistema.
	 *
	 * @param id identificador único del post a eliminar
	 * @param currentUser usuario que realiza la eliminación
	 * @return respuesta de confirmación de la operación
	 * @throws ResourceNotFoundException si el post no existe
	 * @throws UnauthorizedException si el usuario no tiene permisos
	 */
	ApiResponse deletePost(Long id, UserPrincipal currentUser);

	/**
	 * Crea un nuevo post en el sistema.
	 *
	 * @param postRequest datos del nuevo post
	 * @param currentUser usuario que crea el post
	 * @return datos del post creado
	 * @throws BadRequestException si los datos son inválidos
	 */
	PostResponse addPost(PostRequest postRequest, UserPrincipal currentUser);

	/**
	 * Obtiene un post específico por su identificador.
	 *
	 * @param id identificador único del post
	 * @return datos del post solicitado
	 * @throws ResourceNotFoundException si el post no existe
	 */
	Post getPost(Long id);

}
package com.sopromadze.blogapi.service;

import com.sopromadze.blogapi.exception.BadRequestException;
import com.sopromadze.blogapi.exception.ResourceNotFoundException;
import com.sopromadze.blogapi.exception.UnauthorizedException;
import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.payload.PhotoRequest;
import com.sopromadze.blogapi.payload.PhotoResponse;
import com.sopromadze.blogapi.security.UserPrincipal;

/**
 * Interfaz de servicio para gestionar operaciones de fotos.
 *
 * <p>Define el contrato para la lógica de negocio de fotos incluyendo operaciones CRUD,
 * soporte de paginación y funcionalidad específica del usuario.</p>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
public interface PhotoService {

	/**
	 * Obtiene todas las fotos con paginación.
	 *
	 * @param page número de página (base 0)
	 * @param size tamaño de la página
	 * @return respuesta paginada con lista de fotos
	 */
	PagedResponse<PhotoResponse> getAllPhotos(int page, int size);

	/**
	 * Obtiene una foto específica por su identificador.
	 *
	 * @param id identificador único de la foto
	 * @return datos de la foto solicitada
	 * @throws ResourceNotFoundException si la foto no existe
	 */
	PhotoResponse getPhoto(Long id);

	/**
	 * Actualiza los datos de una foto existente.
	 *
	 * @param id identificador único de la foto a actualizar
	 * @param photoRequest datos actualizados de la foto
	 * @param currentUser usuario que realiza la operación
	 * @return datos de la foto actualizada
	 * @throws ResourceNotFoundException si la foto no existe
	 * @throws UnauthorizedException si el usuario no tiene permisos
	 */
	PhotoResponse updatePhoto(Long id, PhotoRequest photoRequest, UserPrincipal currentUser);

	/**
	 * Crea una nueva foto en el sistema.
	 *
	 * @param photoRequest datos de la nueva foto
	 * @param currentUser usuario que crea la foto
	 * @return datos de la foto creada
	 * @throws BadRequestException si los datos son inválidos
	 */
	PhotoResponse addPhoto(PhotoRequest photoRequest, UserPrincipal currentUser);

	/**
	 * Elimina una foto del sistema.
	 *
	 * @param id identificador único de la foto a eliminar
	 * @param currentUser usuario que realiza la eliminación
	 * @return respuesta de confirmación de la operación
	 * @throws ResourceNotFoundException si la foto no existe
	 * @throws UnauthorizedException si el usuario no tiene permisos
	 */
	ApiResponse deletePhoto(Long id, UserPrincipal currentUser);

	/**
	 * Obtiene todas las fotos de un álbum específico con paginación.
	 *
	 * @param albumId identificador único del álbum
	 * @param page número de página (base 0)
	 * @param size tamaño de la página
	 * @return respuesta paginada con fotos del álbum
	 * @throws ResourceNotFoundException si el álbum no existe
	 */
	PagedResponse<PhotoResponse> getAllPhotosByAlbum(Long albumId, int page, int size);

}
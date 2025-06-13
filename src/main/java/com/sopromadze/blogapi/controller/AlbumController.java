package com.sopromadze.blogapi.controller;

import com.sopromadze.blogapi.exception.*;
import com.sopromadze.blogapi.model.Album;
import com.sopromadze.blogapi.payload.AlbumResponse;
import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.payload.PhotoResponse;
import com.sopromadze.blogapi.payload.request.AlbumRequest;
import com.sopromadze.blogapi.security.CurrentUser;
import com.sopromadze.blogapi.security.UserPrincipal;
import com.sopromadze.blogapi.service.AlbumService;
import com.sopromadze.blogapi.service.PhotoService;
import com.sopromadze.blogapi.utils.AppConstants;
import com.sopromadze.blogapi.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
 * Controlador REST para la gestión de álbumes en el sistema de blog.
 *
 * <p>Proporciona endpoints para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre los álbumes y gestionar las fotos asociadas a cada álbum.</p>
 *
 * <p>Endpoints disponibles:
 * <ul>
 *   <li>GET /api/albums - Obtener todos los álbumes con paginación</li>
 *   <li>POST /api/albums - Crear un nuevo álbum (requiere autenticación)</li>
 *   <li>GET /api/albums/{id} - Obtener un álbum específico por ID</li>
 *   <li>PUT /api/albums/{id} - Actualizar un álbum existente</li>
 *   <li>DELETE /api/albums/{id} - Eliminar un álbum</li>
 *   <li>GET /api/albums/{id}/photos - Obtener fotos de un álbum específico</li>
 * </ul>
 * </p>
 *
 * @author Hamilton Jumbo
 * @since 1.0
 * @version 1.4
 * @created 12 de junio de 2025
 */
@RestController
@RequestMapping("/api/albums")
public class AlbumController {

	/**
	 * Servicio para operaciones de negocio relacionadas con álbumes
	 *
	 * <p>Inyectado automáticamente por Spring Framework mediante @Autowired</p>
	 */
	@Autowired
	private AlbumService albumService;

	/**
	 * Servicio para operaciones de negocio relacionadas con fotos
	 *
	 * <p>Inyectado automáticamente por Spring Framework mediante @Autowired</p>
	 */
	@Autowired
	private PhotoService photoService;

	/**
	 * Manejador global de excepciones para el controlador
	 *
	 * <p>Captura y procesa las excepciones de tipo ResponseEntityErrorException
	 * que pueden ocurrir durante la ejecución de cualquier endpoint del controlador.</p>
	 *
	 * @param exception La excepción capturada de tipo ResponseEntityErrorException
	 * @return ResponseEntity con la respuesta de error apropiada
	 * @see com.sopromadze.blogapi.exception.ResponseEntityErrorException
	 * @see com.sopromadze.blogapi.payload.ApiResponse
	 */
	@ExceptionHandler(ResponseEntityErrorException.class)
	public ResponseEntity<ApiResponse> handleExceptions(ResponseEntityErrorException exception) {
		return exception.getApiResponse();
	}

	/**
	 * Obtiene todos los álbumes del sistema con paginación
	 *
	 * <p>Endpoint público que retorna una lista paginada de todos los álbumes
	 * disponibles en el sistema. Soporta parámetros de paginación opcionales.</p>
	 *
	 * <p>Parámetros de paginación:
	 * <ul>
	 *   <li>page: Número de página (por defecto: 0)</li>
	 *   <li>size: Tamaño de página (por defecto: 30)</li>
	 * </ul>
	 * </p>
	 *
	 * @param page Número de página a recuperar (opcional, por defecto desde AppConstants.DEFAULT_PAGE_NUMBER)
	 * @param size Cantidad de elementos por página (opcional, por defecto desde AppConstants.DEFAULT_PAGE_SIZE)
	 * @return PagedResponse con la lista de álbumes y metadatos de paginación
	 * @throws BadRequestException si los parámetros de paginación son inválidos
	 * @see com.sopromadze.blogapi.payload.PagedResponse
	 * @see com.sopromadze.blogapi.payload.AlbumResponse
	 * @see com.sopromadze.blogapi.utils.AppConstants
	 */
	@GetMapping
	public PagedResponse<AlbumResponse> getAllAlbums(
			@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
		AppUtils.validatePageNumberAndSize(page, size);

		return albumService.getAllAlbums(page, size);
	}

	/**
	 * Crea un nuevo álbum en el sistema
	 *
	 * <p>Endpoint protegido que permite a usuarios autenticados con rol 'USER'
	 * crear nuevos álbumes. El álbum se asocia automáticamente al usuario actual.</p>
	 *
	 * <p>Seguridad:
	 * <ul>
	 *   <li>Requiere autenticación JWT válida</li>
	 *   <li>Requiere rol 'USER' o superior</li>
	 * </ul>
	 * </p>
	 *
	 * @param albumRequest Datos del álbum a crear (título, descripción, etc.)
	 * @param currentUser Usuario autenticado actual obtenido del contexto de seguridad
	 * @return ResponseEntity con el álbum creado y código de estado HTTP 201
	 * @throws BadRequestException si los datos del álbum son inválidos
	 * @throws UnauthorizedException si el usuario no está autenticado
	 * @throws AccessDeniedException si el usuario no tiene permisos suficientes
	 * @see com.sopromadze.blogapi.security.UserPrincipal
	 * @see com.sopromadze.blogapi.model.Album
	 */
	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Album> addAlbum(@Valid @RequestBody AlbumRequest albumRequest, @CurrentUser UserPrincipal currentUser) {
		return albumService.addAlbum(albumRequest, currentUser);
	}

	/**
	 * Obtiene un álbum específico por su identificador único
	 *
	 * <p>Endpoint público que retorna los detalles completos de un álbum
	 * específico identificado por su ID único.</p>
	 *
	 * @param id Identificador único del álbum a recuperar
	 * @return ResponseEntity con el álbum solicitado y código de estado HTTP 200
	 * @throws ResourceNotFoundException si no existe un álbum con el ID especificado
	 * @throws BadRequestException si el ID proporcionado no es válido
	 * @see com.sopromadze.blogapi.model.Album
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Album> getAlbum(@PathVariable(name = "id") Long id) {
		return albumService.getAlbum(id);
	}

	/**
	 * Actualiza un álbum existente en el sistema
	 *
	 * <p>Endpoint protegido que permite a usuarios autenticados con rol 'USER' o 'ADMIN'
	 * actualizar álbumes existentes. Los usuarios solo pueden actualizar sus propios álbumes,
	 * mientras que los administradores pueden actualizar cualquier álbum.</p>
	 *
	 * <p>Seguridad:
	 * <ul>
	 *   <li>Requiere autenticación JWT válida</li>
	 *   <li>Requiere rol 'USER' (propios álbumes) o 'ADMIN' (cualquier álbum)</li>
	 * </ul>
	 * </p>
	 *
	 * @param id Identificador único del álbum a actualizar
	 * @param newAlbum Nuevos datos del álbum (título, descripción, etc.)
	 * @param currentUser Usuario autenticado actual obtenido del contexto de seguridad
	 * @return ResponseEntity con el álbum actualizado y código de estado HTTP 200
	 * @throws ResourceNotFoundException si no existe un álbum con el ID especificado
	 * @throws UnauthorizedException si el usuario no está autenticado
	 * @throws AccessDeniedException si el usuario no tiene permisos para actualizar el álbum
	 * @throws BadRequestException si los nuevos datos del álbum son inválidos
	 * @see com.sopromadze.blogapi.payload.AlbumResponse
	 * @see com.sopromadze.blogapi.security.UserPrincipal
	 */
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<AlbumResponse> updateAlbum(@PathVariable(name = "id") Long id, @Valid @RequestBody AlbumRequest newAlbum,
													 @CurrentUser UserPrincipal currentUser) {
		return albumService.updateAlbum(id, newAlbum, currentUser);
	}

	/**
	 * Elimina un álbum del sistema
	 *
	 * <p>Endpoint protegido que permite a usuarios autenticados con rol 'USER' o 'ADMIN'
	 * eliminar álbumes. Los usuarios solo pueden eliminar sus propios álbumes,
	 * mientras que los administradores pueden eliminar cualquier álbum.</p>
	 *
	 * <p>Importante: La eliminación de un álbum también eliminará todas las fotos
	 * asociadas a dicho álbum de forma cascada.</p>
	 *
	 * <p>Seguridad:
	 * <ul>
	 *   <li>Requiere autenticación JWT válida</li>
	 *   <li>Requiere rol 'USER' (propios álbumes) o 'ADMIN' (cualquier álbum)</li>
	 * </ul>
	 * </p>
	 *
	 * @param id Identificador único del álbum a eliminar
	 * @param currentUser Usuario autenticado actual obtenido del contexto de seguridad
	 * @return ResponseEntity con mensaje de confirmación y código de estado HTTP 200
	 * @throws ResourceNotFoundException si no existe un álbum con el ID especificado
	 * @throws UnauthorizedException si el usuario no está autenticado
	 * @throws AccessDeniedException si el usuario no tiene permisos para eliminar el álbum
	 * @see com.sopromadze.blogapi.payload.ApiResponse
	 * @see com.sopromadze.blogapi.security.UserPrincipal
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteAlbum(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
		return albumService.deleteAlbum(id, currentUser);
	}

	/**
	 * Obtiene todas las fotos pertenecientes a un álbum específico
	 *
	 * <p>Endpoint público que retorna una lista paginada de todas las fotos
	 * que pertenecen a un álbum específico identificado por su ID.</p>
	 *
	 * <p>Parámetros de paginación:
	 * <ul>
	 *   <li>page: Número de página (por defecto: 0)</li>
	 *   <li>size: Tamaño de página (por defecto: 30)</li>
	 * </ul>
	 * </p>
	 *
	 * @param id Identificador único del álbum del cual obtener las fotos
	 * @param page Número de página a recuperar (opcional, por defecto desde AppConstants.DEFAULT_PAGE_NUMBER)
	 * @param size Cantidad de elementos por página (opcional, por defecto desde AppConstants.DEFAULT_PAGE_SIZE)
	 * @return ResponseEntity con PagedResponse de fotos y código de estado HTTP 200
	 * @throws ResourceNotFoundException si no existe un álbum con el ID especificado
	 * @throws BadRequestException si los parámetros de paginación son inválidos
	 * @see com.sopromadze.blogapi.payload.PagedResponse
	 * @see com.sopromadze.blogapi.payload.PhotoResponse
	 * @see com.sopromadze.blogapi.utils.AppConstants
	 */
	@GetMapping("/{id}/photos")
	public ResponseEntity<PagedResponse<PhotoResponse>> getAllPhotosByAlbum(@PathVariable(name = "id") Long id,
																			@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
																			@RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {

		PagedResponse<PhotoResponse> response = photoService.getAllPhotosByAlbum(id, page, size);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
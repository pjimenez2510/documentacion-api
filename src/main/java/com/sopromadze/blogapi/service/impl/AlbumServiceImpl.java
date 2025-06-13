package com.sopromadze.blogapi.service.impl;

import com.sopromadze.blogapi.exception.BlogapiException;
import com.sopromadze.blogapi.exception.ResourceNotFoundException;
import com.sopromadze.blogapi.model.Album;
import com.sopromadze.blogapi.model.role.RoleName;
import com.sopromadze.blogapi.model.user.User;
import com.sopromadze.blogapi.payload.AlbumResponse;
import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.payload.request.AlbumRequest;
import com.sopromadze.blogapi.repository.AlbumRepository;
import com.sopromadze.blogapi.repository.UserRepository;
import com.sopromadze.blogapi.security.UserPrincipal;
import com.sopromadze.blogapi.service.AlbumService;
import com.sopromadze.blogapi.utils.AppUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.sopromadze.blogapi.utils.AppConstants.ID;

/**
 * Implementación del servicio de gestión de álbumes.
 *
 * <p>Proporciona la lógica de negocio para operaciones CRUD de álbumes,
 * incluyendo validación de permisos y soporte para paginación.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Operaciones CRUD completas para álbumes</li>
 *   <li>Control de permisos basado en propietario y roles</li>
 *   <li>Soporte para paginación y ordenamiento</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Service
public class AlbumServiceImpl implements AlbumService {

	/**
	 * Campo utilizado para ordenamiento por fecha de creación.
	 */
	private static final String CREATED_AT = "createdAt";

	/**
	 * Cadena de texto utilizada para identificar la entidad álbum en mensajes de error.
	 */
	private static final String ALBUM_STR = "Album";

	/**
	 * Mensaje de error para operaciones sin permisos suficientes.
	 */
	private static final String YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION = "You don't have permission to make this operation";

	/**
	 * Repositorio para operaciones de persistencia de álbumes.
	 */
	@Autowired
	private AlbumRepository albumRepository;

	/**
	 * Repositorio para operaciones de persistencia de usuarios.
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * Mapeador de objetos para conversión entre entidades y DTOs.
	 */
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Obtiene todos los álbumes del sistema con paginación.
	 *
	 * @param page número de página (base 0)
	 * @param size tamaño de la página
	 * @return respuesta paginada con lista de álbumes
	 * @throws BadRequestException si los parámetros de paginación son inválidos
	 */
	@Override
	public PagedResponse<AlbumResponse> getAllAlbums(int page, int size) {
		AppUtils.validatePageNumberAndSize(page, size);

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

		Page<Album> albums = albumRepository.findAll(pageable);

		if (albums.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), albums.getNumber(), albums.getSize(), albums.getTotalElements(),
					albums.getTotalPages(), albums.isLast());
		}

		List<AlbumResponse> albumResponses = Arrays.asList(modelMapper.map(albums.getContent(), AlbumResponse[].class));

		return new PagedResponse<>(albumResponses, albums.getNumber(), albums.getSize(), albums.getTotalElements(), albums.getTotalPages(),
				albums.isLast());
	}

	/**
	 * Crea un nuevo álbum en el sistema.
	 *
	 * @param albumRequest datos del nuevo álbum
	 * @param currentUser usuario que crea el álbum
	 * @return respuesta con el álbum creado y código de estado 201
	 * @throws ResourceNotFoundException si el usuario no existe
	 */
	@Override
	public ResponseEntity<Album> addAlbum(AlbumRequest albumRequest, UserPrincipal currentUser) {
		User user = userRepository.getUser(currentUser);

		Album album = new Album();

		modelMapper.map(albumRequest, album);

		album.setUser(user);
		Album newAlbum = albumRepository.save(album);
		return new ResponseEntity<>(newAlbum, HttpStatus.CREATED);
	}

	/**
	 * Obtiene un álbum específico por su identificador.
	 *
	 * @param id identificador único del álbum
	 * @return respuesta con el álbum solicitado
	 * @throws ResourceNotFoundException si el álbum no existe
	 */
	@Override
	public ResponseEntity<Album> getAlbum(Long id) {
		Album album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ALBUM_STR, ID, id));
		return new ResponseEntity<>(album, HttpStatus.OK);
	}

	/**
	 * Actualiza los datos de un álbum existente.
	 *
	 * @param id identificador único del álbum a actualizar
	 * @param newAlbum datos actualizados del álbum
	 * @param currentUser usuario que realiza la actualización
	 * @return respuesta con el álbum actualizado
	 * @throws ResourceNotFoundException si el álbum no existe
	 * @throws BlogapiException si el usuario no tiene permisos
	 */
	@Override
	public ResponseEntity<AlbumResponse> updateAlbum(Long id, AlbumRequest newAlbum, UserPrincipal currentUser) {
		Album album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ALBUM_STR, ID, id));
		User user = userRepository.getUser(currentUser);
		if (album.getUser().getId().equals(user.getId()) || currentUser.getAuthorities()
				.contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
			album.setTitle(newAlbum.getTitle());
			Album updatedAlbum = albumRepository.save(album);

			AlbumResponse albumResponse = new AlbumResponse();

			modelMapper.map(updatedAlbum, albumResponse);

			return new ResponseEntity<>(albumResponse, HttpStatus.OK);
		}

		throw new BlogapiException(HttpStatus.UNAUTHORIZED, YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION);
	}

	/**
	 * Elimina un álbum del sistema.
	 *
	 * @param id identificador único del álbum a eliminar
	 * @param currentUser usuario que realiza la eliminación
	 * @return respuesta de confirmación de la operación
	 * @throws ResourceNotFoundException si el álbum no existe
	 * @throws BlogapiException si el usuario no tiene permisos
	 */
	@Override
	public ResponseEntity<ApiResponse> deleteAlbum(Long id, UserPrincipal currentUser) {
		Album album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ALBUM_STR, ID, id));
		User user = userRepository.getUser(currentUser);
		if (album.getUser().getId().equals(user.getId()) || currentUser.getAuthorities()
				.contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
			albumRepository.deleteById(id);
			return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted album"), HttpStatus.OK);
		}

		throw new BlogapiException(HttpStatus.UNAUTHORIZED, YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION);
	}

	/**
	 * Obtiene todos los álbumes creados por un usuario específico con paginación.
	 *
	 * @param username nombre del usuario propietario de los álbumes
	 * @param page número de página (base 0)
	 * @param size tamaño de la página
	 * @return respuesta paginada con álbumes del usuario
	 * @throws ResourceNotFoundException si el usuario no existe
	 */
	@Override
	public PagedResponse<Album> getUserAlbums(String username, int page, int size) {
		User user = userRepository.getUserByName(username);

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

		Page<Album> albums = albumRepository.findByCreatedBy(user.getId(), pageable);

		List<Album> content = albums.getNumberOfElements() > 0 ? albums.getContent() : Collections.emptyList();

		return new PagedResponse<>(content, albums.getNumber(), albums.getSize(), albums.getTotalElements(), albums.getTotalPages(), albums.isLast());
	}
}
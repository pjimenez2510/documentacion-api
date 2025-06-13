package com.sopromadze.blogapi.controller;

import com.sopromadze.blogapi.model.Album;
import com.sopromadze.blogapi.model.Post;
import com.sopromadze.blogapi.model.user.User;
import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.InfoRequest;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.payload.UserIdentityAvailability;
import com.sopromadze.blogapi.payload.UserProfile;
import com.sopromadze.blogapi.payload.UserSummary;
import com.sopromadze.blogapi.security.CurrentUser;
import com.sopromadze.blogapi.security.UserPrincipal;
import com.sopromadze.blogapi.service.AlbumService;
import com.sopromadze.blogapi.service.PostService;
import com.sopromadze.blogapi.service.UserService;
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
 * Controlador REST para gestión de usuarios
 *
 * <p>Maneja todas las operaciones relacionadas con usuarios incluyendo
 * perfil, posts, álbumes y administración de roles</p>
 *
 * @author Hamilton Jumbo
 * @since 1.0
 * @version 1.4
 * @created 12 de junio de 2025
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

	/**
	 * Servicio para manejo de operaciones de usuarios
	 */
	@Autowired
	private UserService userService;

	/**
	 * Servicio para manejo de operaciones de posts
	 */
	@Autowired
	private PostService postService;

	/**
	 * Servicio para manejo de operaciones de álbumes
	 */
	@Autowired
	private AlbumService albumService;

	/**
	 * Obtiene información del usuario actualmente autenticado
	 *
	 * @param currentUser Usuario autenticado actual
	 * @return ResponseEntity con resumen del usuario actual
	 */
	@GetMapping("/me")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<UserSummary> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
		UserSummary userSummary = userService.getCurrentUser(currentUser);

		return new ResponseEntity< >(userSummary, HttpStatus.OK);
	}

	/**
	 * Verifica la disponibilidad de un nombre de usuario
	 *
	 * @param username Nombre de usuario a verificar
	 * @return ResponseEntity con disponibilidad del username
	 */
	@GetMapping("/checkUsernameAvailability")
	public ResponseEntity<UserIdentityAvailability> checkUsernameAvailability(@RequestParam(value = "username") String username) {
		UserIdentityAvailability userIdentityAvailability = userService.checkUsernameAvailability(username);

		return new ResponseEntity< >(userIdentityAvailability, HttpStatus.OK);
	}

	/**
	 * Verifica la disponibilidad de una dirección de email
	 *
	 * @param email Email a verificar
	 * @return ResponseEntity con disponibilidad del email
	 */
	@GetMapping("/checkEmailAvailability")
	public ResponseEntity<UserIdentityAvailability> checkEmailAvailability(@RequestParam(value = "email") String email) {
		UserIdentityAvailability userIdentityAvailability = userService.checkEmailAvailability(email);
		return new ResponseEntity< >(userIdentityAvailability, HttpStatus.OK);
	}

	/**
	 * Obtiene el perfil público de un usuario
	 *
	 * @param username Nombre de usuario del perfil a obtener
	 * @return ResponseEntity con el perfil del usuario
	 */
	@GetMapping("/{username}/profile")
	public ResponseEntity<UserProfile> getUSerProfile(@PathVariable(value = "username") String username) {
		UserProfile userProfile = userService.getUserProfile(username);

		return new ResponseEntity< >(userProfile, HttpStatus.OK);
	}

	/**
	 * Obtiene los posts creados por un usuario específico
	 *
	 * @param username Nombre del usuario creador de los posts
	 * @param page Número de página (por defecto: 0)
	 * @param size Tamaño de página (por defecto: 30)
	 * @return ResponseEntity con respuesta paginada de posts
	 */
	@GetMapping("/{username}/posts")
	public ResponseEntity<PagedResponse<Post>> getPostsCreatedBy(@PathVariable(value = "username") String username,
																 @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
																 @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
		PagedResponse<Post> response = postService.getPostsByCreatedBy(username, page, size);

		return new ResponseEntity<  >(response, HttpStatus.OK);
	}

	/**
	 * Obtiene los álbumes de un usuario específico
	 *
	 * @param username Nombre del usuario propietario de los álbumes
	 * @param page Número de página (por defecto: 0)
	 * @param size Tamaño de página (por defecto: 30)
	 * @return ResponseEntity con respuesta paginada de álbumes
	 */
	@GetMapping("/{username}/albums")
	public ResponseEntity<PagedResponse<Album>> getUserAlbums(@PathVariable(name = "username") String username,
															  @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
															  @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {

		PagedResponse<Album> response = albumService.getUserAlbums(username, page, size);

		return new ResponseEntity<  >(response, HttpStatus.OK);
	}

	/**
	 * Agrega un nuevo usuario al sistema (solo administradores)
	 *
	 * @param user Datos del nuevo usuario
	 * @return ResponseEntity con el usuario creado
	 */
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		User newUser = userService.addUser(user);

		return new ResponseEntity< >(newUser, HttpStatus.CREATED);
	}

	/**
	 * Actualiza la información de un usuario existente
	 *
	 * @param newUser Datos actualizados del usuario
	 * @param username Nombre de usuario a actualizar
	 * @param currentUser Usuario autenticado actual
	 * @return ResponseEntity con el usuario actualizado
	 */
	@PutMapping("/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<User> updateUser(@Valid @RequestBody User newUser,
										   @PathVariable(value = "username") String username, @CurrentUser UserPrincipal currentUser) {
		User updatedUSer = userService.updateUser(newUser, username, currentUser);

		return new ResponseEntity< >(updatedUSer, HttpStatus.CREATED);
	}

	/**
	 * Elimina un usuario del sistema
	 *
	 * @param username Nombre del usuario a eliminar
	 * @param currentUser Usuario autenticado actual
	 * @return ResponseEntity con confirmación de eliminación
	 */
	@DeleteMapping("/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable(value = "username") String username,
												  @CurrentUser UserPrincipal currentUser) {
		ApiResponse apiResponse = userService.deleteUser(username, currentUser);

		return new ResponseEntity< >(apiResponse, HttpStatus.OK);
	}

	/**
	 * Otorga permisos de administrador a un usuario
	 *
	 * @param username Nombre del usuario que recibirá permisos de admin
	 * @return ResponseEntity con confirmación de la operación
	 */
	@PutMapping("/{username}/giveAdmin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> giveAdmin(@PathVariable(name = "username") String username) {
		ApiResponse apiResponse = userService.giveAdmin(username);

		return new ResponseEntity< >(apiResponse, HttpStatus.OK);
	}

	/**
	 * Remueve permisos de administrador de un usuario
	 *
	 * @param username Nombre del usuario al que se le removerán permisos de admin
	 * @return ResponseEntity con confirmación de la operación
	 */
	@PutMapping("/{username}/takeAdmin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> takeAdmin(@PathVariable(name = "username") String username) {
		ApiResponse apiResponse = userService.removeAdmin(username);

		return new ResponseEntity< >(apiResponse, HttpStatus.OK);
	}

	/**
	 * Establece o actualiza información adicional del usuario
	 *
	 * @param currentUser Usuario autenticado actual
	 * @param infoRequest Información adicional a establecer o actualizar
	 * @return ResponseEntity con el perfil actualizado del usuario
	 */
	@PutMapping("/setOrUpdateInfo")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<UserProfile> setAddress(@CurrentUser UserPrincipal currentUser,
												  @Valid @RequestBody InfoRequest infoRequest) {
		UserProfile userProfile = userService.setOrUpdateInfo(currentUser, infoRequest);

		return new ResponseEntity< >(userProfile, HttpStatus.OK);
	}

}

package com.sopromadze.blogapi.service;

import com.sopromadze.blogapi.model.user.User;
import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.InfoRequest;
import com.sopromadze.blogapi.payload.UserIdentityAvailability;
import com.sopromadze.blogapi.payload.UserProfile;
import com.sopromadze.blogapi.payload.UserSummary;
import com.sopromadze.blogapi.security.UserPrincipal;

/**
 * Interfaz de servicio para gestionar operaciones de Usuario.
 *
 * <p>Define el contrato para la lógica de negocio de usuarios incluyendo operaciones CRUD,
 * soporte de paginación y funcionalidad específica del usuario.</p>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
public interface UserService {

	/**
	 * Obtiene el resumen del usuario autenticado actual
	 *
	 * @param currentUser principal del usuario autenticado
	 * @return resumen con información básica del usuario
	 */
	UserSummary getCurrentUser(UserPrincipal currentUser);

	/**
	 * Verifica la disponibilidad de un nombre de usuario
	 *
	 * @param username nombre de usuario a verificar
	 * @return resultado de disponibilidad del nombre de usuario
	 */
	UserIdentityAvailability checkUsernameAvailability(String username);

	/**
	 * Verifica la disponibilidad de una dirección de correo electrónico
	 *
	 * @param email dirección de correo electrónico a verificar
	 * @return resultado de disponibilidad del email
	 */
	UserIdentityAvailability checkEmailAvailability(String email);

	/**
	 * Obtiene el perfil completo de un usuario por nombre de usuario
	 *
	 * @param username nombre de usuario del perfil a obtener
	 * @return perfil completo del usuario especificado
	 */
	UserProfile getUserProfile(String username);

	/**
	 * Agrega un nuevo usuario al sistema
	 *
	 * @param user entidad de usuario a crear
	 * @return usuario creado con información actualizada
	 */
	User addUser(User user);

	/**
	 * Actualiza la información de un usuario existente
	 *
	 * @param newUser datos actualizados del usuario
	 * @param username nombre de usuario del usuario a actualizar
	 * @param currentUser principal del usuario que realiza la actualización
	 * @return usuario actualizado con nueva información
	 */
	User updateUser(User newUser, String username, UserPrincipal currentUser);

	/**
	 * Elimina un usuario del sistema
	 *
	 * @param username nombre de usuario del usuario a eliminar
	 * @param currentUser principal del usuario que realiza la eliminación
	 * @return respuesta de la API indicando el resultado de la operación
	 */
	ApiResponse deleteUser(String username, UserPrincipal currentUser);

	/**
	 * Concede privilegios de administrador a un usuario
	 *
	 * @param username nombre de usuario al que conceder privilegios
	 * @return respuesta de la API indicando el resultado de la operación
	 */
	ApiResponse giveAdmin(String username);

	/**
	 * Remueve privilegios de administrador de un usuario
	 *
	 * @param username nombre de usuario al que remover privilegios
	 * @return respuesta de la API indicando el resultado de la operación
	 */
	ApiResponse removeAdmin(String username);

	/**
	 * Establece o actualiza la información adicional del perfil de usuario
	 *
	 * @param currentUser principal del usuario autenticado
	 * @param infoRequest solicitud con información adicional del perfil
	 * @return perfil de usuario actualizado con la nueva información
	 */
	UserProfile setOrUpdateInfo(UserPrincipal currentUser, InfoRequest infoRequest);
}
package com.sopromadze.blogapi.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Interfaz de servicio para gestionar operaciones de detalles de usuario personalizados.
 *
 * <p>Define el contrato para la carga de información de usuario para procesos de autenticación
 * y autorización, soportando búsqueda tanto por nombre de usuario/email como por identificador único.</p>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
public interface CustomUserDetailsService {

	/**
	 * Carga los detalles del usuario por nombre de usuario o correo electrónico
	 *
	 * @param usernameOrEmail nombre de usuario o dirección de correo electrónico
	 * @return detalles del usuario para autenticación
	 * @throws UsernameNotFoundException cuando no se encuentra el usuario especificado
	 */
	UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException;

	/**
	 * Carga los detalles del usuario por su identificador único
	 *
	 * @param id identificador único del usuario
	 * @return detalles del usuario para autenticación
	 */
	UserDetails loadUserById(Long id);
}
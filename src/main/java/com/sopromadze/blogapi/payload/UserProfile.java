package com.sopromadze.blogapi.payload;

import com.sopromadze.blogapi.model.user.Address;
import com.sopromadze.blogapi.model.user.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Clase de perfil completo del usuario con información detallada.
 *
 * <p>Representa el perfil completo de un usuario incluyendo datos personales,
 * información de contacto, dirección, detalles de empresa y estadísticas de actividad.
 * Utilizada para mostrar información completa del usuario en perfiles y configuraciones.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Información completa y detallada del usuario</li>
 *   <li>Incluye datos de contacto y ubicación</li>
 *   <li>Estadísticas de actividad y fecha de registro</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

	/**
	 * Identificador único del usuario
	 */
	private Long id;

	/**
	 * Nombre de usuario único en el sistema
	 */
	private String username;

	/**
	 * Nombre propio del usuario
	 */
	private String firstName;

	/**
	 * Apellido del usuario
	 */
	private String lastName;

	/**
	 * Fecha y hora de registro en el sistema
	 */
	private Instant joinedAt;

	/**
	 * Dirección de correo electrónico del usuario
	 */
	private String email;

	/**
	 * Información de dirección física del usuario
	 */
	private Address address;

	/**
	 * Número de teléfono de contacto
	 */
	private String phone;

	/**
	 * Sitio web personal o profesional
	 */
	private String website;

	/**
	 * Información de la empresa donde trabaja el usuario
	 */
	private Company company;

	/**
	 * Número total de publicaciones realizadas por el usuario
	 */
	private Long postCount;
}

package com.sopromadze.blogapi.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Clase de solicitud para el registro de nuevos usuarios en el sistema.
 *
 * <p>Encapsula toda la información necesaria para crear una nueva cuenta de usuario,
 * incluyendo datos personales y credenciales de acceso. Implementa validaciones
 * completas para garantizar la integridad y seguridad de los datos de registro.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Validaciones exhaustivas de longitud y formato</li>
 *   <li>Verificación de formato de correo electrónico</li>
 *   <li>Restricciones de seguridad para contraseñas</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Data
public class SignUpRequest {

	/**
	 * Nombre propio del usuario con validación de longitud
	 */
	@NotBlank
	@Size(min = 4, max = 40)
	private String firstName;

	/**
	 * Apellido del usuario con validación de longitud
	 */
	@NotBlank
	@Size(min = 4, max = 40)
	private String lastName;

	/**
	 * Nombre de usuario único con restricciones de longitud
	 */
	@NotBlank
	@Size(min = 3, max = 15)
	private String username;

	/**
	 * Dirección de correo electrónico con validación de formato
	 */
	@NotBlank
	@Size(max = 40)
	@Email
	private String email;

	/**
	 * Contraseña con requisitos mínimos de seguridad
	 */
	@NotBlank
	@Size(min = 6, max = 20)
	private String password;
}
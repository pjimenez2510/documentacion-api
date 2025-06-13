package com.sopromadze.blogapi.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Clase de solicitud para el proceso de autenticación de usuarios.
 *
 * <p>Encapsula las credenciales necesarias para el inicio de sesión en el sistema,
 * permitiendo autenticación tanto por nombre de usuario como por dirección de correo
 * electrónico. Incluye validaciones para garantizar que los campos requeridos no estén vacíos.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Soporte para login con username o email</li>
 *   <li>Validación automática de campos obligatorios</li>
 *   <li>Estructura simple y segura para credenciales</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Data
public class LoginRequest {

	/**
	 * Nombre de usuario o dirección de correo electrónico para autenticación
	 */
	@NotBlank
	private String usernameOrEmail;

	/**
	 * Contraseña del usuario para verificación de identidad
	 */
	@NotBlank
	private String password;
}
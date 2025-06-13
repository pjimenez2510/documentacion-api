package com.sopromadze.blogapi.payload;

import lombok.Data;

/**
 * Clase de respuesta para autenticación JWT en el sistema.
 *
 * <p>Encapsula la información del token de acceso generado tras una autenticación
 * exitosa, incluyendo el token JWT y su tipo. Proporciona la estructura estándar
 * para respuestas de autenticación basadas en tokens Bearer.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Manejo de tokens JWT para autenticación</li>
 *   <li>Tipo de token predefinido como Bearer</li>
 *   <li>Estructura simple y eficiente para respuestas de login</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Data
public class JwtAuthenticationResponse {

	/**
	 * Token de acceso JWT generado para el usuario autenticado
	 */
	private String accessToken;

	/**
	 * Tipo de token utilizado para la autenticación
	 */
	private String tokenType = "Bearer";

	/**
	 * Constructor para crear una respuesta de autenticación JWT
	 *
	 * @param accessToken token de acceso JWT generado
	 */
	public JwtAuthenticationResponse(String accessToken) {
		this.accessToken = accessToken;
	}
}
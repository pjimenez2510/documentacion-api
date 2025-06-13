package com.sopromadze.blogapi.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Punto de entrada de autenticación JWT para manejo de errores de autorización.
 *
 * <p>Implementa el manejo personalizado de errores de autenticación para endpoints
 * protegidos con JWT, proporcionando respuestas consistentes cuando los usuarios
 * no autorizados intentan acceder a recursos protegidos.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Manejo centralizado de errores de autenticación JWT</li>
 *   <li>Logging detallado de intentos de acceso no autorizados</li>
 *   <li>Respuestas HTTP estandarizadas para errores 401</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/**
	 * Logger para registrar eventos de autenticación fallidos
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

	/**
	 * Maneja los errores de autenticación enviando respuesta de no autorizado
	 *
	 * @param httpServletRequest solicitud HTTP que generó el error de autenticación
	 * @param httpServletResponse respuesta HTTP donde se enviará el error
	 * @param e excepción de autenticación que se produjo
	 * @throws IOException si ocurre un error de E/S durante el envío de la respuesta
	 * @throws ServletException si ocurre un error relacionado con el servlet
	 */
	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e)
			throws IOException, ServletException {
		LOGGER.error("Responding with unauthorized error. Message - {}", e.getMessage());
		httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sorry, You're not authorized to access this resource.");
	}
}
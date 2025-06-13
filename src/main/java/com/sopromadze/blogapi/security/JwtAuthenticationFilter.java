package com.sopromadze.blogapi.security;

import com.sopromadze.blogapi.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtro de autenticación JWT para procesamiento de tokens en cada solicitud.
 *
 * <p>Intercepta todas las solicitudes HTTP para extraer y validar tokens JWT,
 * estableciendo el contexto de seguridad apropiado para usuarios autenticados.
 * Extiende OncePerRequestFilter para garantizar ejecución única por solicitud.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Validación automática de tokens JWT en cada solicitud</li>
 *   <li>Configuración del contexto de seguridad Spring</li>
 *   <li>Manejo robusto de errores de autenticación</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	/**
	 * Logger para registrar eventos del filtro de autenticación
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	/**
	 * Proveedor de tokens JWT para validación y extracción de datos
	 */
	@Autowired
	private JwtTokenProvider tokenProvider;

	/**
	 * Servicio personalizado para cargar detalles de usuario
	 */
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	/**
	 * Procesa la autenticación JWT para cada solicitud HTTP
	 *
	 * @param request solicitud HTTP entrante
	 * @param response respuesta HTTP saliente
	 * @param filterChain cadena de filtros para continuar el procesamiento
	 * @throws ServletException si ocurre un error relacionado con el servlet
	 * @throws IOException si ocurre un error de E/S durante el procesamiento
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = getJwtFromRequest(request);

			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				Long userId = tokenProvider.getUserIdFromJWT(jwt);

				UserDetails userDetails = customUserDetailsService.loadUserById(userId);
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (Exception ex) {
			LOGGER.error("Could not set user authentication in security context", ex);
		}

		filterChain.doFilter(request, response);
	}

	/**
	 * Extrae el token JWT del encabezado Authorization de la solicitud
	 *
	 * @param request solicitud HTTP de la cual extraer el token
	 * @return token JWT sin el prefijo Bearer o null si no existe
	 */
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}
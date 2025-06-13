package com.sopromadze.blogapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Proveedor de tokens JWT para generación, validación y extracción de datos.
 *
 * <p>Componente centralizado para el manejo completo de tokens JWT incluyendo
 * generación con información de usuario autenticado, validación de integridad
 * y extracción segura de datos del payload. Utiliza configuración externa para
 * secreto y tiempo de expiración.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Generación segura de tokens JWT con firma HMAC-SHA512</li>
 *   <li>Validación completa con manejo de múltiples tipos de error</li>
 *   <li>Extracción confiable de identificadores de usuario</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Component
public class JwtTokenProvider {

	/**
	 * Logger para registrar eventos de manejo de tokens JWT
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

	/**
	 * Clave secreta para firmar tokens JWT obtenida de configuración
	 */
	@Value(value = "${app.jwtSecret}")
	private String jwtSecret;

	/**
	 * Tiempo de expiración de tokens en milisegundos obtenido de configuración
	 */
	@Value(value = "${app.jwtExpirationInMs}")
	private int jwtExpirationInMs;

	/**
	 * Genera un token JWT basado en la información de autenticación del usuario
	 *
	 * @param authentication objeto de autenticación con datos del usuario
	 * @return token JWT firmado y con fecha de expiración
	 */
	public String generateToken(Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

		return Jwts.builder()
				.setSubject(Long.toString(userPrincipal.getId()))
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	/**
	 * Extrae el identificador de usuario del token JWT
	 *
	 * @param token token JWT del cual extraer el ID de usuario
	 * @return identificador único del usuario contenido en el token
	 */
	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody();

		return Long.valueOf(claims.getSubject());
	}

	/**
	 * Valida la integridad y vigencia de un token JWT
	 *
	 * @param authToken token JWT a validar
	 * @return true si el token es válido, false en caso contrario
	 */
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			LOGGER.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			LOGGER.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			LOGGER.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			LOGGER.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			LOGGER.error("JWT claims string is empty");
		}
		return false;
	}
}
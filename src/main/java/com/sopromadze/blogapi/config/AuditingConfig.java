package com.sopromadze.blogapi.config;

import com.sopromadze.blogapi.security.UserPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Configuración de auditoría para entidades JPA.
 *
 * <p>Esta clase proporciona la configuración necesaria para habilitar la auditoría automática
 * de entidades que extiendan de {@link UserDateAudit}.</p>
 *
 * <p>Funcionalidades principales:
 * <ul>
 *   <li>Habilita la auditoría JPA con {@link EnableJpaAuditing}</li>
 *   <li>Provee un {@link AuditorAware} basado en Spring Security</li>
 *   <li>Gestiona el ID del usuario actual para campos de auditoría</li>
 * </ul>
 * </p>
 *
 * @author Hamilton Jumbo
 * @since 1.0
 * @version 1.7
 * @created 12 de junio de 2025
 */
@Configuration
@EnableJpaAuditing
public class AuditingConfig {

	/**
	 * Provee el servicio de auditoría basado en Spring Security.
	 *
	 * <p>Este bean permite a Spring Data JPA conocer el usuario actual
	 * para los campos de auditoría automática.</p>
	 *
	 * @return Implementación de {@link AuditorAware} para Long
	 */
	@Bean
	public AuditorAware<Long> auditorProvider() {
		return new SpringSecurityAuditAwareImpl();
	}

	/**
	 * Implementación de {@link AuditorAware} que obtiene el ID del usuario actual
	 * desde el contexto de seguridad de Spring.
	 *
	 * <p>Flujo de funcionamiento:
	 * <ol>
	 *   <li>Obtiene la autenticación actual del contexto de seguridad</li>
	 *   <li>Verifica si el usuario está autenticado correctamente</li>
	 *   <li>Extrae el ID del {@link UserPrincipal}</li>
	 * </ol>
	 * </p>
	 */
	static class SpringSecurityAuditAwareImpl implements AuditorAware<Long> {

		/**
		 * Obtiene el ID del usuario actual para auditoría.
		 *
		 * @return Optional con el ID del usuario o vacío si no está autenticado
		 * @throws ClassCastException si el principal no es del tipo esperado
		 */
		@Override
		public Optional<Long> getCurrentAuditor() {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication == null ||
					!authentication.isAuthenticated() ||
					authentication instanceof AnonymousAuthenticationToken) {
				return Optional.empty();
			}

			UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
			return Optional.ofNullable(userPrincipal.getId());
		}
	}
}

package com.sopromadze.blogapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de Spring MVC para el manejo de CORS (Cross-Origin Resource Sharing).
 *
 * <p>Define las políticas de CORS para permitir que el frontend acceda a la API
 * desde diferentes dominios de forma segura.</p>
 *
 * @author Hamilton Jumbo
 * @since 1.0
 * @version 1.4
 * @created 12 de junio de 2025
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	/**
	 * Orígenes permitidos para las solicitudes CORS.
	 *
	 * <p>Obtenido desde el archivo application.properties mediante @Value</p>
	 */
	@Value("cors.allowedOrings")
	private String allowedOrigins;

	/**
	 * Configura las reglas CORS para todos los endpoints de la aplicación.
	 *
	 * <p>Permite solicitudes desde los orígenes configurados con métodos HTTP
	 * estándar y un tiempo de cache de 1 hora.</p>
	 *
	 * @param registry Registro de configuraciones CORS de Spring
	 */
	public void addCorsMappings(CorsRegistry registry) {
		final long MAX_AGE_SECS = 3600;

		registry.addMapping("/**")
				.allowedOrigins(allowedOrigins)
				.allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowedHeaders("*")
				.maxAge(MAX_AGE_SECS);
	}
}
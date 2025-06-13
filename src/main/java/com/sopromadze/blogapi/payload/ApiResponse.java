package com.sopromadze.blogapi.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
/**
 * Clase de respuesta estándar para las API REST del sistema.
 *
 * <p>Proporciona una estructura uniforme para todas las respuestas de la API, incluyendo
 * indicadores de éxito, mensajes descriptivos y códigos de estado HTTP. Implementa
 * serialización JSON con orden específico de propiedades.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Estructura consistente para respuestas de API</li>
 *   <li>Soporte completo para serialización JSON</li>
 *   <li>Manejo integrado de códigos de estado HTTP</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Data
@JsonPropertyOrder({
		"success",
		"message"
})
public class ApiResponse implements Serializable {

	/**
	 * Identificador de versión para la serialización
	 */
	@JsonIgnore
	private static final long serialVersionUID = 7702134516418120340L;

	/**
	 * Indicador de éxito de la operación
	 */
	@JsonProperty("success")
	private Boolean success;

	/**
	 * Mensaje descriptivo de la respuesta
	 */
	@JsonProperty("message")
	private String message;

	/**
	 * Código de estado HTTP de la respuesta
	 */
	@JsonIgnore
	private HttpStatus status;

	/**
	 * Constructor por defecto
	 */
	public ApiResponse() {

	}

	/**
	 * Constructor con parámetros básicos de respuesta
	 *
	 * @param success indicador de éxito de la operación
	 * @param message mensaje descriptivo de la respuesta
	 */
	public ApiResponse(Boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	/**
	 * Constructor completo con estado HTTP
	 *
	 * @param success indicador de éxito de la operación
	 * @param message mensaje descriptivo de la respuesta
	 * @param httpStatus código de estado HTTP de la respuesta
	 */
	public ApiResponse(Boolean success, String message, HttpStatus httpStatus) {
		this.success = success;
		this.message = message;
		this.status = httpStatus;
	}
}
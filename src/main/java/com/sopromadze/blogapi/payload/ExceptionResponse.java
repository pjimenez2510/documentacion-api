package com.sopromadze.blogapi.payload;

import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase de respuesta para el manejo de excepciones en la API.
 *
 * <p>Proporciona una estructura estandarizada para devolver información detallada
 * sobre errores y excepciones que ocurren durante el procesamiento de solicitudes.
 * Incluye múltiples mensajes de error, códigos de estado y marca temporal automática.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Manejo seguro de colecciones de mensajes de error</li>
 *   <li>Registro automático de timestamp al momento de la excepción</li>
 *   <li>Estructura consistente para respuestas de error</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Data
public class ExceptionResponse {

	/**
	 * Descripción del tipo de error ocurrido
	 */
	private String error;

	/**
	 * Código de estado HTTP asociado al error
	 */
	private Integer status;

	/**
	 * Lista de mensajes detallados del error
	 */
	private List<String> messages;

	/**
	 * Marca temporal del momento en que ocurrió la excepción
	 */
	private Instant timestamp;

	/**
	 * Constructor para crear una respuesta de excepción con información completa
	 *
	 * @param messages lista de mensajes descriptivos del error
	 * @param error descripción del tipo de error
	 * @param status código de estado HTTP del error
	 */
	public ExceptionResponse(List<String> messages, String error, Integer status) {
		setMessages(messages);
		this.error = error;
		this.status = status;
		this.timestamp = Instant.now();
	}

	/**
	 * Obtiene una copia defensiva de la lista de mensajes de error
	 *
	 * @return nueva lista con los mensajes de error o null si no hay mensajes
	 */
	public List<String> getMessages() {
		return messages == null ? null : new ArrayList<>(messages);
	}

	/**
	 * Establece la lista de mensajes de error de forma inmutable
	 *
	 * @param messages lista de mensajes de error a asignar
	 */
	public final void setMessages(List<String> messages) {
		if (messages == null) {
			this.messages = null;
		} else {
			this.messages = Collections.unmodifiableList(messages);
		}
	}
}
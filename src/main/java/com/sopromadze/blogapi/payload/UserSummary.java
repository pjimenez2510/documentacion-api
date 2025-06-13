package com.sopromadze.blogapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase de resumen con información básica del usuario.
 *
 * <p>Proporciona una representación simplificada de los datos del usuario conteniendo
 * únicamente la información esencial para operaciones de consulta rápida y listados.
 * Útil para reducir la transferencia de datos en operaciones que no requieren detalles completos.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Información mínima y esencial del usuario</li>
 *   <li>Optimizado para operaciones de consulta rápida</li>
 *   <li>Constructor completo para inicialización directa</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Data
@AllArgsConstructor
public class UserSummary {

	/**
	 * Identificador único del usuario
	 */
	private Long id;

	/**
	 * Nombre de usuario único en el sistema
	 */
	private String username;

	/**
	 * Nombre propio del usuario
	 */
	private String firstName;

	/**
	 * Apellido del usuario
	 */
	private String lastName;
}
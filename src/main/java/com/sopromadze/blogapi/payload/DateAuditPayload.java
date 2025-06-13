package com.sopromadze.blogapi.payload;

import java.time.Instant;

/**
 * Clase abstracta base para proporcionar funcionalidad de auditoría de fechas en DTOs.
 *
 * <p>Esta clase proporciona campos comunes para el seguimiento de fechas de creación
 * y actualización en objetos de transferencia de datos (DTOs). Está diseñada para
 * ser extendida por otras clases que requieran información de auditoría temporal.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Seguimiento automático de fecha de creación</li>
 *   <li>Seguimiento automático de fecha de actualización</li>
 *   <li>Uso de Instant para precisión temporal</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
public abstract class DateAuditPayload {

	/**
	 * Fecha y hora de creación del registro
	 */
	private Instant createdAt;

	/**
	 * Fecha y hora de la última actualización del registro
	 */
	private Instant updatedAt;

	/**
	 * Obtiene la fecha de creación del registro
	 *
	 * @return Instant representando la fecha de creación
	 */
	public Instant getCreatedAt() {
		return createdAt;
	}

	/**
	 * Establece la fecha de creación del registro
	 *
	 * @param createdAt Fecha de creación a establecer
	 */
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Obtiene la fecha de última actualización del registro
	 *
	 * @return Instant representando la fecha de actualización
	 */
	public Instant getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * Establece la fecha de última actualización del registro
	 *
	 * @param updatedAt Fecha de actualización a establecer
	 */
	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
}
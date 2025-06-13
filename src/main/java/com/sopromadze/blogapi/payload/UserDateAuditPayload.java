package com.sopromadze.blogapi.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Clase abstracta de auditoría que extiende funcionalidad de fecha con información de usuario.
 *
 * <p>Proporciona campos de auditoría adicionales para rastrear qué usuarios específicos
 * crearon y modificaron registros, complementando la información de fecha heredada.
 * Base para entidades que requieren trazabilidad completa de cambios por usuario.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Hereda funcionalidad de auditoría de fechas</li>
 *   <li>Rastrea usuarios responsables de creación y modificación</li>
 *   <li>Base abstracta para clases de auditoría completa</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class UserDateAuditPayload extends DateAuditPayload {

	/**
	 * Identificador del usuario que creó el registro
	 */
	private Long createdBy;

	/**
	 * Identificador del usuario que realizó la última modificación
	 */
	private Long updatedBy;
}

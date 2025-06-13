package com.sopromadze.blogapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase de respuesta para verificar disponibilidad de identidad de usuario.
 *
 * <p>Proporciona información sobre la disponibilidad de identificadores únicos
 * como nombres de usuario o direcciones de correo electrónico durante el proceso
 * de registro o actualización de perfil. Utilizada para validaciones en tiempo real.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Verificación rápida de disponibilidad de identidades</li>
 *   <li>Soporte para validaciones en tiempo real</li>
 *   <li>Estructura simple y eficiente para respuestas booleanas</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Data
@AllArgsConstructor
public class UserIdentityAvailability {

	/**
	 * Indicador de disponibilidad de la identidad consultada
	 */
	private Boolean available;
}
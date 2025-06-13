package com.sopromadze.blogapi.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Clase de solicitud para crear o actualizar comentarios.
 *
 * <p>Encapsula los datos necesarios para las operaciones de comentarios en el sistema,
 * incluyendo validaciones de entrada para garantizar la calidad del contenido.
 * Aplica restricciones de longitud mínima para el cuerpo del comentario.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Validación automática del contenido del comentario</li>
 *   <li>Restricción de longitud mínima de 10 caracteres</li>
 *   <li>Prevención de comentarios vacíos o en blanco</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Data
public class CommentRequest {

	/**
	 * Contenido del comentario con validaciones de longitud y formato
	 */
	@NotBlank
	@Size(min = 10, message = "Comment body must be minimum 10 characters")
	private String body;
}
package com.sopromadze.blogapi.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Clase de transferencia de datos para solicitudes de creación y actualización de fotos.
 *
 * <p>Contiene todos los campos necesarios para crear o modificar una foto en el sistema,
 * incluyendo validaciones de entrada para garantizar la integridad de los datos.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Validación automática de campos obligatorios</li>
 *   <li>Restricciones de longitud mínima para URLs y título</li>
 *   <li>Asociación requerida con un álbum existente</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Data
public class PhotoRequest {

	/**
	 * Título de la foto.
	 */
	@NotBlank
	@Size(min = 3)
	private String title;

	/**
	 * URL de la imagen completa de la foto.
	 */
	@NotBlank
	@Size(min = 10)
	private String url;

	/**
	 * URL de la imagen en miniatura de la foto.
	 */
	@NotBlank
	@Size(min = 10)
	private String thumbnailUrl;

	/**
	 * Identificador único del álbum al que pertenece la foto.
	 */
	@NotNull
	private Long albumId;
}
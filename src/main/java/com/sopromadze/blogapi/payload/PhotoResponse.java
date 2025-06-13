package com.sopromadze.blogapi.payload;

import lombok.Data;

/**
 * Clase de transferencia de datos para respuestas de operaciones con fotos.
 *
 * <p>Representa la información completa de una foto que se devuelve al cliente,
 * incluyendo su identificador único y toda la información asociada.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Contiene todos los datos públicos de una foto</li>
 *   <li>Incluye referencia al álbum contenedor</li>
 *   <li>Optimizada para transferencia de datos hacia el cliente</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Data
public class PhotoResponse {

	/**
	 * Identificador único de la foto.
	 */
	private Long id;

	/**
	 * Título de la foto.
	 */
	private String title;

	/**
	 * URL de la imagen completa de la foto.
	 */
	private String url;

	/**
	 * URL de la imagen en miniatura de la foto.
	 */
	private String thumbnailUrl;

	/**
	 * Identificador único del álbum al que pertenece la foto.
	 */
	private Long albumId;

	/**
	 * Constructor con todos los parámetros de la foto.
	 *
	 * @param id identificador único de la foto
	 * @param title título de la foto
	 * @param url URL de la imagen completa
	 * @param thumbnailUrl URL de la imagen en miniatura
	 * @param albumId identificador del álbum contenedor
	 */
	public PhotoResponse(Long id, String title, String url, String thumbnailUrl, Long albumId) {
		this.id = id;
		this.title = title;
		this.url = url;
		this.thumbnailUrl = thumbnailUrl;
		this.albumId = albumId;
	}

}
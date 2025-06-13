package com.sopromadze.blogapi.payload;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase contenedora para respuestas paginadas de la API.
 *
 * <p>Encapsula una lista de elementos junto con metadatos de paginación,
 * proporcionando información completa sobre el conjunto de resultados paginados.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Soporte genérico para cualquier tipo de contenido</li>
 *   <li>Información detallada de paginación</li>
 *   <li>Protección contra modificaciones externas del contenido</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Data
public class PagedResponse<T> {

	/**
	 * Lista de elementos contenidos en la página actual.
	 */
	private List<T> content;

	/**
	 * Número de la página actual (base 0).
	 */
	private int page;

	/**
	 * Tamaño de la página solicitada.
	 */
	private int size;

	/**
	 * Número total de elementos en todos los resultados.
	 */
	private long totalElements;

	/**
	 * Número total de páginas disponibles.
	 */
	private int totalPages;

	/**
	 * Indica si esta es la última página de resultados.
	 */
	private boolean last;

	/**
	 * Constructor por defecto sin argumentos.
	 */
	public PagedResponse() {

	}

	/**
	 * Constructor con todos los parámetros de paginación.
	 *
	 * @param content lista de elementos de la página
	 * @param page número de la página actual
	 * @param size tamaño de la página
	 * @param totalElements número total de elementos
	 * @param totalPages número total de páginas
	 * @param last indica si es la última página
	 */
	public PagedResponse(List<T> content, int page, int size, long totalElements, int totalPages, boolean last) {
		setContent(content);
		this.page = page;
		this.size = size;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.last = last;
	}

	/**
	 * Obtiene una copia defensiva de la lista de contenido.
	 *
	 * @return nueva lista con los elementos de contenido, o null si no hay contenido
	 */
	public List<T> getContent() {
		return content == null ? null : new ArrayList<>(content);
	}

	/**
	 * Establece el contenido de la página como una lista inmutable.
	 *
	 * @param content lista de elementos a establecer
	 */
	public final void setContent(List<T> content) {
		if (content == null) {
			this.content = null;
		} else {
			this.content = Collections.unmodifiableList(content);
		}
	}

	/**
	 * Verifica si esta es la última página de resultados.
	 *
	 * @return true si es la última página, false en caso contrario
	 */
	public boolean isLast() {
		return last;
	}
}
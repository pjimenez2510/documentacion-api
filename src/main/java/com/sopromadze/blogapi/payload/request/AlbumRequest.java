package com.sopromadze.blogapi.payload.request;

import com.sopromadze.blogapi.model.Photo;
import com.sopromadze.blogapi.model.user.User;
import com.sopromadze.blogapi.payload.UserDateAuditPayload;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase de solicitud para operaciones con álbumes de fotos.
 *
 * <p>Representa una solicitud de datos para crear o actualizar un álbum, extendiendo
 * la funcionalidad de auditoría de usuario y fecha. Encapsula la información necesaria
 * para gestionar álbumes incluyendo título, usuario propietario y colección de fotos.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Hereda funcionalidad de auditoría de fecha y usuario</li>
 *   <li>Manejo seguro de colecciones de fotos con métodos defensivos</li>
 *   <li>Soporte para operaciones CRUD de álbumes</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Data
public class AlbumRequest extends UserDateAuditPayload {

	/**
	 * Identificador único del álbum
	 */
	private Long id;

	/**
	 * Título descriptivo del álbum
	 */
	private String title;

	/**
	 * Usuario propietario del álbum
	 */
	private User user;

	/**
	 * Lista de fotos contenidas en el álbum
	 */
	private List<Photo> photo;

	/**
	 * Obtiene una copia defensiva de la lista de fotos del álbum
	 *
	 * @return nueva lista con las fotos del álbum o null si no hay fotos
	 */
	public List<Photo> getPhoto() {
		return photo == null ? null : new ArrayList<>(photo);
	}

	/**
	 * Establece la lista de fotos del álbum de forma inmutable
	 *
	 * @param photo lista de fotos a asignar al álbum
	 */
	public void setPhoto(List<Photo> photo) {
		if (photo == null) {
			this.photo = null;
		} else {
			this.photo = Collections.unmodifiableList(photo);
		}
	}
}
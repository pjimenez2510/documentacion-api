package com.sopromadze.blogapi.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sopromadze.blogapi.model.Photo;
import com.sopromadze.blogapi.model.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase de respuesta DTO para representar un álbum en las respuestas de la API.
 *
 * <p>Esta clase encapsula la información del álbum que se devuelve al cliente,
 * incluyendo detalles del usuario propietario y las fotos asociadas. Extiende
 * UserDateAuditPayload para incluir información de auditoría de fechas.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Exclusión de campos nulos en la serialización JSON</li>
 *   <li>Copia defensiva para la colección de fotos</li>
 *   <li>Información de auditoría heredada</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(Include.NON_NULL)
public class AlbumResponse extends UserDateAuditPayload {

	/**
	 * Identificador único del álbum
	 */
	private Long id;

	/**
	 * Título del álbum
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
	 * Obtiene las fotos del álbum con copia defensiva
	 *
	 * @return Nueva ArrayList con las fotos del álbum, o null si no hay fotos
	 */
	public List<Photo> getPhoto() {
		return photo == null ? null : new ArrayList<>(photo);
	}

	/**
	 * Establece las fotos del álbum con protección de lista inmutable
	 *
	 * @param photo Lista de fotos a asignar al álbum
	 */
	public void setPhoto(List<Photo> photo) {
		if (photo == null) {
			this.photo = null;
		} else {
			this.photo = Collections.unmodifiableList(photo);
		}
	}
}
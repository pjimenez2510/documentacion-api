package com.sopromadze.blogapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sopromadze.blogapi.model.audit.UserDateAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;


/**
 * Entidad que representa una foto en el sistema de álbumes.
 *
 * <p>Extiende de UserDateAudit para incluir campos de auditoría automáticos
 * (fecha de creación, modificación y usuario relacionado).</p>
 *
 * @author Hamilton Jumbo
 * @since 1.0
 * @version 1.4
 * @created 12 de junio de 2025
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "photos", uniqueConstraints = { @UniqueConstraint(columnNames = { "title" }) })
public class Photo extends UserDateAudit {
	private static final long serialVersionUID = 1L;

	/**
	 * ID único generado automáticamente para la foto
	 *
	 * <p>Estrategia: Generación por identidad de base de datos</p>
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Título de la foto (debe ser único en el sistema)
	 *
	 * <p>Restricciones:
	 * <ul>
	 *   <li>No puede estar vacío (@NotBlank)</li>
	 * </ul>
	 * </p>
	 */
	@NotBlank
	@Column(name = "title")
	private String title;

	/**
	 * URL completa de la imagen original
	 *
	 * <p>Restricciones:
	 * <ul>
	 *   <li>No puede estar vacío (@NotBlank)</li>
	 *   <li>Debe ser una URL válida</li>
	 * </ul>
	 * </p>
	 */
	@NotBlank
	@Column(name = "url")
	private String url;

	/**
	 * URL de la miniatura (thumbnail) de la foto
	 *
	 * <p>Restricciones:
	 * <ul>
	 *   <li>No puede estar vacío (@NotBlank)</li>
	 *   <li>Debe ser una URL válida</li>
	 * </ul>
	 * </p>
	 */
	@NotBlank
	@Column(name = "thumbnail_url")
	private String thumbnailUrl;

	/**
	 * Álbum al que pertenece esta foto (relación ManyToOne)
	 *
	 * <p>Configuración:
	 * <ul>
	 *   <li>Carga perezosa (FetchType.LAZY)</li>
	 *   <li>Mapeado a columna album_id</li>
	 *   <li>Ignorado en serialización JSON (@JsonIgnore)</li>
	 * </ul>
	 * </p>
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "album_id")
	private Album album;

	/**
	 * Constructor completo para crear una nueva foto
	 *
	 * @param title Título único de la foto (no puede estar vacío)
	 * @param url URL de la imagen original (no puede estar vacía)
	 * @param thumbnailUrl URL de la miniatura (no puede estar vacía)
	 * @param album Álbum al que pertenece la foto
	 * @throws IllegalArgumentException si algún parámetro no cumple las validaciones
	 */
	public Photo(@NotBlank String title,
				 @NotBlank String url,
				 @NotBlank String thumbnailUrl,
				 Album album) {
		this.title = title;
		this.url = url;
		this.thumbnailUrl = thumbnailUrl;
		this.album = album;
	}

	/**
	 * Obtiene el álbum al que pertenece la foto
	 *
	 * @return El objeto Album relacionado
	 * @see com.sopromadze.blogapi.model.Album
	 */
	@JsonIgnore
	public Album getAlbum() {
		return album;
	}
}
package com.sopromadze.blogapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sopromadze.blogapi.model.audit.UserDateAudit;
import com.sopromadze.blogapi.model.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @file Album.java
 * @brief Entidad que representa un álbum de fotos en el sistema
 *
 * @author Sopromadze
 * @date Creado el [fecha de creación]
 * @version 1.0
 *
 * @class Album
 * @brief Modelo de álbum que extiende UserDateAudit para auditoría
 *
 * Esta clase representa un álbum de fotos en la aplicación, asociado a un usuario
 * y conteniendo múltiples fotos. Implementa funcionalidades de auditoría automática.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "albums", uniqueConstraints = { @UniqueConstraint(columnNames = { "title" }) })
public class Album extends UserDateAudit {
	private static final long serialVersionUID = 1L;

	/**
	 * @brief Identificador único del álbum
	 * @var id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * @brief Título del álbum
	 * @details Debe ser único en el sistema y no puede estar vacío
	 * @var title
	 */
	@NotBlank
	@Column(name = "title")
	private String title;

	/**
	 * @brief Usuario propietario del álbum
	 * @var user
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	/**
	 * @brief Lista de fotos contenidas en el álbum
	 * @var photo
	 */
	@OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Photo> photo;

	/**
	 * @brief Obtiene el usuario propietario
	 * @return User El usuario propietario del álbum
	 * @note Esta anotación ignora la serialización JSON
	 */
	@JsonIgnore
	public User getUser() {
		return user;
	}

	/**
	 * @brief Obtiene una copia inmutable de la lista de fotos
	 * @return List<Photo> Lista de fotos o null si no hay fotos
	 */
	public List<Photo> getPhoto() {
		return this.photo == null ? null : new ArrayList<>(this.photo);
	}

	/**
	 * @brief Establece la lista de fotos del álbum
	 * @param photo Lista de fotos a establecer
	 * @details Convierte la lista en una lista inmutable
	 */
	public void setPhoto(List<Photo> photo) {
		if (photo == null) {
			this.photo = null;
		} else {
			this.photo = Collections.unmodifiableList(photo);
		}
	}
}
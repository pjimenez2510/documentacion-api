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
 * Entidad que representa un álbum de fotos en el sistema.
 *
 * <p>Extiende de UserDateAudit para obtener campos de auditoría automáticos
 * (fecha creación, modificación y usuario).</p>
 *
 * @author Hamilton Jumbo
 * @since 1.0
 * @version 1.1
 * @created 12 de junio de 2025
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "albums", uniqueConstraints = { @UniqueConstraint(columnNames = { "title" }) })
public class Album extends UserDateAudit {
	private static final long serialVersionUID = 1L;

	/**
	 * ID único generado automáticamente para el álbum
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Título del álbum (debe ser único en el sistema)
	 *
	 * <p>Validación: No puede ser nulo o vacío (@NotBlank)</p>
	 */
	@NotBlank
	@Column(name = "title")
	private String title;

	/**
	 * Usuario propietario del álbum (relación ManyToOne lazy)
	 *
	 * <p>Mapeado a la columna user_id en la base de datos</p>
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	/**
	 * Lista de fotos contenidas en este álbum (relación OneToMany)
	 *
	 * <p>Configuración:
	 * <ul>
	 *   <li>CascadeType.ALL: Operaciones se propagan a las fotos</li>
	 *   <li>orphanRemoval: Fotos sin álbum son eliminadas</li>
	 * </ul>
	 * </p>
	 */
	@OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Photo> photo;

	/**
	 * Obtiene el usuario propietario (con exclusión JSON)
	 *
	 * @return User objeto del propietario
	 * @see com.sopromadze.blogapi.model.user.User
	 */
	@JsonIgnore
	public User getUser() {
		return user;
	}

	/**
	 * Obtiene una copia defensiva de las fotos del álbum
	 *
	 * @return Lista inmutable de fotos o null si no existen
	 * @see com.sopromadze.blogapi.model.Photo
	 */
	public List<Photo> getPhoto() {
		return this.photo == null ? null : new ArrayList<>(this.photo);
	}

	/**
	 * Establece la lista de fotos del álbum
	 *
	 * @param photo Lista de fotos a asignar (se convierte en inmutable internamente)
	 * @throws IllegalArgumentException si la lista contiene fotos no válidas
	 */
	public void setPhoto(List<Photo> photo) {
		if (photo == null) {
			this.photo = null;
		} else {
			this.photo = Collections.unmodifiableList(photo);
		}
	}
}
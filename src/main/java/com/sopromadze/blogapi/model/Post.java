package com.sopromadze.blogapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Entidad que representa una publicación (post) en el sistema de blog.
 *
 * <p>Extiende de UserDateAudit para incluir campos de auditoría automáticos
 * (fecha de creación, modificación y usuario relacionado).</p>
 *
 * <p>Características principales:
 * <ul>
 *   <li>Título único en el sistema</li>
 *   <li>Relaciones con Usuario, Categoría, Comentarios y Tags</li>
 *   <li>Manejo de identidad JSON para evitar referencias circulares</li>
 *   <li>Colecciones inmutables para relaciones</li>
 * </ul>
 * </p>
 *
 * @author Hamilton Jumbo
 * @since 1.0
 * @version 1.5
 * @created 12 de junio de 2025
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "posts", uniqueConstraints = { @UniqueConstraint(columnNames = { "title" }) })
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id"
)
public class Post extends UserDateAudit {
	private static final long serialVersionUID = 1L;

	/**
	 * ID único generado automáticamente para el post
	 *
	 * <p>Estrategia: Generación por identidad de base de datos</p>
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Título del post (debe ser único en el sistema)
	 */
	@Column(name = "title")
	private String title;

	/**
	 * Contenido principal del post
	 */
	@Column(name = "body")
	private String body;

	/**
	 * Usuario autor del post (relación ManyToOne)
	 *
	 * <p>Configuración:
	 * <ul>
	 *   <li>Carga perezosa (FetchType.LAZY)</li>
	 *   <li>Mapeado a columna user_id</li>
	 *   <li>Ignorado en serialización JSON (@JsonIgnore)</li>
	 * </ul>
	 * </p>
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	/**
	 * Categoría a la que pertenece el post (relación ManyToOne)
	 *
	 * <p>Configuración:
	 * <ul>
	 *   <li>Carga perezosa (FetchType.LAZY)</li>
	 *   <li>Mapeado a columna category_id</li>
	 * </ul>
	 * </p>
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	/**
	 * Comentarios asociados al post (relación OneToMany)
	 *
	 * <p>Configuración:
	 * <ul>
	 *   <li>Mapeado por campo "post" en entidad Comment</li>
	 *   <li>CascadeType.ALL: Operaciones se propagan a comentarios</li>
	 *   <li>orphanRemoval: Comentarios sin post son eliminados</li>
	 *   <li>Ignorado en serialización JSON (@JsonIgnore)</li>
	 * </ul>
	 * </p>
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments;

	/**
	 * Tags asociados al post (relación ManyToMany)
	 *
	 * <p>Configuración:
	 * <ul>
	 *   <li>Carga perezosa (FetchType.LAZY)</li>
	 *   <li>Tabla intermedia post_tag</li>
	 *   <li>Columnas: post_id y tag_id</li>
	 * </ul>
	 * </p>
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "post_tag",
			joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
	)
	private List<Tag> tags;

	/**
	 * Obtiene el usuario autor del post
	 *
	 * @return Objeto User relacionado
	 * @see com.sopromadze.blogapi.model.user.User
	 */
	@JsonIgnore
	public User getUser() {
		return user;
	}

	/**
	 * Establece el usuario autor del post
	 *
	 * @param user Objeto User a asignar
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Obtiene una copia inmutable de los comentarios del post
	 *
	 * @return Lista de comentarios o null si no existen
	 * @see com.sopromadze.blogapi.model.Comment
	 */
	public List<Comment> getComments() {
		return comments == null ? null : new ArrayList<>(comments);
	}

	/**
	 * Establece los comentarios del post
	 *
	 * @param comments Lista de comentarios a asignar
	 * @throws IllegalArgumentException si la lista contiene comentarios no válidos
	 */
	public void setComments(List<Comment> comments) {
		if (comments == null) {
			this.comments = null;
		} else {
			this.comments = Collections.unmodifiableList(comments);
		}
	}

	/**
	 * Obtiene una copia inmutable de los tags del post
	 *
	 * @return Lista de tags o null si no existen
	 * @see com.sopromadze.blogapi.model.Tag
	 */
	public List<Tag> getTags() {
		return tags == null ? null : new ArrayList<>(tags);
	}

	/**
	 * Establece los tags del post
	 *
	 * @param tags Lista de tags a asignar
	 * @throws IllegalArgumentException si la lista contiene tags no válidos
	 */
	public void setTags(List<Tag> tags) {
		if (tags == null) {
			this.tags = null;
		} else {
			this.tags = Collections.unmodifiableList(tags);
		}
	}
}
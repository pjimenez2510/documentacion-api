package com.sopromadze.blogapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sopromadze.blogapi.model.audit.UserDateAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Entidad que representa una categoría de posts en el sistema.
 *
 * <p>Extiende de UserDateAudit para incluir campos de auditoría automáticos
 * (fecha de creación, modificación y usuario relacionado).</p>
 *
 * <p>Configuraciones especiales:
 * <ul>
 *   <li>Serialización JSON con manejo de identidad circular</li>
 *   <li>Generación automática de equals/hashCode incluyendo la superclase</li>
 *   <li>Constructor sin argumentos generado automáticamente</li>
 * </ul>
 * </p>
 *
 * @author Hamilton Jumbo
 * @since 1.0
 * @version 1.2
 * @created 12 de junio de 2025
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "categories")
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id"
)
public class Category extends UserDateAudit {
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador único generado automáticamente para la categoría
	 *
	 * <p>Estrategia: Generación por identidad de base de datos</p>
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nombre de la categoría
	 *
	 * <p>Mapeado a la columna "name" en la base de datos</p>
	 */
	@Column(name = "name")
	private String name;

	/**
	 * Lista de posts asociados a esta categoría (relación OneToMany)
	 *
	 * <p>Configuración:
	 * <ul>
	 *   <li>Mapeado por el campo "category" en la entidad Post</li>
	 *   <li>CascadeType.ALL: Operaciones se propagan a los posts</li>
	 *   <li>orphanRemoval: Posts sin categoría son eliminados</li>
	 * </ul>
	 * </p>
	 */
	@OneToMany(
			mappedBy = "category",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<Post> posts;

	/**
	 * Constructor con parámetro para el nombre de categoría
	 *
	 * @param name Nombre de la categoría a crear
	 */
	public Category(String name) {
		super();
		this.name = name;
	}

	/**
	 * Obtiene una copia defensiva de los posts de la categoría
	 *
	 * @return Lista inmutable de posts o null si no existen
	 * @see com.sopromadze.blogapi.model.Post
	 */
	public List<Post> getPosts() {
		return this.posts == null ? null : new ArrayList<>(this.posts);
	}

	/**
	 * Establece la lista de posts para esta categoría
	 *
	 * @param posts Lista de posts a asignar (se convierte en inmutable internamente)
	 * @throws IllegalArgumentException si la lista contiene posts no válidos
	 */
	public void setPosts(List<Post> posts) {
		if (posts == null) {
			this.posts = null;
		} else {
			this.posts = Collections.unmodifiableList(posts);
		}
	}
}

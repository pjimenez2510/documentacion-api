package com.sopromadze.blogapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sopromadze.blogapi.model.audit.UserDateAudit;
import com.sopromadze.blogapi.model.Post;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Entidad que representa una etiqueta (tag) en el sistema de blog.
 *
 * <p>Las etiquetas permiten categorizar y organizar los posts del blog.
 * Extiende de UserDateAudit para incluir campos de auditoría automáticos
 * (fecha de creación, modificación y usuario relacionado).</p>
 *
 * <p>Implementa una relación muchos-a-muchos con Post, donde una etiqueta
 * puede estar asociada a múltiples posts y un post puede tener múltiples etiquetas.</p>
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
@Table(name = "tags")
public class Tag extends UserDateAudit {

	/**
	 * Número de versión para la serialización de la clase
	 */
	private static final long serialVersionUID = -5298707266367331514L;

	/**
	 * ID único generado automáticamente para la etiqueta
	 *
	 * <p>Estrategia: Generación por identidad de base de datos</p>
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nombre de la etiqueta
	 *
	 * <p>Representa el texto que identifica la etiqueta.
	 * Se almacena en la columna 'name' de la tabla 'tags'.</p>
	 */
	@Column(name = "name")
	private String name;

	/**
	 * Lista de posts asociados a esta etiqueta (relación ManyToMany)
	 *
	 * <p>Configuración de la relación:
	 * <ul>
	 *   <li>Carga ansiosa (FetchType.EAGER)</li>
	 *   <li>Tabla intermedia: post_tag</li>
	 *   <li>Columna de enlace: tag_id</li>
	 *   <li>Columna inversa: post_id</li>
	 *   <li>Ignorado en serialización JSON (@JsonIgnore)</li>
	 * </ul>
	 * </p>
	 *
	 * @see com.sopromadze.blogapi.model.Post
	 */
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "post_tag",
			joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"))
	private List<Post> posts;

	/**
	 * Constructor para crear una nueva etiqueta con nombre específico
	 *
	 * <p>Llama al constructor padre (super()) para inicializar los campos
	 * de auditoría heredados de UserDateAudit.</p>
	 *
	 * @param name Nombre de la etiqueta (texto identificativo)
	 * @throws IllegalArgumentException si el name es null o vacío
	 */
	public Tag(String name) {
		super();
		this.name = name;
	}

	/**
	 * Obtiene una copia defensiva de la lista de posts asociados
	 *
	 * <p>Retorna una nueva ArrayList con los posts para evitar
	 * modificaciones externas no controladas de la colección interna.
	 * Si la lista interna es null, retorna null.</p>
	 *
	 * @return Una nueva lista con los posts asociados, o null si no hay posts
	 * @see java.util.ArrayList
	 */
	public List<Post> getPosts() {
		return posts == null ? null : new ArrayList<>(posts);
	}

	/**
	 * Establece la lista de posts asociados de forma segura
	 *
	 * <p>Implementa una copia defensiva para proteger la integridad
	 * de los datos internos:
	 * <ul>
	 *   <li>Si posts es null, establece la lista interna como null</li>
	 *   <li>Si posts no es null, crea una lista inmutable</li>
	 * </ul>
	 * </p>
	 *
	 * @param posts Lista de posts a asociar con esta etiqueta, puede ser null
	 * @see java.util.Collections#unmodifiableList(List)
	 */
	public void setPosts(List<Post> posts) {
		if (posts == null) {
			this.posts = null;
		} else {
			this.posts = Collections.unmodifiableList(posts);
		}
	}
}
package com.sopromadze.blogapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sopromadze.blogapi.model.audit.UserDateAudit;
import com.sopromadze.blogapi.model.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
 * Entidad que representa una tarea (Todo) en el sistema.
 *
 * <p>Extiende de UserDateAudit para incluir campos de auditoría automáticos
 * (fecha de creación, modificación y usuario relacionado).</p>
 *
 * <p>Características principales:
 * <ul>
 *   <li>Título único en el sistema</li>
 *   <li>Estado de completado (true/false)</li>
 *   <li>Relación con el usuario asignado</li>
 * </ul>
 * </p>
 *
 * @author Hamilton Jumbo
 * @since 1.0
 * @version 1.6
 * @created 12 de junio de 2025
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "todos", uniqueConstraints = { @UniqueConstraint(columnNames = { "title" }) })
public class Todo extends UserDateAudit {
	private static final long serialVersionUID = 1L;

	/**
	 * ID único generado automáticamente para la tarea
	 *
	 * <p>Estrategia: Generación por identidad de base de datos</p>
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Título de la tarea (debe ser único en el sistema)
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
	 * Estado de completado de la tarea
	 *
	 * <p>Valores posibles:
	 * <ul>
	 *   <li>true: Tarea completada</li>
	 *   <li>false: Tarea pendiente</li>
	 *   <li>null: Estado no definido</li>
	 * </ul>
	 * </p>
	 */
	@Column(name = "completed")
	private Boolean completed;

	/**
	 * Usuario asignado a la tarea (relación ManyToOne)
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
	 * Obtiene el usuario asignado a la tarea
	 *
	 * @return Objeto User relacionado
	 * @see com.sopromadze.blogapi.model.user.User
	 */
	@JsonIgnore
	public User getUser() {
		return user;
	}
}
package com.sopromadze.blogapi.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sopromadze.blogapi.model.audit.UserDateAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Instant;
/**
 * Entidad que representa una empresa o compañía en el sistema.
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
@Table(name = "company")
public class Company extends UserDateAudit {
	private static final long serialVersionUID = 1L;

	/**
	 * ID único generado automáticamente para la empresa
	 *
	 * <p>Estrategia: Generación por identidad de base de datos</p>
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nombre oficial de la empresa
	 *
	 * <p>Campo que almacena la razón social de la compañía</p>
	 */
	@Column(name = "name")
	private String name;

	/**
	 * Frase publicitaria o eslogan de la empresa
	 *
	 * <p>Campo opcional que contiene la frase característica de marketing</p>
	 */
	@Column(name = "catch_phrase")
	private String catchPhrase;

	/**
	 * Descripción del negocio o actividad comercial
	 *
	 * <p>Campo que describe el giro comercial o área de negocio</p>
	 */
	@Column(name = "bs")
	private String bs;

	/**
	 * Usuario asociado a esta empresa
	 *
	 * <p>Relación OneToOne bidireccional mapeada por el campo 'company' en User</p>
	 */
	@OneToOne(mappedBy = "company")
	private User user;

	/**
	 * Constructor completo para crear una nueva empresa
	 *
	 * @param name Nombre oficial de la empresa
	 * @param catchPhrase Frase publicitaria o eslogan
	 * @param bs Descripción del negocio
	 */
	public Company(String name, String catchPhrase, String bs) {
		this.name = name;
		this.catchPhrase = catchPhrase;
		this.bs = bs;
	}

	/**
	 * Obtiene el ID de la empresa (oculto en serialización JSON)
	 *
	 * @return ID único de la empresa
	 */
	@JsonIgnore
	public Long getId() {
		return id;
	}

	/**
	 * Establece el ID de la empresa
	 *
	 * @param id ID único de la empresa
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el ID del usuario que creó el registro (oculto en JSON)
	 *
	 * @return ID del usuario creador
	 */
	@JsonIgnore
	@Override
	public Long getCreatedBy() {
		return super.getCreatedBy();
	}

	/**
	 * Establece el ID del usuario que creó el registro (oculto en JSON)
	 *
	 * @param createdBy ID del usuario creador
	 */
	@JsonIgnore
	@Override
	public void setCreatedBy(Long createdBy) {
		super.setCreatedBy(createdBy);
	}

	/**
	 * Obtiene el ID del usuario que actualizó el registro (oculto en JSON)
	 *
	 * @return ID del usuario que actualizó
	 */
	@JsonIgnore
	@Override
	public Long getUpdatedBy() {
		return super.getUpdatedBy();
	}

	/**
	 * Establece el ID del usuario que actualizó el registro (oculto en JSON)
	 *
	 * @param updatedBy ID del usuario que actualizó
	 */
	@JsonIgnore
	@Override
	public void setUpdatedBy(Long updatedBy) {
		super.setUpdatedBy(updatedBy);
	}

	/**
	 * Obtiene la fecha de creación del registro (oculto en JSON)
	 *
	 * @return Fecha y hora de creación
	 */
	@JsonIgnore
	@Override
	public Instant getCreatedAt() {
		return super.getCreatedAt();
	}

	/**
	 * Establece la fecha de creación del registro (oculto en JSON)
	 *
	 * @param createdAt Fecha y hora de creación
	 */
	@JsonIgnore
	@Override
	public void setCreatedAt(Instant createdAt) {
		super.setCreatedAt(createdAt);
	}

	/**
	 * Obtiene la fecha de última actualización del registro (oculto en JSON)
	 *
	 * @return Fecha y hora de última actualización
	 */
	@JsonIgnore
	@Override
	public Instant getUpdatedAt() {
		return super.getUpdatedAt();
	}

	/**
	 * Establece la fecha de última actualización del registro (oculto en JSON)
	 *
	 * @param updatedAt Fecha y hora de última actualización
	 */
	@JsonIgnore
	@Override
	public void setUpdatedAt(Instant updatedAt) {
		super.setUpdatedAt(updatedAt);
	}
}
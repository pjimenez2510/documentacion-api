package com.sopromadze.blogapi.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sopromadze.blogapi.model.audit.UserDateAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Instant;
/**
 * Entidad que representa coordenadas geográficas (latitud y longitud).
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
@Table(name = "geo")
public class Geo extends UserDateAudit {
	private static final long serialVersionUID = 1L;

	/**
	 * ID único generado automáticamente para las coordenadas geográficas
	 *
	 * <p>Estrategia: Generación por identidad de base de datos.
	 * Oculto en serialización JSON.</p>
	 */
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Coordenada de latitud
	 *
	 * <p>Representa la posición norte-sur en grados decimales</p>
	 */
	@Column(name = "lat")
	private String lat;

	/**
	 * Coordenada de longitud
	 *
	 * <p>Representa la posición este-oeste en grados decimales</p>
	 */
	@Column(name = "lng")
	private String lng;

	/**
	 * Dirección asociada a estas coordenadas geográficas
	 *
	 * <p>Relación OneToOne bidireccional mapeada por el campo 'geo' en Address</p>
	 */
	@OneToOne(mappedBy = "geo")
	private Address address;

	/**
	 * Constructor para crear nuevas coordenadas geográficas
	 *
	 * @param lat Coordenada de latitud en formato string
	 * @param lng Coordenada de longitud en formato string
	 */
	public Geo(String lat, String lng) {
		this.lat = lat;
		this.lng = lng;
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
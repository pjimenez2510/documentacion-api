package com.sopromadze.blogapi.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sopromadze.blogapi.model.audit.UserDateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Instant;

/**
 * Entidad que representa una dirección física en el sistema.
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
@Table(name = "address")
public class Address extends UserDateAudit {
	private static final long serialVersionUID = 1L;

	/**
	 * ID único generado automáticamente para la dirección
	 *
	 * <p>Estrategia: Generación por identidad de base de datos</p>
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nombre de la calle o avenida
	 *
	 * <p>Campo opcional que especifica la vía principal de la dirección</p>
	 */
	@Column(name = "street")
	private String street;

	/**
	 * Número de suite, apartamento o unidad
	 *
	 * <p>Campo opcional para especificar ubicación específica dentro de un edificio</p>
	 */
	@Column(name = "suite")
	private String suite;

	/**
	 * Nombre de la ciudad
	 *
	 * <p>Campo opcional que especifica la ciudad de la dirección</p>
	 */
	@Column(name = "city")
	private String city;

	/**
	 * Código postal o ZIP
	 *
	 * <p>Campo opcional para el código postal de la dirección</p>
	 */
	@Column(name = "zipcode")
	private String zipcode;

	/**
	 * Coordenadas geográficas asociadas a la dirección
	 *
	 * <p>Relación OneToOne con cascada completa hacia la entidad Geo</p>
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "geo_id")
	private Geo geo;

	/**
	 * Usuario propietario de esta dirección
	 *
	 * <p>Relación OneToOne bidireccional mapeada por el campo 'address' en User</p>
	 */
	@OneToOne(mappedBy = "address")
	private User user;

	/**
	 * Constructor completo para crear una nueva dirección
	 *
	 * @param street Nombre de la calle
	 * @param suite Número de suite o apartamento
	 * @param city Nombre de la ciudad
	 * @param zipcode Código postal
	 * @param geo Coordenadas geográficas asociadas
	 */
	public Address(String street, String suite, String city, String zipcode, Geo geo) {
		this.street = street;
		this.suite = suite;
		this.city = city;
		this.zipcode = zipcode;
		this.geo = geo;
	}

	/**
	 * Obtiene el ID de la dirección (oculto en serialización JSON)
	 *
	 * @return ID único de la dirección
	 */
	@JsonIgnore
	public Long getId() {
		return id;
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
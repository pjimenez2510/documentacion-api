package com.sopromadze.blogapi.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Clase de solicitud para información de perfil y ubicación de usuarios.
 *
 * <p>Encapsula los datos necesarios para crear o actualizar información detallada
 * de usuarios incluyendo dirección física, datos de contacto, ubicación geográfica
 * y detalles de empresa. Aplica validaciones obligatorias para campos esenciales.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Validación obligatoria de datos de dirección básica</li>
 *   <li>Soporte para información empresarial opcional</li>
 *   <li>Integración de coordenadas geográficas para geolocalización</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Data
public class InfoRequest {

	/**
	 * Nombre de la calle de la dirección
	 */
	@NotBlank
	private String street;

	/**
	 * Número de suite, apartamento o unidad
	 */
	@NotBlank
	private String suite;

	/**
	 * Ciudad de residencia
	 */
	@NotBlank
	private String city;

	/**
	 * Código postal de la dirección
	 */
	@NotBlank
	private String zipcode;

	/**
	 * Nombre de la empresa o compañía
	 */
	private String companyName;

	/**
	 * Frase comercial o eslogan de la empresa
	 */
	private String catchPhrase;

	/**
	 * Descripción del negocio o actividad empresarial
	 */
	private String bs;

	/**
	 * Sitio web corporativo o personal
	 */
	private String website;

	/**
	 * Número de teléfono de contacto
	 */
	private String phone;

	/**
	 * Coordenada de latitud geográfica
	 */
	private String lat;

	/**
	 * Coordenada de longitud geográfica
	 */
	private String lng;
}
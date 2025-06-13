package com.sopromadze.blogapi.repository;

import com.sopromadze.blogapi.model.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de datos para la gestión de álbumes.
 *
 * <p>Extiende JpaRepository para proporcionar operaciones CRUD estándar y consultas
 * personalizadas para la entidad Album, incluyendo búsquedas por usuario creador.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Operaciones CRUD heredadas de JpaRepository</li>
 *   <li>Consultas paginadas por usuario creador</li>
 *   <li>Integración automática con Spring Data JPA</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

	/**
	 * Busca todos los álbumes creados por un usuario específico con paginación.
	 *
	 * @param userId identificador único del usuario creador
	 * @param pageable información de paginación y ordenamiento
	 * @return página de álbumes creados por el usuario
	 */
	Page<Album> findByCreatedBy(Long userId, Pageable pageable);
}
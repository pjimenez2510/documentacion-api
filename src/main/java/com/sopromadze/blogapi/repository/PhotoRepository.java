package com.sopromadze.blogapi.repository;

import com.sopromadze.blogapi.model.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de datos para la gestión de fotos.
 *
 * <p>Extiende JpaRepository para proporcionar operaciones CRUD estándar y consultas
 * personalizadas para la entidad Photo, incluyendo búsquedas por álbum asociado.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Operaciones CRUD heredadas de JpaRepository</li>
 *   <li>Consultas paginadas por álbum específico</li>
 *   <li>Integración automática con Spring Data JPA</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

	/**
	 * Busca todas las fotos asociadas a un álbum específico con paginación.
	 *
	 * @param albumId identificador único del álbum
	 * @param pageable información de paginación y ordenamiento
	 * @return página de fotos del álbum especificado
	 */
	Page<Photo> findByAlbumId(Long albumId, Pageable pageable);
}
package com.sopromadze.blogapi.repository;

import com.sopromadze.blogapi.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de datos para la gestión de comentarios.
 *
 * <p>Extiende JpaRepository para proporcionar operaciones CRUD estándar y consultas
 * personalizadas para la entidad Comment, incluyendo búsquedas por post asociado.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Operaciones CRUD heredadas de JpaRepository</li>
 *   <li>Consultas paginadas por post específico</li>
 *   <li>Integración automática con Spring Data JPA</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	/**
	 * Busca todos los comentarios asociados a un post específico con paginación.
	 *
	 * @param postId identificador único del post
	 * @param pageable información de paginación y ordenamiento
	 * @return página de comentarios del post especificado
	 */
	Page<Comment> findByPostId(Long postId, Pageable pageable);
}
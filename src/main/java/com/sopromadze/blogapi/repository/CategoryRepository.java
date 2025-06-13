package com.sopromadze.blogapi.repository;

import com.sopromadze.blogapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de datos para la gestión de categorías.
 *
 * <p>Extiende JpaRepository para proporcionar operaciones CRUD estándar
 * para la entidad Category con integración automática de Spring Data JPA.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Operaciones CRUD heredadas de JpaRepository</li>
 *   <li>Consultas automáticas por campos de la entidad</li>
 *   <li>Integración automática con Spring Data JPA</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
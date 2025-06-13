package com.sopromadze.blogapi.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación personalizada para inyectar el usuario autenticado actual.
 *
 * <p>Proporciona una forma conveniente de obtener el principal del usuario
 * autenticado en métodos de controladores y otros componentes. Simplifica
 * el acceso al contexto de seguridad mediante una anotación limpia y reutilizable.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Inyección automática del usuario autenticado</li>
 *   <li>Simplificación del acceso al contexto de seguridad</li>
 *   <li>Reutilización en múltiples métodos y controladores</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@Target({ ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {
}

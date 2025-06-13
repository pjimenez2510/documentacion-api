package com.sopromadze.blogapi.service;

import com.sopromadze.blogapi.model.Todo;
import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.PagedResponse;
import com.sopromadze.blogapi.security.UserPrincipal;

/**
 * Interfaz de servicio para gestionar operaciones de Tareas (Todo).
 *
 * <p>Define el contrato para la lógica de negocio de tareas incluyendo operaciones CRUD,
 * soporte de paginación y funcionalidad específica del usuario.</p>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
public interface TodoService {

	/**
	 * Marca una tarea como completada
	 *
	 * @param id identificador único de la tarea
	 * @param currentUser principal del usuario autenticado
	 * @return tarea actualizada con estado completado
	 */
	Todo completeTodo(Long id, UserPrincipal currentUser);

	/**
	 * Marca una tarea como no completada
	 *
	 * @param id identificador único de la tarea
	 * @param currentUser principal del usuario autenticado
	 * @return tarea actualizada con estado no completado
	 */
	Todo unCompleteTodo(Long id, UserPrincipal currentUser);

	/**
	 * Obtiene todas las tareas del usuario con paginación
	 *
	 * @param currentUser principal del usuario autenticado
	 * @param page número de página solicitada
	 * @param size tamaño de página para la paginación
	 * @return respuesta paginada con las tareas del usuario
	 */
	PagedResponse<Todo> getAllTodos(UserPrincipal currentUser, int page, int size);

	/**
	 * Agrega una nueva tarea para el usuario autenticado
	 *
	 * @param todo entidad de tarea a crear
	 * @param currentUser principal del usuario autenticado
	 * @return tarea creada con información actualizada
	 */
	Todo addTodo(Todo todo, UserPrincipal currentUser);

	/**
	 * Obtiene una tarea específica por su identificador
	 *
	 * @param id identificador único de la tarea
	 * @param currentUser principal del usuario autenticado
	 * @return tarea solicitada del usuario
	 */
	Todo getTodo(Long id, UserPrincipal currentUser);

	/**
	 * Actualiza una tarea existente del usuario
	 *
	 * @param id identificador único de la tarea a actualizar
	 * @param newTodo datos actualizados de la tarea
	 * @param currentUser principal del usuario autenticado
	 * @return tarea actualizada con nueva información
	 */
	Todo updateTodo(Long id, Todo newTodo, UserPrincipal currentUser);

	/**
	 * Elimina una tarea del usuario autenticado
	 *
	 * @param id identificador único de la tarea a eliminar
	 * @param currentUser principal del usuario autenticado
	 * @return respuesta de la API indicando el resultado de la operación
	 */
	ApiResponse deleteTodo(Long id, UserPrincipal currentUser);
}

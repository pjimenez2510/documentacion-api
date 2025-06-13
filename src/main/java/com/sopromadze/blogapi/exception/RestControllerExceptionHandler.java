package com.sopromadze.blogapi.exception;

import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * Manejador global de excepciones para controladores REST de la aplicación.
 *
 * <p>Centraliza el manejo de excepciones específicas de la aplicación y excepciones de Spring,
 * proporcionando respuestas consistentes y estructuradas para los errores de la API.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Manejo de excepciones personalizadas de la aplicación</li>
 *   <li>Manejo de excepciones de validación de Spring</li>
 *   <li>Formateo consistente de respuestas de error</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@ControllerAdvice
public class RestControllerExceptionHandler {

	/**
	 * Maneja excepciones generales de la aplicación Blog API.
	 *
	 * @param exception excepción específica de la aplicación
	 * @return respuesta con detalles del error y código de estado correspondiente
	 */
	public ResponseEntity<ApiResponse> resolveException(BlogapiException exception) {
		String message = exception.getMessage();
		HttpStatus status = exception.getStatus();

		ApiResponse apiResponse = new ApiResponse();

		apiResponse.setSuccess(Boolean.FALSE);
		apiResponse.setMessage(message);

		return new ResponseEntity<>(apiResponse, status);
	}

	/**
	 * Maneja excepciones de autorización no válida.
	 *
	 * @param exception excepción de autorización
	 * @return respuesta con código de estado 401 UNAUTHORIZED
	 */
	@ExceptionHandler(UnauthorizedException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public ResponseEntity<ApiResponse> resolveException(UnauthorizedException exception) {

		ApiResponse apiResponse = exception.getApiResponse();

		return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Maneja excepciones de solicitudes mal formadas.
	 *
	 * @param exception excepción de solicitud incorrecta
	 * @return respuesta con código de estado 400 BAD_REQUEST
	 */
	@ExceptionHandler(BadRequestException.class)
	@ResponseBody
	public ResponseEntity<ApiResponse> resolveException(BadRequestException exception) {
		ApiResponse apiResponse = exception.getApiResponse();

		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Maneja excepciones de recursos no encontrados.
	 *
	 * @param exception excepción de recurso no encontrado
	 * @return respuesta con código de estado 404 NOT_FOUND
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseBody
	public ResponseEntity<ApiResponse> resolveException(ResourceNotFoundException exception) {
		ApiResponse apiResponse = exception.getApiResponse();

		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}

	/**
	 * Maneja excepciones de acceso denegado.
	 *
	 * @param exception excepción de acceso denegado
	 * @return respuesta con código de estado 403 FORBIDDEN
	 */
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseBody
	public ResponseEntity<ApiResponse> resolveException(AccessDeniedException exception) {
		ApiResponse apiResponse = exception.getApiResponse();

		return new ResponseEntity< >(apiResponse, HttpStatus.FORBIDDEN);
	}

	/**
	 * Maneja excepciones de validación de argumentos de métodos.
	 *
	 * @param ex excepción de validación de argumentos
	 * @return respuesta con detalles de los errores de validación y código 400 BAD_REQUEST
	 */
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponse> resolveException(MethodArgumentNotValidException ex) {
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		List<String> messages = new ArrayList<>(fieldErrors.size());
		for (FieldError error : fieldErrors) {
			messages.add(error.getField() + " - " + error.getDefaultMessage());
		}
		return new ResponseEntity<>(new ExceptionResponse(messages, HttpStatus.BAD_REQUEST.getReasonPhrase(),
				HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Maneja excepciones de tipo de argumento incorrecto en métodos.
	 *
	 * @param ex excepción de tipo de argumento incorrecto
	 * @return respuesta con mensaje de error de tipo y código 400 BAD_REQUEST
	 */
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponse> resolveException(MethodArgumentTypeMismatchException ex) {
		String message = "Parameter '" + ex.getParameter().getParameterName() + "' must be '"
				+ Objects.requireNonNull(ex.getRequiredType()).getSimpleName() + "'";
		List<String> messages = new ArrayList<>(1);
		messages.add(message);
		return new ResponseEntity<>(new ExceptionResponse(messages, HttpStatus.BAD_REQUEST.getReasonPhrase(),
				HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Maneja excepciones de método HTTP no soportado.
	 *
	 * @param ex excepción de método HTTP no soportado
	 * @return respuesta con métodos soportados y código 405 METHOD_NOT_ALLOWED
	 */
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	public ResponseEntity<ExceptionResponse> resolveException(HttpRequestMethodNotSupportedException ex) {
		String message = "Request method '" + ex.getMethod() + "' not supported. List of all supported methods - "
				+ ex.getSupportedHttpMethods();
		List<String> messages = new ArrayList<>(1);
		messages.add(message);

		return new ResponseEntity<>(new ExceptionResponse(messages, HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(),
				HttpStatus.METHOD_NOT_ALLOWED.value()), HttpStatus.METHOD_NOT_ALLOWED);
	}

	/**
	 * Maneja excepciones de mensaje HTTP no legible.
	 *
	 * @param ex excepción de mensaje HTTP no legible
	 * @return respuesta con mensaje de formato JSON inválido y código 400 BAD_REQUEST
	 */
	@ExceptionHandler({ HttpMessageNotReadableException.class })
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponse> resolveException(HttpMessageNotReadableException ex) {
		String message = "Please provide Request Body in valid JSON format";
		List<String> messages = new ArrayList<>(1);
		messages.add(message);
		return new ResponseEntity<>(new ExceptionResponse(messages, HttpStatus.BAD_REQUEST.getReasonPhrase(),
				HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
	}
}
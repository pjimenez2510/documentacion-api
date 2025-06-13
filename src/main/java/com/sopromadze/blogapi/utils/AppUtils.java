package com.sopromadze.blogapi.utils;

import com.sopromadze.blogapi.exception.BlogapiException;
import org.springframework.http.HttpStatus;

/**
 * Utility class containing common application helper methods.
 *
 * <p>This class provides static utility methods for common operations
 * across the blog API application, including validation methods for
 * pagination parameters and other shared functionality.</p>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
public class AppUtils {

	/**
	 * Validates pagination parameters to ensure they are within acceptable ranges.
	 *
	 * <p>This method performs the following validations:</p>
	 * <ul>
	 *   <li>Page number must be greater than or equal to 0</li>
	 *   <li>Size must be greater than or equal to 0</li>
	 *   <li>Size must not exceed the maximum allowed page size</li>
	 * </ul>
	 *
	 * @param page The page number to validate (0-based)
	 * @param size The page size to validate
	 * @throws BlogapiException if any validation fails with BAD_REQUEST status
	 */
	public static void validatePageNumberAndSize(int page, int size) {
		if (page < 0) {
			throw new BlogapiException(HttpStatus.BAD_REQUEST, "Page number cannot be less than zero.");
		}

		if (size < 0) {
			throw new BlogapiException(HttpStatus.BAD_REQUEST, "Size number cannot be less than zero.");
		}

		if (size > AppConstants.MAX_PAGE_SIZE) {
			throw new BlogapiException(HttpStatus.BAD_REQUEST, "Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
		}
	}
}
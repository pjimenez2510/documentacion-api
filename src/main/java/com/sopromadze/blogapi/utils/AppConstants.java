package com.sopromadze.blogapi.utils;


/**
 * Application-wide constants used throughout the Blog API.
 *
 * <p>This class centralizes all constant values used across the application
 * including pagination defaults, entity names, field names, and common messages.
 * All constants are public, static, and final for immutability and global access.</p>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
public class AppConstants {

	/**
	 * Default page number for pagination requests
	 * Used when no page parameter is specified
	 */
	public static final String DEFAULT_PAGE_NUMBER = "0";

	/**
	 * Default page size for pagination requests
	 * Used when no size parameter is specified
	 */
	public static final String DEFAULT_PAGE_SIZE = "30";

	/**
	 * Maximum allowed page size for pagination requests
	 * Prevents excessive resource consumption from large page requests
	 */
	public static final int MAX_PAGE_SIZE = 30;

	/**
	 * Field name constant for createdAt timestamp
	 * Used for sorting and filtering operations
	 */
	public static final String CREATED_AT = "createdAt";

	/**
	 * Field name constant for entity ID
	 * Used for identification and lookup operations
	 */
	public static final String ID = "id";

	/**
	 * Entity name constant for Photo
	 * Used in error messages and logging
	 */
	public static final String PHOTO = "Photo";

	/**
	 * Entity name constant for Album
	 * Used in error messages and logging
	 */
	public static final String ALBUM = "Album";

	/**
	 * Field name constant for username
	 * Used for user identification and authentication
	 */
	public static final String USERNAME = "username";

	/**
	 * Entity name constant for User
	 * Used in error messages and logging
	 */
	public static final String USER = "User";

	/**
	 * Entity name constant for Category
	 * Used in error messages and logging
	 */
	public static final String CATEGORY = "Category";

	/**
	 * Entity name constant for Tag
	 * Used in error messages and logging
	 */
	public static final String TAG = "Tag";

	/**
	 * Entity name constant for Post
	 * Used in error messages and logging
	 */
	public static final String POST = "Post";

	/**
	 * Entity name constant for ToDo
	 * Used in error messages and logging
	 */
	public static final String TODO = "ToDo";

	/**
	 * Standard permission denied error message
	 * Used when users attempt unauthorized operations
	 */
	public static final String YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION = "You don't have permission to make this operation";
}
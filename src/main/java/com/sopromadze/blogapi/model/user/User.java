package com.sopromadze.blogapi.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sopromadze.blogapi.model.Album;
import com.sopromadze.blogapi.model.audit.DateAudit;
import com.sopromadze.blogapi.model.Comment;
import com.sopromadze.blogapi.model.Post;
import com.sopromadze.blogapi.model.role.Role;
import com.sopromadze.blogapi.model.Todo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Entity representing a user in the blog application.
 *
 * <p>This entity manages user information including personal details, authentication credentials,
 * and relationships with other entities such as posts, comments, albums, and todos.
 * Extends DateAudit to include creation and modification timestamps.</p>
 *
 * <p>Key features:</p>
 * <ul>
 *   <li>Unique constraints on username and email</li>
 *   <li>Bidirectional relationships with posts, comments, albums, and todos</li>
 *   <li>Many-to-many relationship with roles for authorization</li>
 *   <li>One-to-one relationships with address and company</li>
 *   <li>Defensive copying for collection getters/setters</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "username" }),
		@UniqueConstraint(columnNames = { "email" })
})
public class User extends DateAudit {
	private static final long serialVersionUID = 1L;

	/**
	 * Primary key for the user entity
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * User's first name
	 * Required field with maximum length of 40 characters
	 */
	@NotBlank
	@Column(name = "first_name")
	@Size(max = 40)
	private String firstName;

	/**
	 * User's last name
	 * Required field with maximum length of 40 characters
	 */
	@NotBlank
	@Column(name = "last_name")
	@Size(max = 40)
	private String lastName;

	/**
	 * Unique username for authentication
	 * Required field with maximum length of 15 characters
	 */
	@NotBlank
	@Column(name = "username")
	@Size(max = 15)
	private String username;

	/**
	 * User's password for authentication
	 * Write-only field that won't be serialized in JSON responses
	 * Required field with maximum length of 100 characters
	 */
	@NotBlank
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Size(max = 100)
	@Column(name = "password")
	private String password;

	/**
	 * User's email address
	 * Natural identifier, unique and required field with maximum length of 40 characters
	 * Must be a valid email format
	 */
	@NotBlank
	@NaturalId
	@Size(max = 40)
	@Column(name = "email")
	@Email
	private String email;

	/**
	 * User's address information
	 * One-to-one relationship with cascade operations and orphan removal
	 */
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "address_id")
	private Address address;

	/**
	 * User's phone number
	 * Optional field
	 */
	@Column(name = "phone")
	private String phone;

	/**
	 * User's website URL
	 * Optional field
	 */
	@Column(name = "website")
	private String website;

	/**
	 * Roles assigned to the user for authorization
	 * Many-to-many relationship with eager fetching
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> roles;

	/**
	 * Todo items created by the user
	 * One-to-many relationship with cascade operations and orphan removal
	 * Excluded from JSON serialization to prevent circular references
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Todo> todos;

	/**
	 * Albums created by the user
	 * One-to-many relationship with cascade operations and orphan removal
	 * Excluded from JSON serialization to prevent circular references
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Album> albums;

	/**
	 * Posts created by the user
	 * One-to-many relationship with cascade operations and orphan removal
	 * Excluded from JSON serialization to prevent circular references
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Post> posts;

	/**
	 * Comments made by the user
	 * One-to-many relationship with cascade operations and orphan removal
	 * Excluded from JSON serialization to prevent circular references
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments;

	/**
	 * Company information associated with the user
	 * One-to-one relationship with cascade operations and orphan removal
	 */
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "company_id")
	private Company company;

	/**
	 * Constructor for creating a new user with basic information
	 *
	 * @param firstName User's first name
	 * @param lastName User's last name
	 * @param username Unique username
	 * @param email User's email address
	 * @param password User's password
	 */
	public User(String firstName, String lastName, String username, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	/**
	 * Gets the user's todos with defensive copying
	 *
	 * @return A new ArrayList containing the user's todos, or null if no todos exist
	 */
	public List<Todo> getTodos() {
		return todos == null ? null : new ArrayList<>(todos);
	}

	/**
	 * Sets the user's todos with immutable list protection
	 *
	 * @param todos List of todos to assign to the user
	 */
	public void setTodos(List<Todo> todos) {
		if (todos == null) {
			this.todos = null;
		} else {
			this.todos = Collections.unmodifiableList(todos);
		}
	}

	/**
	 * Gets the user's albums with defensive copying
	 *
	 * @return A new ArrayList containing the user's albums, or null if no albums exist
	 */
	public List<Album> getAlbums() {
		return albums == null ? null : new ArrayList<>(albums);
	}

	/**
	 * Sets the user's albums with immutable list protection
	 *
	 * @param albums List of albums to assign to the user
	 */
	public void setAlbums(List<Album> albums) {
		if (albums == null) {
			this.albums = null;
		} else {
			this.albums = Collections.unmodifiableList(albums);
		}
	}

	/**
	 * Gets the user's posts with defensive copying
	 *
	 * @return A new ArrayList containing the user's posts, or null if no posts exist
	 */
	public List<Post> getPosts() {
		return posts == null ? null : new ArrayList<>(posts);
	}

	/**
	 * Sets the user's posts with immutable list protection
	 *
	 * @param posts List of posts to assign to the user
	 */
	public void setPosts(List<Post> posts) {
		if (posts == null) {
			this.posts = null;
		} else {
			this.posts = Collections.unmodifiableList(posts);
		}
	}

	/**
	 * Gets the user's roles with defensive copying
	 *
	 * @return A new ArrayList containing the user's roles, or null if no roles exist
	 */
	public List<Role> getRoles() {
		return roles == null ? null : new ArrayList<>(roles);
	}

	/**
	 * Sets the user's roles with immutable list protection
	 *
	 * @param roles List of roles to assign to the user
	 */
	public void setRoles(List<Role> roles) {
		if (roles == null) {
			this.roles = null;
		} else {
			this.roles = Collections.unmodifiableList(roles);
		}
	}

	/**
	 * Gets the user's comments with defensive copying
	 *
	 * @return A new ArrayList containing the user's comments, or null if no comments exist
	 */
	public List<Comment> getComments() {
		return comments == null ? null : new ArrayList<>(comments);
	}

	/**
	 * Sets the user's comments with immutable list protection
	 *
	 * @param comments List of comments to assign to the user
	 */
	public void setComments(List<Comment> comments) {
		if (comments == null) {
			this.comments = null;
		} else {
			this.comments = Collections.unmodifiableList(comments);
		}
	}
}
package com.sopromadze.blogapi.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sopromadze.blogapi.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementación personalizada de UserDetails para autenticación y autorización.
 *
 * <p>Representa el principal de usuario en el contexto de seguridad de Spring,
 * encapsulando información del usuario autenticado incluyendo credenciales,
 * autoridades y datos personales. Proporciona métodos defensivos para el manejo
 * seguro de colecciones de autoridades.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Implementación completa de la interfaz UserDetails</li>
 *   <li>Manejo seguro de autoridades con copias defensivas</li>
 *   <li>Método factory para creación desde entidades User</li>
 * </ul>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
public class UserPrincipal implements UserDetails {

	/**
	 * Identificador de versión para la serialización
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador único del usuario
	 */
	private Long id;

	/**
	 * Nombre propio del usuario
	 */
	private String firstName;

	/**
	 * Apellido del usuario
	 */
	private String lastName;

	/**
	 * Nombre de usuario único en el sistema
	 */
	private String username;

	/**
	 * Dirección de correo electrónico del usuario
	 */
	@JsonIgnore
	private String email;

	/**
	 * Contraseña encriptada del usuario
	 */
	@JsonIgnore
	private String password;

	/**
	 * Colección de autoridades concedidas al usuario
	 */
	private Collection<? extends GrantedAuthority> authorities;

	/**
	 * Constructor completo para crear un principal de usuario
	 *
	 * @param id identificador único del usuario
	 * @param firstName nombre propio del usuario
	 * @param lastName apellido del usuario
	 * @param username nombre de usuario único
	 * @param email dirección de correo electrónico
	 * @param password contraseña encriptada
	 * @param authorities colección de autoridades del usuario
	 */
	public UserPrincipal(Long id, String firstName, String lastName, String username, String email, String password,
						 Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;

		if (authorities == null) {
			this.authorities = null;
		} else {
			this.authorities = new ArrayList<>(authorities);
		}
	}

	/**
	 * Método factory para crear UserPrincipal desde una entidad User
	 *
	 * @param user entidad de usuario desde la cual crear el principal
	 * @return nueva instancia de UserPrincipal con datos del usuario
	 */
	public static UserPrincipal create(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

		return new UserPrincipal(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(),
				user.getEmail(), user.getPassword(), authorities);
	}

	/**
	 * Obtiene el identificador único del usuario
	 *
	 * @return identificador del usuario
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Obtiene la dirección de correo electrónico del usuario
	 *
	 * @return email del usuario
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Obtiene una copia defensiva de las autoridades del usuario
	 *
	 * @return nueva colección con las autoridades concedidas
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities == null ? null : new ArrayList<>(authorities);
	}

	/**
	 * Obtiene la contraseña encriptada del usuario
	 *
	 * @return contraseña del usuario
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * Obtiene el nombre de usuario único
	 *
	 * @return nombre de usuario
	 */
	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * Indica si la cuenta del usuario no ha expirado
	 *
	 * @return true indicando que la cuenta no expira
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * Indica si la cuenta del usuario no está bloqueada
	 *
	 * @return true indicando que la cuenta no se bloquea
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * Indica si las credenciales del usuario no han expirado
	 *
	 * @return true indicando que las credenciales no expiran
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Indica si el usuario está habilitado
	 *
	 * @return true indicando que el usuario está habilitado
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * Compara la igualdad basada en el identificador único
	 *
	 * @param object objeto a comparar
	 * @return true si los objetos tienen el mismo ID
	 */
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		UserPrincipal that = (UserPrincipal) object;
		return Objects.equals(id, that.id);
	}

	/**
	 * Genera código hash basado en el identificador único
	 *
	 * @return código hash del objeto
	 */
	public int hashCode() {
		return Objects.hash(id);
	}

	/**
	 * Obtiene el nombre propio del usuario
	 *
	 * @return nombre del usuario
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Obtiene el apellido del usuario
	 *
	 * @return apellido del usuario
	 */
	public String getLastName() {
		return lastName;
	}
}
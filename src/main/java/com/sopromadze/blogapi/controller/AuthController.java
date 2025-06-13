package com.sopromadze.blogapi.controller;

import com.sopromadze.blogapi.exception.AppException;
import com.sopromadze.blogapi.exception.BlogapiException;
import com.sopromadze.blogapi.model.role.Role;
import com.sopromadze.blogapi.model.role.RoleName;
import com.sopromadze.blogapi.model.user.User;
import com.sopromadze.blogapi.payload.ApiResponse;
import com.sopromadze.blogapi.payload.JwtAuthenticationResponse;
import com.sopromadze.blogapi.payload.LoginRequest;
import com.sopromadze.blogapi.payload.SignUpRequest;
import com.sopromadze.blogapi.repository.RoleRepository;
import com.sopromadze.blogapi.repository.UserRepository;
import com.sopromadze.blogapi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador REST para autenticación y registro de usuarios.
 *
 * <p>Maneja las operaciones de inicio de sesión y registro de nuevos usuarios
 * con autenticación JWT.</p>
 *
 * @author Hamilton Jumbo
 * @since 1.0
 * @version 1.4
 * @created 12 de junio de 2025
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	/**
	 * Mensaje de error constante para rol de usuario no configurado
	 */
	private static final String USER_ROLE_NOT_SET = "User role not set";

	/**
	 * Gestor de autenticación de Spring Security
	 */
	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * Repositorio para operaciones con usuarios
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * Repositorio para operaciones con roles
	 */
	@Autowired
	private RoleRepository roleRepository;

	/**
	 * Codificador de contraseñas
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Proveedor de tokens JWT
	 */
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	/**
	 * Autentica un usuario existente en el sistema.
	 *
	 * <p>Valida las credenciales y retorna un token JWT si son correctas.</p>
	 *
	 * @param loginRequest Datos de inicio de sesión (username/email y password)
	 * @return ResponseEntity con el token JWT de autenticación
	 * @throws BadCredentialsException si las credenciales son incorrectas
	 */
	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtTokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}

	/**
	 * Registra un nuevo usuario en el sistema.
	 *
	 * <p>Crea un nuevo usuario con rol USER por defecto. Si es el primer usuario
	 * registrado, también recibe rol ADMIN.</p>
	 *
	 * @param signUpRequest Datos del nuevo usuario (nombre, email, username, password)
	 * @return ResponseEntity con confirmación de registro exitoso
	 * @throws BlogapiException si el username o email ya existen
	 * @throws AppException si no se pueden asignar los roles
	 */
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
			throw new BlogapiException(HttpStatus.BAD_REQUEST, "Username is already taken");
		}

		if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
			throw new BlogapiException(HttpStatus.BAD_REQUEST, "Email is already taken");
		}

		String firstName = signUpRequest.getFirstName().toLowerCase();

		String lastName = signUpRequest.getLastName().toLowerCase();

		String username = signUpRequest.getUsername().toLowerCase();

		String email = signUpRequest.getEmail().toLowerCase();

		String password = passwordEncoder.encode(signUpRequest.getPassword());

		User user = new User(firstName, lastName, username, email, password);

		List<Role> roles = new ArrayList<>();

		if (userRepository.count() == 0) {
			roles.add(roleRepository.findByName(RoleName.ROLE_USER)
					.orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
			roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN)
					.orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
		} else {
			roles.add(roleRepository.findByName(RoleName.ROLE_USER)
					.orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
		}

		user.setRoles(roles);

		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{userId}")
				.buildAndExpand(result.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(Boolean.TRUE, "User registered successfully"));
	}
}
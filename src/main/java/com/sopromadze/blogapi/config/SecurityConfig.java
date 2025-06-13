package com.sopromadze.blogapi.config;

import com.sopromadze.blogapi.repository.UserRepository;
import com.sopromadze.blogapi.security.JwtAuthenticationEntryPoint;
import com.sopromadze.blogapi.security.JwtAuthenticationFilter;
import com.sopromadze.blogapi.service.impl.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/**
 * Configuración principal de seguridad para la aplicación.
 *
 * <p>Esta clase configura:
 * <ul>
 *   <li>Autenticación basada en JWT</li>
 *   <li>Autorización de endpoints</li>
 *   <li>Políticas de CORS y CSRF</li>
 *   <li>Manejo de excepciones de seguridad</li>
 * </ul>
 * </p>
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CustomUserDetailsServiceImpl customUserDetailsService;
	private final JwtAuthenticationEntryPoint unauthorizedHandler;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	/**
	 * Constructor para inyección de dependencias
	 *
	 * @param customUserDetailsService Servicio de detalles de usuario personalizado
	 * @param unauthorizedHandler Manejador de excepciones de autenticación
	 * @param jwtAuthenticationFilter Filtro de autenticación JWT
	 */
	@Autowired
	public SecurityConfig(CustomUserDetailsServiceImpl customUserDetailsService,
						  JwtAuthenticationEntryPoint unauthorizedHandler,
						  JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.customUserDetailsService = customUserDetailsService;
		this.unauthorizedHandler = unauthorizedHandler;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	/**
	 * Configuración de seguridad HTTP
	 *
	 * @param http Configuración de seguridad HTTP
	 * @throws Exception Si ocurre un error en la configuración
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors().and()
				.csrf().disable()
				.exceptionHandling()
				.authenticationEntryPoint(unauthorizedHandler)
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/**").permitAll()
				.antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/users/checkUsernameAvailability",
						"/api/users/checkEmailAvailability").permitAll()
				.anyRequest().authenticated();

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	/**
	 * Configura el gestor de autenticación
	 *
	 * @param authenticationManagerBuilder Constructor del gestor de autenticación
	 * @throws Exception Si ocurre un error en la configuración
	 */
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
				.userDetailsService(customUserDetailsService)
				.passwordEncoder(passwordEncoder());
	}

	/**
	 * Expone el gestor de autenticación como bean
	 *
	 * @return Gestor de autenticación
	 * @throws Exception Si ocurre un error al obtener el gestor
	 */
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * Configura el codificador de contraseñas
	 *
	 * @return Instancia de BCryptPasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
package com.sopromadze.blogapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sopromadze.blogapi.model.audit.UserDateAudit;
import com.sopromadze.blogapi.model.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Entidad que representa un comentario en el sistema de blog.
 *
 * <p>Extiende de UserDateAudit para incluir campos de auditoría automáticos
 * (fecha de creación, modificación y usuario relacionado).</p>
 *
 * @author Hamilton Jumbo
 * @since 1.0
 * @version 1.3
 * @created 12 de junio de 2025
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends UserDateAudit {
    private static final long serialVersionUID = 1L;

    /**
     * ID único generado automáticamente para el comentario
     *
     * <p>Estrategia: Generación por identidad de base de datos</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del autor del comentario
     *
     * <p>Restricciones:
     * <ul>
     *   <li>No puede estar vacío (@NotBlank)</li>
     *   <li>Entre 4 y 50 caracteres (@Size)</li>
     * </ul>
     * </p>
     */
    @Column(name = "name")
    @NotBlank
    @Size(min = 4, max = 50)
    private String name;

    /**
     * Email del autor del comentario
     *
     * <p>Validaciones:
     * <ul>
     *   <li>Formato de email válido (@Email)</li>
     *   <li>No puede estar vacío (@NotBlank)</li>
     *   <li>Entre 4 y 50 caracteres (@Size)</li>
     * </ul>
     * </p>
     */
    @Column(name = "email")
    @NotBlank
    @Email
    @Size(min = 4, max = 50)
    private String email;

    /**
     * Contenido principal del comentario
     *
     * <p>Requisitos:
     * <ul>
     *   <li>No puede estar vacío (@NotBlank)</li>
     *   <li>Mínimo 10 caracteres (@Size)</li>
     * </ul>
     * </p>
     */
    @Column(name = "body")
    @NotBlank
    @Size(min = 10, message = "El cuerpo del comentario debe tener al menos 10 caracteres")
    private String body;

    /**
     * Post al que pertenece este comentario (relación ManyToOne)
     *
     * <p>Configuración:
     * <ul>
     *   <li>Carga perezosa (FetchType.LAZY)</li>
     *   <li>Mapeado a columna post_id</li>
     *   <li>Ignorado en serialización JSON (@JsonIgnore)</li>
     * </ul>
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * Usuario que realizó el comentario (relación ManyToOne)
     *
     * <p>Configuración:
     * <ul>
     *   <li>Carga perezosa (FetchType.LAZY)</li>
     *   <li>Mapeado a columna user_id</li>
     *   <li>Ignorado en serialización JSON (@JsonIgnore)</li>
     * </ul>
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Constructor con parámetro obligatorio para el cuerpo del comentario
     *
     * @param body Contenido del comentario (validado con @NotBlank y @Size)
     * @throws IllegalArgumentException si el cuerpo no cumple los requisitos
     */
    public Comment(@NotBlank @Size(min = 10) String body) {
        this.body = body;
    }

    /**
     * Obtiene el post asociado al comentario
     *
     * @return El objeto Post relacionado
     * @see com.sopromadze.blogapi.model.Post
     */
    @JsonIgnore
    public Post getPost() {
        return post;
    }

    /**
     * Obtiene el usuario autor del comentario
     *
     * @return El objeto User relacionado
     * @see com.sopromadze.blogapi.model.user.User
     */
    @JsonIgnore
    public User getUser() {
        return user;
    }
}
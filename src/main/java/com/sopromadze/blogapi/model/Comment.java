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
 * @file Comment.java
 * @brief Entidad que representa un comentario en el sistema
 *
 * @author Sopromadze
 * @date Creado el [fecha de creación]
 * @version 1.0
 *
 * @class Comment
 * @brief Modelo de comentario que extiende UserDateAudit para auditoría
 *
 * Esta clase representa un comentario en la aplicación, asociado a un post
 * y a un usuario. Incluye validaciones para los campos.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends UserDateAudit {
    private static final long serialVersionUID = 1L;

    /**
     * @brief Identificador único del comentario
     * @var id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @brief Nombre del autor del comentario
     * @details Debe tener entre 4 y 50 caracteres
     * @var name
     */
    @Column(name = "name")
    @NotBlank
    @Size(min = 4, max = 50)
    private String name;

    /**
     * @brief Email del autor del comentario
     * @details Debe ser un email válido y tener entre 4 y 50 caracteres
     * @var email
     */
    @Column(name = "email")
    @NotBlank
    @Email
    @Size(min = 4, max = 50)
    private String email;

    /**
     * @brief Cuerpo del comentario
     * @details Debe tener al menos 10 caracteres
     * @var body
     */
    @Column(name = "body")
    @NotBlank
    @Size(min = 10, message = "Comment body must be minimum 10 characters")
    private String body;

    /**
     * @brief Post al que pertenece el comentario
     * @var post
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * @brief Usuario que realizó el comentario
     * @var user
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * @brief Constructor con cuerpo del comentario
     * @param body Cuerpo del comentario (mínimo 10 caracteres)
     */
    public Comment(@NotBlank @Size(min = 10, message = "Comment body must be minimum 10 characters") String body) {
        this.body = body;
    }

    /**
     * @brief Obtiene el post asociado
     * @return Post El post asociado al comentario
     * @note Esta anotación ignora la serialización JSON
     */
    @JsonIgnore
    public Post getPost() {
        return post;
    }

    /**
     * @brief Obtiene el usuario que comentó
     * @return User El usuario que realizó el comentario
     * @note Esta anotación ignora la serialización JSON
     */
    @JsonIgnore
    public User getUser() {
        return user;
    }
}
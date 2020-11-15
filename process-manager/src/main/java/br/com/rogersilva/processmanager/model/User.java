package br.com.rogersilva.processmanager.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = -1470898176580467827L;

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false, length = 50)
    private String name;

    @Column(name = "senha", nullable = false, length = 50)
    private String password;

    @Column(name = "funcao", nullable = false)
    private Role role;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime updatedAt;
}
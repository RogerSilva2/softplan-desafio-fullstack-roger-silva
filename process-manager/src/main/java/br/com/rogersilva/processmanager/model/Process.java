package br.com.rogersilva.processmanager.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "processo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Process implements Serializable {

    private static final long serialVersionUID = -7869934203526386159L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false, length = 50)
    private String name;

    @Column(name = "conteudo", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "id_usuario_criador", referencedColumnName = "id", nullable = false)
    private User creator;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime updatedAt;
}
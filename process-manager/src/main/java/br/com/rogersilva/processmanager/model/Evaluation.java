package br.com.rogersilva.processmanager.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "avaliacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Evaluation implements Serializable {

    private static final long serialVersionUID = 6398621932168382532L;

    @EmbeddedId
    private EvaluationId id;

    @Column(name = "comentario")
    private String feedback;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime updatedAt;
}
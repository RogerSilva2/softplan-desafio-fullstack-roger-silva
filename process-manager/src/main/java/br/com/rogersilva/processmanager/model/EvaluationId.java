package br.com.rogersilva.processmanager.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class EvaluationId implements Serializable {

    private static final long serialVersionUID = -1213330801975560499L;

    @ManyToOne
    @JoinColumn(name = "id_usuario_avaliador", referencedColumnName = "id", updatable = false, nullable = false)
    private User evaluator;

    @ManyToOne
    @JoinColumn(name = "id_processo", referencedColumnName = "id", updatable = false, nullable = false)
    private Process process;
}
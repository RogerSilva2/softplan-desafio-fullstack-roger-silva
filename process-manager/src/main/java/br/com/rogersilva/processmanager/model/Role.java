package br.com.rogersilva.processmanager.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Role {

    ADMINISTRATOR("ADMINISTRADOR"), GRADER("CLASSIFICADOR"), EVALUATOR("AVALIADOR");

    private String value;
}
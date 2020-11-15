package br.com.rogersilva.processmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rogersilva.processmanager.model.Evaluation;
import br.com.rogersilva.processmanager.model.EvaluationId;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, EvaluationId> {
}
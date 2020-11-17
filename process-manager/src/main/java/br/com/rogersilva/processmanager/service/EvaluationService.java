package br.com.rogersilva.processmanager.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rogersilva.processmanager.dto.EvaluationDto;
import br.com.rogersilva.processmanager.exception.BadRequestException;
import br.com.rogersilva.processmanager.exception.NotFoundException;
import br.com.rogersilva.processmanager.model.Evaluation;
import br.com.rogersilva.processmanager.model.EvaluationId;
import br.com.rogersilva.processmanager.model.Process;
import br.com.rogersilva.processmanager.model.User;
import br.com.rogersilva.processmanager.repository.EvaluationRepository;
import br.com.rogersilva.processmanager.repository.ProcessRepository;

@Service
@Transactional
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private ProcessRepository processRepository;

    public EvaluationDto createEvaluation(EvaluationDto evaluationDto, User user)
            throws BadRequestException, NotFoundException {
        Process process = processRepository.findById(evaluationDto.getProcessId())
                .orElseThrow(() -> new BadRequestException(
                        String.format("Process with id %s not found", evaluationDto.getProcessId())));

        Evaluation evaluation = evaluationRepository
                .findById(EvaluationId.builder().evaluator(user).process(process).build())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Evaluation for user with id %s and process with id %s not found", user.getId(),
                                evaluationDto.getProcessId())));

        evaluation.setFeedback(evaluationDto.getFeedback());
        evaluation.setUpdatedAt(LocalDateTime.now());

        return convertToEvaluationDto(evaluationRepository.save(evaluation));
    }

    private EvaluationDto convertToEvaluationDto(Evaluation evaluation) {
        return EvaluationDto.builder().feedback(evaluation.getFeedback()).build();
    }
}
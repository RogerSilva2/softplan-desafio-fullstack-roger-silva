package br.com.rogersilva.processmanager.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rogersilva.processmanager.dto.EvaluationDto;
import br.com.rogersilva.processmanager.exception.BadRequestException;
import br.com.rogersilva.processmanager.model.Evaluation;
import br.com.rogersilva.processmanager.model.EvaluationId;
import br.com.rogersilva.processmanager.model.Process;
import br.com.rogersilva.processmanager.model.User;
import br.com.rogersilva.processmanager.repository.EvaluationRepository;
import br.com.rogersilva.processmanager.repository.ProcessRepository;
import br.com.rogersilva.processmanager.repository.UserRepository;

@Service
@Transactional
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProcessRepository processRepository;

    public EvaluationDto createEvaluation(EvaluationDto evaluationDto) throws BadRequestException {
        User user = userRepository.findById(evaluationDto.getEvaluatorId()).orElseThrow(() -> new BadRequestException(
                String.format("User with id %s not found", evaluationDto.getEvaluatorId())));

        Process process = processRepository.findById(evaluationDto.getProcessId())
                .orElseThrow(() -> new BadRequestException(
                        String.format("Process with id %s not found", evaluationDto.getProcessId())));

        LocalDateTime now = LocalDateTime.now();

        Evaluation evaluation = convertToEvaluation(evaluationDto, user, process);
        evaluation.setCreatedAt(now);
        evaluation.setUpdatedAt(now);

        return convertToEvaluationDto(evaluationRepository.save(evaluation));
    }

    private Evaluation convertToEvaluation(EvaluationDto evaluationDto, User user, Process process) {
        return Evaluation.builder().id(EvaluationId.builder().evaluator(user).process(process).build())
                .feedback(evaluationDto.getFeedback()).build();
    }

    private EvaluationDto convertToEvaluationDto(Evaluation evaluation) {
        return EvaluationDto.builder().feedback(evaluation.getFeedback()).build();
    }
}
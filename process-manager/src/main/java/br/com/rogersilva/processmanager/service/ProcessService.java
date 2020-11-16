package br.com.rogersilva.processmanager.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rogersilva.processmanager.dto.ProcessDto;
import br.com.rogersilva.processmanager.exception.BadRequestException;
import br.com.rogersilva.processmanager.model.Evaluation;
import br.com.rogersilva.processmanager.model.EvaluationId;
import br.com.rogersilva.processmanager.model.Process;
import br.com.rogersilva.processmanager.model.Role;
import br.com.rogersilva.processmanager.model.User;
import br.com.rogersilva.processmanager.repository.EvaluationRepository;
import br.com.rogersilva.processmanager.repository.ProcessRepository;
import br.com.rogersilva.processmanager.repository.UserRepository;

@Service
@Transactional
public class ProcessService {

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    public List<ProcessDto> findProcesses(User user) {
        switch (user.getRole()) {
        case GRADER:
            return processRepository.findAll().stream().map(this::convertToProcessDto).collect(Collectors.toList());
        case EVALUATOR:
            return evaluationRepository.findByIdEvaluatorId(user.getId()).orElseGet(List::of).stream()
                    .map(e -> e.getId().getProcess()).map(this::convertToProcessDto).collect(Collectors.toList());
        default:
            return List.of();
        }
    }

    public ProcessDto createProcess(ProcessDto processDto, User creator) throws BadRequestException {
        LocalDateTime now = LocalDateTime.now();

        Process process = convertToProcess(processDto);

        process.setCreator(creator);
        process.setCreatedAt(now);
        process.setUpdatedAt(now);

        process = processRepository.save(process);
        for (Long evaluatorId : processDto.getEvaluatorIds()) {
            User evaluator = userRepository.findById(evaluatorId).orElseThrow(
                    () -> new BadRequestException(String.format("User with id %s not found", evaluatorId)));

            if (!evaluator.getRole().equals(Role.EVALUATOR)) {
                throw new BadRequestException(String.format("User with id %s not is evaluator", evaluatorId));
            }

            Evaluation evaluation = Evaluation.builder()
                    .id(EvaluationId.builder().evaluator(evaluator).process(process).build()).createdAt(now)
                    .updatedAt(now).build();

            evaluationRepository.save(evaluation);
        }

        return convertToProcessDto(process);
    }

    private Process convertToProcess(ProcessDto processDto) {
        return Process.builder().name(processDto.getName()).content(processDto.getContent()).build();
    }

    private ProcessDto convertToProcessDto(Process process) {
        return ProcessDto.builder().id(process.getId()).name(process.getName()).content(process.getContent()).build();
    }
}
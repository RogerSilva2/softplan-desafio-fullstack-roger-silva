package br.com.rogersilva.processmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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

@SpringBootTest
public class ProcessServiceTest {

    @InjectMocks
    private ProcessService processService;

    @Mock
    private ProcessRepository processRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EvaluationRepository evaluationRepository;

    private Process mockProcess;

    private ProcessDto mockProcessDto;

    @BeforeEach
    public void setup() {
        mockProcess = Process.builder().id(1L).name("Proceso").content("Conteúdo").build();

        mockProcessDto = ProcessDto.builder().id(mockProcess.getId()).name(mockProcess.getName())
                .content(mockProcess.getContent()).build();
    }

    @Test
    public void findProcessesWithRoleAdministrator() {
        List<ProcessDto> processes = processService.findProcesses(User.builder().role(Role.ADMINISTRATOR).build());

        assertThat(processes).isEmpty();
    }

    @Test
    public void findProcessesWithRoleGrader() {
        when(processRepository.findAll()).thenReturn(List.of(mockProcess));

        List<ProcessDto> processes = processService.findProcesses(User.builder().role(Role.GRADER).build());

        assertThat(processes).isNotEmpty();
        assertThat(processes.get(0)).isEqualTo(mockProcessDto);
    }

    @Test
    public void findProcessesEmptyWithRoleGrader() {
        when(processRepository.findAll()).thenReturn(List.of());

        List<ProcessDto> processes = processService.findProcesses(User.builder().role(Role.GRADER).build());

        assertThat(processes).isEmpty();
    }

    @Test
    public void findProcessesWithRoleEvaluator() {
        Long evaluatorId = 1L;

        when(evaluationRepository.findByIdEvaluatorId(evaluatorId)).thenReturn(Optional
                .of(List.of(Evaluation.builder().id(EvaluationId.builder().process(mockProcess).build()).build())));

        List<ProcessDto> processes = processService
                .findProcesses(User.builder().id(evaluatorId).role(Role.EVALUATOR).build());

        assertThat(processes).isNotEmpty();
        assertThat(processes.get(0)).isEqualTo(mockProcessDto);
    }

    @Test
    public void findProcessesEmptyWithRoleEvaluator() {
        Long evaluatorId = 1L;

        when(evaluationRepository.findByIdEvaluatorId(evaluatorId)).thenReturn(Optional.empty());

        List<ProcessDto> processes = processService
                .findProcesses(User.builder().id(evaluatorId).role(Role.EVALUATOR).build());

        assertThat(processes).isEmpty();
    }

    @Test
    public void createProcessWithoutEvaluatorIds() throws BadRequestException {
        when(processRepository.save(ArgumentMatchers.any(Process.class))).thenReturn(mockProcess);

        ProcessDto process = processService.createProcess(ProcessDto.builder().name(mockProcess.getName())
                .content(mockProcess.getContent()).evaluatorIds(List.of()).build(), User.builder().build());

        assertThat(process).isEqualTo(mockProcessDto);
    }

    @Test
    public void createProcessWithEvaluatorIdsAndUserNotFound() {
        Long evaluatorId = 1L;

        when(processRepository.save(ArgumentMatchers.any(Process.class))).thenReturn(mockProcess);

        when(userRepository.findById(evaluatorId)).thenReturn(Optional.empty());

        try {
            processService.createProcess(ProcessDto.builder().name(mockProcess.getName())
                    .content(mockProcess.getContent()).evaluatorIds(List.of(evaluatorId)).build(),
                    User.builder().build());
            fail("BadRequestException should have been generated");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
            assertThat(e.getMessage()).isEqualTo(String.format("User with id %s not found", evaluatorId));
        }
    }

    @Test
    public void createProcessWithEvaluatorIdsAndUserNotEvaluator() {
        Long evaluatorId = 1L;

        when(processRepository.save(ArgumentMatchers.any(Process.class))).thenReturn(mockProcess);

        when(userRepository.findById(evaluatorId))
                .thenReturn(Optional.of(User.builder().role(Role.ADMINISTRATOR).build()));

        try {
            processService.createProcess(ProcessDto.builder().name(mockProcess.getName())
                    .content(mockProcess.getContent()).evaluatorIds(List.of(evaluatorId)).build(),
                    User.builder().build());
            fail("BadRequestException should have been generated");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
            assertThat(e.getMessage()).isEqualTo(String.format("User with id %s not is evaluator", evaluatorId));
        }
    }

    @Test
    public void createProcessWithEvaluatorIds() throws BadRequestException {
        Long evaluatorId = 1L;

        when(processRepository.save(ArgumentMatchers.any(Process.class))).thenReturn(mockProcess);

        when(userRepository.findById(evaluatorId)).thenReturn(Optional.of(User.builder().role(Role.EVALUATOR).build()));

        when(evaluationRepository.save(ArgumentMatchers.any(Evaluation.class)))
                .thenReturn(Evaluation.builder().build());

        ProcessDto process = processService.createProcess(ProcessDto.builder().name(mockProcess.getName())
                .content(mockProcess.getContent()).evaluatorIds(List.of(evaluatorId)).build(), User.builder().build());

        assertThat(process).isEqualTo(mockProcessDto);
    }
}
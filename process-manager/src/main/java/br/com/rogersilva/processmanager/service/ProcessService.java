package br.com.rogersilva.processmanager.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rogersilva.processmanager.dto.ProcessDto;
import br.com.rogersilva.processmanager.model.Process;
import br.com.rogersilva.processmanager.model.User;
import br.com.rogersilva.processmanager.repository.ProcessRepository;

@Service
@Transactional
public class ProcessService {

    @Autowired
    private ProcessRepository processRepository;

    public List<ProcessDto> findProcesses() {
        return processRepository.findAll().stream().map(this::convertToProcessDto).collect(Collectors.toList());
    }

    public ProcessDto createProcess(ProcessDto processDto) {
        LocalDateTime now = LocalDateTime.now();

        Process process = convertToProcess(processDto);
        // TODO: pegar usuário da sessão quando tiver autenticação
        process.setCreator(User.builder().id(1L).build());
        process.setCreatedAt(now);
        process.setUpdatedAt(now);

        return convertToProcessDto(processRepository.save(process));
    }

    private Process convertToProcess(ProcessDto processDto) {
        return Process.builder().name(processDto.getName()).content(processDto.getContent()).build();
    }

    private ProcessDto convertToProcessDto(Process process) {
        return ProcessDto.builder().id(process.getId()).name(process.getName()).content(process.getContent()).build();
    }
}
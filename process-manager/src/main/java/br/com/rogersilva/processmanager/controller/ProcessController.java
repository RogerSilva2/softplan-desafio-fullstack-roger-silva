package br.com.rogersilva.processmanager.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rogersilva.processmanager.dto.ProcessDto;
import br.com.rogersilva.processmanager.exception.BadRequestException;
import br.com.rogersilva.processmanager.model.User;
import br.com.rogersilva.processmanager.service.ProcessService;

@RestController
@Validated
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @GetMapping
    @PreAuthorize("hasAuthority('GRADER') or hasAuthority('EVALUATOR')")
    public ResponseEntity<List<ProcessDto>> findProcesses(Authentication authentication) {
        return ResponseEntity.ok().body(processService.findProcesses((User) authentication.getPrincipal()));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('GRADER')")
    public ResponseEntity<ProcessDto> createProcess(Authentication authentication,
            @Valid @RequestBody ProcessDto processDto) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(processService.createProcess(processDto, (User) authentication.getPrincipal()));
    }
}
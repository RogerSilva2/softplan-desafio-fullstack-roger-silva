package br.com.rogersilva.processmanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rogersilva.processmanager.dto.EvaluationDto;
import br.com.rogersilva.processmanager.exception.BadRequestException;
import br.com.rogersilva.processmanager.exception.NotFoundException;
import br.com.rogersilva.processmanager.service.EvaluationService;

@RestController
@Validated
@RequestMapping("/evaluation")
@PreAuthorize("hasAuthority('EVALUATOR')")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping
    public ResponseEntity<EvaluationDto> createEvaluation(@Valid @RequestBody EvaluationDto evaluationDto)
            throws BadRequestException, NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(evaluationService.createEvaluation(evaluationDto));
    }
}
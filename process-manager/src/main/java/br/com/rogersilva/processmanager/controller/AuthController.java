package br.com.rogersilva.processmanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rogersilva.processmanager.dto.AccessTokenDto;
import br.com.rogersilva.processmanager.dto.LoginDto;
import br.com.rogersilva.processmanager.service.AuthService;

@RestController
@Validated
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<AccessTokenDto> authenticate(@Valid @RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                loginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        return ResponseEntity.ok().body(authService.createToken(authentication));
    }
}
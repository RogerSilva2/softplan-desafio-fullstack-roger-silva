package br.com.rogersilva.processmanager.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rogersilva.processmanager.dto.AccessTokenDto;
import br.com.rogersilva.processmanager.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Transactional
public class AuthService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public AccessTokenDto createToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        String accessToken = Jwts.builder().setIssuer("Process Manager API").setSubject(user.getId().toString())
                .setIssuedAt(now).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS256, secret).compact();

        return AccessTokenDto.builder().accessToken(accessToken).tokenType("Bearer").build();
    }
}
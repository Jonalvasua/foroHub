package com.alura.cursos.foroHub.controller;

import com.alura.cursos.foroHub.domain.user.User;
import com.alura.cursos.foroHub.domain.user.UserData;
import com.alura.cursos.foroHub.infra.security.DataJWTToken;
import com.alura.cursos.foroHub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity userAuthentication(@RequestBody @Valid UserData userData) {
        Authentication aUtoken = new UsernamePasswordAuthenticationToken(userData.username(), userData.password());

        var authenticadedUser = authenticationManager.authenticate(aUtoken);
        var token = tokenService.generateToken((User) authenticadedUser.getPrincipal());

        return ResponseEntity.ok(new DataJWTToken(token));
    }
}
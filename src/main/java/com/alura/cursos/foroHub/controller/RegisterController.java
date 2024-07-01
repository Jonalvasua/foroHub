package com.alura.cursos.foroHub.controller;

import com.alura.cursos.foroHub.domain.user.DataRegister;
import com.alura.cursos.foroHub.domain.user.User;
import com.alura.cursos.foroHub.domain.user.UserRepository;
import com.alura.cursos.foroHub.domain.user.UserResponseData;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserRepository userRepositoy;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid DataRegister dataRegister, UriComponentsBuilder uriComponentsBuilder) {
        try {
            User user = userRepositoy.save(new User(dataRegister, bCryptPasswordEncoder));

            UserResponseData userResponseData = new UserResponseData(
                    user.getId(), user.getName()
            );

            URI url = uriComponentsBuilder.path("user/{idUser}").buildAndExpand(user.getId()).toUri();

            return ResponseEntity.created(url).body(userResponseData);
        } catch (ConstraintViolationException ex) {
            return ResponseEntity.badRequest().body("Validation failed: " + ex.getMessage());
        }
    }
}

package com.alura.cursos.foroHub.domain.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ResponseData(
        @NotBlank
        String solution,
        @NotNull
        @Valid
        Long idUser,
        @NotNull
        @Valid
        Long idTopic,
        LocalDateTime creationdate) {
}

package com.tiagomolero.gerenciamentotarefas.model.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record AuthenticationDTO(

        @Email(message = "Email Inválido! Informe um email válido",regexp = ".+[@].+[\\\\.].+")
        String email,

        @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
        String senha
) {}

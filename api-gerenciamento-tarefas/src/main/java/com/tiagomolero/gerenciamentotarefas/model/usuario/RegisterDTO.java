package com.tiagomolero.gerenciamentotarefas.model.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
        @Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
        String nome,

        @Email(message = "Email Inválido! Informe um email válido",regexp = ".+[@].+[\\\\.].+")
        String email,

        @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
        String senha
) {}

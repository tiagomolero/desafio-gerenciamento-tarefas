package com.tiagomolero.gerenciamentotarefas.model.usuario;

import jakarta.validation.constraints.Size;

public record AuthenticationDTO(
        String email,

        @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
        String senha
) {}

package com.tiagomolero.gerenciamentotarefas.model.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
        @Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
        String nome,

        @Email(message = "Formato de e-mail inválido", regexp = "^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")
        String email,

        @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "A senha deve conter pelo menos 1 letra e 1 número")
        String senha
) {}

package com.tiagomolero.gerenciamentotarefas.model.tarefa;

import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public record TarefaDTO(
        @Size(min = 3, max = 100, message = "Título deve ter entre 3 e 100 caracteres")
        String titulo,

        @Size(min = 10, max = 500, message = "Descrição deve ter entre 10 e 500 caracteres")
        String descricao,
        Status status,
        Prioridade prioridade
)
{}

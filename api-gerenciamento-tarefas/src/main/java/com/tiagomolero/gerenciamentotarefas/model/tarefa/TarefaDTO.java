package com.tiagomolero.gerenciamentotarefas.model.tarefa;

import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.UUID;

public record TarefaDTO(String titulo, String descricao, Status status, Prioridade prioridade) {
}

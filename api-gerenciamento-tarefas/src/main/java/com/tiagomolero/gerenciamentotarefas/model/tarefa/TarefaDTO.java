package com.tiagomolero.gerenciamentotarefas.model.tarefa;

import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.UUID;

public record TarefaDTO(UUID id, String titulo, String descricao, Status status, Prioridade prioridade, String criador, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
}

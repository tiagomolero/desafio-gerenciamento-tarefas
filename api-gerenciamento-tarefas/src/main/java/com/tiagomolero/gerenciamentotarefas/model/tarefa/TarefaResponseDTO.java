package com.tiagomolero.gerenciamentotarefas.model.tarefa;

import java.time.LocalDateTime;
import java.util.UUID;

public record TarefaResponseDTO(UUID id, String titulo, String descricao, Status status, Prioridade prioridade, String criador, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
}

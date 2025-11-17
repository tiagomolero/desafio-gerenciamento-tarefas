package com.tiagomolero.gerenciamentotarefas.model.dashboard;

public record DashboardResponseDTO(
        long pendentes,
        long emProgresso,
        long concluídas
) {}

package com.tiagomolero.gerenciamentotarefas.repository;

import com.tiagomolero.gerenciamentotarefas.model.tarefa.Status;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.Tarefa;
import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TarefaRepository extends JpaRepository<Tarefa, UUID> {
    List<Tarefa> findByCriadorId(UUID id);
    Tarefa findByIdAndCriadorId(UUID id, UUID criadorId);
    long countByCriadorIdAndStatus(UUID criadorId, Status status);
}

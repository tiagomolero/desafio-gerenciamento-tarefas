package com.tiagomolero.gerenciamentotarefas.repository;

import com.tiagomolero.gerenciamentotarefas.model.tarefa.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TarefaRepository extends JpaRepository<Tarefa, UUID> {
    List<Tarefa> findByCriadorId(UUID id);
}

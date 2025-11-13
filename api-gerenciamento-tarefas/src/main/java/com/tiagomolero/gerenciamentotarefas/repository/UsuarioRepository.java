package com.tiagomolero.gerenciamentotarefas.repository;

import com.tiagomolero.gerenciamentotarefas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}

package com.tiagomolero.gerenciamentotarefas.repository;

import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    UserDetails findByEmail(String email);
    Optional<Usuario> findUsuarioByEmail(String email);
}

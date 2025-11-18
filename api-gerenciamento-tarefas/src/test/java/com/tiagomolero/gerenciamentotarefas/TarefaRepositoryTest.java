package com.tiagomolero.gerenciamentotarefas.repository;

import com.tiagomolero.gerenciamentotarefas.model.tarefa.*;
import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TarefaRepositoryTest {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void deveContarTarefasPorStatus() {
        Usuario u = new Usuario("Teste", "u@u.com", "123");
        usuarioRepository.save(u);

        Tarefa t1 = new Tarefa("T1", "D1", Status.PENDENTE, Prioridade.MEDIA, u);
        Tarefa t2 = new Tarefa("T2", "D2", Status.PENDENTE, Prioridade.BAIXA, u);

        tarefaRepository.save(t1);
        tarefaRepository.save(t2);

        long count = tarefaRepository.countByCriadorIdAndStatus(u.getId(), Status.PENDENTE);

        assertEquals(2, count);
    }
}

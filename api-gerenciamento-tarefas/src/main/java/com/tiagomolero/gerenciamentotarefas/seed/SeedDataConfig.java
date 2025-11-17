package com.tiagomolero.gerenciamentotarefas.seed;

import com.tiagomolero.gerenciamentotarefas.model.tarefa.Prioridade;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.Status;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.Tarefa;
import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;
import com.tiagomolero.gerenciamentotarefas.repository.TarefaRepository;
import com.tiagomolero.gerenciamentotarefas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SeedDataConfig implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (usuarioRepository.count() > 0) return;

        Usuario usuario1 = new Usuario("Admin Teste", "admin@teste.com",
                passwordEncoder.encode("Teste@123"));

        Usuario usuario2 = new Usuario("João Silva", "joao@teste.com",
                passwordEncoder.encode("Teste@123"));

        Usuario usuario3 = new Usuario("Maria Santos", "maria@teste.com",
                passwordEncoder.encode("Teste@123"));

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);
        usuarioRepository.save(usuario3);

        tarefaRepository.save(new Tarefa(
                "Configurar projeto",
                "Criar estrutura inicial do projeto",
                Status.CONCLUIDA,
                Prioridade.ALTA,
                usuario1
        ));

        tarefaRepository.save(new Tarefa(
                "Implementar autenticação",
                "Criar sistema de login e registro",
                Status.EM_PROGRESSO,
                Prioridade.ALTA,
                usuario1
        ));

        tarefaRepository.save(new Tarefa(
                "Criar dashboard",
                "Página principal com lista de tarefas",
                Status.PENDENTE,
                Prioridade.MEDIA,
                usuario1
        ));

        tarefaRepository.save(new Tarefa(
                "Adicionar filtros",
                "Filtrar tarefas por status e prioridade",
                Status.PENDENTE,
                Prioridade.BAIXA,
                usuario2
        ));

        tarefaRepository.save(new Tarefa(
                "Escrever testes",
                "Testes unitários do backend",
                Status.PENDENTE,
                Prioridade.ALTA,
                usuario2
        ));

        tarefaRepository.save(new Tarefa(
                "Dockerizar aplicação",
                "Criar Docker Compose",
                Status.PENDENTE,
                Prioridade.MEDIA,
                usuario2
        ));

        tarefaRepository.save(new Tarefa(
                "Documentar API",
                "Escrever README completo",
                Status.PENDENTE,
                Prioridade.ALTA,
                usuario3
        ));

        tarefaRepository.save(new Tarefa(
                "Corrigir bugs",
                "Revisar e corrigir problemas encontrados",
                Status.PENDENTE,
                Prioridade.BAIXA,
                usuario3
        ));

        System.out.println("Seed inicial criada com sucesso!");
    }
}

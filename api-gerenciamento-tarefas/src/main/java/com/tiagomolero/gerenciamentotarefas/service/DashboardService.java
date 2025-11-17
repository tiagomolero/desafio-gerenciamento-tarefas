package com.tiagomolero.gerenciamentotarefas.service;

import com.tiagomolero.gerenciamentotarefas.model.dashboard.DashboardResponseDTO;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.Status;
import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;
import com.tiagomolero.gerenciamentotarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private TarefaRepository tarefaRepository;

    public DashboardResponseDTO gerarDashboard(){
        Usuario usuario = authorizationService.getUsuarioLogado();

        long pendentes = tarefaRepository.countByCriadorIdAndStatus(usuario.getId(), Status.PENDENTE);
        long emProgresso = tarefaRepository.countByCriadorIdAndStatus(usuario.getId(), Status.EM_PROGRESSO);
        long concluidas = tarefaRepository.countByCriadorIdAndStatus(usuario.getId(), Status.CONCLUIDA);

        return new DashboardResponseDTO(pendentes, emProgresso, concluidas);
    }

}

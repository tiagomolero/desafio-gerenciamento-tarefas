package com.tiagomolero.gerenciamentotarefas.service;

import com.tiagomolero.gerenciamentotarefas.exception.TarefaException;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.Tarefa;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.TarefaDTO;
import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;
import com.tiagomolero.gerenciamentotarefas.repository.TarefaRepository;
import com.tiagomolero.gerenciamentotarefas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TarefaService {

    @Autowired
    TarefaRepository tarefaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AuthorizationService authorizationService;

    public TarefaDTO criarTarefa(TarefaDTO tarefaDTO){
        Usuario usuarioLogado = authorizationService.getUsuarioLogado();
        Tarefa tarefa = new Tarefa(tarefaDTO.titulo(), tarefaDTO.descricao(), tarefaDTO.status(), tarefaDTO.prioridade(), usuarioLogado);
        Tarefa tarefaSalva = tarefaRepository.save(tarefa);
        TarefaDTO tarefaResponseDTO = parseToTarefaDTO(tarefaSalva);
        return tarefaResponseDTO;
    }

    public List<TarefaDTO> listarTarefas(){
        List<Tarefa> tarefas = tarefaRepository.findAll();
        return parseToTarefasDTO(tarefas);
    }

    public TarefaDTO buscarTarefa(UUID id){
        Tarefa tarefaEncontrada = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa Não Encontrada"));

        TarefaDTO tarefaDTO = parseToTarefaDTO(tarefaEncontrada);
        return tarefaDTO;
    }

    private TarefaDTO parseToTarefaDTO(Tarefa tarefa){
        TarefaDTO tarefaDTO = new TarefaDTO(
          tarefa.getId(),
          tarefa.getTitulo(),
          tarefa.getDescricao(),
          tarefa.getStatus(),
          tarefa.getPrioridade(),
          tarefa.getCriador().getEmail(),
          tarefa.getDataCriacao(),
          tarefa.getDataAtualizacao()
        );
        return tarefaDTO;
    }

    private List<TarefaDTO> parseToTarefasDTO(List<Tarefa> tarefas){
        List<TarefaDTO> tarefaDTOs = new ArrayList<>();
        for (Tarefa tarefa : tarefas){
            tarefaDTOs.add(parseToTarefaDTO(tarefa));
        }
        return tarefaDTOs;
    }

}

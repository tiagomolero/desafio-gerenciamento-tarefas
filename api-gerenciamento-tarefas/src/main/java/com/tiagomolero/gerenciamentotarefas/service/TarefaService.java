package com.tiagomolero.gerenciamentotarefas.service;

import com.tiagomolero.gerenciamentotarefas.exception.TarefaException;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.Tarefa;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.TarefaDTO;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.TarefaResponseDTO;
import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;
import com.tiagomolero.gerenciamentotarefas.repository.TarefaRepository;
import com.tiagomolero.gerenciamentotarefas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public TarefaResponseDTO criarTarefa(TarefaDTO tarefaDTO){
        Usuario usuarioLogado = authorizationService.getUsuarioLogado();
        Tarefa tarefa = new Tarefa(tarefaDTO.titulo(), tarefaDTO.descricao(), tarefaDTO.status(), tarefaDTO.prioridade(), usuarioLogado);
        Tarefa tarefaSalva = tarefaRepository.save(tarefa);
        TarefaResponseDTO tarefaResponseDTO = parseToTarefaResponseDTO(tarefaSalva);
        return tarefaResponseDTO;
    }

    public List<TarefaResponseDTO> listarTarefas(){
        List<Tarefa> tarefas = tarefaRepository.findAll();
        return parseToTarefasDTO(tarefas);
    }

    public TarefaResponseDTO buscarTarefa(UUID id){
        Tarefa tarefaEncontrada = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa Não Encontrada"));

        TarefaResponseDTO tarefaResponseDTO = parseToTarefaResponseDTO(tarefaEncontrada);
        return tarefaResponseDTO;
    }

    public TarefaResponseDTO editarTarefa(UUID id, TarefaDTO tarefaDTO){
        Tarefa tarefaExistente = tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaException(id));

        tarefaExistente.setTitulo(tarefaDTO.titulo());
        tarefaExistente.setDescricao(tarefaDTO.descricao());
        tarefaExistente.setStatus(tarefaDTO.status());
        tarefaExistente.setPrioridade(tarefaDTO.prioridade());

        Tarefa tarefaNova = tarefaRepository.save(tarefaExistente);
        return parseToTarefaResponseDTO(tarefaNova);
    }

    private TarefaResponseDTO parseToTarefaResponseDTO(Tarefa tarefa){
        TarefaResponseDTO tarefaResponseDTO = new TarefaResponseDTO(
          tarefa.getId(),
          tarefa.getTitulo(),
          tarefa.getDescricao(),
          tarefa.getStatus(),
          tarefa.getPrioridade(),
          tarefa.getCriador().getEmail(),
          tarefa.getDataCriacao(),
          tarefa.getDataAtualizacao()
        );
        return tarefaResponseDTO;
    }

    private List<TarefaResponseDTO> parseToTarefasDTO(List<Tarefa> tarefas){
        List<TarefaResponseDTO> tarefaResponseDTO = new ArrayList<>();
        for (Tarefa tarefa : tarefas){
            tarefaResponseDTO.add(parseToTarefaResponseDTO(tarefa));
        }
        return tarefaResponseDTO;
    }

}

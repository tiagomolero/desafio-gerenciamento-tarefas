package com.tiagomolero.gerenciamentotarefas.service;

import com.tiagomolero.gerenciamentotarefas.exception.TarefaException;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.Tarefa;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.TarefaDTO;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.TarefaResponseDTO;
import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;
import com.tiagomolero.gerenciamentotarefas.repository.TarefaRepository;
import com.tiagomolero.gerenciamentotarefas.repository.UsuarioRepository;
import com.tiagomolero.gerenciamentotarefas.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TarefaService {

    @Autowired
    TarefaRepository tarefaRepository;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    TokenService tokenService;

    public TarefaResponseDTO criarTarefa(TarefaDTO tarefaDTO){
        Usuario usuarioLogado = authorizationService.getUsuarioLogado();
        Tarefa tarefa = new Tarefa(tarefaDTO.titulo(), tarefaDTO.descricao(), tarefaDTO.status(), tarefaDTO.prioridade(), usuarioLogado);
        Tarefa tarefaSalva = tarefaRepository.save(tarefa);
        TarefaResponseDTO tarefaResponseDTO = parseToTarefaResponseDTO(tarefaSalva);
        return tarefaResponseDTO;
    }

    public List<TarefaResponseDTO> listarTarefas(){
        Usuario usuarioLogado = authorizationService.getUsuarioLogado();
        List<Tarefa> tarefas = tarefaRepository.findByCriadorId(usuarioLogado.getId());
        return parseToTarefasResponseDTO(tarefas);
    }

    public TarefaResponseDTO buscarTarefa(UUID id){
        Usuario usuarioLogado = authorizationService.getUsuarioLogado();
        Tarefa tarefaEncontrada = tarefaRepository.findByIdAndCriadorId(id, usuarioLogado.getId());
        return parseToTarefaResponseDTO(tarefaEncontrada);
    }

    public TarefaResponseDTO editarTarefa(UUID id, TarefaDTO tarefaDTO){
        Usuario usuarioLogado = authorizationService.getUsuarioLogado();
        Tarefa tarefaExistente = tarefaRepository.findByIdAndCriadorId(id, usuarioLogado.getId());

        if (tarefaExistente != null){
            tarefaExistente.setTitulo(tarefaDTO.titulo());
            tarefaExistente.setDescricao(tarefaDTO.descricao());
            tarefaExistente.setStatus(tarefaDTO.status());
            tarefaExistente.setPrioridade(tarefaDTO.prioridade());

            Tarefa tarefaNova = tarefaRepository.save(tarefaExistente);
            return parseToTarefaResponseDTO(tarefaNova);
        }
        return null;
    }

    public boolean excluirTarefa(UUID id){
        Usuario usuarioLogado = authorizationService.getUsuarioLogado();
        Tarefa tarefaEncontrada = tarefaRepository.findByIdAndCriadorId(id, usuarioLogado.getId());
        if (tarefaEncontrada != null){
            tarefaRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
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

    private List<TarefaResponseDTO> parseToTarefasResponseDTO(List<Tarefa> tarefas){
        List<TarefaResponseDTO> tarefaResponseDTO = new ArrayList<>();
        for (Tarefa tarefa : tarefas){
            tarefaResponseDTO.add(parseToTarefaResponseDTO(tarefa));
        }
        return tarefaResponseDTO;
    }

}

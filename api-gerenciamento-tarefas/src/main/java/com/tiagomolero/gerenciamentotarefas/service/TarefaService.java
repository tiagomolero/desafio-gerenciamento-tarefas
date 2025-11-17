package com.tiagomolero.gerenciamentotarefas.service;

import com.tiagomolero.gerenciamentotarefas.exception.TarefaException;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.*;
import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;
import com.tiagomolero.gerenciamentotarefas.repository.TarefaRepository;
import com.tiagomolero.gerenciamentotarefas.repository.UsuarioRepository;
import com.tiagomolero.gerenciamentotarefas.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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

    public List<TarefaResponseDTO> listarTarefasComFiltros(String statusString, String prioridadeString){
        Usuario usuarioLogado = authorizationService.getUsuarioLogado();

         Status status = null;
        if (statusString != null){
            try{
                status = Status.valueOf(statusString.toUpperCase());
            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException("Status inválido: " + statusString);
            }
        }

        Prioridade prioridade = null;
        if (prioridadeString != null){
            try{
                prioridade = Prioridade.valueOf(prioridadeString.toUpperCase());
            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException("Prioridade inválida: " + prioridadeString);
            }
        }

        List<Tarefa> tarefas;

        if (status != null && prioridade != null){
            tarefas = tarefaRepository.findByCriadorIdAndStatusAndPrioridade(usuarioLogado.getId(), status, prioridade);
        } else if (status != null){
            tarefas = tarefaRepository.findByCriadorIdAndStatus(usuarioLogado.getId(), status);
        } else if (prioridade != null) {
            tarefas = tarefaRepository.findByCriadorIdAndPrioridade(usuarioLogado.getId(), prioridade);
        } else {
            tarefas = tarefaRepository.findByCriadorId(usuarioLogado.getId());
        }

        // Ordenação do mais recente primeiro
        tarefas.sort(Comparator.comparing(Tarefa::getDataCriacao).reversed());

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

            if (tarefaDTO.prioridade() != null){
                tarefaExistente.setPrioridade(tarefaDTO.prioridade());
            }

            Status statusAtual = tarefaExistente.getStatus();
            Status novoStatus = tarefaDTO.status() != null ? tarefaDTO.status() : statusAtual;

            if (!novoStatus.equals(statusAtual)){
                switch (statusAtual){
                    case PENDENTE:
                        if (!(novoStatus == Status.EM_PROGRESSO || novoStatus == Status.CONCLUIDA)){
                            throw new IllegalArgumentException("Fluxo de status inválido: de PENDENTE só pode ir para EM_PROGRESSO ou CONCLUIDA");
                        }
                        break;
                    case EM_PROGRESSO:
                        if (novoStatus != Status.CONCLUIDA){
                            throw new IllegalArgumentException("Fluxo de status inválido: de EM_PROGRESSO só pode ir para CONCLUIDA");
                        }
                        break;
                    case CONCLUIDA:
                        throw new IllegalArgumentException("Status 'CONCLUIDA' não pode ser alterado");
                }
                tarefaExistente.setStatus(novoStatus);
            }

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

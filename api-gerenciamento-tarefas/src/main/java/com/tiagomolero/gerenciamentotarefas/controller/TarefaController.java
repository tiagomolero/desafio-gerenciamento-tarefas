package com.tiagomolero.gerenciamentotarefas.controller;

import com.tiagomolero.gerenciamentotarefas.exception.TarefaException;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.Tarefa;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.TarefaDTO;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.TarefaResponseDTO;
import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;
import com.tiagomolero.gerenciamentotarefas.repository.TarefaRepository;
import com.tiagomolero.gerenciamentotarefas.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/tarefa")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private TarefaService tarefaService;

    @PostMapping("/criar")
    public ResponseEntity<TarefaResponseDTO> criarTarefa(@Valid @RequestBody TarefaDTO tarefaDTO){
        TarefaResponseDTO tarefaCriada = tarefaService.criarTarefa(tarefaDTO);
        return ResponseEntity.ok(tarefaCriada);
    }

    @GetMapping
    public ResponseEntity<List<TarefaResponseDTO>> listaTarefas(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String prioridade
    ){
        List<TarefaResponseDTO> tarefaResponseDTOs = tarefaService.listarTarefasComFiltros(status, prioridade);
        return ResponseEntity.ok(tarefaResponseDTOs);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<TarefaResponseDTO> buscarTarefa(@PathVariable(value = "id") UUID id){
        if (!tarefaRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        TarefaResponseDTO TarefaResponseDTO = tarefaService.buscarTarefa(id);
        return ResponseEntity.ok(TarefaResponseDTO);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarTarefa(@Valid @PathVariable(value = "id") UUID id, @RequestBody TarefaDTO tarefaDTO){
        if (!tarefaRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        TarefaResponseDTO tarefaResponseEditadaDTO = tarefaService.editarTarefa(id, tarefaDTO);
        if (tarefaResponseEditadaDTO != null){
            return ResponseEntity.ok(tarefaResponseEditadaDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Você não é o criador dessa tarefa, portanto você não poderá edita-la");
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirTarefa(@PathVariable(value = "id") UUID id){

        if (!tarefaRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        boolean excluiuTarefa = tarefaService.excluirTarefa(id);
        if (excluiuTarefa){
            return ResponseEntity.ok().body("Tarefa excluída com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Você não é o criador dessa tarefa, portanto você não poderá excluí-la");
    }

}

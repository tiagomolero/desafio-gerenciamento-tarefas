package com.tiagomolero.gerenciamentotarefas.controller;

import com.tiagomolero.gerenciamentotarefas.exception.TarefaException;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.Tarefa;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.TarefaDTO;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.TarefaResponseDTO;
import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;
import com.tiagomolero.gerenciamentotarefas.repository.TarefaRepository;
import com.tiagomolero.gerenciamentotarefas.service.TarefaService;
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
    TarefaRepository tarefaRepository;

    @Autowired
    TarefaService tarefaService;

    @PostMapping("/criar")
    public ResponseEntity<TarefaResponseDTO> criarTarefa(@RequestBody TarefaDTO tarefaDTO){
        TarefaResponseDTO tarefaCriada = tarefaService.criarTarefa(tarefaDTO);
        return ResponseEntity.ok(tarefaCriada);
    }

    @GetMapping
    public ResponseEntity<List<TarefaResponseDTO>> listaTarefas(){
        List<TarefaResponseDTO> tarefaResponseDTOs = tarefaService.listarTarefas();
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
    public ResponseEntity<TarefaResponseDTO> editarTarefa(@PathVariable(value = "id") UUID id, @RequestBody TarefaDTO tarefaDTO){
        if (!tarefaRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        try {
            TarefaResponseDTO tarefaResponseEditadaDTO = tarefaService.editarTarefa(id, tarefaDTO);
            return ResponseEntity.ok(tarefaResponseEditadaDTO);
        }catch (TarefaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirTarefa(@PathVariable(value = "id") UUID id){

        if (!tarefaRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        tarefaRepository.deleteById(id);
        return ResponseEntity.ok().body("Tarefa excluída com sucesso");
    }

}

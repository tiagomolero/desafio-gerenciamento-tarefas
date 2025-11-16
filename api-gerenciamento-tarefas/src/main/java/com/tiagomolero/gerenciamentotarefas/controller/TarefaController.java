package com.tiagomolero.gerenciamentotarefas.controller;

import com.tiagomolero.gerenciamentotarefas.exception.TarefaException;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.Tarefa;
import com.tiagomolero.gerenciamentotarefas.model.tarefa.TarefaDTO;
import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;
import com.tiagomolero.gerenciamentotarefas.repository.TarefaRepository;
import com.tiagomolero.gerenciamentotarefas.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<TarefaDTO> criarTarefa(@RequestBody TarefaDTO tarefaDTO){
        TarefaDTO tarefaCriada = tarefaService.criarTarefa(tarefaDTO);
        return ResponseEntity.ok(tarefaCriada);
    }

    @GetMapping
    public ResponseEntity<List<TarefaDTO>> listaTarefas(){
        List<TarefaDTO> tarefaDTOs = tarefaService.listarTarefas();
        return ResponseEntity.ok(tarefaDTOs);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<TarefaDTO> buscarTarefa(@PathVariable(value = "id") UUID id){
        if (!tarefaRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        TarefaDTO tarefaDTO = tarefaService.buscarTarefa(id);
        return ResponseEntity.ok(tarefaDTO);
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

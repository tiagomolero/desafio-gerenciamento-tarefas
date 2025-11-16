package com.tiagomolero.gerenciamentotarefas.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class TarefaException extends RuntimeException{
    public TarefaException(String mensagem){
        super(mensagem);
    }

    public TarefaException(UUID id){
        super("Tarefa não encontrada com ID: " + id);
    }
}

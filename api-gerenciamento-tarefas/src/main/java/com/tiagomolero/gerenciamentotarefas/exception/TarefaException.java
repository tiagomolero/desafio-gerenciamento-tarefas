package com.tiagomolero.gerenciamentotarefas.exception;

import java.util.UUID;

public class TarefaException extends RuntimeException{
    public TarefaException(String mensagem){
        super(mensagem);
    }
    public TarefaException(UUID id){
        super("Tarefa não encontrada com ID: " + id);
    }
}

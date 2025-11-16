package com.tiagomolero.gerenciamentotarefas.exception;

import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String erro;
    private String mensagem;
    private String path;
    private List<FieldError> fieldErrors;

    public ErrorResponse(int status, String erro, String mensagem, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.path = path;
    }

    public ErrorResponse(int status, String erro, String mensagem, String path, List<FieldError> fieldErrors) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.path = path;
        this.fieldErrors = fieldErrors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getErro() {
        return erro;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getPath() {
        return path;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public static class FieldError{
        private String field;
        private String message;
        private Object rejectedValue;

        public FieldError(String field, String message, Object rejectedValue) {
            this.field = field;
            this.message = message;
            this.rejectedValue = rejectedValue;
        }

        // Getters
        public String getField() { return field; }
        public String getMessage() { return message; }
        public Object getRejectedValue() { return rejectedValue; }
    }

}

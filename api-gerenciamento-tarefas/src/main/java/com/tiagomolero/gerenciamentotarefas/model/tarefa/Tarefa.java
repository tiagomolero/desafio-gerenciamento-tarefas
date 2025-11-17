package com.tiagomolero.gerenciamentotarefas.model.tarefa;

import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tarefas")
public class Tarefa {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDENTE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade = Prioridade.MEDIA;

    @ManyToOne
    @JoinColumn(name = "criador_id")
    private Usuario criador;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public Tarefa(){}

    public Tarefa(String titulo, String descricao, Status status, Prioridade prioridade, Usuario criador){
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.prioridade = prioridade;
        this.criador = criador;
    }

    @PrePersist
    protected void onCreate(){
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();

        // Garantir que status tenha um valor padrão se for nulo
        if (status == null || status.toString().isEmpty()) {
            status = Status.PENDENTE;
        }
        if (prioridade == null || prioridade.toString().isEmpty()){
            prioridade = Prioridade.MEDIA;
        }
    }

    @PreUpdate
    protected void onUpdate(){
        dataAtualizacao = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Usuario getCriador() {
        return criador;
    }

    public void setCriador(Usuario criador) {
        this.criador = criador;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

}

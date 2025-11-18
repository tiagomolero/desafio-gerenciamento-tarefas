package com.tiagomolero.gerenciamentotarefas.service;

import com.tiagomolero.gerenciamentotarefas.model.tarefa.*;
import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;
import com.tiagomolero.gerenciamentotarefas.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TarefaServiceTest {

    @InjectMocks
    private TarefaService tarefaService;

    @Mock
    private AuthorizationService authorizationService;

    @Mock
    private TarefaRepository tarefaRepository;

    private Usuario usuario;
    private UUID tarefaId;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario("Teste", "teste@teste.com", "123");
        usuario.setId(UUID.randomUUID());
        tarefaId = UUID.randomUUID();

        when(authorizationService.getUsuarioLogado()).thenReturn(usuario);
    }

    @Test
    void deveCriarTarefaComPadroes() {
        TarefaDTO dto = new TarefaDTO("Título", "Descrição", null, null);
        Tarefa tarefaSalva = new Tarefa("Título", "Descrição", Status.PENDENTE, Prioridade.MEDIA, usuario);

        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefaSalva);

        var response = tarefaService.criarTarefa(dto);

        assertEquals(Status.PENDENTE, response.status());
        assertEquals(Prioridade.MEDIA, response.prioridade());
        verify(tarefaRepository, times(1)).save(any());
    }

    @Test
    void deveAlterarStatusDePendenteParaEmProgresso() {
        Tarefa existente = new Tarefa("T1", "Desc", Status.PENDENTE, Prioridade.MEDIA, usuario);
        existente.setId(tarefaId);

        when(tarefaRepository.findByIdAndCriadorId(tarefaId, usuario.getId())).thenReturn(existente);
        when(tarefaRepository.save(existente)).thenReturn(existente);

        TarefaDTO dto = new TarefaDTO("T1", "Desc", Status.EM_PROGRESSO, Prioridade.MEDIA);

        var response = tarefaService.editarTarefa(tarefaId, dto);

        assertEquals(Status.EM_PROGRESSO, response.status());
    }

    @Test
    void deveLancarErroQuandoStatusRetrocede() {
        Tarefa existente = new Tarefa("T1", "Desc", Status.CONCLUIDA, Prioridade.MEDIA, usuario);

        when(tarefaRepository.findByIdAndCriadorId(tarefaId, usuario.getId())).thenReturn(existente);

        TarefaDTO dto = new TarefaDTO("T1", "Desc", Status.EM_PROGRESSO, Prioridade.MEDIA);

        assertThrows(IllegalArgumentException.class,
                () -> tarefaService.editarTarefa(tarefaId, dto));
    }
}

package com.tiagomolero.gerenciamentotarefas.service;

import com.tiagomolero.gerenciamentotarefas.model.tarefa.Status;
import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;
import com.tiagomolero.gerenciamentotarefas.repository.TarefaRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DashboardServiceTest {

    @InjectMocks
    private DashboardService dashboardService;

    @Mock
    private AuthorizationService authorizationService;

    @Mock
    private TarefaRepository tarefaRepository;

    private Usuario usuario;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario("Teste", "teste@teste.com", "123");
        usuario.setId(java.util.UUID.randomUUID());
        when(authorizationService.getUsuarioLogado()).thenReturn(usuario);
    }

    @Test
    void deveGerarDashboardCorretamente() {
        when(tarefaRepository.countByCriadorIdAndStatus(usuario.getId(), Status.PENDENTE)).thenReturn(3L);
        when(tarefaRepository.countByCriadorIdAndStatus(usuario.getId(), Status.EM_PROGRESSO)).thenReturn(2L);
        when(tarefaRepository.countByCriadorIdAndStatus(usuario.getId(), Status.CONCLUIDA)).thenReturn(5L);

        var dashboard = dashboardService.gerarDashboard();

        assertEquals(3, dashboard.pendentes());
        assertEquals(2, dashboard.emProgresso());
        assertEquals(5, dashboard.concluídas());
    }
}

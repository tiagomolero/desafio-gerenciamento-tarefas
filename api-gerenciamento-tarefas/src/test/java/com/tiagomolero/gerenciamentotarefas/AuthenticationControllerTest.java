package com.tiagomolero.gerenciamentotarefas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiagomolero.gerenciamentotarefas.model.usuario.AuthenticationDTO;
import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;
import com.tiagomolero.gerenciamentotarefas.security.TokenService;
import com.tiagomolero.gerenciamentotarefas.service.AuthorizationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
@Import(com.tiagomolero.gerenciamentotarefas.controller.TestSecurityConfig.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private AuthorizationService authorizationService;

    @MockitoBean
    private com.tiagomolero.gerenciamentotarefas.repository.UsuarioRepository usuarioRepository;

    @Test
    void deveRealizarLoginComSucesso() throws Exception {
        Usuario usuario = new Usuario("Teste", "teste@teste.com", "123");
        usuario.setId(java.util.UUID.randomUUID());

        // Quando autenticar, retornar um Authentication válido
        when(authenticationManager.authenticate(any()))
                .thenReturn(new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()));

        // Mock do token
        when(tokenService.generateToken(usuario)).thenReturn("token-falso");

        AuthenticationDTO dto = new AuthenticationDTO("teste@teste.com", "abcdef123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-falso"));
    }
}

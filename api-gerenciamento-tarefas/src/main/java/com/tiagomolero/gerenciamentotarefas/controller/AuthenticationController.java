package com.tiagomolero.gerenciamentotarefas.controller;

import com.tiagomolero.gerenciamentotarefas.model.usuario.AuthenticationDTO;
import com.tiagomolero.gerenciamentotarefas.model.usuario.LoginResponseDTO;
import com.tiagomolero.gerenciamentotarefas.model.usuario.RegisterDTO;
import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;
import com.tiagomolero.gerenciamentotarefas.repository.UsuarioRepository;
import com.tiagomolero.gerenciamentotarefas.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthenticationDTO authenticationDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDto.email(), authenticationDto.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO registerDTO){
        if (this.usuarioRepository.findByEmail(registerDTO.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.senha());
        Usuario novoUsuario = new Usuario(registerDTO.nome(), registerDTO.email(), encryptedPassword);

        this.usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok().build();
    }

}

package com.tiagomolero.gerenciamentotarefas.controller;

import com.tiagomolero.gerenciamentotarefas.model.Usuario;
import com.tiagomolero.gerenciamentotarefas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping("/cadastrar")
    public Usuario cadastrarUsuario(@RequestBody Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    @GetMapping("/listar")
    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

}

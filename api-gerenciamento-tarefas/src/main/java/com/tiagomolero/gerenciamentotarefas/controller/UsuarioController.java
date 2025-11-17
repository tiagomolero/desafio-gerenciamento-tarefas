package com.tiagomolero.gerenciamentotarefas.controller;

import com.tiagomolero.gerenciamentotarefas.model.usuario.Usuario;
import com.tiagomolero.gerenciamentotarefas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/listar")
    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

}

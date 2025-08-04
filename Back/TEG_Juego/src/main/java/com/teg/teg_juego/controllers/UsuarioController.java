package com.teg.teg_juego.controllers;



import com.teg.teg_juego.Service.UsuarioService;
import com.teg.teg_juego.model.DTO.loginDTO;
import com.teg.teg_juego.model.DTO.usuarioDTO;
import com.teg.teg_juego.model.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/Usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<usuarioDTO> listarTodos() {
        return usuarioService.getAll();
    }

    @GetMapping("/{id}")
    public usuarioDTO obtenerPorId(@PathVariable Integer id) {
        return usuarioService.getById(id);
    }

    @PostMapping("/register")
    public usuarioDTO crear(@RequestBody loginDTO usuario) {
        return usuarioService.create(usuario);
    }

    @PutMapping("/{id}")
    public usuarioDTO actualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        return usuarioService.update(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        usuarioService.delete(id);
    }

    @PostMapping("/login")
    public usuarioDTO login(@RequestBody loginDTO loginDto) {
        return usuarioService.login(loginDto);
    }
}

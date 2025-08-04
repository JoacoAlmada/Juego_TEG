package com.teg.teg_juego.Service;


import com.teg.teg_juego.Repository.usuarioRepository;
import com.teg.teg_juego.model.DTO.loginDTO;
import com.teg.teg_juego.model.DTO.usuarioDTO;
import com.teg.teg_juego.model.entities.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private usuarioRepository UsuarioRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<usuarioDTO> getAll() {
        return UsuarioRepository.findAll().stream()
                .map(user -> modelMapper.map(user, usuarioDTO.class))
                .collect(Collectors.toList());
    }

    public usuarioDTO getById(Integer id) {
        Usuario usuario = UsuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrada"));
        return modelMapper.map(usuario, usuarioDTO.class);
    }

    public usuarioDTO create(loginDTO loginDTO) {
        Usuario registrado = modelMapper.map(loginDTO, Usuario.class);
        registrado.setNivel(1);
        return modelMapper.map(UsuarioRepository.save(registrado), usuarioDTO.class);
    }

    public usuarioDTO update(Integer id, Usuario nuevoUsuario) {
        Usuario actual = UsuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        actual.setNombre(nuevoUsuario.getNombre());
        actual.setContrasenia(nuevoUsuario.getContrasenia());
        actual.setNivel(nuevoUsuario.getNivel());
        return modelMapper.map(UsuarioRepository.save(actual), usuarioDTO.class);
    }

    public void delete(Integer id) {
        UsuarioRepository.deleteById(id);
    }

    public usuarioDTO login(loginDTO loginDto) {
        Usuario usuario = UsuarioRepository.findAll().stream()
                .filter(u -> u.getNombre().equals(loginDto.getNombre()) &&
                        u.getContrasenia().equals(loginDto.getContrasenia()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        return modelMapper.map(usuario, usuarioDTO.class);
    }
}
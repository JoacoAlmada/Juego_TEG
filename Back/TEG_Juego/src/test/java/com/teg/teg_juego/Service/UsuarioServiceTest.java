package com.teg.teg_juego.Service;


import com.teg.teg_juego.Repository.usuarioRepository;
import com.teg.teg_juego.model.DTO.loginDTO;
import com.teg.teg_juego.model.DTO.usuarioDTO;
import com.teg.teg_juego.model.entities.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static com.teg.teg_juego.model.enums.TipoJugador.HUMANO;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private usuarioRepository usuarioRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void getAll() {
        Usuario user = new Usuario(1, "TegUser", "1234", 1, HUMANO);
        usuarioDTO dto = new usuarioDTO(1, "TegUser", "1234", 1);

        List<Usuario> usuarios = List.of(user);
        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);
        Mockito.when(modelMapper.map(user, usuarioDTO.class)).thenReturn(dto);

        List<usuarioDTO> resultado = usuarioService.getAll();

        assertEquals(1, resultado.size());
        assertEquals("TegUser", resultado.get(0).getNombre());
        assertEquals("1234", resultado.get(0).getContrasenia());
    }

    @Test
    void getById() {
        Usuario user = new Usuario(1, "TegUser", "1234", 1, HUMANO);
        usuarioDTO dto = new usuarioDTO(1, "TegUser", "1234", 1);

        Mockito.when(usuarioRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(modelMapper.map(user, usuarioDTO.class)).thenReturn(dto);

        usuarioDTO resultado = usuarioService.getById(1);

        assertNotNull(resultado);
        assertEquals("TegUser", resultado.getNombre());
    }

    @Test
    void testGetById_UsuarioNoEncontrado() {
        Mockito.when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usuarioService.getById(99));
    }

    @Test
    void create() {
        loginDTO login = new loginDTO("TegUser", "1234");

        Usuario usuarioMapeado = new Usuario(null, "TegUser", "1234", 0, HUMANO); // inicial
        Usuario usuarioGuardado = new Usuario(1, "TegUser", "1234", 1, HUMANO);   // con ID y nivel 1
        usuarioDTO dtoEsperado = new usuarioDTO(1, "TegUser", "1234", 1);

        Mockito.when(modelMapper.map(login, Usuario.class)).thenReturn(usuarioMapeado);
        Mockito.when(usuarioRepository.save(usuarioMapeado)).thenReturn(usuarioGuardado);
        Mockito.when(modelMapper.map(usuarioGuardado, usuarioDTO.class)).thenReturn(dtoEsperado);

        usuarioDTO resultado = usuarioService.create(login);

        assertNotNull(resultado);
        assertEquals("TegUser", resultado.getNombre());
        assertEquals(1, resultado.getNivel());
    }

    @Test
    void update() {
        Usuario usuarioExistente = new Usuario(1, "OldUser", "oldpass", 1, HUMANO);
        Usuario nuevoUsuario = new Usuario(null, "NewUser", "newpass", 2, HUMANO); // no importa el id

        Usuario actualizado = new Usuario(1, "NewUser", "newpass", 2, HUMANO);
        usuarioDTO dtoEsperado = new usuarioDTO(1, "NewUser", "newpass", 2);

        Mockito.when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioExistente));
        Mockito.when(usuarioRepository.save(usuarioExistente)).thenReturn(actualizado);
        Mockito.when(modelMapper.map(actualizado, usuarioDTO.class)).thenReturn(dtoEsperado);

        usuarioDTO resultado = usuarioService.update(1, nuevoUsuario);

        assertEquals("NewUser", resultado.getNombre());
        assertEquals("newpass", resultado.getContrasenia());
        assertEquals(2, resultado.getNivel());
    }

    @Test
    void testUpdate_UsuarioNoExistente() {
        Usuario nuevoUsuario = new Usuario(null, "NewUser", "newpass", 2, HUMANO);
        Mockito.when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usuarioService.update(99, nuevoUsuario));
    }


    @Test
    void delete() {
        Integer id = 1;

        usuarioService.delete(id);

        Mockito.verify(usuarioRepository).deleteById(id);
    }

    @Test
    void login() {
        loginDTO login = new loginDTO("TegUser", "1234");

        Usuario usuario = new Usuario(1, "TegUser", "1234", 1, HUMANO);
        usuarioDTO dtoEsperado = new usuarioDTO(1, "TegUser", "1234", 1);

        List<Usuario> usuarios = List.of(usuario);

        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);
        Mockito.when(modelMapper.map(usuario, usuarioDTO.class)).thenReturn(dtoEsperado);

        usuarioDTO resultado = usuarioService.login(login);

        assertNotNull(resultado);
        assertEquals("TegUser", resultado.getNombre());
    }

    @Test
    void testLogin_CredencialesInvalidas() {
        loginDTO login = new loginDTO("UserX", "wrongpass");

        Usuario usuario = new Usuario(1, "TegUser", "1234", 1, HUMANO);

        Mockito.when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        assertThrows(RuntimeException.class, () -> usuarioService.login(login));
    }
}
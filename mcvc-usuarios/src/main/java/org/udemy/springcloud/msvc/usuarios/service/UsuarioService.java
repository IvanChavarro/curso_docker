package org.udemy.springcloud.msvc.usuarios.service;

import java.util.List;
import java.util.Optional;

import org.udemy.springcloud.msvc.usuarios.models.entity.Usuario;

public interface UsuarioService {
	public List<Usuario> listar();

	public Optional<Usuario> listarPorId(Long id) throws Exception;

	public Usuario guardarUsuario(Usuario usuario);

	public void eliminarUsuario(Long id);
	
	public Optional<Usuario>porEmail(String email);
	
	public List<Usuario>listarPorVariosId(Iterable<Long> ids);
}

package org.udemy.springcloud.msvc.usuarios.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udemy.springcloud.msvc.usuarios.clients.CursosClientRest;
import org.udemy.springcloud.msvc.usuarios.models.entity.Usuario;
import org.udemy.springcloud.msvc.usuarios.repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private CursosClientRest cursoClientRest;

	@Override
	public List<Usuario> listar() {
		log.info("starting listar at " + new Date());
		return usuarioRepository.findAll();
	}

	@Override
	public Optional<Usuario> listarPorId(Long id) throws Exception {
		log.info("starting listarPorId at " + new Date());
		return usuarioRepository.findById(id);
	}

	@Override
	public Usuario guardarUsuario(Usuario usuario) {
		log.info("starting guardarUsuario at " + new Date());
		if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {

		}
		return usuarioRepository.save(usuario);
	}

	@Override
	public void eliminarUsuario(Long id) {
		log.info("starting eliminarUsuario at " + new Date());
		cursoClientRest.deleteFromCursoUsuario(id);
		usuarioRepository.deleteById(id);
	}

	@Override
	public Optional<Usuario> porEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

	@Override
	public List<Usuario> listarPorVariosId(Iterable<Long> ids) {
		return usuarioRepository.findAllById(ids);
	}

}

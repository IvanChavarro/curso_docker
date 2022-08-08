package org.udemy.springcloud.msvc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udemy.springcloud.msvc.clients.UsuarioClientRest;
import org.udemy.springcloud.msvc.models.Usuario;
import org.udemy.springcloud.msvc.models.entity.CursoUsuario;
import org.udemy.springcloud.msvc.models.entity.Cursos;
import org.udemy.springcloud.msvc.repository.CursosRepository;

@Service
public class CursosServiceImpl implements CursosService {

	@Autowired
	private CursosRepository repository;

	@Autowired
	private UsuarioClientRest usuarioClientRest;

	@Override
	public List<Cursos> listar() {
		return repository.findAll();
	}

	@Override
	public Optional<Cursos> porId(Long id) {
		Optional<Cursos> curso = repository.findById(id);
		if (curso.isPresent()) {
			Cursos cursos = curso.get();
			List<Long> idsUsuarios = new ArrayList<>();
			cursos.getCursoUsuarios().forEach(cu -> {
				idsUsuarios.add(cu.getUsuarioId());
			});
			List<Usuario> usuarios = usuarioClientRest.usuariosPorCurso(idsUsuarios);
			cursos.setUsuarios(usuarios);
			repository.save(cursos);
			return Optional.of(cursos);
		}
		return Optional.empty();
	}

	@Override
	public Cursos guardar(Cursos curso) {

		return repository.save(curso);
	}

	@Override
	public void eliminar(Long id) {
		if (!porId(id).isEmpty()) {
			repository.deleteById(id);
		}
	}

	@Override
	public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
		Optional<Cursos> opt = repository.findById(cursoId);
		if (opt.isPresent()) {
			Usuario usuarioMsvc = usuarioClientRest.detalle(usuario.getId());
			Cursos curso = opt.get();
			CursoUsuario cursoUsuario = CursoUsuario.builder().usuarioId(usuarioMsvc.getId()).build();
			curso.addCursoUsuarios(cursoUsuario);
			repository.save(curso);
			return Optional.of(usuarioMsvc);
		}
		return Optional.empty();
	}

	@Override
	public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
		Optional<Cursos> opt = repository.findById(cursoId);
		if (opt.isPresent()) {
			Usuario usuarioNuevo = usuarioClientRest.guardar(usuario);
			Cursos curso = opt.get();
			CursoUsuario cursoUsuario = CursoUsuario.builder().usuarioId(usuarioNuevo.getId()).build();
			curso.addCursoUsuarios(cursoUsuario);
			repository.save(curso);
			return Optional.of(usuarioNuevo);
		}
		return Optional.empty();
	}

	@Override
	public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
		Optional<Cursos> opt = repository.findById(cursoId);
		if (opt.isPresent()) {
			Usuario usuarioMsvc = usuarioClientRest.detalle(usuario.getId());
			Cursos curso = opt.get();
			CursoUsuario cursoUsuario = CursoUsuario.builder().usuarioId(usuarioMsvc.getId()).build();
			curso.removeCursoUsuario(cursoUsuario);
			repository.save(curso);
			return Optional.of(usuarioMsvc);
		}
		return Optional.empty();
	}


	@Override
	public void eliminarCursoUsuarioPorId(Long id) {
		repository.deleteFromCursoUsuario(id);
	}

}

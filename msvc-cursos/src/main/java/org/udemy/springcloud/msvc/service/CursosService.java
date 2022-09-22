package org.udemy.springcloud.msvc.service;

import java.util.List;
import java.util.Optional;

import org.udemy.springcloud.msvc.models.Usuario;
import org.udemy.springcloud.msvc.models.entity.Cursos;

public interface CursosService {
	public List<Cursos> listar();

	public Optional<Cursos> porId(Long id);

	public Cursos guardar(Cursos curso);

	public void eliminar(Long id);

	// Métodos relacionados al microservicio usuario
	public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId, String token);

	public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId, String token);

	public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId, String token);

	public void eliminarCursoUsuarioPorId(Long id);
}

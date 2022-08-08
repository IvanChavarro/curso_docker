package org.udemy.springcloud.msvc.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.udemy.springcloud.msvc.models.entity.Cursos;

import feign.Param;

public interface CursosRepository extends JpaRepository<Cursos, Long> {
	@Transactional
	@Modifying
	@Query(value = "DELETE from CursoUsuario cu where cu.usuarioId = :idUsuario")
	public void deleteFromCursoUsuario(@Param(value = "idUsuario") Long idUsuario);
}

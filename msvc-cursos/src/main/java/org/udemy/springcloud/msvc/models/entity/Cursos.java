package org.udemy.springcloud.msvc.models.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.udemy.springcloud.msvc.models.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "cursos")
@Data
@AllArgsConstructor
public class Cursos {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "El nombre del curso no puede ir vacío")
	private String nombre;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "curso_id")
	private List<CursoUsuario> cursoUsuarios;

	@Transient // Un atributo no mapeado a tabla
	private List<Usuario> usuarios;

	public Cursos() {
		cursoUsuarios = new ArrayList<>();
		usuarios = new ArrayList<>();
	}

	public void addCursoUsuarios(CursoUsuario cursoUsuario) {
		cursoUsuarios.add(cursoUsuario);
	}

	public void removeCursoUsuario(CursoUsuario cursoUsuario) {
		cursoUsuarios.remove(cursoUsuario);
	}

}

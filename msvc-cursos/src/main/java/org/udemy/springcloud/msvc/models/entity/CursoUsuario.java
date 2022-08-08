package org.udemy.springcloud.msvc.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cursos_usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoUsuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "usuario_id")
	private Long usuarioId;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CursoUsuario)) {
			return false;
		}
		CursoUsuario o = (CursoUsuario) obj;
		return this.usuarioId != null && this.usuarioId.equals(o.usuarioId);

	}
}

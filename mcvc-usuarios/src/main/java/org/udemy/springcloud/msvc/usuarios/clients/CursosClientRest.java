package org.udemy.springcloud.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url = "${msvc.cursos.url}")
public interface CursosClientRest {
	@DeleteMapping("/eliminar-usuario-curso/{id}")
	public void deleteFromCursoUsuario(@PathVariable Long id);
}

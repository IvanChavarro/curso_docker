package org.udemy.springcloud.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "msvc-cursos", url = "${msvc.cursos.url}") Está con el puerto y el ip
@FeignClient(name = "msvc-cursos/cursos") // Ahora va a estar desacoplado y funcionará con solo nombre de servicio
public interface CursosClientRest {
	@DeleteMapping("/eliminar-usuario-curso/{id}")
	public void deleteFromCursoUsuario(@PathVariable Long id);
}

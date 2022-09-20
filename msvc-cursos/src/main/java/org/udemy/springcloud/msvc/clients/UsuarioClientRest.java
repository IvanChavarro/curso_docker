package org.udemy.springcloud.msvc.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.udemy.springcloud.msvc.models.Usuario;

//@FeignClient(name = "msvc-usuarios", url = "${msvc.usuarios.url}")
@FeignClient(name = "msvc-usuarios/usuarios")
public interface UsuarioClientRest {
	@GetMapping(value = "/{id}")
	public Usuario detalle(@PathVariable Long id);

	@PostMapping
	public Usuario guardar(@RequestBody Usuario usuario);

	@GetMapping(value = "usuarios-por-curso")
	public List<Usuario> usuariosPorCurso(@RequestParam List<Long> ids);
}

package org.udemy.springcloud.msvc.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.udemy.springcloud.msvc.models.Usuario;
import org.udemy.springcloud.msvc.models.entity.Cursos;
import org.udemy.springcloud.msvc.service.CursosService;

import feign.FeignException;

@RestController
@RequestMapping(value = "/cursos")
public class CursosController {

	@Autowired
	private CursosService service;

	@GetMapping
	private ResponseEntity<?> listarTodos() {
		return ResponseEntity.ok(service.listar());
	}

	@GetMapping(value = "/{id}")
	private ResponseEntity<?> listarPorId(@PathVariable Long id) {
		if (!service.porId(id).isEmpty()) {
			return ResponseEntity.ok(service.porId(id));
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	private ResponseEntity<?> guardar(@Valid @RequestBody Cursos cursos, BindingResult result) {
		Map<String, String> errores = new HashMap<>();
		if (result.hasErrors()) {
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(), err.getDefaultMessage());
			});
			return ResponseEntity.badRequest().body(errores);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cursos));
	}

	@DeleteMapping(value = "/{id}")
	private ResponseEntity<?> eliminarPorId(@PathVariable Long id) {
		service.eliminar(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping(value = "/asignar-usuario/{cursoId}")
	private ResponseEntity<?> asignarCursoUsuario(@PathVariable Long cursoId, @RequestBody Usuario usuario,
			@RequestHeader(value = "Authorization", required = true) String token) {
		Optional<Usuario> optional = null;
		try {
			optional = service.asignarUsuario(usuario, cursoId, token);
			if (optional.isPresent()) {
				return ResponseEntity.status(HttpStatus.CREATED).body(optional.get());
			}
			return ResponseEntity.notFound().build();
		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("Error", e.getMessage()));
		}

	}

	@PostMapping(value = "/crear-usuario/{cursoId}")
	private ResponseEntity<?> crearCursoUsuario(@PathVariable Long cursoId, @RequestBody Usuario usuario,
			@RequestHeader(value = "Authorization", required = true) String token) {
		Optional<Usuario> optional = null;
		try {
			optional = service.crearUsuario(usuario, cursoId, token);
			if (optional.isPresent()) {
				return ResponseEntity.status(HttpStatus.CREATED).body(optional.get());
			}
			return ResponseEntity.notFound().build();
		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("Error", e.getMessage()));
		}

	}

	@DeleteMapping(value = "/eliminar-usuario/{cursoId}")
	private ResponseEntity<?> eliminarCursoUsuario(@PathVariable Long cursoId, @RequestBody Usuario usuario,
			@RequestHeader(value = "Authorization", required = true) String token) {
		Optional<Usuario> optional = null;
		try {
			optional = service.eliminarUsuario(usuario, cursoId, token);
			if (optional.isPresent()) {
				return ResponseEntity.status(HttpStatus.OK).body(optional.get());
			}
			return ResponseEntity.notFound().build();
		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("Error", e.getMessage()));
		}

	}

	@DeleteMapping("/eliminar-usuario-curso/{id}")
	private ResponseEntity<?> deleteFromCursoUsuario(@PathVariable Long id) {
		service.eliminarCursoUsuarioPorId(id);
		return ResponseEntity.noContent().build();
	}

}

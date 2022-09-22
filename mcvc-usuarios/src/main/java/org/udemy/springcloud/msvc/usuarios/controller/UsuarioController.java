package org.udemy.springcloud.msvc.usuarios.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.udemy.springcloud.msvc.usuarios.models.entity.Usuario;
import org.udemy.springcloud.msvc.usuarios.service.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {
	@Autowired
	private UsuarioService service;

	@Autowired
	private ApplicationContext context;

	@Autowired
	private Environment env;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping(value = "/crash")
	public void crash() {
		((ConfigurableApplicationContext) context).close();
	}

	@GetMapping
	public ResponseEntity<?> listarTodos() {
		Map<String, Object> body = new HashMap<>();
		body.put("usuarios", service.listar());
		body.put("texto", env.getProperty("config.texto"));
		return ResponseEntity.ok(body);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> listarTodos(@PathVariable(required = true) Long id) throws Exception {
		Optional<Usuario> usuario = service.listarPorId(id);
		if (usuario.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(usuario);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/email/{email}")
	public ResponseEntity<?> listarPorEmail(@PathVariable(required = true) String email) throws Exception {
		Optional<Usuario> usuario = service.porEmail(email);
		if (usuario.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(usuario);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/usuarios-por-curso")
	public ResponseEntity<?> listarTodosPorId(@RequestParam List<Long> ids) throws Exception {
		return ResponseEntity.ok(service.listarPorVariosId(ids));
	}

	@PostMapping
	public ResponseEntity<?> guardar(@Valid @RequestBody Usuario usuario, BindingResult result) throws Exception {
		if (usuario.getId() != null) {
			if (service.listarPorId(usuario.getId()).isPresent()) {
				if (!usuario.getEmail().equalsIgnoreCase(service.listarPorId(usuario.getId()).get().getEmail())
						&& service.porEmail(usuario.getEmail()).isPresent()) {
					return validarCorreo(usuario);
				}
			}
		} else {
			if (service.porEmail(usuario.getEmail()).isPresent()) {
				return validarCorreo(usuario);
			}
		}
		if (result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(e -> {
				errores.put(e.getField(), e.getDefaultMessage());
			});
			return ResponseEntity.badRequest().body(errores);
		}
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		return ResponseEntity.status(HttpStatus.CREATED).body(service.guardarUsuario(usuario));
	}

	private ResponseEntity<?> validarCorreo(Usuario usuario) {
		// mensaje de error personalizado con la clase singleton del jdk
		return ResponseEntity.badRequest().body(
				Collections.singletonMap("Error", "Ya existe!!!! un usuario con el correo " + usuario.getEmail()));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) throws Exception {
		Optional<Usuario> usuario = service.listarPorId(id);
		if (usuario.isPresent()) {
			service.eliminarUsuario(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		return ResponseEntity.notFound().build();
	}
}

package org.udemy.springcloud.msvc.usuarios.controller;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.udemy.springcloud.msvc.usuarios.models.entity.Usuario;
import org.udemy.springcloud.msvc.usuarios.service.UsuarioService;

@RestController
@RequestMapping
public class AuthController {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping("/authorized")
	public Map<String, Object> authorized(@RequestParam(name = "code") String code) {
		return Collections.singletonMap("code", code);
	}
	
	@GetMapping("/login")
	public ResponseEntity<?>loginByEmail(@RequestParam String email){
		Optional<Usuario> o =  service.porEmail(email);
		if(o.isPresent()) {
			return ResponseEntity.ok(o.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
}

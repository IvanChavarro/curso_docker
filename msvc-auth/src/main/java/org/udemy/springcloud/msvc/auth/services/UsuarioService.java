package org.udemy.springcloud.msvc.auth.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.udemy.springcloud.msvc.auth.models.Usuario;

import lombok.extern.slf4j.Slf4j;

// clase de inyección de UserDetailService para el login de forma personalizada
@Service
@Slf4j
public class UsuarioService implements UserDetailsService {

	@Autowired
	private WebClient.Builder client;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			// el client maneja tipos de request (CRUD)
			Usuario usuario = client.build().get()
					.uri("http://msvc-usuarios/login", uri -> uri.queryParam("email", email).build())
					.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Usuario.class).block();

			log.info("Usuario login: " + usuario.getEmail());
			log.info("Usuario nombre: " + usuario.getName());
			log.info("Usuario password: " + usuario.getPassword());
			return new User(email, usuario.getPassword(), true, true, true, true,
					Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			throw new UsernameNotFoundException("User " + email + " not found");
		}
	}

}

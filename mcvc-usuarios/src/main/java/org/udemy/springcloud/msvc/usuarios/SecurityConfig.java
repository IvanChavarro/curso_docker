package org.udemy.springcloud.msvc.usuarios;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class SecurityConfig {
/*
	@Bean // notación que registra un objeto como un componente en el contexto de spring
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// todas las paginas requieren autenticación
		http.authorizeRequests().anyRequest().authenticated()
		// paths con autorización publica o con permisos de lectura, escritura o ambos
		.antMatchers("/authorized").permitAll()
		.antMatchers(HttpMethod.GET).hasAnyAuthority("SCOPE_read", "SCOPE_write")
		.antMatchers(HttpMethod.POST).hasAuthority("SCOPE_write")
		.antMatchers(HttpMethod.DELETE).hasAuthority("SCOPE_write")
		.and()
				// evitamos que se guarden credenciales de cliente y obligamos que siempre sea
				// la autenticación por token
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// definimos el EP de login
				// nombre
				// del
				// yaml
				// del
				// cliente en el login
				.oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/msvc-usuarios-client"))
				.oauth2Client(withDefaults())
				.oauth2ResourceServer().jwt();

		return http.build();
	}
*/

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/authorized", "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/", "/{id}").hasAnyAuthority("SCOPE_read", "SCOPE_write")
                .antMatchers(HttpMethod.POST, "/").hasAuthority("SCOPE_write")
                .antMatchers(HttpMethod.DELETE, "/").hasAuthority("SCOPE_write")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/msvc-usuarios-client"))
                .oauth2Client(withDefaults())
                .oauth2ResourceServer().jwt();

        return http.build();
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}

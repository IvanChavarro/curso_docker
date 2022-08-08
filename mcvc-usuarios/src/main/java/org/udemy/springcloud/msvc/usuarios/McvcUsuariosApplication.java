package org.udemy.springcloud.msvc.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients // Habilita a la aplicación el contexto de feign
public class McvcUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(McvcUsuariosApplication.class, args);
	}

}

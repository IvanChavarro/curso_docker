package org.udemy.springcloud.msvc.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Usuario {

	private Long Id;

	private String nombre;

	private String email;

	private String password;
}

package org.udemy.springcloud.msvc.auth.models;

import lombok.Data;

@Data
public class Usuario {
	private Long Id;

	private String name;

	private String email;

	private String password;
}

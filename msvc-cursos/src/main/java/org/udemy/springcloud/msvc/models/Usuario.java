package org.udemy.springcloud.msvc.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Usuario {

	private Long Id;

	private String name;

	private String email;

	private String password;
}

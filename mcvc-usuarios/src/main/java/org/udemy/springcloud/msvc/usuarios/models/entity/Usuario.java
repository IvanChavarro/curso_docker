package org.udemy.springcloud.msvc.usuarios.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@NotBlank(message = "El name no debe ser vacío")
	private String name;
	@Column(unique = true)
	@NotBlank(message = "El email no debe ser vacío")
	@Email(message = "El email debe estar escrito correctamente")
	private String email;
	@NotBlank(message = "El password no debe ser vacío")
	private String password;

}

package com.betancourt.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.betancourt.backend.domain.enums.UserRole;
import com.betancourt.backend.model.Usuario;
import com.betancourt.backend.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

	@Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {
	}

	@Test
	void jwtCanBeValidatedAndCarriesUserIdentity() {
		Usuario usuario = Usuario.builder()
				.id(7L)
				.email("test@example.com")
				.role(UserRole.CLIENTE)
				.build();

		String token = jwtService.generarToken(usuario);

		assertEquals("test@example.com", jwtService.extractUsername(token));
		assertTrue(jwtService.isTokenValid(token, "test@example.com"));
		assertFalse(jwtService.isTokenValid(token, "other@example.com"));
		assertEquals(7, jwtService.obtenerIdUsuario(token));
	}
}


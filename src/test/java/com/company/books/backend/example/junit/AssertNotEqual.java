package com.company.books.backend.example.junit;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;


/*
 * Este metodo nos sirve para identificar si son resultados opuestos a lo esperado, en este caso
 * recibe 2 pero espera que sea 1, entonces este metodo es valido
 */
public class AssertNotEqual {

	@Test
	void myTest() {
		assertNotEquals(1, 2);
	}
}

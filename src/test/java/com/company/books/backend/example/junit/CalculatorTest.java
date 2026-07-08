package com.company.books.backend.example.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CalculatorTest {

	@Test
	void testCalulator() {
		Calulator calcu = new Calulator();
		assertEquals(2, calcu.sum(1,1));
	}
	@Test
	void testCalulatorTrueOrFalse() {
		Calulator calcu = new Calulator();
		assertTrue(calcu.sum(2, 2)==4);
	}
}

package com.company.books.backend.example.junit;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class assertArraysEqualsTest {

	Calulator calcu;
	String  [] array1 = {"a","b"};
	String  [] array2 = {"b","a"};
	String  [] array3 = {"a","b"};
	List<String> list1 = new ArrayList<>(Arrays.asList("a", "b"));

	
	@BeforeAll
	static void printFirst() {
		System.out.println("Esta anotacion se ejecuta al PRINCIPIO de todo");
	}
	
	@AfterAll
	static void printLast() {
		System.out.println("Esta anotacion se ejecuta a lo ULTIMO de todo");
	}
	
	@BeforeEach
	 void CalculatorInst() {
		 calcu = new Calulator();
		System.out.println("Esto suma 10.5 + 5.5 = "+ calcu.sum(10.5, 5.5));
	}
	
	@AfterEach
	void CalculatorAfter() {
		 calcu = new Calulator();
		System.out.println("Esto multiplica 25.43 + 4.4 = "+ calcu.mult(25.43, 4.4));
	}
	
	@Test
	@DisplayName("Esta anotacion nos da  a elegir el nombre de la misma")
	void TrueOrFalse() {
	assertArrayEquals(array1, array3);
	System.out.println("Se logro encontrar equivalencia entre los arreglos");
	}

	@Test
	@Disabled("Esta prueba no se utilizara por que la desabilita")
	void CalculatorUsingDisable() {
		 calcu = new Calulator();
		System.out.println("Esto resta y da: "+ calcu.res(25, 4));
	}
}



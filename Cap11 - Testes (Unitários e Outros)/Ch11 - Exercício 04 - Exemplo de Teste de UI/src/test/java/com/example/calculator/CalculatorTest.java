package com.example.calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    Calculator calculator = new Calculator();

    @Test
    void testAddition() {
        assertEquals(5, calculator.add(2, 3));
        assertEquals(5.5, calculator.add(2.5, 3.0));
    }

    @Test
    void testSubtraction() {
        assertEquals(2, calculator.subtract(5, 3));
        assertEquals(-0.5, calculator.subtract(2.5, 3.0));
    }

    @Test
    void testMultiplication() {
        assertEquals(15, calculator.multiply(3, 5));
        assertEquals(7.5, calculator.multiply(2.5, 3.0));
    }

    @Test
    void testDivision() {
        assertEquals(2, calculator.divide(10, 5));
        assertEquals(2.5, calculator.divide(5.0, 2.0));
    }

    @Test
    void testDivisionByZero() {
        Exception exception = assertThrows(ArithmeticException.class, () -> calculator.divide(10, 0));
        assertEquals("Division by zero is not allowed", exception.getMessage());
    }

    @Test
    void testSquareRoot() {
        assertEquals(3, calculator.squareRoot(9));
        assertEquals(2.5, calculator.squareRoot(6.25));
    }

    @Test
    void testSquareRootOfNegativeNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> calculator.squareRoot(-1));
        assertEquals("Cannot calculate square root of a negative number", exception.getMessage());
    }
}

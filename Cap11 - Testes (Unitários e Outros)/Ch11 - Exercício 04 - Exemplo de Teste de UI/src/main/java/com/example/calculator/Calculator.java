package com.example.calculator;

        public class Calculator {

            public double add(double a, double b) {
                return a + b;
            }

            public double subtract(double a, double b) {
                return a - b;
            }

            public double multiply(double a, double b) {
                return a * b;
            }

            public double divide(double a, double b) {
                if (b == 0) {
                    throw new ArithmeticException("Division by zero is not allowed");
                }
                return a / b;
            }

            public double squareRoot(double a) {
                if (a < 0) {
                    throw new IllegalArgumentException("Cannot calculate square root of a negative number");
                }
                return Math.sqrt(a);
            }
        }
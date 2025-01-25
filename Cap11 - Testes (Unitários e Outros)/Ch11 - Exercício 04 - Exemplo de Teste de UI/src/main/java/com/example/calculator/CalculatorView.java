package com.example.calculator;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class CalculatorView extends VerticalLayout {

    private final TextField display = new TextField(); // Display field for showing results
    private final Calculator calculator = new Calculator(); // Calculator logic
    private double firstOperand = 0; // First operand for operations
    private String operator = ""; // Operator selected for the operation
    private boolean startNewInput = true; // Indicates if new input should overwrite the display

    public CalculatorView() {
        // Configure the display
        display.setReadOnly(true);
        display.setPlaceholder("0");
        display.setWidthFull();

        // Add the display to the layout
        add(display);

        // Create and add rows of buttons
        add(createRow(createSpecialButton("C"), createSpecialButton("√"), createOperationButton("/"), createOperationButton("*")));
        add(createRow(createNumberButton("7"), createNumberButton("8"), createNumberButton("9"), createOperationButton("-")));
        add(createRow(createNumberButton("4"), createNumberButton("5"), createNumberButton("6"), createOperationButton("+")));
        add(createRow(createNumberButton("1"), createNumberButton("2"), createNumberButton("3"), createSpecialButton("=")));
        add(createRow(createNumberButton("0"), createSpecialButton(".")));
    }

    // Create a row of buttons using HorizontalLayout
    private HorizontalLayout createRow(Button... buttons) {
        HorizontalLayout row = new HorizontalLayout(buttons);
        row.setWidthFull();
        return row;
    }

    // Create a button for numbers
    private Button createNumberButton(String number) {
        return new Button(number, event -> {
            if (startNewInput) {
                display.setValue(number);
                startNewInput = false;
            } else {
                display.setValue(display.getValue() + number);
            }
        });
    }

    // Create a button for operations
    private Button createOperationButton(String op) {
        return new Button(op, event -> {
            try {
                firstOperand = Double.parseDouble(display.getValue());
                operator = op;
                startNewInput = true; // Prepare for the next input
            } catch (NumberFormatException e) {
                Notification.show("Invalid number!", 3000, Notification.Position.MIDDLE);
            }
        });
    }

    // Create special buttons (C, √, ., =, etc.)
    private Button createSpecialButton(String label) {
        return new Button(label, event -> {
            switch (label) {
                case "C": // Clear button
                    display.setValue("");
                    firstOperand = 0;
                    operator = "";
                    break;
                case "√": // Square root
                    try {
                        double value = Double.parseDouble(display.getValue());
                        display.setValue(String.valueOf(calculator.squareRoot(value)));
                        startNewInput = true;
                    } catch (NumberFormatException e) {
                        Notification.show("Invalid number!", 3000, Notification.Position.MIDDLE);
                    } catch (IllegalArgumentException e) {
                        Notification.show(e.getMessage(), 3000, Notification.Position.MIDDLE);
                    }
                    break;
                case ".": // Decimal point
                    if (!display.getValue().contains(".")) {
                        display.setValue(display.getValue() + ".");
                    }
                    break;
                case "=": // Equals button
                    try {
                        double secondOperand = Double.parseDouble(display.getValue());
                        double result = performOperation(firstOperand, secondOperand, operator);
                        display.setValue(String.valueOf(result));
                        startNewInput = true;
                    } catch (NumberFormatException e) {
                        Notification.show("Invalid number!", 3000, Notification.Position.MIDDLE);
                    } catch (ArithmeticException e) {
                        Notification.show(e.getMessage(), 3000, Notification.Position.MIDDLE);
                    }
                    break;
            }
        });
    }

    // Perform the selected operation
    private double performOperation(double firstOperand, double secondOperand, String operator) {
        switch (operator) {
            case "+":
                return calculator.add(firstOperand, secondOperand);
            case "-":
                return calculator.subtract(firstOperand, secondOperand);
            case "*":
                return calculator.multiply(firstOperand, secondOperand);
            case "/":
                return calculator.divide(firstOperand, secondOperand);
            default:
                throw new UnsupportedOperationException("Unknown operator: " + operator);
        }
    }
}

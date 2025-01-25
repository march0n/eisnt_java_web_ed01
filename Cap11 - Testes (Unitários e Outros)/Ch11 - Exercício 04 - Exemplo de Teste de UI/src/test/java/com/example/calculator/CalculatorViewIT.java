package com.example.calculator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorViewSeleniumIT {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.get("http://localhost:8080");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testAddition() {
        // Click 2, +, and 3
        driver.findElement(By.xpath("//button[text()='2']")).click();
        driver.findElement(By.xpath("//button[text()='+']")).click();
        driver.findElement(By.xpath("//button[text()='3']")).click();
        driver.findElement(By.xpath("//button[text()='=']")).click();

        // Verify the result
        WebElement display = driver.findElement(By.tagName("vaadin-text-field"));
        assertEquals("5", display.getAttribute("value"));
    }

    @Test
    void testSubtraction() {
        // Click 9, -, and 3
        driver.findElement(By.xpath("//button[text()='9']")).click();
        driver.findElement(By.xpath("//button[text()='-']")).click();
        driver.findElement(By.xpath("//button[text()='3']")).click();
        driver.findElement(By.xpath("//button[text()='=']")).click();

        // Verify the result
        WebElement display = driver.findElement(By.tagName("vaadin-text-field"));
        assertEquals("6", display.getAttribute("value"));
    }

    @Test
    void testClear() {
        // Click 7, then C
        driver.findElement(By.xpath("//button[text()='7']")).click();
        driver.findElement(By.xpath("//button[text()='C']")).click();

        // Verify the display is cleared
        WebElement display = driver.findElement(By.tagName("vaadin-text-field"));
        assertEquals("", display.getAttribute("value"));
    }
}

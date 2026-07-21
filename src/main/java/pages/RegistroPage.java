package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Pantalla de registro de un nuevo usuario (GET/POST /registro).
 */
public class RegistroPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By campoNombre = By.id("nombre");
    private final By campoEmail = By.id("email");
    private final By campoPassword = By.id("password");
    private final By botonRegistrarse = By.cssSelector("button[type='submit']");

    public RegistroPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void abrir(String baseUrl) {
        driver.get(baseUrl + "/registro");
        wait.until(ExpectedConditions.visibilityOfElementLocated(campoNombre));
    }

    public boolean formularioVisible() {
        return driver.findElement(campoNombre).isDisplayed()
                && driver.findElement(campoEmail).isDisplayed()
                && driver.findElement(campoPassword).isDisplayed();
    }

    public void registrar(String nombre, String email, String password) {
        WebElement nombreInput = driver.findElement(campoNombre);
        nombreInput.clear();
        nombreInput.sendKeys(nombre);

        WebElement emailInput = driver.findElement(campoEmail);
        emailInput.clear();
        emailInput.sendKeys(email);

        WebElement passwordInput = driver.findElement(campoPassword);
        passwordInput.clear();
        passwordInput.sendKeys(password);

        driver.findElement(botonRegistrarse).click();
    }
}

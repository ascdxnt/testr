package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Pantalla de inicio de sesion (GET/POST /login).
 */
public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By campoUsuario = By.id("username");
    private final By campoPassword = By.id("password");
    private final By botonIngresar = By.cssSelector("button[type='submit']");
    private final By alertaExito = By.cssSelector(".alert-success");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void abrir(String baseUrl) {
        driver.get(baseUrl + "/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(campoUsuario));
    }

    public boolean botonIngresarVisibleYHabilitado() {
        WebElement boton = driver.findElement(botonIngresar);
        return boton.isDisplayed() && boton.isEnabled();
    }

    public boolean campoUsuarioAceptaTexto(String texto) {
        WebElement usuario = driver.findElement(campoUsuario);
        usuario.clear();
        usuario.sendKeys(texto);
        return usuario.getAttribute("value").equals(texto);
    }

    public void iniciarSesion(String email, String password) {
        driver.findElement(campoUsuario).clear();
        driver.findElement(campoUsuario).sendKeys(email);
        driver.findElement(campoPassword).clear();
        driver.findElement(campoPassword).sendKeys(password);
        driver.findElement(botonIngresar).click();
    }

    public boolean mensajeDeRegistroExitosoVisible() {
        return !driver.findElements(alertaExito).isEmpty()
                && driver.findElement(alertaExito).getText().contains("Registro exitoso");
    }

    public boolean mensajeDeBienvenidaVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(alertaExito));
        return driver.findElement(alertaExito).getText().contains("Bienvenido");
    }
}

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Pantalla de detalle de un evento y formulario de compra
 * (GET /eventos/{id}, POST /eventos/comprar/{id}).
 */
public class EventoDetallePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By tituloEvento = By.cssSelector(".card-title");
    private final By campoCantidad = By.id("cantidad");
    // Se localiza por texto: "button[type='submit']" tambien matchea el boton
    // oculto "Cerrar sesion" del menu de perfil en el navbar, que aparece antes
    // en el DOM y no es interactuable (queda oculto hasta abrir el dropdown).
    private final By botonComprar = By.xpath("//button[normalize-space()='Comprar']");

    public EventoDetallePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void esperarCarga() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tituloEvento));
    }

    public String nombreEventoMostrado() {
        return driver.findElement(tituloEvento).getText();
    }

    public boolean campoCantidadAceptaNumero(int cantidad) {
        WebElement campo = driver.findElement(campoCantidad);
        campo.clear();
        campo.sendKeys(String.valueOf(cantidad));
        return campo.getAttribute("value").equals(String.valueOf(cantidad));
    }

    public boolean botonComprarHabilitado() {
        return driver.findElement(botonComprar).isEnabled();
    }

    public void comprar(int cantidad) {
        WebElement campo = driver.findElement(campoCantidad);
        campo.clear();
        campo.sendKeys(String.valueOf(cantidad));
        driver.findElement(botonComprar).click();
    }
}

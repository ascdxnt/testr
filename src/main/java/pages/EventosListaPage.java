package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Pantalla de listado de eventos disponibles (GET /eventos).
 */
public class EventosListaPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By tarjetasEvento = By.cssSelector(".card");
    private final By tituloTarjeta = By.cssSelector(".card-title");
    private final By botonesVerDetalles = By.linkText("Ver detalles");
    private final By alertaExito = By.cssSelector(".alert-success");

    public EventosListaPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void abrir(String baseUrl) {
        driver.get(baseUrl + "/eventos");
        wait.until(ExpectedConditions.visibilityOfElementLocated(tarjetasEvento));
    }

    public boolean hayAlMenosUnEventoVisible() {
        List<WebElement> tarjetas = driver.findElements(tarjetasEvento);
        return !tarjetas.isEmpty() && tarjetas.get(0).isDisplayed();
    }

    public boolean botonVerDetallesVisible() {
        List<WebElement> botones = driver.findElements(botonesVerDetalles);
        return !botones.isEmpty() && botones.get(0).isDisplayed();
    }

    public String nombreDelPrimerEvento() {
        return driver.findElements(tituloTarjeta).get(0).getText();
    }

    public void verDetalleDelPrimerEvento() {
        driver.findElements(botonesVerDetalles).get(0).click();
    }

    public boolean alertaDeCompraExitosaVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(alertaExito));
        return driver.findElement(alertaExito).isDisplayed();
    }
}

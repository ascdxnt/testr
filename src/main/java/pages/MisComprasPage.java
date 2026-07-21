package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Pantalla de historial de compras del usuario autenticado (GET /mis-compras).
 */
public class MisComprasPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By tabla = By.cssSelector(".app-table");
    private final By filas = By.cssSelector(".app-table tbody tr");

    public MisComprasPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void abrir(String baseUrl) {
        driver.get(baseUrl + "/mis-compras");
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(tabla),
                ExpectedConditions.visibilityOfElementLocated(By.className("app-empty-state"))
        ));
    }

    public boolean tablaDeComprasVisible() {
        return !driver.findElements(tabla).isEmpty() && driver.findElement(tabla).isDisplayed();
    }

    public boolean existeCompraDelEvento(String nombreEvento) {
        List<WebElement> filasCompra = driver.findElements(filas);
        for (WebElement fila : filasCompra) {
            if (fila.getText().contains(nombreEvento) && fila.getText().contains("Confirmada")) {
                return true;
            }
        }
        return false;
    }
}

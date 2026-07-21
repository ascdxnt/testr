package tests;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EventosListaPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.Pausas;

import java.time.Duration;

/**
 * TC02 - Inicio de sesion con el usuario administrador.
 * Pantallas recorridas: Login -> Eventos (lista).
 */
public class TC02_LoginTest extends BaseTest {

    @Test(description = "El usuario administrador inicia sesion y llega al listado de eventos")
    public void loginExitoso() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.abrir(baseUrl);
        Pausas.breve();

        // Validacion 1 (pantalla Login): el boton "Ingresar" esta visible y habilitado.
        Assert.assertTrue(loginPage.botonIngresarVisibleYHabilitado(),
                "El boton Ingresar deberia estar visible y habilitado.");

        // Validacion 2 (pantalla Login): el campo de usuario acepta texto.
        Assert.assertTrue(loginPage.campoUsuarioAceptaTexto(ConfigReader.obtener("admin.email")),
                "El campo de usuario deberia aceptar el correo ingresado.");
        Pausas.breve();

        loginPage.iniciarSesion(ConfigReader.obtener("admin.email"), ConfigReader.obtener("admin.password"));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/eventos"));
        Pausas.breve();

        // Validacion 3 (pantalla Eventos): login exitoso redirige a /eventos.
        Assert.assertTrue(driver.getCurrentUrl().contains("/eventos"),
                "Tras un login valido, Ticketr deberia redirigir a /eventos.");

        // Validacion adicional (pantalla Eventos): se muestra el mensaje de bienvenida.
        Assert.assertTrue(loginPage.mensajeDeBienvenidaVisible(),
                "Deberia mostrarse el mensaje de bienvenida tras iniciar sesion.");

        EventosListaPage eventosListaPage = new EventosListaPage(driver);

        // Validacion 4 (pantalla Eventos): al menos un evento visible.
        Assert.assertTrue(eventosListaPage.hayAlMenosUnEventoVisible(),
                "Deberia verse al menos una tarjeta de evento en el listado.");
        Pausas.breve();
    }
}

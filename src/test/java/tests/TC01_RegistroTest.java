package tests;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.RegistroPage;
import utils.Pausas;

import java.time.Duration;

/**
 * TC01 - Registro de un usuario nuevo.
 * Pantallas recorridas: Registro -> Login.
 */
public class TC01_RegistroTest extends BaseTest {

    @Test(description = "Un usuario nuevo se registra y es redirigido a la pantalla de login")
    public void registroDeUsuarioNuevo() {
        RegistroPage registroPage = new RegistroPage(driver);
        registroPage.abrir(baseUrl);
        Pausas.breve();

        // Validacion 1 (pantalla Registro): el formulario esta visible con sus campos.
        Assert.assertTrue(registroPage.formularioVisible(),
                "El formulario de registro deberia estar visible con sus 3 campos.");

        String correoUnico = "usuario" + System.currentTimeMillis() + "@ticketr-qa.com";
        registroPage.registrar("Usuario de Prueba", correoUnico, "ClaveSegura123!");
        Pausas.breve();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/login"));
        Pausas.breve();

        // Validacion 2 (pantalla Login, destino de la redireccion): el registro
        // exitoso redirige a /login.
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"),
                "Tras un registro exitoso, Ticketr deberia redirigir a /login.");

        // Validacion 3 (pantalla Login): se muestra el mensaje de registro exitoso.
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.mensajeDeRegistroExitosoVisible(),
                "Deberia mostrarse el mensaje de registro exitoso en la pantalla de login.");
    }
}

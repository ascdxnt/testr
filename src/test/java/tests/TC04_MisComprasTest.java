package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EventoDetallePage;
import pages.EventosListaPage;
import pages.LoginPage;
import pages.MisComprasPage;
import utils.ConfigReader;
import utils.Pausas;

/**
 * TC04 - Verificar que una compra realizada aparece en el historial del usuario.
 * Pantallas recorridas: Eventos (lista) -> Evento (detalle) -> Mis Compras.
 *
 * Precondicion: debe existir al menos un evento en la base de datos con
 * boletos disponibles.
 */
public class TC04_MisComprasTest extends BaseTest {

    @Test(description = "El historial de compras muestra la compra recien realizada")
    public void compraRegistradaEnHistorial() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.abrir(baseUrl);
        loginPage.iniciarSesion(ConfigReader.obtener("admin.email"), ConfigReader.obtener("admin.password"));
        Pausas.breve();

        EventosListaPage eventosListaPage = new EventosListaPage(driver);
        eventosListaPage.abrir(baseUrl);
        String nombreEvento = eventosListaPage.nombreDelPrimerEvento();
        eventosListaPage.verDetalleDelPrimerEvento();
        Pausas.breve();

        EventoDetallePage eventoDetallePage = new EventoDetallePage(driver);
        eventoDetallePage.esperarCarga();
        eventoDetallePage.comprar(1);
        Pausas.breve();

        MisComprasPage misComprasPage = new MisComprasPage(driver);
        misComprasPage.abrir(baseUrl);
        Pausas.breve();

        // Validacion 1 (pantalla Mis Compras): la tabla de compras esta visible.
        Assert.assertTrue(misComprasPage.tablaDeComprasVisible(),
                "La tabla de compras deberia estar visible tras registrar una compra.");

        // Validacion 2 (pantalla Mis Compras): la compra del evento aparece confirmada.
        Assert.assertTrue(misComprasPage.existeCompraDelEvento(nombreEvento),
                "El historial deberia mostrar la compra del evento '" + nombreEvento + "' como Confirmada.");
        Pausas.breve();
    }
}

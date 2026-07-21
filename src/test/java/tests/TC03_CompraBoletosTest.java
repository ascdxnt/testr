package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EventoDetallePage;
import pages.EventosListaPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.Pausas;

/**
 * TC03 - Ver el detalle de un evento y comprar boletos.
 * Pantallas recorridas: Eventos (lista) -> Evento (detalle) -> Eventos (lista, confirmacion).
 *
 * Precondicion: debe existir al menos un evento en la base de datos con
 * boletos disponibles.
 */
public class TC03_CompraBoletosTest extends BaseTest {

    @Test(description = "Un usuario autenticado ve el detalle de un evento y compra boletos")
    public void verDetalleYComprarBoletos() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.abrir(baseUrl);
        loginPage.iniciarSesion(ConfigReader.obtener("admin.email"), ConfigReader.obtener("admin.password"));
        Pausas.breve();

        EventosListaPage eventosListaPage = new EventosListaPage(driver);
        eventosListaPage.abrir(baseUrl);
        Pausas.breve();

        // Validacion 1 (pantalla Eventos): tarjeta de evento visible.
        Assert.assertTrue(eventosListaPage.hayAlMenosUnEventoVisible(),
                "Deberia existir al menos un evento visible en el listado.");

        // Validacion 2 (pantalla Eventos): boton "Ver detalles" visible.
        Assert.assertTrue(eventosListaPage.botonVerDetallesVisible(),
                "El boton Ver detalles deberia estar visible.");

        String nombreEventoEsperado = eventosListaPage.nombreDelPrimerEvento();
        eventosListaPage.verDetalleDelPrimerEvento();
        Pausas.breve();

        EventoDetallePage eventoDetallePage = new EventoDetallePage(driver);
        eventoDetallePage.esperarCarga();

        // Validacion 3 (pantalla Evento detalle): el nombre del evento coincide con el seleccionado.
        Assert.assertEquals(eventoDetallePage.nombreEventoMostrado(), nombreEventoEsperado,
                "El nombre del evento en el detalle deberia coincidir con el seleccionado en el listado.");

        // Validacion 4 (pantalla Evento detalle): el campo cantidad acepta numeros y el boton esta habilitado.
        Assert.assertTrue(eventoDetallePage.campoCantidadAceptaNumero(1),
                "El campo cantidad deberia aceptar un valor numerico.");
        Assert.assertTrue(eventoDetallePage.botonComprarHabilitado(),
                "El boton Comprar deberia estar habilitado.");
        Pausas.breve();

        eventoDetallePage.comprar(1);
        Pausas.breve();

        // Validacion 5 (pantalla Eventos, confirmacion): alerta de compra exitosa visible.
        Assert.assertTrue(eventosListaPage.alertaDeCompraExitosaVisible(),
                "Deberia mostrarse el mensaje de compra realizada con exito.");
        Pausas.breve();
    }
}

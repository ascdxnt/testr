package utils;

/**
 * Pausas breves y deliberadas entre pasos de un caso de prueba, unicamente
 * con fines de presentacion: permiten que una persona observando la
 * ejecucion en vivo (por ejemplo, en la exposicion oral) pueda seguir con
 * la vista cada paso del navegador, que de otro modo ocurre en milisegundos.
 *
 * No afectan el resultado de las validaciones (los Assert siguen siendo los
 * que determinan si el caso pasa o falla); solo ralentizan la percepcion
 * visual de la ejecucion. La duracion se controla desde config.properties
 * (demo.pause.ms) y puede ponerse en 0 para una ejecucion rapida sin demo.
 */
public final class Pausas {

    private static final long DURACION_MS = Long.parseLong(
            ConfigReader.obtener("demo.pause.ms") != null ? ConfigReader.obtener("demo.pause.ms") : "0");

    private Pausas() {
    }

    public static void breve() {
        if (DURACION_MS <= 0) {
            return;
        }
        try {
            Thread.sleep(DURACION_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

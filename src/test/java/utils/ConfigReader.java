package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utilidad simple para leer valores de src/test/resources/config.properties
 * (URL base de la aplicacion y credenciales de prueba).
 */
public final class ConfigReader {

    private static final Properties PROPIEDADES = new Properties();

    static {
        try (InputStream entrada = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (entrada == null) {
                throw new IllegalStateException("No se encontro config.properties en el classpath");
            }
            PROPIEDADES.load(entrada);
        } catch (IOException e) {
            throw new IllegalStateException("Error leyendo config.properties", e);
        }
    }

    private ConfigReader() {
    }

    public static String obtener(String clave) {
        return PROPIEDADES.getProperty(clave);
    }
}

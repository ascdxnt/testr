package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;
import utils.ExtentManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;

/**
 * Clase base para todos los casos de prueba: administra el ciclo de vida del
 * WebDriver y la integracion con Extent Reports (logs, screenshots en fallos
 * y metricas de tiempo por caso).
 */
public abstract class BaseTest {

    protected WebDriver driver;
    protected String baseUrl;

    private static ExtentReports extent;
    protected ExtentTest test;
    private Instant inicioCaso;

    @BeforeSuite(alwaysRun = true)
    public void configurarReporte() {
        extent = ExtentManager.obtenerInstancia();
    }

    @BeforeMethod(alwaysRun = true)
    public void configurarNavegador(Method method) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions opciones = new ChromeOptions();
        driver = new ChromeDriver(opciones);
        driver.manage().window().maximize();

        baseUrl = ConfigReader.obtener("base.url");
        inicioCaso = Instant.now();

        test = extent.createTest(method.getName());
        test.log(Status.INFO, "Inicio del caso de prueba: " + method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void cerrarNavegador(ITestResult resultado) {
        Duration duracion = Duration.between(inicioCaso, Instant.now());
        test.log(Status.INFO, "Duracion del caso: " + duracion.toMillis() + " ms");

        if (resultado.getStatus() == ITestResult.FAILURE) {
            test.fail(resultado.getThrowable());
            String rutaCaptura = tomarCaptura(resultado.getName() + "_FALLO");
            if (rutaCaptura != null) {
                test.addScreenCaptureFromPath(rutaCaptura);
            }
        } else if (resultado.getStatus() == ITestResult.SUCCESS) {
            test.pass("Caso de prueba ejecutado correctamente.");
            // Ademas de la captura obligatoria en fallos, se guarda una
            // captura del estado final tambien cuando el caso pasa, como
            // evidencia visual adicional para el reporte y la demo en vivo.
            String rutaCaptura = tomarCaptura(resultado.getName() + "_OK");
            if (rutaCaptura != null) {
                test.addScreenCaptureFromPath(rutaCaptura);
            }
        } else if (resultado.getStatus() == ITestResult.SKIP) {
            test.skip("Caso de prueba omitido.");
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void finalizarReporte() {
        if (extent != null) {
            extent.flush();
        }
    }

    private String tomarCaptura(String nombreCaso) {
        try {
            File origen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File carpeta = new File("screenshots");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            File destino = new File(carpeta, nombreCaso + "_" + System.currentTimeMillis() + ".png");
            Files.copy(origen.toPath(), destino.toPath());
            return destino.getAbsolutePath();
        } catch (IOException e) {
            return null;
        }
    }
}

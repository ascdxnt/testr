package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * Crea una unica instancia de ExtentReports para toda la suite,
 * con salida HTML en la carpeta reports/.
 */
public final class ExtentManager {

    private static ExtentReports instancia;

    private ExtentManager() {
    }

    public static synchronized ExtentReports obtenerInstancia() {
        if (instancia == null) {
            ExtentSparkReporter reporteSpark = new ExtentSparkReporter("reports/report.html");
            reporteSpark.config().setTheme(Theme.STANDARD);
            reporteSpark.config().setDocumentTitle("AutomationQA - Ticketr");
            reporteSpark.config().setReportName("Simulacion SC-405 - Casos de prueba Ticketr");

            instancia = new ExtentReports();
            instancia.attachReporter(reporteSpark);
            instancia.setSystemInfo("Aplicacion bajo prueba", "Ticketr (Sistema de Boletos)");
            instancia.setSystemInfo("Framework", "Selenium WebDriver + TestNG");
        }
        return instancia;
    }
}

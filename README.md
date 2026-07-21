# Simulación: Mi Primera Ejecución de Pruebas

**Curso:** SC-405 Calidad del Software
**Autor:** Jeferson Andrew Fuentes García
**Horario:** LN

## 1. Descripción del proyecto

Este proyecto implementa una suite de pruebas funcionales automatizadas
mediante el patrón **Page Object Model**, utilizando **Selenium WebDriver**
para la interacción con el navegador, **TestNG** como framework de
estructuración y ejecución, y **Extent Reports** para la generación de
evidencia de ejecución.

## 2. Aplicación bajo prueba

La aplicación evaluada es **Ticketr**, un sistema de venta y gestión de
boletos para eventos desarrollado en Spring Boot, Thymeleaf y MySQL. Se
trata de una aplicación propia, no utilizada durante las sesiones del curso,
en cumplimiento del requisito de selección libre de aplicación web
establecido en el enunciado de la evaluación.

## 3. Tecnologías utilizadas

| Tecnología | Versión | Propósito |
|---|---|---|
| Java | 17+ | Lenguaje de implementación |
| Maven | 3.9 | Gestión de dependencias y ciclo de vida del proyecto |
| Selenium WebDriver | 4.34.0 | Automatización del navegador |
| TestNG | 7.11.0 | Estructuración y ejecución de casos de prueba |
| WebDriverManager | 6.3.0 | Gestión automática del driver de Chrome |
| Extent Reports | 5.1.2 | Generación de reportes de ejecución |

## 4. Estructura del proyecto

```
pom.xml                             Configuración Maven y dependencias
src/main/java/pages/                Page Objects (uno por pantalla de Ticketr)
src/test/java/tests/                Clase base y casos de prueba TestNG
src/test/java/utils/                Utilidades de configuración y reporte
src/test/resources/                 Configuración de entorno y suite de TestNG
reports/report.html                 Reporte de ejecución generado por Extent Reports
screenshots/                        Evidencia fotográfica capturada durante la ejecución
Test-Case-Template-Ticketr.xlsx     Documentación de los casos de prueba
```

## 5. Escenario de navegación y casos de prueba

La suite recorre cinco pantallas distintas de la aplicación (Registro,
Inicio de sesión, Listado de eventos, Detalle de evento y Mis Compras),
distribuidas en cuatro casos de prueba independientes, cada uno con sus
propias aserciones:

| ID | Caso de prueba | Pantallas recorridas |
|---|---|---|
| TC01 | Registro de un usuario nuevo | Registro → Login |
| TC02 | Inicio de sesión del administrador | Login → Eventos (listado) |
| TC03 | Consulta de detalle y compra de boletos | Eventos (listado) → Evento (detalle) → Eventos (confirmación) |
| TC04 | Verificación del historial de compras | Eventos (listado) → Evento (detalle) → Mis Compras |

El detalle de precondiciones, pasos y resultado esperado de cada caso se
documenta en `Test-Case-Template-Ticketr.xlsx`.

## 6. Requisitos de ejecución

- Java 17 o superior y Maven.
- Google Chrome instalado.
- Servidor MySQL activo con la base de datos `sistema_boletos` creada.
- Aplicación Ticketr en ejecución en `http://localhost:8080`.
- Al menos un evento registrado con boletos disponibles (requerido por los
  casos TC03 y TC04).

## 7. Ejecución de la suite

```bash
mvn test
```

La ejecución corre la suite definida en `src/test/resources/testng.xml`.
Alternativamente, puede ejecutarse desde cualquier IDE con soporte para
Maven y TestNG.

## 8. Resultados obtenidos

La suite fue ejecutada de forma completa contra una instancia real de
Ticketr, obteniendo los cuatro casos de prueba en estado exitoso
(`4/4 passed`). El reporte de ejecución, con registros, métricas de tiempo
por caso y evidencia fotográfica, se encuentra disponible en
`reports/report.html`.

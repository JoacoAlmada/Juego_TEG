package com.teg.teg_juego.dtos.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorApiTest {

    @Test
    public void testConstructorSinParametros() {
        // Act
        ErrorApi errorApi = new ErrorApi();

        // Assert
        assertNotNull(errorApi);
        assertNull(errorApi.getTimestamp());
        assertNull(errorApi.getStatus());
        assertNull(errorApi.getError());
        assertNull(errorApi.getMessage());
    }

    @Test
    public void testSettersYGetters() {
        // Arrange
        ErrorApi errorApi = new ErrorApi();
        String timestampEsperado = "2024-01-15T10:30:00Z";
        Integer statusEsperado = 404;
        String errorEsperado = "Not Found";
        String messageEsperado = "El recurso solicitado no fue encontrado";

        // Act
        errorApi.setTimestamp(timestampEsperado);
        errorApi.setStatus(statusEsperado);
        errorApi.setError(errorEsperado);
        errorApi.setMessage(messageEsperado);

        // Assert
        assertEquals(timestampEsperado, errorApi.getTimestamp());
        assertEquals(statusEsperado, errorApi.getStatus());
        assertEquals(errorEsperado, errorApi.getError());
        assertEquals(messageEsperado, errorApi.getMessage());
    }

    @Test
    public void testSetTimestamp() {
        // Arrange
        ErrorApi errorApi = new ErrorApi();
        String timestampEsperado = "2024-06-30T14:25:30Z";

        // Act
        errorApi.setTimestamp(timestampEsperado);

        // Assert
        assertEquals(timestampEsperado, errorApi.getTimestamp());
    }

    @Test
    public void testSetStatus() {
        // Arrange
        ErrorApi errorApi = new ErrorApi();
        Integer statusEsperado = 500;

        // Act
        errorApi.setStatus(statusEsperado);

        // Assert
        assertEquals(statusEsperado, errorApi.getStatus());
    }

    @Test
    public void testSetError() {
        // Arrange
        ErrorApi errorApi = new ErrorApi();
        String errorEsperado = "Internal Server Error";

        // Act
        errorApi.setError(errorEsperado);

        // Assert
        assertEquals(errorEsperado, errorApi.getError());
    }

    @Test
    public void testSetMessage() {
        // Arrange
        ErrorApi errorApi = new ErrorApi();
        String messageEsperado = "Ocurrió un error interno del servidor";

        // Act
        errorApi.setMessage(messageEsperado);

        // Assert
        assertEquals(messageEsperado, errorApi.getMessage());
    }

    @Test
    public void testSetTimestampConNull() {
        // Arrange
        ErrorApi errorApi = new ErrorApi();

        // Act
        errorApi.setTimestamp(null);

        // Assert
        assertNull(errorApi.getTimestamp());
    }

    @Test
    public void testSetStatusConNull() {
        // Arrange
        ErrorApi errorApi = new ErrorApi();

        // Act
        errorApi.setStatus(null);

        // Assert
        assertNull(errorApi.getStatus());
    }

    @Test
    public void testSetErrorConNull() {
        // Arrange
        ErrorApi errorApi = new ErrorApi();

        // Act
        errorApi.setError(null);

        // Assert
        assertNull(errorApi.getError());
    }

    @Test
    public void testSetMessageConNull() {
        // Arrange
        ErrorApi errorApi = new ErrorApi();

        // Act
        errorApi.setMessage(null);

        // Assert
        assertNull(errorApi.getMessage());
    }

    @Test
    public void testCodigosDeErrorComunes() {
        // Arrange
        ErrorApi errorApi = new ErrorApi();

        // Test 400 - Bad Request
        errorApi.setStatus(400);
        errorApi.setError("Bad Request");
        errorApi.setMessage("La solicitud es inválida");

        // Assert
        assertEquals(400, errorApi.getStatus());
        assertEquals("Bad Request", errorApi.getError());
        assertEquals("La solicitud es inválida", errorApi.getMessage());

        // Test 401 - Unauthorized
        errorApi.setStatus(401);
        errorApi.setError("Unauthorized");
        errorApi.setMessage("No autorizado");

        // Assert
        assertEquals(401, errorApi.getStatus());
        assertEquals("Unauthorized", errorApi.getError());
        assertEquals("No autorizado", errorApi.getMessage());

        // Test 403 - Forbidden
        errorApi.setStatus(403);
        errorApi.setError("Forbidden");
        errorApi.setMessage("Acceso prohibido");

        // Assert
        assertEquals(403, errorApi.getStatus());
        assertEquals("Forbidden", errorApi.getError());
        assertEquals("Acceso prohibido", errorApi.getMessage());
    }

    @Test
    public void testValoresLimiteStatus() {
        // Arrange
        ErrorApi errorApi = new ErrorApi();

        // Test valor mínimo
        Integer statusMinimo = 100;
        errorApi.setStatus(statusMinimo);
        assertEquals(statusMinimo, errorApi.getStatus());

        // Test valor máximo
        Integer statusMaximo = 599;
        errorApi.setStatus(statusMaximo);
        assertEquals(statusMaximo, errorApi.getStatus());

        // Test valor extremo
        Integer statusExtremo = Integer.MAX_VALUE;
        errorApi.setStatus(statusExtremo);
        assertEquals(statusExtremo, errorApi.getStatus());
    }

    @Test
    public void testCamposVacios() {
        // Arrange
        ErrorApi errorApi = new ErrorApi();

        // Act
        errorApi.setTimestamp("");
        errorApi.setError("");
        errorApi.setMessage("");

        // Assert
        assertEquals("", errorApi.getTimestamp());
        assertEquals("", errorApi.getError());
        assertEquals("", errorApi.getMessage());
    }

    @Test
    public void testModificacionesMultiples() {
        // Arrange
        ErrorApi errorApi = new ErrorApi();

        // Primera modificación
        errorApi.setStatus(404);
        errorApi.setError("Not Found");
        assertEquals(404, errorApi.getStatus());
        assertEquals("Not Found", errorApi.getError());

        // Segunda modificación
        errorApi.setStatus(500);
        errorApi.setError("Internal Server Error");
        assertEquals(500, errorApi.getStatus());
        assertEquals("Internal Server Error", errorApi.getError());

        // Tercera modificación
        errorApi.setStatus(200);
        errorApi.setError("OK");
        assertEquals(200, errorApi.getStatus());
        assertEquals("OK", errorApi.getError());
    }

    @Test
    public void testEscenarioCompleto() {
        // Arrange
        ErrorApi errorApi = new ErrorApi();
        String timestamp = "2024-06-30T12:00:00Z";
        Integer status = 422;
        String error = "Unprocessable Entity";
        String message = "Los datos enviados no son válidos para el procesamiento";

        // Act
        errorApi.setTimestamp(timestamp);
        errorApi.setStatus(status);
        errorApi.setError(error);
        errorApi.setMessage(message);

        // Assert - Verificar que todos los campos fueron asignados correctamente
        assertEquals(timestamp, errorApi.getTimestamp());
        assertEquals(status, errorApi.getStatus());
        assertEquals(error, errorApi.getError());
        assertEquals(message, errorApi.getMessage());

        // Verificar que el objeto no es null
        assertNotNull(errorApi);

        // Verificar que ningún campo es null después de la asignación
        assertNotNull(errorApi.getTimestamp());
        assertNotNull(errorApi.getStatus());
        assertNotNull(errorApi.getError());
        assertNotNull(errorApi.getMessage());
    }

    @Test
    void testAllArgsConstructor() {
        ErrorApi error = new ErrorApi("2025-07-01T20:00:00", 404, "NOT_FOUND", "Recurso no encontrado");

        assertEquals("2025-07-01T20:00:00", error.getTimestamp());
        assertEquals(404, error.getStatus());
        assertEquals("NOT_FOUND", error.getError());
        assertEquals("Recurso no encontrado", error.getMessage());
    }

    @Test
    void testBuilder() {
        ErrorApi error = ErrorApi.builder()
                .timestamp("2025-07-01T20:00:00")
                .status(500)
                .error("INTERNAL_SERVER_ERROR")
                .message("Ocurrió un error inesperado")
                .build();

        assertEquals("2025-07-01T20:00:00", error.getTimestamp());
        assertEquals(500, error.getStatus());
        assertEquals("INTERNAL_SERVER_ERROR", error.getError());
        assertEquals("Ocurrió un error inesperado", error.getMessage());
    }
}

package KirbyGame_HVL.git.utils.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

public class NetworkUtils {
    private static final int BUFFER_SIZE = 1024;
    private StringBuilder lineBuffer = new StringBuilder();
    private byte[] readBuffer = new byte[BUFFER_SIZE];

    public String readLine(InputStream input) throws IOException {
        // Si hay una línea completa en el buffer, retornarla
        int newlineIndex = lineBuffer.indexOf("\n");
        if (newlineIndex != -1) {
            String line = lineBuffer.substring(0, newlineIndex);
            lineBuffer.delete(0, newlineIndex + 1);
            return line;
        }

        // Leer más datos del InputStream
        int bytesRead = input.read(readBuffer);
        if (bytesRead == -1) {
            // Si no hay más datos y hay contenido en el buffer, retornarlo
            if (lineBuffer.length() > 0) {
                String remainingLine = lineBuffer.toString();
                lineBuffer.setLength(0);
                return remainingLine;
            }
            return null;
        }

        // Agregar los nuevos datos al buffer
        lineBuffer.append(new String(readBuffer, 0, bytesRead));

        // Intentar obtener una línea completa
        newlineIndex = lineBuffer.indexOf("\n");
        if (newlineIndex != -1) {
            String line = lineBuffer.substring(0, newlineIndex);
            lineBuffer.delete(0, newlineIndex + 1);
            return line;
        }

        // Si no hay línea completa, retornar null y esperar más datos
        return null;
    }

    // Método alternativo que usa ByteArrayOutputStream para manejar mensajes más largos
    public String readLineAlternative(InputStream input) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int character;

        while ((character = input.read()) != -1) {
            if (character == '\n') {
                // Convertir el buffer a String y limpiarlo
                String line = buffer.toString();
                buffer.reset();
                return line;
            }
            buffer.write(character);
        }

        // Si llegamos al final del stream y hay datos en el buffer
        if (buffer.size() > 0) {
            String line = buffer.toString();
            buffer.reset();
            return line;
        }

        return null;
    }
}

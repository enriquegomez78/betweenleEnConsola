import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Diccionario {
    private HashMap<String, Integer> palabras;

    // Constructor recibe el archivo y el tamaño de dificultad
    public Diccionario(String nombreArchivo, int longitudDificultad) {
        this.palabras = new HashMap<>();
        cargarPalabrasDesdeArchivo(nombreArchivo, longitudDificultad);
    }

    private void cargarPalabrasDesdeArchivo(String nombreArchivo, int longitudDificultad) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Limpieza: Cortar sufijos compuestos (Ej: "zoquete, ta" -> "zoquete")
                if (linea.contains(",")) {
                    linea = linea.split(",")[0];
                }

                String palabra = linea.trim().toLowerCase();

                // Limpieza: Quitar acentos
                palabra = Normalizer.normalize(palabra, Normalizer.Form.NFD);
                palabra = palabra.replaceAll("[^\\p{ASCII}]", "");

                // Validar que sea una palabra limpia de puras letras
                if (!palabra.isEmpty() && !palabra.startsWith("-") && palabra.matches("[a-z]+")) {

                    // Solo guarda en memoria si mide lo solicitado
                    if (palabra.length() == longitudDificultad) {
                        palabras.put(palabra, palabra.length());
                    }
                }
            }
            System.out.println("--> [API] Diccionario (" + nombreArchivo + ") cargado con éxito. Palabras útiles en memoria: " + palabras.size());
        } catch (IOException e) {
            System.out.println("--> [API] Error: No se pudo leer " + nombreArchivo);
        }
    }

    public boolean existePalabra(String palabra) {
        return palabras.containsKey(palabra.toLowerCase());
    }

    public void agregarPalabra(String palabra) {
        palabras.put(palabra.toLowerCase(), palabra.length());
    }

    // Filtra las palabras usando Streams y Lambdas
    public List<String> obtenerPalabrasPorLongitudOrdenadas(int longitud) {
        return palabras.keySet().stream()
                .filter(p -> p.length() == longitud) // <-- LAMBDA 1 (Filtro redundante de seguridad)
                .sorted()
                .collect(Collectors.toList());
    }
}
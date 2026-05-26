import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class BetweenleAPI {
    private Diccionario diccionario;
    private List<String> palabrasFiltradas;
    private String palabraSecreta;

    private int intentosMaximos;
    private int intentosActuales;

    private int idxInferior;
    private int idxSuperior;

    private HashSet<Character> letrasUtilizadas;
    private List<String> historialIntentos;

    public BetweenleAPI(int longitud, int intentosMaximos, int idiomaElegido) {

        String archivoElegido;
        if (idiomaElegido == 1) {
            archivoElegido = "allwords.txt";
        } else {
            archivoElegido = "words_alpha.txt";
        }

        this.diccionario = new Diccionario(archivoElegido, longitud);
        this.intentosMaximos = intentosMaximos;
        this.intentosActuales = 0;
        this.letrasUtilizadas = new HashSet<>();
        this.historialIntentos = new ArrayList<>();

        this.palabrasFiltradas = diccionario.obtenerPalabrasPorLongitudOrdenadas(longitud);
        if (palabrasFiltradas.isEmpty()) {
            System.out.println("-> [ALERTA] Archivo .txt no encontrado. Asegúrate de ponerlo en la carpeta raíz del proyecto.");
            if (idiomaElegido == 1) {
                this.palabrasFiltradas.add("casas");
                this.palabraSecreta = "casas";
            } else {
                this.palabrasFiltradas.add("house");
                this.palabraSecreta = "house";
            }
        } else {
            int indiceAleatorio = (int) (Math.random() * palabrasFiltradas.size());
            this.palabraSecreta = palabrasFiltradas.get(indiceAleatorio);
        }

        // Sirve para que empiece ej. AAAAA y ZZZZZ
        StringBuilder aBuilder = new StringBuilder();
        StringBuilder zBuilder = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            aBuilder.append("a");
            zBuilder.append("z");
        }

        this.palabrasFiltradas.add(0, aBuilder.toString());
        this.palabrasFiltradas.add(zBuilder.toString());

        this.idxInferior = 0;
        this.idxSuperior = palabrasFiltradas.size() - 1;
    }

    public int validarYProcesarIntento(String intento) {
        intento = intento.toLowerCase().trim();

        if (!diccionario.existePalabra(intento)) {
            return 0;
        }

        int idxIntento = palabrasFiltradas.indexOf(intento);
        int idxSecreta = palabrasFiltradas.indexOf(palabraSecreta);

        if (idxIntento != -1) {
            if (idxIntento <= idxInferior) {
                return -1;
            }
            if (idxIntento >= idxSuperior) {
                return 2;
            }
        }

        intentosActuales++;
        historialIntentos.add(intento);

        for (char c : intento.toCharArray()) {
            letrasUtilizadas.add(c);
        }

        if (idxIntento != -1) {
            if (idxIntento < idxSecreta) {
                idxInferior = idxIntento;
            } else if (idxIntento > idxSecreta) {
                idxSuperior = idxIntento;
            }
        }

        return 1;
    }

    public void recorrerLimiteSuperiorUnoPorCiento() {
        int paso = Math.max(1, palabrasFiltradas.size() / 100);
        int idxSecreta = palabrasFiltradas.indexOf(palabraSecreta);
        idxSuperior = Math.max(idxSecreta, idxSuperior - paso);
    }

    public void recorrerLimiteInferiorUnoPorCiento() {
        int paso = Math.max(1, palabrasFiltradas.size() / 100);
        int idxSecreta = palabrasFiltradas.indexOf(palabraSecreta);
        idxInferior = Math.min(idxSecreta, idxInferior + paso);
    }

    public char obtenerPistaLetraInicial() {
        return palabraSecreta.charAt(0);
    }

    public String obtenerLetrasUsadasFormateadas() {
        StringBuilder sb = new StringBuilder();
        Iterator<Character> it = letrasUtilizadas.iterator();
        while (it.hasNext()) {
            sb.append(it.next()).append(" ");
        }
        return sb.toString().trim().toUpperCase();
    }

    public void mostrarHistorialEnConsola() {
        System.out.print("Palabras escritas hasta ahora: ");
        historialIntentos.forEach(p -> System.out.print("[" + p.toUpperCase() + "] "));
        System.out.println();
    }

    public double getPorcentajeInferior() {
        int idxSecreta = palabrasFiltradas.indexOf(palabraSecreta);
        if (idxSecreta == 0) return 100.0;

        return ((double) idxInferior / idxSecreta) * 100.0;
    }

    public double getPorcentajeSuperior() {
        int idxSecreta = palabrasFiltradas.indexOf(palabraSecreta);
        int maxIdx = palabrasFiltradas.size() - 1;
        if (maxIdx == idxSecreta) return 100.0;

        return ((double) (maxIdx - idxSuperior) / (maxIdx - idxSecreta)) * 100.0;
    }

    public String getLimiteInferior() {
        return palabrasFiltradas.get(idxInferior).toUpperCase();
    }

    public String getLimiteSuperior() {
        return palabrasFiltradas.get(idxSuperior).toUpperCase();
    }

    public boolean haGanado(String ultimoIntento) {
        return ultimoIntento.trim().equalsIgnoreCase(palabraSecreta);
    }

    public boolean haPerdido() {
        return intentosActuales >= intentosMaximos;
    }

    public int getIntentosActuales() {
        return intentosActuales;
    }

    public int getIntentosMaximos() {
        return intentosMaximos;
    }

    public Diccionario getDiccionario() {
        return diccionario;
    }

    public String getPalabraSecreta() {
        return palabraSecreta;
    }
}
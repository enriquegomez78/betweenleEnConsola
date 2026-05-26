import java.util.Scanner;

public class BetweenleConsole {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=========================================");
        System.out.println("         BIENVENIDO A BETWEENLE          ");
        System.out.println("=========================================");

        // Menú para seleccionar idioma
        System.out.println("Seleccione Idioma / Choose Language:");
        System.out.println("1. Español");
        System.out.println("2. English");
        System.out.print("Opción (1 o 2): ");
        int idiomaElegido = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("\nSeleccione Dificultad (Facil=5 letras, Intermedio=6 letras, Dificil=7 letras): ");
        String dif = scanner.nextLine().trim().toLowerCase();
        int longitud = dif.equals("intermedio") ? 6 : dif.equals("dificil") ? 7 : 5;

        System.out.print("Seleccione cantidad de oportunidades (10, 12 o 14): ");
        int intentos = Integer.parseInt(scanner.nextLine().trim());

        // Le enviamos los 3 parámetros requeridos a nuestra API sincronizada
        BetweenleAPI juego = new BetweenleAPI(longitud, intentos, idiomaElegido);

        System.out.println("\n--- ¡QUE COMIENCE EL JUEGO! ---");
        System.out.println("Escribe '777' en cualquier momento si necesitas ayuda.");

        while (!juego.haPerdido()) {
            System.out.println("\n=========================================");
            System.out.println("NUMERO DE INTENTO: " + (juego.getIntentosActuales() + 1) + " / " + juego.getIntentosMaximos());
            System.out.println("RANGO ALFABETICO ACTUAL:");
            // printf para formatear los porcentajes a solo 2 decimales
            System.out.printf("   [%s - %.2f%%]  <--- PALABRA SECRETA --->  [%.2f%% - %s]\n", juego.getLimiteInferior(), juego.getPorcentajeInferior(), juego.getPorcentajeSuperior(), juego.getLimiteSuperior());
            System.out.println("Letras ya intentadas: " + juego.obtenerLetrasUsadasFormateadas());

            juego.mostrarHistorialEnConsola();

            System.out.print("\nEscribe tu palabra de " + longitud + " letras: ");
            String entrada = scanner.nextLine().trim().toLowerCase();

            if (entrada.equals("777")) {
                System.out.println("\n--- MENU DE PISTAS ---");
                System.out.println("a) Recorrer un 1% la palabra de arriba");
                System.out.println("b) Recorrer un 1% la palabra de abajo");
                System.out.println("c) Letra con la que empieza la palabra");
                System.out.print("Seleccione una opcion (a, b o c): ");
                String opcPista = scanner.nextLine().trim().toLowerCase();

                if (opcPista.equals("a")) {
                    juego.recorrerLimiteSuperiorUnoPorCiento();
                    System.out.println("-> Pista aplicada: El límite superior bajó un 1% en el diccionario.");
                } else if (opcPista.equals("b")) {
                    juego.recorrerLimiteInferiorUnoPorCiento();
                    System.out.println("-> Pista aplicada: El límite inferior subió un 1% en el diccionario.");
                } else if (opcPista.equals("c")) {
                    System.out.println("-> La palabra secreta comienza con la letra: '" + juego.obtenerPistaLetraInicial() + "'");
                } else {
                    System.out.println("-> Opción de pista inválida.");
                }
                continue;
            }

            if (entrada.length() != longitud) {
                System.out.println("¡Error! La palabra debe tener exactamente " + longitud + " letras.");
                continue;
            }
            //  Evaluamos el código que nos manda la API
            int codigoRespuesta = juego.validarYProcesarIntento(entrada);

            if (codigoRespuesta == 0) {
                System.out.println("¡Alerta! La palabra '" + entrada + "' no está registrada en el diccionario.");
                System.out.print("¿Puedes demostrar que es válida y deseas agregarla al juego? (si/no): ");
                String respuesta = scanner.nextLine().trim().toLowerCase();

                if (respuesta.equals("si")) {
                    juego.getDiccionario().agregarPalabra(entrada);
                    System.out.println("¡Palabra guardada con éxito en el HashMap! Escríbela de nuevo para continuar.");
                }
                continue;

            } else if (codigoRespuesta == -1) {
                // Si mandó -1, avisamos y usamos 'continue' para saltar el turno sin gastar intentos
                System.out.println("\n-> [AVISO] ¡Palabra antes! Esta palabra va ANTES de tu límite inferior actual.");
                continue;

            } else if (codigoRespuesta == 2) {
                // Si mandó 2, lo mismo pero para el límite superior
                System.out.println("\n-> [AVISO] ¡Palabra después! Esta palabra va DESPUÉS de tu límite superior actual.");
                continue;
            }

            // Si llega aquí (código 1), es porque el intento fue válido y ya se sumó al contador.
            // Evaluar si ganó... (Esto se queda exactamente igual)
            if (juego.haGanado(entrada)) {
                System.out.println("\n=========================================");
                System.out.println("¡¡ENHORABUENA, HAS ADIVINADO LA PALABRA!!");
                System.out.println("La palabra secreta era: " + entrada.toUpperCase());
                System.out.println("Lo lograste en el intento número: " + juego.getIntentosActuales());
                System.out.println("=========================================");
                break;
            }
        }

        if (juego.haPerdido() && !juego.haGanado("")) {
            System.out.println("\n=========================================");
            System.out.println("GAME OVER - SE ACABARON LAS OPORTUNIDADES");
            System.out.println("La palabra secreta era: " + juego.getPalabraSecreta().toUpperCase());
            System.out.println("=========================================");
        }

        scanner.close();
    }
}

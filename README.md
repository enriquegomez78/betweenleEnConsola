# Betweenle (Versión de Consola) 🖥️

Una implementación en terminal del juego de deducción alfabética **Betweenle**, desarrollada en **Java**. Este componente representa el núcleo lógico del proyecto, diseñado para ejecutarse directamente sobre el flujo de entrada y salida estándar ($I/O$).

---

## 📌 Descripción del Juego

Esta versión recrea la experiencia de **Betweenle** prescindiendo de una interfaz gráfica, utilizando la consola del sistema para desplegar el estado de la partida. El objetivo principal es adivinar una palabra secreta mediante la reducción sistemática de un rango alfabético acotado por dos palabras límite: el **Límite Inferior** (inicialmente la primera palabra del diccionario) y el **Límite Superior** (la última palabra del diccionario).

A cada intento, el sistema valida la palabra introducida y restringe las fronteras alfabéticas, exigiendo al jugador un análisis deductivo para no quedar sin intentos.

---

## 🚀 Características Principales

* **Consola Interactiva Dinámica:** Flujo continuo de lectura y escritura optimizado mediante la clase `Scanner`.
* **Validación de Diccionario:** Filtra rigurosamente cada intento del usuario para asegurar que la palabra introducida sea válida y exista dentro del léxico del juego.
* **Fronteras Alfabéticas Estrictas:** Evalúa mediante comparaciones de cadenas (`compareTo`) si la palabra propuesta se encuentra dentro del rango permitido, penalizando las jugadas que queden fuera de los límites.
* **Cálculo de Proximidad:** Determina de manera matemática la distancia alfabética restante para ofrecer retroalimentación del progreso actual.
* **Configuración por Parámetros:** Permite inicializar el motor de juego definiendo variables clave como la longitud de caracteres de las palabras y el límite de intentos directamente desde el flujo principal.

---

## 📂 Estructura y Lógica del Sistema

El flujo del programa en consola se ejecuta bajo un ciclo de control clásico de videojuegos (*Game Loop*) estructurado de la siguiente manera:

1. **Inicialización:** Carga del diccionario de palabras y selección interna de la palabra secreta objetivo según la longitud parametrizada.
2. **Fase de Lectura (Input):** Captura de la cadena de caracteres introducida por el usuario a través de la terminal, estandarizando la entrada a mayúsculas de forma automática.
3. **Procesamiento de Reglas de Negocio:**
   * Comprobación de existencia en el diccionario.
   * Verificación de rango alfabético ($LimiteInferior < Intento < LimiteSuperior$).
   * Contracción dinámica de los límites si el intento es válido.
4. **Fase de Despliegue (Render):** Impresión en texto plano de los nuevos límites alfabéticos, los intentos restantes y los mensajes de estado (retroalimentación de errores o aciertos).
5. **Evaluación de Fin de Partida:** Verificación condicional de los estados de victoria (coincidencia exacta) o derrota (agotamiento de intentos) para romper el ciclo y liberar los recursos de entrada.

---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java 8 o superior.
* **Componentes Core:** `java.util.Scanner` para captura de datos, `java.util.List` / `ArrayList` para la persistencia del diccionario en memoria, y métodos nativos de la clase `String` para ordenamiento alfabético.

---

## Autor 
 Gomez Maynez Luis Enrique

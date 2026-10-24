/**
 * TDA 2D: representa un curso con un vector de estudiantes
 * (cada estudiante tiene su vector de notas, formando una matriz [n][m]).
 */
public class Curso {
    // Constantes (evitan números "mágicos")
    private static final double UMBRAL_APROBADO = 3.0;
    private static final double UMBRAL_RIESGO = 2.0;
    private static final double UMBRAL_DESTACADO = 4.5;
    private static final int MAX_LONGITUD_NOMBRE = 20;

    // Atributos
    private final Estudiante[] estudiantes;
    private final int cantidadCortes;
    private final Entrada entrada; // un solo lector compartido
    private boolean estudiantesRegistrados = false;
    private boolean notasRegistradas = false;

    // Constructor
    public Curso(int cantidadEstudiantes, int cantidadCortes, Entrada entrada) {
        if (cantidadEstudiantes <= 0 || cantidadCortes <= 0) {
            throw new IllegalArgumentException("Estudiantes y cortes deben ser mayores a 0.");
        }
        this.estudiantes = new Estudiante[cantidadEstudiantes];
        this.cantidadCortes = cantidadCortes;
        this.entrada = entrada;
    }

    // ---------- Guardas para evitar NullPointerException ----------
    public boolean hayEstudiantes() {
        return estudiantesRegistrados;
    }

    public boolean hayNotas() {
        return notasRegistradas;
    }

    private boolean verificarEstudiantes() {
        if (!estudiantesRegistrados) {
            System.out.println("\n⚠ Primero registre los estudiantes (opción 1).");
            return false;
        }
        return true;
    }

    private void avisarSiFaltanNotas() {
        if (!notasRegistradas) {
            System.out.println("\n⚠ Aún no se han registrado todas las notas (opción 2); se mostrarán en 0.0.");
        }
    }

    // 1. Método para registrar nombres de estudiantes
    public void registrarEstudiantes() {
        if (estudiantesRegistrados
                && !entrada.confirmar("Ya hay estudiantes registrados; se perderán sus notas. ¿Continuar?")) {
            return;
        }
        System.out.println("\n--- REGISTRO DE ESTUDIANTES ---");
        for (int i = 0; i < estudiantes.length; i++) {
            String nombre;
            boolean repetido;
            do {
                nombre = entrada.leerTexto("Ingrese el nombre del estudiante " + (i + 1) + ": ", MAX_LONGITUD_NOMBRE);
                repetido = existeNombre(nombre, i);
                if (repetido) {
                    System.out.println("  Ese nombre ya existe, use otro.");
                }
            } while (repetido);
            estudiantes[i] = new Estudiante(nombre, cantidadCortes);
        }
        estudiantesRegistrados = true;
        notasRegistradas = false;
    }

    // Verifica si un nombre ya existe entre las primeras 'hasta' posiciones
    private boolean existeNombre(String nombre, int hasta) {
        for (int i = 0; i < hasta; i++) {
            if (estudiantes[i].getNombre().equalsIgnoreCase(nombre.trim())) {
                return true;
            }
        }
        return false;
    }

    // 2. Método para registrar notas con validación (0.0 a 5.0)
    public void registrarNotas() {
        if (!verificarEstudiantes()) {
            return;
        }
        System.out.println("\n--- REGISTRO DE NOTAS ---");
        for (int i = 0; i < estudiantes.length; i++) {
            System.out.println("Ingresando notas de: " + estudiantes[i].getNombre());
            for (int j = 0; j < cantidadCortes; j++) {
                double nota = entrada.leerDecimal("  Nota del corte " + (j + 1) + " (0.0 a 5.0): ",
                        Estudiante.NOTA_MIN, Estudiante.NOTA_MAX);
                estudiantes[i].setNota(j, nota);
            }
        }
        notasRegistradas = true;
    }

    // ---------- Vector de nombres y matriz de notas (según el PDF) ----------
    public String[] obtenerNombresEstudiantes() {
        String[] nombresEstudiantes = new String[estudiantes.length];
        for (int i = 0; i < estudiantes.length; i++) {
            nombresEstudiantes[i] = estudiantes[i].getNombre();
        }
        return nombresEstudiantes;
    }

    public double[][] obtenerMatrizNotas() {
        double[][] notas = new double[estudiantes.length][cantidadCortes];
        for (int i = 0; i < estudiantes.length; i++) {
            notas[i] = estudiantes[i].getNotas();
        }
        return notas;
    }

    // 3. Método para calcular los promedios de cada estudiante
    public double[] calcularPromediosPorEstudiante() {
        double[] promedioEstudiante = new double[estudiantes.length];
        for (int i = 0; i < estudiantes.length; i++) {
            promedioEstudiante[i] = estudiantes[i].calcularPromedio();
        }
        return promedioEstudiante;
    }

    // 4. Método para calcular el promedio general de cada corte/evaluación
    public double[] calcularPromediosPorCorte() {
        double[] promedioCorte = new double[cantidadCortes];
        for (int j = 0; j < cantidadCortes; j++) {
            double suma = 0;
            for (int i = 0; i < estudiantes.length; i++) {
                suma += estudiantes[i].getNota(j);
            }
            promedioCorte[j] = suma / estudiantes.length;
        }
        return promedioCorte;
    }

    // Nota máxima y mínima de TODO el curso (requisito del PDF)
    public double obtenerNotaMayorCurso() {
        double mayor = estudiantes[0].obtenerNotaMayor();
        for (int i = 1; i < estudiantes.length; i++) {
            mayor = Math.max(mayor, estudiantes[i].obtenerNotaMayor());
        }
        return mayor;
    }

    public double obtenerNotaMenorCurso() {
        double menor = estudiantes[0].obtenerNotaMenor();
        for (int i = 1; i < estudiantes.length; i++) {
            menor = Math.min(menor, estudiantes[i].obtenerNotaMenor());
        }
        return menor;
    }

    // 5. Método de búsqueda lineal por nombre (ignora mayúsculas y espacios extremos)
    public Estudiante buscarEstudiantePorNombre(String nombreBusqueda) {
        if (!estudiantesRegistrados || nombreBusqueda == null) {
            return null;
        }
        String buscado = nombreBusqueda.trim();
        for (int i = 0; i < estudiantes.length; i++) {
            if (estudiantes[i].getNombre().equalsIgnoreCase(buscado)) {
                return estudiantes[i];
            }
        }
        return null;
    }

    // 6. Ordenamiento Burbuja: ordena el vector de estudiantes por promedio (menor a mayor)
    public void ordenarPorPromedio() {
        if (!verificarEstudiantes()) {
            return;
        }
        int n = estudiantes.length;
        for (int i = 0; i < n - 1; i++) {
            boolean huboCambio = false; // burbuja optimizada: si no hay cambios, termina
            for (int j = 0; j < n - i - 1; j++) {
                if (estudiantes[j].calcularPromedio() > estudiantes[j + 1].calcularPromedio()) {
                    // Intercambiamos los objetos Estudiante completos
                    Estudiante temp = estudiantes[j];
                    estudiantes[j] = estudiantes[j + 1];
                    estudiantes[j + 1] = temp;
                    huboCambio = true;
                }
            }
            if (!huboCambio) {
                break;
            }
        }
        System.out.println("\n¡Los estudiantes han sido ordenados por promedio exitosamente!");
    }

    // Muestra el vector de promedios (útil después de ordenar)
    public void mostrarRankingPromedios() {
        if (!verificarEstudiantes()) {
            return;
        }
        double[] promedios = calcularPromediosPorEstudiante();
        System.out.println("\n--- PROMEDIOS ORDENADOS (menor a mayor) ---");
        for (int i = 0; i < estudiantes.length; i++) {
            System.out.printf("%d. %-" + MAX_LONGITUD_NOMBRE + "s %.2f%n", (i + 1), estudiantes[i].getNombre(), promedios[i]);
        }
    }

    // Imprime los nombres que cumplen una categoría (0=aprobados, 1=reprobados, 2=riesgo, 3=destacados)
    private int imprimirListado(String titulo, int categoria) {
        System.out.println(titulo);
        int contador = 0;
        for (int i = 0; i < estudiantes.length; i++) {
            double prom = estudiantes[i].calcularPromedio();
            boolean cumple;
            switch (categoria) {
                case 0:  cumple = prom >= UMBRAL_APROBADO; break;
                case 1:  cumple = prom < UMBRAL_APROBADO; break;
                case 2:  cumple = prom >= UMBRAL_RIESGO && prom < UMBRAL_APROBADO; break;
                default: cumple = prom >= UMBRAL_DESTACADO; break;
            }
            if (cumple) {
                System.out.printf("   - %-" + MAX_LONGITUD_NOMBRE + "s (%.2f)%n", estudiantes[i].getNombre(), prom);
                contador++;
            }
        }
        if (contador == 0) {
            System.out.println("   (ninguno)");
        }
        return contador;
    }

    // 7. Método para mostrar el resumen general del curso (Tabla, promedios y estados)
    public void mostrarResumenCurso() {
        if (!verificarEstudiantes()) {
            return;
        }
        avisarSiFaltanNotas();
        System.out.println("\n========== RESUMEN DEL CURSO ==========");

        // Mostrar tabla de notas
        System.out.printf("%-" + (MAX_LONGITUD_NOMBRE + 2) + "s", "Nombre");
        for (int j = 0; j < cantidadCortes; j++) {
            System.out.printf("%-10s", "Corte " + (j + 1));
        }
        System.out.println("Promedio");
        int ancho = (MAX_LONGITUD_NOMBRE + 2) + cantidadCortes * 10 + 8;
        System.out.println("-".repeat(ancho));

        for (int i = 0; i < estudiantes.length; i++) {
            System.out.printf("%-" + (MAX_LONGITUD_NOMBRE + 2) + "s", estudiantes[i].getNombre());
            for (int j = 0; j < cantidadCortes; j++) {
                System.out.printf("%-10.2f", estudiantes[i].getNota(j));
            }
            System.out.printf("%.2f%n", estudiantes[i].calcularPromedio());
        }

        // Mostrar promedios por corte
        System.out.println("\n--- PROMEDIOS POR CORTE ---");
        double[] promediosCortes = calcularPromediosPorCorte();
        for (int j = 0; j < promediosCortes.length; j++) {
            System.out.printf("Corte %d: %.2f%n", (j + 1), promediosCortes[j]);
        }

        // Nota máxima y mínima de todo el curso
        System.out.println("\n--- NOTAS EXTREMAS DEL CURSO ---");
        System.out.printf("Nota máxima: %.2f%n", obtenerNotaMayorCurso());
        System.out.printf("Nota mínima: %.2f%n", obtenerNotaMenorCurso());

        // Estado individual de los estudiantes (se conserva la lógica original)
        int aprobados = 0, reprobados = 0, enRiesgo = 0, destacados = 0;

        System.out.println("\n--- ESTADO DE LOS ESTUDIANTES ---");
        for (int i = 0; i < estudiantes.length; i++) {
            double prom = estudiantes[i].calcularPromedio();
            System.out.print("- " + estudiantes[i].getNombre() + ": ");

            if (prom >= UMBRAL_APROBADO) {
                System.out.print("Aprobado ");
                aprobados++;
                if (prom >= UMBRAL_DESTACADO) {
                    System.out.print("(¡Desempeño Destacado!)");
                    destacados++;
                }
            } else {
                System.out.print("Reprobado ");
                reprobados++;
                if (prom >= UMBRAL_RIESGO) { // corrige el hueco 2.9 - 3.0
                    System.out.print("(En Riesgo)");
                    enRiesgo++;
                }
            }
            System.out.println();
        }

        // Listados separados (requisito del PDF)
        System.out.println("\n--- LISTADOS ---");
        imprimirListado("Aprobados (promedio >= 3.0):", 0);
        imprimirListado("Reprobados (promedio < 3.0):", 1);
        imprimirListado("En riesgo (promedio 2.0 a < 3.0):", 2);
        imprimirListado("Destacados (promedio >= 4.5):", 3);

        // Totales estadísticos
        System.out.println("\n--- ESTADÍSTICAS TOTALES ---");
        System.out.println("Total Aprobados: " + aprobados);
        System.out.println("Total Reprobados: " + reprobados);
        System.out.println("Estudiantes en Riesgo (2.0 - 2.99): " + enRiesgo);
        System.out.println("Estudiantes Destacados (>= 4.5): " + destacados);
        System.out.println("=======================================\n");
    }
}

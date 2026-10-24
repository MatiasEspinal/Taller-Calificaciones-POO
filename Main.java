import java.util.Scanner;

public class Main {
    private static final int MAX_ESTUDIANTES = 100;
    private static final int MAX_CORTES = 10;

    public static void main(String[] args) {
        // try-with-resources: el Scanner se cierra solo, y es el ÚNICO sobre System.in
        try (Scanner scanner = new Scanner(System.in)) {
            Entrada entrada = new Entrada(scanner);

            System.out.println("==================================================");
            System.out.println(" BIENVENIDO AL SISTEMA DEL CAMPUS DIGITAL ANDINO ");
            System.out.println("==================================================");

            // 1. Pedir y validar cantidad de estudiantes (n) y cortes (m)
            int n = entrada.leerEntero("Ingrese la cantidad de estudiantes (1 a " + MAX_ESTUDIANTES + "): ",
                    1, MAX_ESTUDIANTES);
            int m = entrada.leerEntero("Ingrese la cantidad de evaluaciones/cortes (1 a " + MAX_CORTES + "): ",
                    1, MAX_CORTES);

            // 2. Instanciar el objeto Curso (TDA 2D)
            Curso curso = new Curso(n, m, entrada);
            int opcion;

            // 3. Menú interactivo
            do {
                System.out.println("\n--- MENÚ PRINCIPAL ---");
                System.out.println("1. Registrar nombres de estudiantes");
                System.out.println("2. Registrar notas de los estudiantes");
                System.out.println("3. Mostrar resumen del curso (Tabla, promedios y estado)");
                System.out.println("4. Ordenar estudiantes por promedio (Menor a Mayor)");
                System.out.println("5. Buscar estudiante por nombre");
                System.out.println("6. Salir");
                opcion = entrada.leerEntero("Seleccione una opción: ", 1, 6);

                switch (opcion) {
                    case 1:
                        curso.registrarEstudiantes();
                        break;
                    case 2:
                        curso.registrarNotas();
                        break;
                    case 3:
                        curso.mostrarResumenCurso();
                        break;
                    case 4:
                        if (curso.hayEstudiantes()) {
                            curso.ordenarPorPromedio();
                            curso.mostrarRankingPromedios();
                            curso.mostrarResumenCurso();
                        } else {
                            System.out.println("\n⚠ Primero registre los estudiantes (opción 1).");
                        }
                        break;
                    case 5:
                        if (!curso.hayEstudiantes()) {
                            System.out.println("\n⚠ Primero registre los estudiantes (opción 1).");
                            break;
                        }
                        String nombreBusqueda = entrada.leerTexto("\nIngrese el nombre del estudiante a buscar: ", 50);
                        Estudiante encontrado = curso.buscarEstudiantePorNombre(nombreBusqueda);
                        if (encontrado != null) {
                            System.out.println("\n¡Estudiante encontrado!");
                            encontrado.mostrarInfo();
                            System.out.printf("Nota más alta: %.2f%n", encontrado.obtenerNotaMayor());
                            System.out.printf("Nota más baja: %.2f%n", encontrado.obtenerNotaMenor());
                        } else {
                            System.out.println("\nEstudiante no encontrado en el sistema.");
                        }
                        break;
                    case 6:
                        System.out.println("\n¡Saliendo del sistema! Gracias por usar la aplicación.");
                        break;
                    default:
                        System.out.println("\nOpción no válida. Intente de nuevo.");
                }
            } while (opcion != 6);

        } catch (IllegalStateException e) {
            // Se cerró la entrada (Ctrl+D / Ctrl+Z)
            System.out.println("\nEntrada finalizada. Cerrando el programa.");
        }
    }
}

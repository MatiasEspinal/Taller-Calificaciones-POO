import java.util.Scanner;

public class Curso {
    // Atributos
    Estudiante[] estudiantes;
    int cantidadCortes;
    Scanner scanner;

    // Constructor
    public Curso(int cantidadEstudiantes, int cantidadCortes) {
        this.estudiantes = new Estudiante[cantidadEstudiantes];
        this.cantidadCortes = cantidadCortes;
        this.scanner = new Scanner(System.in);
    }

    // 1. Método para registrar nombres de estudiantes
    public void registrarEstudiantes() {
        System.out.println("\n--- REGISTRO DE ESTUDIANTES ---");
        for (int i = 0; i < estudiantes.length; i++) {
            System.out.print("Ingrese el nombre del estudiante " + (i + 1) + ": ");
            String nombre = scanner.nextLine();
            estudiantes[i] = new Estudiante(nombre, cantidadCortes);
        }
    }

    // 2. Método para registrar notas con validación
    public void registrarNotas() {
        System.out.println("\n--- REGISTRO DE NOTAS ---");
        for (int i = 0; i < estudiantes.length; i++) {
            System.out.println("Ingresando notas de: " + estudiantes[i].nombre);
            for (int j = 0; j < cantidadCortes; j++) {
                double nota = -1;
                while (nota < 0.0 || nota > 5.0) {
                    System.out.print("  Nota del corte " + (j + 1) + " (0.0 a 5.0): ");
                    nota = scanner.nextDouble();
                    if (nota < 0.0 || nota > 5.0) {
                        System.out.println("  ¡Error! La nota debe estar en el rango de 0.0 a 5.0.");
                    }
                }
                estudiantes[i].notas[j] = nota;
            }
            scanner.nextLine(); // Limpiar el buffer
        }
    }

    // 3. Método para calcular los promedios de cada estudiante
    public double[] calcularPromediosPorEstudiante() {
        double[] promedios = new double[estudiantes.length];
        for (int i = 0; i < estudiantes.length; i++) {
            promedios[i] = estudiantes[i].calcularPromedio();
        }
        return promedios;
    }

    // 4. Método para calcular el promedio general de cada corte/evaluación
    public double[] calcularPromediosPorCorte() {
        double[] promediosCortes = new double[cantidadCortes];
        for (int j = 0; j < cantidadCortes; j++) {
            double suma = 0;
            for (int i = 0; i < estudiantes.length; i++) {
                suma += estudiantes[i].notas[j];
            }
            promediosCortes[j] = suma / estudiantes.length;
        }
        return promediosCortes;
    }

    // 5. Método de búsqueda lineal por nombre
    public Estudiante buscarEstudiantePorNombre(String nombreBusqueda) {
        for (int i = 0; i < estudiantes.length; i++) {
            if (estudiantes[i].nombre.equalsIgnoreCase(nombreBusqueda)) {
                return estudiantes[i];
            }
        }
        return null;
    }

    // 6. Ordenamiento Burbuja: Ordena el vector de estudiantes por promedio (de menor a mayor)
    public void ordenarPorPromedio() {
        int n = estudiantes.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (estudiantes[j].calcularPromedio() > estudiantes[j + 1].calcularPromedio()) {
                    // Intercambiamos los objetos Estudiante completos
                    Estudiante temp = estudiantes[j];
                    estudiantes[j] = estudiantes[j + 1];
                    estudiantes[j + 1] = temp;
                }
            }
        }
        System.out.println("\n¡Los estudiantes han sido ordenados por promedio exitosamente!");
    }

    // 7. Método para mostrar el resumen general del curso (Tabla, promedios y estados)
    public void mostrarResumenCurso() {
        System.out.println("\n========== RESUMEN DEL CURSO ==========");
        
        // Mostrar tabla de notas
        System.out.printf("%-15s", "Nombre");
        for (int j = 0; j < cantidadCortes; j++) {
            System.out.printf("Corte %d   ", (j + 1));
        }
        System.out.println("Promedio");
        System.out.println("--------------------------------------------------");

        for (int i = 0; i < estudiantes.length; i++) {
            System.out.printf("%-15s", estudiantes[i].nombre);
            for (int j = 0; j < cantidadCortes; j++) {
                System.out.printf("%-10.1f", estudiantes[i].notas[j]);
            }
            System.out.printf("%-10.2f\n", estudiantes[i].calcularPromedio());
        }

        // Mostrar promedios por corte
        System.out.println("\n--- PROMEDIOS POR CORTE ---");
        double[] promediosCortes = calcularPromediosPorCorte();
        for (int j = 0; j < promediosCortes.length; j++) {
            System.out.printf("Corte %d: %.2f\n", (j + 1), promediosCortes[j]);
        }

        // Listados de desempeño
        int aprobados = 0, reprobados = 0, enRiesgo = 0, destacados = 0;
        
        System.out.println("\n--- ESTADO DE LOS ESTUDIANTES ---");
        for (int i = 0; i < estudiantes.length; i++) {
            double prom = estudiantes[i].calcularPromedio();
            System.out.print("- " + estudiantes[i].nombre + ": ");
            
            if (prom >= 3.0) {
                System.out.print("Aprobado ");
                aprobados++;
                if (prom >= 4.5) {
                    System.out.print("(¡Desempeño Destacado!)");
                    destacados++;
                }
            } else {
                System.out.print("Reprobado ");
                reprobados++;
                if (prom >= 2.0 && prom <= 2.9) {
                    System.out.print("(En Riesgo)");
                    enRiesgo++;
                }
            }
            System.out.println();
        }

        // Totales estadísticos
        System.out.println("\n--- ESTADÍSTICAS TOTALES ---");
        System.out.println("Total Aprobados: " + aprobados);
        System.out.println("Total Reprobados: " + reprobados);
        System.out.println("Estudiantes en Riesgo (2.0 - 2.9): " + enRiesgo);
        System.out.println("Estudiantes Destacados (>= 4.5): " + destacados);
        System.out.println("=======================================\n");
    }
}
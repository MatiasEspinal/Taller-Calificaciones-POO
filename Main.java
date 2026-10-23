import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("==================================================");
        System.out.println(" BIENVENIDO AL SISTEMA DEL CAMPUS DIGITAL ANDINO ");
        System.out.println("==================================================");

        // 1. Pedir y validar cantidad de estudiantes (n) y cortes (m)
        int n = 0, m = 0;
        
        while (n <= 0) {
            System.out.print("Ingrese la cantidad de estudiantes (mayor a 0): ");
            n = scanner.nextInt();
        }
        
        while (m <= 0) {
            System.out.print("Ingrese la cantidad de evaluaciones/cortes (mayor a 0): ");
            m = scanner.nextInt();
        }

        // 2. Instanciar el objeto Curso (TDA 2D)
        Curso curso = new Curso(n, m);
        int opcion = 0;

        // 3. Menú interactivo
        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Registrar nombres de estudiantes");
            System.out.println("2. Registrar notas de los estudiantes");
            System.out.println("3. Mostrar resumen del curso (Tabla, promedios y estado)");
            System.out.println("4. Ordenar estudiantes por promedio (Menor a Mayor)");
            System.out.println("5. Buscar estudiante por nombre");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

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
                    curso.ordenarPorPromedio();
                    curso.mostrarResumenCurso();
                    break;
                case 5:
                    System.out.print("\nIngrese el nombre del estudiante a buscar: ");
                    String nombreBusqueda = scanner.nextLine();
                    Estudiante encontrado = curso.buscarEstudiantePorNombre(nombreBusqueda);
                    if (encontrado != null) {
                        System.out.println("\n¡Estudiante encontrado!");
                        encontrado.mostrarInfo();
                        System.out.println("Nota más alta: " + encontrado.obtenerNotaMayor());
                        System.out.println("Nota más baja: " + encontrado.obtenerNotaMenor());
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

        scanner.close();
    }
}

public class Estudiante {
    // Atributos
    String nombre;
    double[] notas; // Vector de notas del estudiante

    // Constructor
    public Estudiante(String nombre, int cantidadNotas) {
        this.nombre = nombre;
        // Inicializamos el vector con la cantidad de cortes/evaluaciones
        this.notas = new double[cantidadNotas]; 
    }

    // Método para calcular el promedio de este estudiante
    public double calcularPromedio() {
        double suma = 0;
        for (int i = 0; i < notas.length; i++) {
            suma += notas[i];
        }
        return suma / notas.length;
    }

    // Método para obtener la nota más alta
    public double obtenerNotaMayor() {
        double mayor = notas[0];
        for (int i = 1; i < notas.length; i++) {
            if (notas[i] > mayor) {
                mayor = notas[i];
            }
        }
        return mayor;
    }

    // Método para obtener la nota más baja
    public double obtenerNotaMenor() {
        double menor = notas[0];
        for (int i = 1; i < notas.length; i++) {
            if (notas[i] < menor) {
                menor = notas[i];
            }
        }
        return menor;
    }

    // Método para mostrar la información básica del estudiante
    public void mostrarInfo() {
        System.out.println("Estudiante: " + nombre);
        System.out.print("Notas: ");
        for (int i = 0; i < notas.length; i++) {
            System.out.print(notas[i] + " | ");
        }
        System.out.println("\nPromedio: " + calcularPromedio());
    }
}
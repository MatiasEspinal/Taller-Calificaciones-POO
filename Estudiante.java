import java.util.Arrays;

/**
 * TDA 1D: representa a un estudiante y su vector de notas.
 */
public class Estudiante {
    public static final double NOTA_MIN = 0.0;
    public static final double NOTA_MAX = 5.0;

    // Atributos
    private final String nombre;
    private final double[] notas; // Vector de notas del estudiante

    // Constructor
    public Estudiante(String nombre, int cantidadNotas) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (cantidadNotas <= 0) {
            throw new IllegalArgumentException("Debe haber al menos una evaluación.");
        }
        this.nombre = nombre.trim();
        // Inicializamos el vector con la cantidad de cortes/evaluaciones
        this.notas = new double[cantidadNotas];
    }

    // Getters (encapsulamiento)
    public String getNombre() {
        return nombre;
    }

    public int getCantidadNotas() {
        return notas.length;
    }

    public double getNota(int indice) {
        if (indice < 0 || indice >= notas.length) {
            throw new IndexOutOfBoundsException("Corte inexistente: " + indice);
        }
        return notas[indice];
    }

    /** Devuelve una copia para que nadie modifique el vector interno. */
    public double[] getNotas() {
        return Arrays.copyOf(notas, notas.length);
    }

    /** Asigna una nota validando índice y rango 0.0 - 5.0 (rechaza NaN). */
    public void setNota(int indice, double valor) {
        if (indice < 0 || indice >= notas.length) {
            throw new IndexOutOfBoundsException("Corte inexistente: " + indice);
        }
        if (Double.isNaN(valor) || valor < NOTA_MIN || valor > NOTA_MAX) {
            throw new IllegalArgumentException("La nota debe estar entre 0.0 y 5.0.");
        }
        notas[indice] = valor;
    }

    // Método para calcular el promedio de este estudiante (redondeado a 2 decimales)
    public double calcularPromedio() {
        double suma = 0;
        for (int i = 0; i < notas.length; i++) {
            suma += notas[i];
        }
        return Math.round(suma / notas.length * 100.0) / 100.0;
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
            System.out.printf("%.2f | ", notas[i]);
        }
        System.out.printf("%nPromedio: %.2f%n", calcularPromedio());
    }
}

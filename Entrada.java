import java.util.Scanner;

/**
 * Clase de apoyo que centraliza TODA la lectura por consola.
 * Evita tener varios Scanner sobre System.in y valida los datos ingresados.
 */
public class Entrada {
    private final Scanner sc;

    public Entrada(Scanner sc) {
        this.sc = sc;
    }

    /** Lee una línea completa; si la entrada se cierra (Ctrl+D / Ctrl+Z) lanza IllegalStateException. */
    private String leerLinea() {
        if (!sc.hasNextLine()) {
            throw new IllegalStateException("Entrada finalizada por el usuario.");
        }
        return sc.nextLine().trim();
    }

    /** Lee un entero dentro de [min, max], repitiendo hasta que sea válido. */
    public int leerEntero(String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            String linea = leerLinea();
            if (!linea.matches("-?\\d+")) {
                System.out.println("  Error: ingrese un número entero válido.");
                continue;
            }
            try {
                int v = Integer.parseInt(linea);
                if (v >= min && v <= max) {
                    return v;
                }
                System.out.printf("  Error: el valor debe estar entre %d y %d.%n", min, max);
            } catch (NumberFormatException e) { // número demasiado grande
                System.out.println("  Error: el número es demasiado grande.");
            }
        }
    }

    /**
     * Lee un decimal dentro de [min, max]. Acepta punto o coma (4.5 y 4,5)
     * y rechaza letras, NaN e Infinity.
     */
    public double leerDecimal(String mensaje, double min, double max) {
        while (true) {
            System.out.print(mensaje);
            String linea = leerLinea().replace(',', '.');
            if (!linea.matches("-?\\d+(\\.\\d+)?")) {
                System.out.println("  Error: ingrese un número decimal válido (ej. 3.5).");
                continue;
            }
            double v = Double.parseDouble(linea);
            if (v >= min && v <= max) {
                return v;
            }
            System.out.printf("  Error: el valor debe estar entre %.1f y %.1f.%n", min, max);
        }
    }

    /** Lee un texto no vacío de máximo maxLongitud caracteres. */
    public String leerTexto(String mensaje, int maxLongitud) {
        while (true) {
            System.out.print(mensaje);
            String t = leerLinea();
            if (t.isEmpty()) {
                System.out.println("  Error: el texto no puede estar vacío.");
            } else if (t.length() > maxLongitud) {
                System.out.println("  Error: máximo " + maxLongitud + " caracteres.");
            } else {
                return t;
            }
        }
    }

    /** Pregunta sí/no. */
    public boolean confirmar(String mensaje) {
        System.out.print(mensaje + " (s/n): ");
        return leerLinea().equalsIgnoreCase("s");
    }
}

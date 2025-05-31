package View;

import java.util.Scanner;

public class Vista {
    private Scanner sc = new Scanner(System.in);

    public void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Crear cliente");
        System.out.println("2. Crear registrador");
        System.out.println("3. Cargar consumo automático");
        System.out.println("4. Calcular valor total");
        System.out.println("5. Editar cliente");
        System.out.println("6. Editar registrador");
        System.out.println("7. Consumo mínimo y máximo");
        System.out.println("8. Consumo por franjas");
        System.out.println("9. Consumo por días");
        System.out.println("10. Cargar consumo de un cliente específico");
        System.out.println("11. Cambiar el consumo de una hora");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public int leerOpcion() {
    while (!sc.hasNextInt()) {
        System.out.println("Por favor, ingrese un número válido.");
        sc.next(); // descarta la entrada inválida
        System.out.print("Seleccione una opción: ");
    }
    int opcion = sc.nextInt();
    sc.nextLine(); // <-- línea agregada
    return opcion;
}

    public Scanner getScanner() {
        return sc;
    }

    // 🆕 Métodos adicionales para entrada de texto
    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        sc.nextLine(); // limpiar buffer
        return sc.nextLine();
    }

    public String leerLineaSimple(String mensaje) {
        System.out.print(mensaje);
        return sc.next();
    }
}

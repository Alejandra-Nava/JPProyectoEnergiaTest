package Controller;

import Model.*;
import View.Vista;
import Controller.FacturaPDF;
import java.util.ArrayList;
import java.util.Scanner;

public class Controlador {
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private Vista vista = new Vista();
    private Scanner sc = vista.getScanner();

    public void menuPrincipal() {
    Scanner scanner = new Scanner(System.in);
    int opcion;
    do {
        System.out.println("--- MENÚ PRINCIPAL ---");
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
        System.out.println("12. Generar factura PDF");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
        opcion = scanner.nextInt();
        scanner.nextLine(); // limpia buffer

        switch (opcion) {
            case 1:
                crearCliente();
                break;
            case 2:
                crearRegistrador();
                break;
            case 3:
                cargarConsumoAutomatico();
                break;
            case 4:
                calcularValorTotal();
                break;
            case 5:
                editarCliente();
                break;
            case 6:
                editarRegistrador();
                break;
            case 7:
                consumoMinimoMaximo();
                break;
            case 8:
                consumoPorFranjas();
                break;
            case 9:
                consumoPorDias();
                break;
            case 10:
                cargarConsumoCliente();
                break;
            case 11:
                cambiarConsumoHora();
                break;
            case 12:
                generarFacturaPDF();
                break;
            case 0:
                System.out.println("Saliendo...");
                break;
            default:
                System.out.println("Opción inválida.");
        }
    } while (opcion != 0);
}

    private void crearCliente() {
        System.out.println("\n--- Crear Cliente ---");
        String nombre = vista.leerTexto("Nombre: ");
        String tipoId = vista.leerLineaSimple("Tipo ID: ");
        String id = vista.leerLineaSimple("Número de ID: ");
        String correo = vista.leerTexto("Correo electrónico: ");
        String direccion = vista.leerTexto("Dirección: ");
        clientes.add(new Cliente(id, tipoId, correo, direccion, nombre));
        System.out.println("Cliente creado correctamente.");
    }

    private void crearRegistrador() {
        System.out.println("\n--- Crear Registrador ---");
        String idCliente = vista.leerLineaSimple("ID del cliente: ");
        Cliente cliente = buscarCliente(idCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        String id = vista.leerLineaSimple("ID del registrador: ");
        String dir = vista.leerTexto("Dirección del registrador: ");
        String ciudad = vista.leerTexto("Ciudad: ");
        cliente.agregarRegistrador(new Registrador(id, dir, ciudad));
        System.out.println("Registrador agregado.");
    }

    private void cargarConsumoAutomatico() {
        for (Cliente c : clientes) {
            for (Registrador r : c.getRegistradores()) {
                for (int d = 0; d < 31; d++) {
                    for (int h = 0; h < 24; h++) {
                        double valor = Math.random() * 1000;
                        r.registrarConsumo(d, h, valor);
                    }
                }
            }
        }
        System.out.println("Consumos cargados automáticamente.");
    }

    private void calcularValorTotal() {
        System.out.print("ID del cliente: ");
        String idCliente = sc.nextLine();
        Cliente cliente = buscarCliente(idCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        for (Registrador r : cliente.getRegistradores()) {
            double total = Consumo.calcularValorTotal(r.getConsumo());
            System.out.println("Factura total del registrador " + r.getId() + ": " + total + " COP");
        }
    }

    private void editarCliente() {
        System.out.println("\n--- Editar Cliente ---");
        String id = vista.leerLineaSimple("ID del cliente: ");
        Cliente c = buscarCliente(id);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        c.setTipoId(vista.leerLineaSimple("Nuevo tipo ID: "));
        c.setCorreo(vista.leerTexto("Nuevo correo: "));
        c.setDireccion(vista.leerTexto("Nueva dirección: "));
        c.setNombre(vista.leerTexto("Nuevo nombre: "));
        System.out.println("Cliente editado.");
    }

    private void editarRegistrador() {
        System.out.println("\n--- Editar Registrador ---");
        String idCliente = vista.leerLineaSimple("ID del cliente: ");
        Cliente cliente = buscarCliente(idCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        String idReg = vista.leerLineaSimple("ID del registrador: ");
        for (Registrador r : cliente.getRegistradores()) {
            if (r.getId().equals(idReg)) {
                r.setDireccion(vista.leerTexto("Nueva dirección: "));
                r.setCiudad(vista.leerTexto("Nueva ciudad: "));
                System.out.println("Registrador editado.");
                return;
            }
        }
        System.out.println("Registrador no encontrado.");
    }

    private void cargarConsumoCliente() {
        System.out.print("ID del cliente: ");
        String idCliente = sc.nextLine();
        Cliente cliente = buscarCliente(idCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        for (Registrador r : cliente.getRegistradores()) {
            for (int d = 0; d < 31; d++) {
                for (int h = 0; h < 24; h++) {
                    double valor = Math.random() * 1000;
                    r.registrarConsumo(d, h, valor);
                }
            }
        }
        System.out.println("Consumo cargado para el cliente " + idCliente);
    }

    private void cambiarConsumoHora() {
        System.out.print("ID del cliente: ");
        String idCliente = sc.nextLine();
        Cliente cliente = buscarCliente(idCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        System.out.print("ID del registrador: ");
        String idRegistrador = sc.nextLine();
        Registrador registrador = null;
        for (Registrador r : cliente.getRegistradores()) {
            if (r.getId().equals(idRegistrador)) {
                registrador = r;
                break;
            }
        }
        if (registrador == null) {
            System.out.println("Registrador no encontrado.");
            return;
        }
        System.out.print("Día (1-31): ");
        int dia = sc.nextInt();
        System.out.print("Hora (0-23): ");
        int hora = sc.nextInt();
        System.out.print("Nuevo valor de consumo: ");
        double nuevoConsumo = sc.nextDouble();
        sc.nextLine(); // limpiar buffer
        registrador.registrarConsumo(dia - 1, hora, nuevoConsumo);
        System.out.println("Consumo actualizado correctamente.");
    }

    private void consumoMinimoMaximo() {
        System.out.print("ID del cliente: ");
        Cliente cliente = buscarCliente(sc.nextLine());
        if (cliente == null) return;
        for (Registrador r : cliente.getRegistradores()) {
            double min = Consumo.consumoMinimo(r.getConsumo());
            double max = Consumo.consumoMaximo(r.getConsumo());
            System.out.println("Registrador " + r.getId() + ": Min = " + min + ", Max = " + max);
        }
    }

    private void consumoPorFranjas() {
        System.out.print("ID del cliente: ");
        Cliente cliente = buscarCliente(sc.nextLine());
        if (cliente == null) return;
        for (Registrador r : cliente.getRegistradores()) {
            double[] franjas = Consumo.consumoPorFranjas(r.getConsumo());
            System.out.println("Registrador " + r.getId() + ":");
            System.out.println("Franja 00-06: " + franjas[0]);
            System.out.println("Franja 07-17: " + franjas[1]);
            System.out.println("Franja 18-23: " + franjas[2]);
        }
    }

    private void consumoPorDias() {
        System.out.print("ID del cliente: ");
        Cliente cliente = buscarCliente(sc.nextLine());
        if (cliente == null) return;
        for (Registrador r : cliente.getRegistradores()) {
            double[] dias = Consumo.consumoPorDia(r.getConsumo());
            System.out.println("Registrador " + r.getId() + " por días:");
            for (int i = 0; i < dias.length; i++) {
                System.out.println("Día " + (i + 1) + ": " + dias[i]);
            }
        }
    }
    private void generarFacturaPDF() {
        System.out.print("ID del cliente: ");
        String idCliente = sc.nextLine();
        Cliente cliente = buscarCliente(idCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        System.out.print("ID del registrador: ");
        String idRegistrador = sc.nextLine();
        Registrador registrador = null;
        for (Registrador r : cliente.getRegistradores()) {
            if (r.getId().equals(idRegistrador)) {
                registrador = r;
                break;
            }
        }
        if (registrador == null) {
            System.out.println("Registrador no encontrado.");
            return;
        }
        double total = Consumo.calcularValorTotal(registrador.getConsumo());
        FacturaPDF.generarFactura(cliente, registrador, total);
        System.out.println("Factura PDF generada correctamente.");
    }
    private Cliente buscarCliente(String id) {
        for (Cliente c : clientes) {
            if (c.getId().equals(id)) return c;
        }
        return null;
    }
}
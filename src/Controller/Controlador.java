package Controller;

import Model.*;
import View.Vista;
import java.util.ArrayList;
import java.util.Scanner;

public class Controlador {
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private Vista vista = new Vista();
    private Scanner sc = vista.getScanner();

    public void menuPrincipal() {
        int opcion;
        do {
            vista.mostrarMenu();
            opcion = vista.leerOpcion();

            switch (opcion) {
                case 1:
                    crearCliente();
                    break;
                case 2:
                    crearRegistrador();
                    break;
                case 3:
                    cargarConsumo();
                    break;
                case 4:
                    calcularFactura();
                    break;
                case 5:
                    editarCliente();
                    break;
                case 6:
                    editarRegistrador();
                    break;
                case 7:
                    verMinimoMaximo();
                    break;
                case 8:
                    verFranjas();
                    break;
                case 9:
                    verDias();
                    break;
                case 10:
                    cargarConsumoClienteEspecifico();
                    break;
                case 11:
                    modificarConsumoHora();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
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

    private void cargarConsumo() {
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

    private void calcularFactura() {
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

    private void cargarConsumoClienteEspecifico() {
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

    private void modificarConsumoHora() {
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

    private void verMinimoMaximo() {
        System.out.print("ID del cliente: ");
        Cliente cliente = buscarCliente(sc.nextLine());
        if (cliente == null) return;
        for (Registrador r : cliente.getRegistradores()) {
            double min = Consumo.consumoMinimo(r.getConsumo());
            double max = Consumo.consumoMaximo(r.getConsumo());
            System.out.println("Registrador " + r.getId() + ": Min = " + min + ", Max = " + max);
        }
    }

    private void verFranjas() {
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

    private void verDias() {
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

    private Cliente buscarCliente(String id) {
        for (Cliente c : clientes) {
            if (c.getId().equals(id)) return c;
        }
        return null;
    }
}

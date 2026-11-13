package app;
import Servicio.DomiServicio;
import modelo.*;
import java.util.Scanner;
public class Menu {

    private final Scanner sc = new Scanner(System.in);
    private final DomiServicio service;

    public Menu(DomiServicio service) {
        this.service = service;
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            String opcion = sc.nextLine().trim();

            try {
                salir = ejecutarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Debes escribir un numero valido.");
            } catch (IllegalArgumentException e) {
                System.out.println("Alerta: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        }
        sc.close();
    }

    private boolean ejecutarOpcion(String opcion) {
        switch (opcion) {
            case "1"  -> registrarCliente();
            case "2"  -> service.listarClientes();
            case "3"  -> editarCliente();
            case "4"  -> eliminarCliente();
            case "5"  -> registrarRepartidor();
            case "6"  -> service.listarRepartidores();
            case "7"  -> cambiarDisponibilidad();
            case "8"  -> crearPedido();
            case "9"  -> entregarPedido();
            case "10" -> cancelarPedido();
            case "11" -> service.listarPedidos();
            case "12" -> toggleLluvia();
            case "13" -> System.out.println(service.generarReporteCierre());
            case "0"  -> {
                System.out.println("Hasta luego");
                return true;
            }
            default -> System.out.println("Opcion invalida.");
        }
        return false;
    }

    private void mostrarMenu() {
        System.out.println("================= DOMI =======================");
        if (service.ModoLluviaActivo()) System.out.println(" MODO LLUVIA ACTIVO");

        System.out.println(" CLIENTES      1. Registrar    2. Listar");
        System.out.println("               3. Editar       4. Eliminar");
        System.out.println(" REPARTIDORES  5. Registrar    6. Listar");
        System.out.println("               7. Cambiar disponibilidad");
        System.out.println(" PEDIDOS       8. Crear pedido (cotiza y asigna)");
        System.out.println("               9. Entregar     10. Cancelar");
        System.out.println("               11. Listar pedidos");
        System.out.println(" OPERACION     12. Activar/desactivar lluvia");
        System.out.println("               13. Cierre del dia");
        System.out.println("               0. Salir");
        System.out.println("=================================================");
        System.out.print("Opcion: ");
    }

    // ================== CLIENTES ==================

    private void registrarCliente() {
        System.out.print("Cedula: ");
        String cedula = sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Telefono: ");
        String tel = sc.nextLine();
        System.out.print("Barrio: ");
        String barrio = sc.nextLine();
        System.out.print("Direccion: ");
        String dir = sc.nextLine();

        service.registrarCliente(new Cliente(cedula, nombre, tel, barrio, dir));
        System.out.println("Cliente registrado.");
    }

    private void editarCliente() {
        System.out.print("Cedula a editar: ");
        String cedula = sc.nextLine().trim();
        System.out.print("Nuevo telefono: ");
        String tel = sc.nextLine();
        System.out.print("Nuevo barrio: ");
        String barrio = sc.nextLine();
        System.out.print("Nueva direccion: ");
        String dir = sc.nextLine();

        service.editarCliente(cedula, tel, barrio, dir);
        System.out.println("Cliente actualizado.");
    }

    private void eliminarCliente() {
        System.out.print("Cedula a eliminar: ");
        String cedula = sc.nextLine().trim();

        service.eliminarCliente(cedula);
        System.out.println("Cliente eliminado.");
    }

    // ================== REPARTIDORES ==================

    private void registrarRepartidor() {
        System.out.print("Codigo (ej R-04): ");
        String codigo = sc.nextLine().trim();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Tipo (Bici/Moto/Carro/Dron): ");
        String tipo = sc.nextLine().trim().toLowerCase();

        Repartidor r = switch (tipo) {
            case "bici"  -> new Bici(codigo, nombre);
            case "moto"  -> new Moto(codigo, nombre);
            case "carro" -> new Carro(codigo, nombre);
            case "dron"  -> new Dron(codigo, nombre);
            default -> throw new IllegalArgumentException("Tipo invalido. Use Bici, Moto, Carro o Dron.");
        };

        service.registrarRepartidor(r);
    }

    private void cambiarDisponibilidad() {
        System.out.print("Codigo del repartidor: ");
        String codigo = sc.nextLine().trim();

        service.cambiarDisponibilidad(codigo);
        System.out.println("Disponibilidad cambiada.");
    }

    // ================== PEDIDOS ==================

    private void crearPedido() {
        System.out.print("Cedula del cliente: ");
        String cedula = sc.nextLine().trim();
        System.out.print("Descripcion: ");
        String desc = sc.nextLine();
        System.out.print("¿Es un pedido grande? (s/n): ");
        boolean grande = sc.nextLine().trim().equalsIgnoreCase("s");

        DomiServicio.ResultadoAsignacion res = service.crearPedido(cedula, desc, grande);

        System.out.println("Cotizando pedido...");
        for (DomiServicio.Cotizacion c : res.cotizaciones) {
            System.out.printf(" %s %s (%s) $%.0f %.1f min%n",
                    c.repartidor.getCodigo(),
                    c.repartidor.getNombre(),
                    c.repartidor.getTipo(),
                    c.costo, c.tiempo);
        }

        Pedido p = res.pedido;
        System.out.printf("Pedido #%d asignado a %s (%s) - %s%n",
                p.getNumero(),
                p.getRepartidorAsignado().getNombre(),
                p.getRepartidorAsignado().getTipo(),
                p.getEstado());
    }

    private void entregarPedido() {
        System.out.print("Numero de pedido: ");
        int num = Integer.parseInt(sc.nextLine().trim());

        Pedido p = service.buscarPedido(num);
        if (p == null)
            throw new IllegalArgumentException("No existe el pedido #" + num);
        if (!p.estaEnCamino())
            throw new IllegalArgumentException("El pedido no esta EN_CAMINO.");

        System.out.print("Calificacion del cliente (1-5): ");
        int cal = Integer.parseInt(sc.nextLine().trim());

        service.entregar(num, cal);
        System.out.println("Pedido #" + num + " entregado y calificado.");
    }

    private void cancelarPedido() {
        System.out.print("Numero de pedido: ");
        int num = Integer.parseInt(sc.nextLine().trim());

        service.cancelarPedido(num);
    }

    // ================== OPERACION ==================

    private void toggleLluvia() {
        service.alternarModoLluvia();
        System.out.println(service.ModoLluviaActivo()
                ? "Modo lluvia ACTIVADO"
                : "Modo lluvia DESACTIVADO");
    }
}
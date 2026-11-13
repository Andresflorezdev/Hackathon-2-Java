package Servicio;
import modelo.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class DomiServicio {

    private boolean modoLluvia = false;

    Map<String, Cliente> clientes = new HashMap<>();
    Set<String> barriosCobertura = new HashSet<>();
    Map<String, Double> distanciaBarrios = new HashMap<>();
    List<Repartidor> repartidores = new ArrayList<>();
    List<Pedido> pedidos = new ArrayList<>();

    public DomiServicio() {
        barriosCobertura.add("Chapinero");
        barriosCobertura.add("Teusaquillo");
        barriosCobertura.add("La Candelaria");
        barriosCobertura.add("Usaquen");
        barriosCobertura.add("Cedritos");
        barriosCobertura.add("Fontibon");
        barriosCobertura.add("Suba");
        barriosCobertura.add("Kennedy");
        barriosCobertura.add("Bosa");

        distanciaBarrios.put("Chapinero", 1.5);
        distanciaBarrios.put("Teusaquillo", 3.0);
        distanciaBarrios.put("La Candelaria", 4.5);
        distanciaBarrios.put("Usaquen", 6.0);
        distanciaBarrios.put("Cedritos", 8.0);
        distanciaBarrios.put("Fontibon", 11.0);
        distanciaBarrios.put("Suba", 12.0);
        distanciaBarrios.put("Kennedy", 14.0);
        distanciaBarrios.put("Bosa", 18.0);

        repartidores.add(new Bici("R-01", "Camila Torres"));
        repartidores.add(new Moto("R-02", "Andres Mejia"));
        repartidores.add(new Carro("R-03", "Jorge Pardo"));

        registrarCliente(new Cliente("1020304050", "Laura Gómez", "3001234567", "Teusaquillo", "calle 43 f sur 78 32"));
        registrarCliente(new Cliente("1030405060", "Mateo Rincón", "3109876543", "Usaquen", "calle 43 f sur 78 32"));

    }

    public Cliente buscarCliente(String cedula) {
        return clientes.get(cedula);
    }

    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        System.out.println("lista de clientes");

        for (Cliente c : clientes.values()) {
            System.out.println("cedula: " + c.getCedula() +
                    " - Nombre: " + c.getNombre() +
                    " - Teléfono: " + c.getTelefono());

        }
    }

    public void registrarCliente(Cliente c) {
        if (clientes.containsKey(c.getCedula())) {
            throw new IllegalArgumentException("El cliente ya esta registrado");
        } else if (barriosCobertura.contains(c.getBarrio())) {
            clientes.put(c.getCedula(), c);
        } else {
            throw new IllegalArgumentException("Barrio sin cobertura");
        }
    }

    public void editarCliente(String cedula, String nuevoTelefono, String nuevoBarrio, String nuevaDireccion) {
        Cliente c = buscarCliente(cedula);
        if (c == null) {
            throw new IllegalArgumentException("El cliente no existe");
        }
        if (!barriosCobertura.contains(nuevoBarrio)) {
            throw new IllegalArgumentException("Barrio sin cobertura");
        }
        c.setTelefono(nuevoTelefono);
        c.setBarrio(nuevoBarrio);
        c.setDireccion(nuevaDireccion);
    }

    public Cliente eliminarCliente(String cedula) {
        Cliente c = buscarCliente(cedula);

        if (c == null) {
            throw new IllegalArgumentException("El cliente no existe");
        }

        for (Pedido p : pedidos) {
            if (p.getCliente().getCedula().equals(cedula) && p.getEstado().equals("EN_CAMINO")) {
                throw new IllegalArgumentException("no se puede eliminar cliente tiene un pedido EN_CAMINO");
            }
        }

        return clientes.remove(cedula);

    }

    public Double consultarDistancia(String barrio) {
        return distanciaBarrios.get(barrio);
    }

    public Pedido buscarPedido(int numeroPedido) {
        for (Pedido p : pedidos) {
            if (p.getNumero() == (numeroPedido)) {
                return p;
            }
        }
        return null;
    }


    public void alternarModoLluvia() {

        this.modoLluvia = !this.modoLluvia;
    }

    public boolean ModoLluviaActivo() {

        return modoLluvia;
    }

    public Repartidor asignarRepartidor() {
        for (Repartidor r : repartidores) {

            if (r.isDisponible()) {
                if (modoLluvia && "Bici".equalsIgnoreCase(r.getTipo())) {
                    continue;
                }
                return r;
            }
        }
        return null;
    }

    public boolean registrarRepartidor(Repartidor nuevoRepartidor) {
        if (nuevoRepartidor == null) {
            throw new IllegalArgumentException("El repartidor no puede ser nulo.");
        }
        for (Repartidor r : repartidores) {
            if (r.getCodigo().equals(nuevoRepartidor.getCodigo())) {
                System.out.println("Ya existe un repartidor con ese código.");
                return false;
            }
        }
        repartidores.add(nuevoRepartidor);
        System.out.println("¡Repartidor registrado con éxito!");
        return true;
    }


    public void listarRepartidores() {
        if (repartidores.isEmpty()) {
            System.out.println("No hay repartidores registrados.");
            return;
        }
        System.out.println("Lista de Repartidores");
        for (Repartidor r : repartidores) {
            String estadoDisponibilidad = r.isDisponible() ? "Disponible" : "Ocupado";

            System.out.println("Código: " + r.getCodigo() +
                    " - Nombre: " + r.getNombre() +
                    " - Tipo: " + r.getTipo() +
                    " - Estado: " + estadoDisponibilidad);
        }
    }

    public Repartidor buscarRepartidor(String codigo) {
        for (Repartidor r : repartidores) {
            if (r.getCodigo().equals(codigo)) {
                return r;
            }
        }
        return null;
    }

    public void cambiarDisponibilidad(String codigo) {
        for (Repartidor r : repartidores) {
            if (r.getCodigo().equals(codigo)) {
                r.setDisponible(!r.isDisponible());
                return;
            }
        }
        throw new IllegalArgumentException("El repartidor no existe");
    }

    public void entregar(int numeroPedido, int estrellas) {
        Pedido p = buscarPedido(numeroPedido);
        if (p == null) {
            throw new IllegalArgumentException("El pedido no existe");
        }
        if (!p.estaEnCamino()) {
            throw new IllegalArgumentException("Solo se puede entregar un pedido EN_CAMINO");
        }
        p.getRepartidorAsignado().calificar(estrellas);
        p.entregrar();
    }

    public void cancelarPedido(int numeroPedido) {

        Pedido pedidoEncontrado = buscarPedido(numeroPedido);

        if (pedidoEncontrado == null) {
            throw new IllegalArgumentException("El pedido no existe");
        }

        if (pedidoEncontrado.getEstado().equalsIgnoreCase("ENTREGADO")) {
            System.out.println("Mensaje de error; el pedido sigue ENTREGADO");
            return;
        }

        if (pedidoEncontrado.getRepartidorAsignado() != null) {
            pedidoEncontrado.getRepartidorAsignado().setDisponible(true);
        }

        pedidos.remove(pedidoEncontrado);
        System.out.println("¡El pedido " + numeroPedido + " ha sido eliminado con éxito!");

    }


    public void listarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados");
            return;
        }
        for (Pedido p : pedidos) {
            System.out.println(p);
        }

    }

    public static class Cotizacion {

        public final Repartidor repartidor;
        public final double costo;
        public final double tiempo;

        public Cotizacion(Repartidor repartidor, double costo, double tiempo) {
            this.repartidor = repartidor;
            this.costo = costo;
            this.tiempo = tiempo;
        }
    }

    public static class ResultadoAsignacion {

        public final Pedido pedido;
        public final List<Cotizacion> cotizaciones;

        public ResultadoAsignacion(Pedido pedido, List<Cotizacion> cotizaciones) {
            this.pedido = pedido;
            this.cotizaciones = cotizaciones;
        }
    }

    public ResultadoAsignacion crearPedido(String cedula, String descripcion, boolean grande) {

        Cliente c = buscarCliente(cedula);
        if (c == null) {
            throw new IllegalArgumentException("El cliente no existe");
        }
        Double km = consultarDistancia(c.getBarrio());
        if (km == null) {
            throw new IllegalArgumentException("Barrio sin cobertura");
        }

        List<Cotizacion> cotizaciones = new ArrayList<>();

        for (Repartidor r : repartidores) {
            if (r.isDisponible() && r.LopuedeLlevar(km, grande, modoLluvia)) {
                cotizaciones.add(new Cotizacion(r, r.calcularCosto(km), r.calcularTiempo(km)));
            }
        }
        if (cotizaciones.isEmpty()) {
            throw new IllegalArgumentException("No hay repartidores disponibles para este pedido");
        }

        Cotizacion mejor = Collections.min(cotizaciones,
                Comparator.comparingDouble((Cotizacion q) -> q.costo)
                        .thenComparingDouble(q -> q.tiempo));

        Pedido p = new Pedido(c, descripcion, grande, km, mejor.repartidor, mejor.costo, mejor.tiempo);
        pedidos.add(p);
        return new ResultadoAsignacion(p, cotizaciones);
    }

    public String generarReporteCierre() {
        int entregados = 0, cancelados = 0, enCamino = 0;
        double ingresos = 0;
        for (Pedido p : pedidos) {
            switch (p.getEstado()) {
                case "ENTREGADO" -> {
                    entregados++;
                    ingresos += p.getCosto();
                }
                case "CANCELADO" -> cancelados++;
                default -> enCamino++;
            }
        }
        return "\n===== CIERRE DEL DIA =====\n"
                + "Pedidos totales: " + pedidos.size() + "\n"
                + "Entregados: " + entregados + "\n"
                + "Cancelados: " + cancelados + "\n"
                + "En camino: " + enCamino + "\n"
                + String.format("Ingresos: $%.0f%n", ingresos);
    }

}
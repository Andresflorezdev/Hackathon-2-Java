package Servicio;
import modelo.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class DomiServicio {

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

        repartidores.add( new Bici ("R-01", "Camila Torres"));
        repartidores.add(new Moto("R-02", "Andres Mejia"));
        repartidores.add(new Carro("R-03", "Jorge Pardo"));

        registrarCliente(new Cliente("1020304050", "Laura Gómez", "3001234567", "Teusaquillo", "calle 43 f sur 78 32"));
        registrarCliente(new Cliente("1030405060", "Mateo Rincón", "3109876543", "Usaquén", "calle 43 f sur 78 32"));

    }

    public Cliente buscarCliente (String cedula){
        return clientes.get(cedula);
    }

    public void registrarCliente (Cliente c){
        if (clientes.containsKey(c.getCedula())){
            throw new IllegalArgumentException("El cliente ya esta registrado");
        } else if (barriosCobertura.contains(c.getBarrio())) {
            clientes.put(c.getCedula(), c);
        }
        else {
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

    public Cliente eliminarCliente (String cedula){
        Cliente c = buscarCliente(cedula);

        if (c == null) {
            throw new IllegalArgumentException("El cliente no existe");
        }

        for (Pedido p : pedidos){
            if (p.getCliente().getCedula().equals(cedula)  && p.getEstado().equals("EN_CAMINO")){
                throw new IllegalArgumentException("no se puede eliminar cliente tiene un pedido EN_CAMINO");
            }
        }

        return clientes.remove(cedula);

    }

    public Double consultarDistancia (String barrio){
        return distanciaBarrios.get(barrio);
    }

    public Pedido buscarPedido (int numeroPedido) {
        for (Pedido p : pedidos) {
            if (p.getNumero() == (numeroPedido)) {
                return p;
            }
        }
        return null;
    }

    // hacen falta estos metodos 

    public void registrarRepartidor (){

    }

    public void listarRepartidores (){

    }

    public void cambiarDisponibilidad (String codigo){
        for (Repartidor r : repartidores) {
            if (r.getCodigo().equals(codigo)) {
                r.setDisponible(!r.isDisponible());
                return;
            }
        }
        throw new IllegalArgumentException("El repartidor no existe");
    }

    public void entregar (int numeroPedido, int estrellas){
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

    public void Cancelar (int numeroPedido) {
        Pedido p = buscarPedido(numeroPedido);
        if (p == null) {
            throw new IllegalArgumentException("El pedido no existe");
        }
        if (!p.estaEnCamino()) {
            throw new IllegalArgumentException("Solo se puede cancelar un pedido EN_CAMINO");
        }
        p.cancelar();
    }

    public void listarPedidos(){
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados");
            return;
        }
        for (Pedido p : pedidos) {
            System.out.println(p);
        }

    }

}

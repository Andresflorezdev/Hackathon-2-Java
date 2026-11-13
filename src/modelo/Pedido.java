package modelo;

import java.util.Set;

public class Pedido {

    private static int consecutivo = 0;

    private int numero;
    private Cliente cliente;
    private String descripcion;
    private boolean grande;
    private double km;
    private Repartidor repartidorAsignado;
    private double costo;
    private double tiempo;
    private String estado;

    private static final Set<String> ESTADOS_VALIDOS = Set.of(
            "EN_CAMINO",
            "ENTREGADO",
            "CANCELADO"
    );

    public Pedido(Cliente cliente, String descripcion, boolean grande, double km,
                  Repartidor repartidorAsignado, double costo, double tiempo) {

        setCliente(cliente);
        setDescripcion(descripcion);
        setGrande(grande);
        setKm(km);
        setRepartidorAsignado(repartidorAsignado);
        setCosto(costo);
        setTiempo(tiempo);
        setEstado("EN_CAMINO");

        consecutivo++;
        this.numero = consecutivo;
    }

    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        this.cliente = cliente;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("la descripcion no puede estar vacia");
        }
        this.descripcion = descripcion.trim();
    }

    public void setGrande(boolean grande) {
        this.grande = grande;
    }

    public void setKm(double km) {
        if (km <= 0) {
            throw new IllegalArgumentException("los kilometros deben ser mayores a cero");
        }
        this.km = km;
    }

    public void setRepartidorAsignado(Repartidor repartidorAsignado) {
        if (repartidorAsignado == null) {
            throw new IllegalArgumentException("El repartidor no puede ser nulo");
        }
        this.repartidorAsignado = repartidorAsignado;
    }

    public void setCosto(double costo) {
        if (costo <= 0) {
            throw new IllegalArgumentException("El costo debe ser mayor a cero");
        }
        this.costo = costo;
    }

    public void setTiempo(double tiempo) {
        if (tiempo <= 0) {
            throw new IllegalArgumentException("los tiempos deben ser mayores a cero");
        }
        this.tiempo = tiempo;
    }

    public void setEstado(String estado) {
        if (!ESTADOS_VALIDOS.contains(estado)) {
            throw new IllegalArgumentException("Estado no valido: " + estado);
        }
        this.estado = estado;
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isGrande() {
        return grande;
    }

    public double getKm() {
        return km;
    }

    public Repartidor getRepartidorAsignado() {
        return repartidorAsignado;
    }

    public double getCosto() {
        return costo;
    }

    public double getTiempo() {
        return tiempo;
    }

    public String getEstado() {
        return estado;
    }
}

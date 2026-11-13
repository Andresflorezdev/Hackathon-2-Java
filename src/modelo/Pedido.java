package modelo;

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

    public Pedido(Cliente cliente, String descripcion, boolean grande, double km,
                  Repartidor repartidorAsignado, double costo, double tiempo) {

        consecutivo++;
        this.numero = consecutivo;

        setCliente(cliente);
        setDescripcion(descripcion);
        setGrande(grande);
        setKm(km);

        this.repartidorAsignado = repartidorAsignado;
        this.costo = costo;
        this.tiempo = tiempo;

        this.estado = "EN_CAMINO";
    }

    public void setCliente(Cliente cliente) {

        this.cliente = cliente;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setGrande(boolean grande) {
        this.grande = grande;
    }

    public void setKm(double km) {
        this.km = km;
    }
}

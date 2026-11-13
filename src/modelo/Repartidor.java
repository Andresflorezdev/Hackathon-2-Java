package modelo;

public abstract class Repartidor implements Calificable {

    private String codigo;
    private String nombre;
    private boolean disponible;
    private int sumaCalifi;
    private int cantidadCalifi;

    public abstract String getTipo();

    public abstract double calcularCosto(double km);

    public abstract double calcularTiempo(double km);

    public abstract boolean LopuedeLlevar(double km, boolean pedidoGrande, boolean lloviendo);

    public Repartidor(String codigo, String nombre, boolean disponible) {
        setCodigo(codigo);
        setNombre(nombre);
        this.disponible = true;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede estar vacío.");
        }
        this.codigo = codigo.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }


    @Override
    public void calificar(int estrellas) {

        if (estrellas > 5 || estrellas < 1) {
            System.out.println("Las estrellas debes estar en el rango de 1 a 5");
        }
        sumaCalifi += estrellas;
        cantidadCalifi++;
    }

    @Override
    public double PromedioCalifi() {
        if (cantidadCalifi == 0) {
            return 0;
        }
        return sumaCalifi / cantidadCalifi;
    }

    public int getCantidadCalificaciones() {
        return cantidadCalifi;
    }


}

package modelo;

public class Dron extends Repartidor {
    private static final double base = 4000;
    private static final double valor_km = 600;
    private static final double velocidad = 50;
    private static final double distanciaMax = 8;

    public Dron(String codigo, String nombre) {
        super(codigo, nombre);
    }

    @Override
    public String getTipo() {
        return "Dron";
    }

    @Override
    public double calcularCosto(double km) {
        return base + (km * valor_km);
    }

    @Override
    public double calcularTiempo(double km) {
        return km / velocidad * 60;
    }

    @Override
    public boolean LopuedeLlevar(double km, boolean pedidoGrande, boolean lloviendo) {
        if(km <= distanciaMax && !pedidoGrande && !lloviendo ){
            return true;
        }
        return false;
    }
}
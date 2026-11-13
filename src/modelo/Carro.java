package modelo;

public class Carro extends Repartidor {
    private static final double base = 5000;
    private static final double valor_km = 1200;
    private static final double velocidad = 25;

    public Carro(String codigo, String nombre) {
        super(codigo, nombre);
    }

    @Override
    public String getTipo(){
        return "Carro";
    }
    @Override
    public double calcularCosto(double km){
        return base + (km * valor_km);

    }
    @Override
    public double calcularTiempo(double km){
        return km / velocidad *60;
    }
    @Override
    public boolean LopuedeLlevar(double km, boolean pedidoGrande, boolean lloviendo){
        return true;
    }
}

package modelo;

public class Moto extends Repartidor {

    private static final double base = 3000;
    private static final double valor_km = 1000;
    private static final double velocidad = 30;
    private static final double distanciaMax = 15;

    public Moto(String codigo, String nombre) {
        super(codigo, nombre);

    }

    @Override
    public String getTipo(){
        return "Moto";
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

        if(km <= distanciaMax && !pedidoGrande ){
            return true;
        }
        return false;

    }
}

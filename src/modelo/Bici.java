package modelo;

public class Bici extends Repartidor {
    private static final double base = 2000;
    private static final double valor_km = 800;
    private static final double velocidad = 15;
    private static final double distanciaMax = 5;

    public Bici(String codigo, String nombre) {
        super(codigo, nombre);

    }

    @Override
    public String getTipo(){
        return "Bici";
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

        if(km <= distanciaMax && !pedidoGrande && !lloviendo ){
            return true;
        }
       return false;

    }

}

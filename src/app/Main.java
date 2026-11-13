package app;
import Servicio.DomiServicio;
public class Main {

    public static void main(String[] args) {
        DomiServicio service = new DomiServicio();
        Menu menu = new Menu(service);
        menu.iniciar();
    }
}
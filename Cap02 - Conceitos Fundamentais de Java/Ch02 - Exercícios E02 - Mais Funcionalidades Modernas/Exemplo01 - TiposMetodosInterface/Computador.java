// Implementação da interface Dispositivo na classe Computador

public class Computador implements Dispositivo {

    // Implementação obrigatória dos métodos abstratos
    @Override
    public void ligar() {
        System.out.println("Computador a iniciar...");
    }

    @Override
    public void desligar() {
        System.out.println("Computador a desligar...");
    }

    // Sobrescrita opcional de métodos default
    @Override
    public void mostrarInformacao() {
        System.out.println("Informação do dispositivo: Computador de alta performance.");
    }
}


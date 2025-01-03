
// Implementação da interface Dispositivo na classe Tablet

public class Tablet implements Dispositivo {

    // Implementação obrigatória dos métodos abstratos
    @Override
    public void ligar() {
        System.out.println("Tablet a iniciar...");
    }

    @Override
    public void desligar() {
        System.out.println("Tablet a desligar...");
    }

    // Usa os métodos default da interface sem os sobrescrever
}

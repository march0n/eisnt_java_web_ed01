// Definição da interface Dispositivo

public interface Dispositivo {

    // Métodos abstratos (ilimitados, obrigatórios de implementar pelas classes)
    void ligar();
    void desligar();

    // Métodos default (ilimitados, têm uma implementação base que pode ser sobrescrita)
    default void reiniciar() {
        System.out.println("Reiniciar dispositivo com configuração padrão.");
    }

    default void mostrarInformacao() {
        System.out.println("Informação do dispositivo: Genérico.");
    }

    // Métodos estáticos (ilimitados, pertencem à interface e não às classes)
    static int calcularTempoDeVida(int anosDeUso) {
        return 10 - anosDeUso; // Exemplo: Dispositivos têm uma vida útil estimada de 10 anos
    }

    static boolean verificarConectividade(boolean conectado) {
        return conectado;
    }
}


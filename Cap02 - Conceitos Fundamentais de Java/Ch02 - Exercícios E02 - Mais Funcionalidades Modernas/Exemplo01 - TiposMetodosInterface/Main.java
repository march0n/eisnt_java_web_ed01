public class Main {
    public static void main(String[] args) {
        // Instância de Computador
        Dispositivo computador = new Computador();
        computador.ligar(); // "Computador a iniciar..."
        computador.desligar(); // "Computador a desligar..."
        computador.reiniciar(); // Usa o método default da interface
        computador.mostrarInformacao(); // Sobrescrito: "Computador de alta performance."

        // Instância de Tablet
        Dispositivo tablet = new Tablet();
        tablet.ligar(); // "Tablet a iniciar..."
        tablet.desligar(); // "Tablet a desligar..."
        tablet.reiniciar(); // Usa o método default da interface
        tablet.mostrarInformacao(); // Default: "Informação do dispositivo: Genérico."

        // Uso de métodos estáticos diretamente pela interface
        int tempoDeVida = Dispositivo.calcularTempoDeVida(3); // Exemplo: 7 anos restantes
        System.out.println("Tempo de vida estimado: " + tempoDeVida + " anos.");

        boolean conectado = Dispositivo.verificarConectividade(true); // Exemplo: true
        System.out.println("Está conectado? " + conectado);
    }
}

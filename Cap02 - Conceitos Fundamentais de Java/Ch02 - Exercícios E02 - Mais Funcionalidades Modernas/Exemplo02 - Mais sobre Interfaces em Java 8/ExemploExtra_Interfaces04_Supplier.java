import java.util.function.Supplier;

public class ExemploExtra_Interfaces04_Supplier {
    public static void main(String[] args) {
        // Criação de um Supplier que retorna um número aleatório
        Supplier<Double> gerarNumeroAleatorio = () -> Math.random();

        // Uso do Supplier para gerar e imprimir números
        System.out.println("Números aleatórios:");
        System.out.println(gerarNumeroAleatorio.get()); // Gera e imprime o primeiro número
        System.out.println(gerarNumeroAleatorio.get()); // Gera e imprime o segundo número
    }
}

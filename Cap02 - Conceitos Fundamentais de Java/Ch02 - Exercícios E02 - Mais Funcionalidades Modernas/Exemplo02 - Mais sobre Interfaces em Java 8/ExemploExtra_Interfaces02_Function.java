import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class ExemploExtra_Interfaces02_Function {
    public static void main(String[] args) {
        // Lista de nomes
        List<String> nomes = Arrays.asList("Ana", "João", "Maria");

        // Criação de uma Function que retorna o tamanho de cada nome
        Function<String, Integer> tamanhoNome = nome -> nome.length();

        // Transformação dos nomes em seus tamanhos
        System.out.println("Tamanhos dos nomes:");
        nomes.stream() // Criação de um Stream
             .map(tamanhoNome) // Aplica a função para calcular o tamanho de cada nome
             .forEach(System.out::println); // Imprime os tamanhos
    }
}

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class ExemploExtra_Interfaces01_Predicate {
    public static void main(String[] args) {
        // Lista de números inteiros
        List<Integer> numeros = Arrays.asList(10, 15, 20, 25, 30);

        // Criação de um Predicate para verificar se o número é maior que 20
        Predicate<Integer> maiorQueVinte = num -> num > 20;

        // Filtragem usando o Predicate
        System.out.println("Números maiores que 20:");
        numeros.stream() // Criação de um Stream para trabalhar com a lista
               .filter(maiorQueVinte) // Aplica o Predicate como filtro
               .forEach(System.out::println); // Imprime cada número que passou no filtro
    }
}

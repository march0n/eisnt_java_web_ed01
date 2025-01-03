
// Demonstração dos conceitos modernos de programação funcional em Java:

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ex1FiltrarPares {
    public static void main(String[] args) {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> pares = numeros.stream()
                                     .filter(n -> n % 2 == 0)
                                     .sorted()
                                     .collect(Collectors.toList());
        System.out.println(pares);
    }
}


// Explicação do código em baixo:
// 1. Cria uma lista de números inteiros
// 2. Filtra os números pares
// 3. Ordena os números pares
// 4. Converte o resultado para uma lista
// 5. Imprime o resultado
// Output: [2, 4, 6, 8]


// Explicações adicionais:
// - A classe Arrays é usada para criar uma lista a partir de um array
// - A classe List é usada para criar uma lista
// - A classe Collectors é usada para converter um Stream para uma lista
// - O método asList() é usado para criar uma lista a partir de um array
// - List<Integer> é o tipo da lista que contém números inteiros
// - O método stream() é usado para criar um Stream a partir de uma lista
// - O método filter() é usado para filtrar os números pares
// - O método sorted() é usado para ordenar os números pares
// - O método collect() é usado para converter o resultado para uma lista
// - O método toList() é usado para criar uma lista a partir de um Stream

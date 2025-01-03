
// Demonstração de uso de map e sorted para ordenar uma lista de nomes em ordem alfabética, com as letras em maiúsculas:

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ex3NomesMaiusculas {
    public static void main(String[] args) {
        List<String> nomes = Arrays.asList("ana", "joão", "maria");
        List<String> nomesOrdenados = nomes.stream()
                                           .map(String::toUpperCase)
                                           .sorted()
                                           .collect(Collectors.toList());
        System.out.println(nomesOrdenados);
    }
}


// Explicação do código:
// 1. Cria uma lista de nomes
// 2. Mapeia os nomes para maiúsculas
// 3. Ordena os nomes em ordem alfabética
// 4. Converte o resultado para uma lista
// 5. Imprime o resultado
// Output: [ANA, JOÃO, MARIA]

// Explicações adicionais:
// - O método stream() é usado para criar um Stream a partir de uma lista
// - O método map() é usado para mapear os nomes para maiúsculas
// - O método sorted() é usado para ordenar os nomes em ordem alfabética
// - O método toUpperCase() é usado para converter os nomes para maiúsculas
// - O método collect() é usado para converter o resultado para uma lista
// - O método toList() é usado para criar uma lista a partir de um Stream
// - O operador :: é usado para referenciar um método de uma classe
//           (neste caso ::toUpperCase é usado para referenciar o método toUpperCase da classe String)
// - O método Arrays.asList() é usado para criar uma lista a partir de um array
// - A classe List é usada para criar uma lista
// - A classe Collectors é usada para converter um Stream para uma lista
// - List<String> é o tipo da lista que contém strings

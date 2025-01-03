
// Demonstração do conceito de expressões lambda em Java e de interfaces funcionais:

@FunctionalInterface
interface Calculador {
    int calcular(int x);
}

public class Ex2Quadrado {
    public static void main(String[] args) {
        Calculador quadrado = x -> x * x;
        System.out.println("O quadrado de 5 é: " + quadrado.calcular(5));
    }
}


// Explicação do código:
// 1. Cria uma interface funcional chamada Calculador
// 2. Define um método abstrato chamado calcular
// 3. Cria uma classe chamada Quadrado
// 4. Implementa o método calcular da interface Calculador
// 5. Cria uma instância da interface Calculador
// 6. Chama o método calcular da instância
// 7. Imprime o resultado
// Output: O quadrado de 5 é: 25


// Explicações adicionais:
// - A interface Calculador é uma interface funcional
// - A interface Calculador tem um único método abstrato
// - A interface Calculador é anotada com @FunctionalInterface
// - A anotação @FunctionalInterface é opcional
// - A interface Calculador pode ser implementada com uma expressão lambda
// - A expressão lambda é usada para implementar o método calcular
// - A expressão lambda é passada como argumento para o método calcular
// - A expressão lambda é usada para criar uma instância da interface Calculador
// - A expressão lambda é usada para chamar o método calcular da instância
// - A expressão lambda é usada para calcular o quadrado de um número

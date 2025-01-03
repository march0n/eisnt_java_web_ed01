import java.util.function.BiFunction;

public class ExemploExtra_Interfaces05_ExemploBiFunction {
    public static void main(String[] args) {
        // Criação de uma BiFunction que concatena duas Strings com um separador
        BiFunction<String, String, String> concatenar = (str1, str2) -> str1 + " e " + str2;

        // Aplicação da BiFunction
        String resultado = concatenar.apply("Ana", "João");
        System.out.println("Resultado da concatenação: " + resultado);
    }
}

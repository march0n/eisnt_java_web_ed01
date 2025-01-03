import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ExemploExtra_Interfaces03_Consumer {
    public static void main(String[] args) {
        // Lista de mensagens
        List<String> mensagens = Arrays.asList("Olá", "Bem-vindo", "Adeus");

        // Criação de um Consumer que imprime cada mensagem com uma saudação adicional
        Consumer<String> imprimirMensagem = mensagem -> System.out.println("Mensagem: " + mensagem);

        // Aplicação do Consumer
        System.out.println("Mensagens:");
        mensagens.forEach(imprimirMensagem); // Para cada mensagem, o Consumer é executado
    }
}

public class ContarAte100 {
    public static void main(String[] args) {


        // vamos contar até 100, mas se o número for 42, vamos mostrar uma mensagem especial
        for (int i = 1; i <= 100; i++) {

            System.out.println(i);

            if (i==42) {
                System.out.println("   Resposta para a vida, o universo e tudo mais, segundo a história de ficção \"The Hitchhiker´s Guide to the Galaxy\" de Douglas Adams.");
                System.out.println("   Aqui só para demonstrar uso de ´if´ dentro de um loop.");
            }

        }
        
    }
}

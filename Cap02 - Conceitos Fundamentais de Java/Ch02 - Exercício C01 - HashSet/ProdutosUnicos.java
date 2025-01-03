import java.util.HashSet;

public class ProdutosUnicos {

    public static void main(String[] args) {
        HashSet<String> produtos = new HashSet<>();
        produtos.add("Computador");
        produtos.add("Teclado");
        produtos.add("Rato");
        produtos.add("Computador"); // Não será adicionado (porque já existe, e o HashSet não permite duplicados)

        for (String produto : produtos) {
            System.out.println(produto);
        }
    }
}

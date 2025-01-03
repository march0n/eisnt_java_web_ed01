import java.util.HashMap;

public class TabelaPrecos {

    public static void main(String[] args) {
        HashMap<String, Double> precos = new HashMap<>();
        precos.put("Computador", 1000.00);
        precos.put("Teclado", 50.00);
        precos.put("Rato", 25.00);

        for (String produto : precos.keySet()) {
            System.out.println(produto + ": " + precos.get(produto));
        }
        
    }
}

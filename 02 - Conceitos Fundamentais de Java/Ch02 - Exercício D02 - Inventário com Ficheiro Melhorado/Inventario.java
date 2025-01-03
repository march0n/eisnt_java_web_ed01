import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Inventario {
    private static final String FICHEIRO = "inventario.txt";

    public static void main(String[] args) {
        ArrayList<String> inventario = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int opcao;

        // Carregar dados do ficheiro
        carregarInventario(inventario);

        do {
            System.out.println("\n=== Inventário ===");
            System.out.println("1. Adicionar produto");
            System.out.println("2. Listar produtos");
            System.out.println("3. Remover produto"); // Nova funcionalidade
            System.out.println("4. Total de produtos"); // Nova funcionalidade
            System.out.println("5. Guardar e sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    // Funcionalidade 1: Adicionar produto com quantidade
                    System.out.print("Digite o nome do produto: ");
                    String produto = sc.nextLine();
                    System.out.print("Digite a quantidade do produto: ");
                    int quantidade = sc.nextInt();
                    sc.nextLine(); // Limpar o buffer
                    inventario.add(produto + ", Quantidade: " + quantidade);
                    System.out.println("Produto adicionado.");
                    break;
                case 2:
                    // Listar produtos
                    System.out.println("Lista de produtos:");
                    for (String item : inventario) {
                        System.out.println("- " + item);
                    }
                    break;
                case 3:
                    // Funcionalidade 2: Remover produto
                    System.out.print("Digite o nome do produto a remover: ");
                    String produtoRemover = sc.nextLine();
                    boolean encontrado = false;
                    for (int i = 0; i < inventario.size(); i++) {
                        if (inventario.get(i).startsWith(produtoRemover + ",")) {
                            inventario.remove(i);
                            System.out.println("Produto removido.");
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("Produto não encontrado.");
                    }
                    break;
                case 4:
                    // Funcionalidade 3: Calcular e imprimir o total de produtos
                    int totalProdutos = calcularTotalProdutos(inventario);
                    System.out.println("Número total de produtos no inventário: " + totalProdutos);
                    break;
                case 5:
                    // Guardar e sair
                    guardarInventario(inventario);
                    System.out.println("Inventário guardado. A sair...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 5);

        sc.close();
    }

    // Método para carregar dados do ficheiro
    private static void carregarInventario(ArrayList<String> inventario) {
        try {
            BufferedReader leitor = new BufferedReader(new FileReader(FICHEIRO));
            String linha;
            while ((linha = leitor.readLine()) != null) {
                inventario.add(linha);
            }
            leitor.close();
        } catch (IOException e) {
            System.out.println("O ficheiro não existe ou está vazio.");
        }
    }

    // Método para guardar dados no ficheiro
    private static void guardarInventario(ArrayList<String> inventario) {
        try {
            FileWriter escritor = new FileWriter(FICHEIRO);
            for (String item : inventario) {
                escritor.write(item + "\n");
            }
            escritor.close();
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao guardar o inventário.");
            e.printStackTrace();
        }
    }

    // Método para calcular o total de produtos no inventário
    private static int calcularTotalProdutos(ArrayList<String> inventario) {
        int total = 0;
        for (String item : inventario) {
            String[] partes = item.split(", Quantidade: ");
            if (partes.length == 2) {
                total += Integer.parseInt(partes[1]);
            }
        }
        return total;
    }
}

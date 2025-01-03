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
            System.out.println("3. Guardar e sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome do produto: ");
                    String produto = sc.nextLine();
                    inventario.add(produto);
                    System.out.println("Produto adicionado.");
                    break;
                case 2:
                    System.out.println("Lista de produtos:");
                    for (String item : inventario) {
                        System.out.println("- " + item);
                    }
                    break;
                case 3:
                    guardarInventario(inventario);
                    System.out.println("Inventário guardado. A sair...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 3);

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
}

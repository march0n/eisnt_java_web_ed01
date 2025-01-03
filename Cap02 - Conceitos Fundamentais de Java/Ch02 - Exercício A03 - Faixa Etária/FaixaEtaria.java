import java.util.Scanner;

public class FaixaEtaria {

    public static void main(String[] args) {

        // Cria um objeto Scanner para ler a idade a partir da consola
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite a sua idade:");


        // Lê a idade a partir da consola
        int idade = sc.nextInt();

        // Determina a faixa etária: menor de idade, adulto ou sénior
        if (idade < 18) {
            System.out.println("Menor de idade.");
        } else if (idade <= 64) {
            System.out.println("Adulto.");
        } else {
            System.out.println("Sénior.");
        }

        // Fecha o objeto Scanner
        sc.close();
    }

}

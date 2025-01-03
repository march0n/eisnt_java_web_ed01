import java.util.ArrayList;
import java.util.Scanner;

public class EditarAluno {

    public static void main(String[] args) {
        ArrayList<String> alunos = new ArrayList<>();
        alunos.add("João");
        alunos.add("Maria");

        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o nome do aluno a editar:");
        String antigoNome = sc.nextLine();
        System.out.println("Digite o novo nome:");
        String novoNome = sc.nextLine();

        if (alunos.contains(antigoNome)) {
            alunos.set(alunos.indexOf(antigoNome), novoNome);
            System.out.println("Nome atualizado!");
        } else {
            System.out.println("Aluno não encontrado.");
        }

        sc.close();
    }
}

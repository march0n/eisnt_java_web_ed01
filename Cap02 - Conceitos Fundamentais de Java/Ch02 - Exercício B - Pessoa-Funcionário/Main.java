public class Main {

    public static void main(String[] args) {

        // Cria um objeto da classe Funcionario
        Funcionario funcionario1 = new Funcionario();
        funcionario1.nome = "João";
        funcionario1.idade = 30;
        funcionario1.salario = 1500.50;

        funcionario1.dizerIdade();
        funcionario1.mostrarSalario();

        // Cria outro objeto da classe Funcionario
        Funcionario funcionario2 = new Funcionario();
        funcionario2.nome = "Maria";
        funcionario2.idade = 25;
        funcionario2.salario = 2000.00;

        funcionario2.dizerIdade();
        funcionario2.mostrarSalario();

        // Podemos ter tantos objetos da classe Funcionario quanto quisermos

        

    
    }
}

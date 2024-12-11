import java.io.*;
import java.util.Random;
import javax.swing.JOptionPane;

public class Adivinha_GUI {

    public static void main(String[] args) {

        Random gerador = new Random();

        final int MINIMO = 1;
        final int MAXIMO = 120;

        // Variável para armazenar o top score
        int topScore = Integer.MAX_VALUE;

        int tentativaDaPessoa = 0;

        // Tenta carregar o top score de um ficheiro
        File ficheiro = new File("topscore.txt");
        if (ficheiro.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(ficheiro))) {
                String linha = reader.readLine();
                if (linha != null) {
                    topScore = Integer.parseInt(linha);
                }
            } // catch (IOException | NumberFormatException e) {
            catch (Exception e) {
                System.out.println("Erro ao carregar o top score. Usando o valor padrão.");
            }
        }

        boolean pessoaQuerContinuarAJogar = true;

        do {
            int numeroQueComputadorEstaAPensar = MINIMO + gerador.nextInt(MAXIMO);

            System.out.println("Batota: " + numeroQueComputadorEstaAPensar);

            JOptionPane.showMessageDialog(null,
                    "Olá, bem-vind@! Estou a pensar num número entre " + MINIMO + " e " + MAXIMO);

            boolean pessoaJaAdivinhou = false;
            int numeroDeTentativas = 0;

            while (!pessoaJaAdivinhou) {

                boolean palpiteValido = false;
                while (!palpiteValido) {
                    String palpiteDaPessoa = JOptionPane.showInputDialog(null, "Palpite (entre " + MINIMO + " e " + MAXIMO + ")", 
                                                                          "Jogo da Adivinha", JOptionPane.QUESTION_MESSAGE);
                    try {
                        tentativaDaPessoa = Integer.parseInt(palpiteDaPessoa);
                        
                        if (tentativaDaPessoa >= MINIMO && tentativaDaPessoa <= MAXIMO) {
                            palpiteValido = true;
                        } else {
                            JOptionPane.showMessageDialog(null, "Por favor, insira um número entre " + MINIMO + " e " + MAXIMO + ".");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Tem de introduzir um número válido.");
                    }
                }

                numeroDeTentativas++;

                if (tentativaDaPessoa > numeroQueComputadorEstaAPensar) {
                    JOptionPane.showMessageDialog(null, "Não... é menor");
                } else if (tentativaDaPessoa < numeroQueComputadorEstaAPensar) {
                    JOptionPane.showMessageDialog(null, "Nope... é maior!!");
                } else {
                    pessoaJaAdivinhou = true;
                    JOptionPane.showMessageDialog(null,
                            "PARABÉNS! Acertou... O número era " + numeroQueComputadorEstaAPensar);
                }
            }

            // Verifica se o jogador bateu o recorde
            if (numeroDeTentativas < topScore) {
                JOptionPane.showMessageDialog(null,
                        "E bateu o recorde do jogo! Novo recorde: " + numeroDeTentativas + " tentativas.");
                topScore = numeroDeTentativas;

                // Atualiza o ficheiro de top score
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(ficheiro))) {
                    writer.write(String.valueOf(topScore));
                } catch (IOException e) {
                    System.out.println("Erro ao salvar o top score.");
                }
            }

            String intencaoDoJogador = JOptionPane.showInputDialog(null, "Continuar? (s/n)", "Quão viciado está você",
                    JOptionPane.QUESTION_MESSAGE);

            if (intencaoDoJogador.toLowerCase().charAt(0) == 'n') {
                pessoaQuerContinuarAJogar = false;
            }

        } while (pessoaQuerContinuarAJogar);

        JOptionPane.showMessageDialog(null, "Então adeus! Foi um gosto.");
    }
}

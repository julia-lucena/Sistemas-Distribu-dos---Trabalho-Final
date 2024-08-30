import java.time.Duration; // Importa a classe Duration para medir o tempo de execução.
import java.time.Instant;  // Importa a classe Instant para capturar o tempo atual.

/* Definição da classe base para toda lógica do código */
public class N_rainhas {
    public static void main(String[] args) { // Método main

        Instant iniciar = Instant.now(); // Objetio instant: marca o início da execução

        int t = 8; // Variavél que define tamanho do tabuleiro (matriz txt)

        /*
         * Criação de um tabuleiro de booleanos para representar as rainhas
         * True indican que há uma rainha no lugar
         */
        boolean[][] tabuleiro = new boolean[t][t];

        /*
         * Função recursiva (procurandoRainhas) para encontrar todas as soluções,
         * Começa da primeira linha (índice 0 da matriz)
         * Imprime as soluções encontradas
         */
        System.out.println("Soluções encontradas: " + procurandoRainhas(tabuleiro, 0));

        Instant finalizar = Instant.now(); // Objeto Instant: marcar o momento em que a execução termina

        long tempo = Duration.between(iniciar, finalizar).toMillis(); // Calcula a diferença entre os dois instantes em
                                                                      // milissegundos

        System.out.println("Executado em: " + tempo + " milissegundos"); // Imprime o tempo calculado
    }

    /*
     * Função recursiva que utiliza backtracking: uma árvore que pesquisa cada nó e
     * explora todas
     * as opçoes sistemáticamente, voltando para trás no ramo quando uma solução é
     * inviavél
     * 
     * Condição IF: verifica se a busca chegou a última linha do tabuleiro, o que
     * significa
     * que todas as rainhas foram posicionadas
     * 
     * Solução: função que é chamda para imprimir o tabuleiro
     * 
     * Return: Incrementação de chamadas recursivas para contagem total de soluções
     */
    static int procurandoRainhas(boolean[][] tabuleiro, int linha) {
        if (linha == tabuleiro.length) {
            solução(tabuleiro);
            return 1;
        }

        int cont = 0; // Contador para incremento do numero de soluções

        for (int coluna = 0; coluna < tabuleiro.length; coluna++) { // Itera sobre todas as colunas da linha atual
            if (seguro(tabuleiro, linha, coluna)) { // Chama a função segura que verifica se é seguro colocar uma rainha
                                                    // na posição (linha, coluna)

                tabuleiro[linha][coluna] = true; // Posiciona a rainha
                cont += procurandoRainhas(tabuleiro, linha + 1); // Chama recursivamente a função procurandoRainhas para
                                                                 // a ir próxima linha
                                                                 
                tabuleiro[linha][coluna] = false; // Marca os espços vazios
            }
        }
        return cont; // Incrementa
    }

    static boolean seguro(boolean[][] tabuleiro, int linha, int coluna) { // Verificação de coluna
        for (int i = 0; i < linha; i++) { // Itera sobre todas as linhas acima da linha atual
            if (tabuleiro[i][coluna]) { // Verifica se há uma rainha na mesma coluna da posição atual
                return false; // Rainha encontrada na posição retorna indicando que a posição não é segura
            }
        }

        /*
         * Condição FOR: Verifica a diagonal superior esquerda
         * Inicializa com valor da linha e coluna atual, O laço continua enquanto l
         * (linha) e c (coluna)
         * forem maiores ou iguais a zero, isso garante a não ultrapassagem do tamanho
         * do tabuleiro
         * Em cada iteração, l é decrementado (anda para cima) e c também é decrementado
         * (anda para a esquerda)
         * 
         * Condição IF: Verifica se a posição atual na diagonal (l, c) está ocupada por
         * uma rainha
         * 
         * Return: Se estiver ocupada retorna indicando que a posição atual não é segura
         */
        for (int l = linha, c = coluna; l >= 0 && c >= 0; l--, c--) { //
            if (tabuleiro[l][c]) {
                return false;
            }
        }

        /*
         * Condição FOR: Verifica a diagonal superior direita
         * Inicializa com valor da linha e coluna atual, O laço continua enquanto l
         * (linha)
         * for maior ou igual a zero e c (coluna) menor que o numero de colunas da
         * matriz
         * Isso garante a não ultrapassagem do tamanho do tabuleiro
         * Em cada iteração, L é decrementado (anda para cima) e C também é incrementada
         * (anda para a direita)
         * 
         * Condição IF: Verifica se a posição atual na diagonal (l, c) está ocupada por
         * uma rainha
         * 
         * Return: Se estiver ocupada retorna indicando que a posição atual não é segura
         */
        for (int l = linha, c = coluna; l >= 0 && c < tabuleiro.length; l--, c++) {
            if (tabuleiro[l][c]) {
                return false;
            }
        }

        return true; // Caso estiver seguro e nao tiver rainha na posição, retorna que é seguro
                     // posicionar.
    }

    static void solução(boolean[][] tabuleiro) { // Função que imprime os tabuleiros
        for (int l = 0; l < tabuleiro.length; l++) { // Itera linha
            for (int c = 0; c < tabuleiro.length; c++) { // Itera coluna
                System.out.print(tabuleiro[l][c] ? "R " : "■ "); // ?: Está ocupada por uma rainha (true) "R "; caso
                                                                 // contrário (false) "■ "
            }
            System.out.println(); // Imprime o caractere "R" e/ou "■" na mesma linha
        }
        System.out.println(); // Imprime uma quebra de linha para ir para a próxima linha do tabuleiro.
    }
}
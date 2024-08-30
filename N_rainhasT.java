import java.time.Duration; // Importa a classe Duration para medir o tempo de execução
import java.time.Instant;  // Importa a classe Instant para capturar o tempo atual
import java.util.ArrayList; // Importa a classe ArrayList para criar uma lista de threads
import java.util.List;      // Importa a interface List para a lista de threads
import java.util.concurrent.atomic.AtomicInteger; // Importa AtomicInteger para contar as soluções de forma segura (sincronizada) em múltiplas threads

public class N_rainhasT { // Declara a classe pública N_rainhasT

    public static AtomicInteger n_solucoes = new AtomicInteger(0); // Inicializa um contador atômico de soluções

    public static void main(String[] args) { /// Método main

        Instant iniciar = Instant.now(); // Objetio instant: marca o início da execução

        int t = 8; // Variavél que define tamanho do tabuleiro (matriz txt)

        List<Thread> threads = new ArrayList<>(); // Cria uma lista para armazenar as threads

        for (int i = 0; i < t; i++) { // Loop para criar uma thread para cada coluna inicial
            final int colunaInicial = i; // Armazena a coluna inicial para cada thread
            Thread thread = new Thread(() -> { // Cria uma nova thread com a tarefa definida
                boolean[][] tabuleiro = new boolean[t][t]; // Cria um tabuleiro de tamanho t x t
                resolvendoNrainhas(tabuleiro, 0, colunaInicial); // Inicia a resolução do problema das N rainhas
            });
            threads.add(thread); // Adiciona a thread à lista de threads
            thread.start(); // Inicia a execução da thread
        }

        for (Thread thread : threads) { // Loop para aguardar a conclusão de todas as threads
            try {
                thread.join(); // Espera a thread terminar a execução
            } catch (InterruptedException e) { // Captura qualquer exceção de interrupção
                e.printStackTrace(); // Imprime a pilha de chamadas da exceção
            }
        }

        System.out.println("Número de soluções: " + n_solucoes.get()); // Imprime o número total de soluções encontradas

        Instant finalizar = Instant.now(); // Objeto Instant: marcar o momento em que a execução termina

        long tempo = Duration.between(iniciar, finalizar).toMillis(); // Calcula a diferença entre os dois instantes em milisegundos

        System.out.println("Executado em: " + tempo + " milissegundos"); // Imprime o tempo calculado
    }

    public static void resolvendoNrainhas(boolean[][] tabuleiro, int linha, int colunaInicial) { // Método recursivo para resolver o problema das N rainhas
        if (linha == tabuleiro.length) { // Verifica se a busca chegou a última linha do tabuleiro, o que significa que todas as rainhas foram posicionadas
            synchronized (System.out) { // Sincroniza a impressão para evitar problemas com múltiplas threads
                solução(tabuleiro); // Imprime a solução encontrada
            }
            n_solucoes.incrementAndGet(); // Incrementa o contador de soluções
            return; // Retorna para a chamada recursiva anterior
        }

        if (linha == 0) { // Primeira linha 
            if (seguro(tabuleiro, linha, colunaInicial)) { // Verifica se é seguro colocar uma rainha na colunaInicial.
                tabuleiro[linha][colunaInicial] = true; // Coloca uma rainha na posição (linha, colunaInicial).
                resolvendoNrainhas(tabuleiro, linha + 1, colunaInicial); // Chama recursivamente para a próxima linha.
                tabuleiro[linha][colunaInicial] = false; // Remove a rainha após a chamada recursiva (backtracking).
            }
        } else {
            for (int coluna = 0; coluna < tabuleiro.length; coluna++) { // Loop para tentar colocar a rainha em cada coluna.
                if (seguro(tabuleiro, linha, coluna)) { // Verifica se é seguro colocar uma rainha na posição (linha, coluna).
                    tabuleiro[linha][coluna] = true; // Coloca uma rainha na posição (linha, coluna).
                    resolvendoNrainhas(tabuleiro, linha + 1, colunaInicial); // Chama recursivamente para a próxima linha.
                    tabuleiro[linha][coluna] = false; // Remove a rainha após a chamada recursiva (backtracking).
                }
            }
        }
    }

    private static boolean seguro(boolean[][] tabuleiro, int linha, int coluna) { // Verificação de coluna
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
    private static void solução(boolean[][] tabuleiro) { // Função que imprime os tabuleiros
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

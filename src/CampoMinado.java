import java.util.Random;

public class CampoMinado {
    private final int linhas;
    private final int colunas;
    private final int quantidadeMinas;
    private Celula[][] tabuleiro;
    private boolean jogoTerminado;
    private boolean venceu;
    private boolean primeiraJogada;
    private int bandeirasColocadas;

    public CampoMinado(int linhas, int colunas, int quantidadeMinas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.quantidadeMinas = quantidadeMinas;
        iniciarJogo();
    }

    public void iniciarJogo() {
        tabuleiro = new Celula[linhas][colunas];
        jogoTerminado = false;
        venceu = false;
        primeiraJogada = true;
        bandeirasColocadas = 0;

        for (int linha = 0; linha < linhas; linha++) {
            for (int coluna = 0; coluna < colunas; coluna++) {
                tabuleiro[linha][coluna] = new Celula();
            }
        }
    }

    private void distribuirMinas(int linhaInicial, int colunaInicial) {
        Random random = new Random();
        int minas = 0;

        while (minas < quantidadeMinas) {
            int linha = random.nextInt(linhas);
            int coluna = random.nextInt(colunas);

            if (linha == linhaInicial && coluna == colunaInicial) {
                continue;
            }

            if (!tabuleiro[linha][coluna].temMina()) {
                tabuleiro[linha][coluna].colocarMina();
                minas++;
            }
        }

        calcularMinasVizinhas();
    }

    private void calcularMinasVizinhas() {
        for (int linha = 0; linha < linhas; linha++) {
            for (int coluna = 0; coluna < colunas; coluna++) {
                if (!tabuleiro[linha][coluna].temMina()) {
                    int quantidade = contarMinasVizinhas(linha, coluna);
                    tabuleiro[linha][coluna].setMinasVizinhas(quantidade);
                }
            }
        }
    }

    private int contarMinasVizinhas(int linha, int coluna) {
        int contador = 0;

        for (int l = linha - 1; l <= linha + 1; l++) {
            for (int c = coluna - 1; c <= coluna + 1; c++) {
                if (l < 0 || l >= linhas || c < 0 || c >= colunas) {
                    continue;
                }

                if (l == linha && c == coluna) {
                    continue;
                }

                if (tabuleiro[l][c].temMina()) {
                    contador++;
                }
            }
        }

        return contador;
    }

    public boolean revelar(int linha, int coluna) {
        if (jogoTerminado) {
            return false;
        }

        Celula celula = tabuleiro[linha][coluna];

        if (celula.estaRevelada() || celula.temBandeira()) {
            return false;
        }

        if (primeiraJogada) {
            distribuirMinas(linha, coluna);
            primeiraJogada = false;
            celula = tabuleiro[linha][coluna];
        }

        celula.revelar();

        if (celula.temMina()) {
            jogoTerminado = true;
            venceu = false;
            revelarTodasAsMinas();
            return true;
        }

        if (celula.getMinasVizinhas() == 0) {
            revelarVizinhas(linha, coluna);
        }

        verificarVitoria();
        return true;
    }

    private void revelarVizinhas(int linha, int coluna) {
        for (int l = linha - 1; l <= linha + 1; l++) {
            for (int c = coluna - 1; c <= coluna + 1; c++) {
                if (l < 0 || l >= linhas || c < 0 || c >= colunas) {
                    continue;
                }

                if (l == linha && c == coluna) {
                    continue;
                }

                Celula vizinha = tabuleiro[l][c];

                if (!vizinha.estaRevelada() && !vizinha.temMina() && !vizinha.temBandeira()) {
                    vizinha.revelar();

                    if (vizinha.getMinasVizinhas() == 0) {
                        revelarVizinhas(l, c);
                    }
                }
            }
        }
    }

    public boolean alternarBandeira(int linha, int coluna) {
        if (jogoTerminado) {
            return false;
        }

        Celula celula = tabuleiro[linha][coluna];

        if (celula.estaRevelada()) {
            return false;
        }

        if (!celula.temBandeira() && bandeirasColocadas >= quantidadeMinas) {
            return false;
        }

        celula.alternarBandeira();

        if (celula.temBandeira()) {
            bandeirasColocadas++;
        } else {
            bandeirasColocadas--;
        }

        return true;
    }

    private void verificarVitoria() {
        for (int linha = 0; linha < linhas; linha++) {
            for (int coluna = 0; coluna < colunas; coluna++) {
                Celula celula = tabuleiro[linha][coluna];

                if (!celula.temMina() && !celula.estaRevelada()) {
                    return;
                }
            }
        }

        venceu = true;
        jogoTerminado = true;

        for (int linha = 0; linha < linhas; linha++) {
            for (int coluna = 0; coluna < colunas; coluna++) {
                Celula celula = tabuleiro[linha][coluna];

                if (celula.temMina() && !celula.temBandeira()) {
                    celula.alternarBandeira();
                }
            }
        }
    }

    private void revelarTodasAsMinas() {
        for (int linha = 0; linha < linhas; linha++) {
            for (int coluna = 0; coluna < colunas; coluna++) {
                if (tabuleiro[linha][coluna].temMina()) {
                    tabuleiro[linha][coluna].revelar();
                }
            }
        }
    }

    public Celula getCelula(int linha, int coluna) {
        return tabuleiro[linha][coluna];
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public int getQuantidadeMinas() {
        return quantidadeMinas;
    }

    public int getBandeirasColocadas() {
        return bandeirasColocadas;
    }

    public boolean isJogoTerminado() {
        return jogoTerminado;
    }

    public boolean isVenceu() {
        return venceu;
    }
}
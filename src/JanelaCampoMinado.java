import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JanelaCampoMinado extends JFrame {
    private static final int INICIANTE_LINHAS = 9;
    private static final int INICIANTE_COLUNAS = 9;
    private static final int INICIANTE_MINAS = 10;

    private static final int INTERMEDIARIO_LINHAS = 16;
    private static final int INTERMEDIARIO_COLUNAS = 16;
    private static final int INTERMEDIARIO_MINAS = 40;

    private static final int ESPECIALISTA_LINHAS = 16;
    private static final int ESPECIALISTA_COLUNAS = 30;
    private static final int ESPECIALISTA_MINAS = 99;

    private static final int TAMANHO_CELULA = 24;
    private static final int TAMANHO_CELULA_INICIANTE = 32;

    private static final Color CINZA_WINDOWS =
            new Color(192, 192, 192);

    private static final Color CINZA_ESCURO =
            new Color(128, 128, 128);

    private CampoMinado campo;
    private JPanel painelTabuleiro;
    private JPanel painelSuperior;
    private JButton[][] botoes;
    private JLabel contadorMinas;
    private JLabel contadorTempo;
    private JButton botaoRosto;
    private Timer timer;
    private int segundos;

    public JanelaCampoMinado() {
        aplicarVisualClassico();

        criarJogo(
                INICIANTE_LINHAS,
                INICIANTE_COLUNAS,
                INICIANTE_MINAS
        );

        setVisible(true);
    }

    private int obterTamanhoCelula() {
        if (campo.getLinhas() == INICIANTE_LINHAS
            && campo.getColunas() == INICIANTE_COLUNAS
            && campo.getQuantidadeMinas() == INICIANTE_MINAS) {
            return TAMANHO_CELULA_INICIANTE;
        }

        return TAMANHO_CELULA;
    }

    private void aplicarVisualClassico() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName()
            );
        } catch (Exception ignored) {
        }

        UIManager.put(
                "Menu.font",
                new Font("MS Sans Serif", Font.PLAIN, 12)
        );

        UIManager.put(
                "MenuItem.font",
                new Font("MS Sans Serif", Font.PLAIN, 12)
        );

        UIManager.put(
                "Label.font",
                new Font("MS Sans Serif", Font.PLAIN, 12)
        );
    }

    private void criarJogo(
            int linhas,
            int colunas,
            int minas
    ) {
        pararCronometro();

        campo = new CampoMinado(
                linhas,
                colunas,
                minas
        );

        segundos = 0;

        getContentPane().removeAll();

        configurarJanela();
        criarMenu();
        criarInterface();

        atualizarInterface();
        iniciarCronometro();

        revalidate();
        repaint();

        pack();
        setLocationRelativeTo(null);
    }

    private void configurarJanela() {
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        getContentPane().setBackground(
                CINZA_WINDOWS
        );
    }

    private void criarMenu() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.setBackground(
                CINZA_WINDOWS
        );

        JMenu menuGame = new JMenu("Game");
        JMenu menuHelp = new JMenu("Help");

        JMenuItem novoJogo = new JMenuItem("New");
        JMenuItem iniciante = new JMenuItem("Beginner");
        JMenuItem intermediario = new JMenuItem("Intermediate");
        JMenuItem especialista = new JMenuItem("Expert");
        JMenuItem sair = new JMenuItem("Exit");

        JMenuItem sobre = new JMenuItem("About");

        novoJogo.addActionListener(
                e -> reiniciarJogo()
        );

        iniciante.addActionListener(
                e -> criarJogo(
                        INICIANTE_LINHAS,
                        INICIANTE_COLUNAS,
                        INICIANTE_MINAS
                )
        );

        intermediario.addActionListener(
                e -> criarJogo(
                        INTERMEDIARIO_LINHAS,
                        INTERMEDIARIO_COLUNAS,
                        INTERMEDIARIO_MINAS
                )
        );

        especialista.addActionListener(
                e -> criarJogo(
                        ESPECIALISTA_LINHAS,
                        ESPECIALISTA_COLUNAS,
                        ESPECIALISTA_MINAS
                )
        );

        sair.addActionListener(
                e -> System.exit(0)
        );

        sobre.addActionListener(
                e -> JOptionPane.showMessageDialog(
                        this,
                        "Campo Minado\n" +
                                "Desenvolvido em Java Swing\n" +
                                "Programação Orientada a Objetos",
                        "About",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        menuGame.add(novoJogo);
        menuGame.addSeparator();
        menuGame.add(iniciante);
        menuGame.add(intermediario);
        menuGame.add(especialista);
        menuGame.addSeparator();
        menuGame.add(sair);

        menuHelp.add(sobre);

        menuBar.add(menuGame);
        menuBar.add(menuHelp);

        setJMenuBar(menuBar);
    }

    private void criarInterface() {
        JPanel painelPrincipal = new JPanel(
                new BorderLayout(6, 6)
        );

        painelPrincipal.setBackground(
                CINZA_WINDOWS
        );

        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(
                        6,
                        6,
                        6,
                        6
                )
        );

        criarPainelSuperior();
        criarTabuleiro();

        painelPrincipal.add(
                painelSuperior,
                BorderLayout.NORTH
        );

        painelPrincipal.add(
                painelTabuleiro,
                BorderLayout.CENTER
        );

        add(painelPrincipal);
    }

    private void criarPainelSuperior() {
        painelSuperior = new JPanel(
                new BorderLayout()
        );

        painelSuperior.setBackground(
                CINZA_WINDOWS
        );

        painelSuperior.setPreferredSize(
                new Dimension(
                        campo.getColunas() * obterTamanhoCelula(),
                        58
                )
        );

        painelSuperior.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createBevelBorder(
                                BevelBorder.LOWERED
                        ),
                        BorderFactory.createEmptyBorder(
                                5,
                                5,
                                5,
                                5
                        )
                )
        );

        contadorMinas = criarDisplay();
        contadorTempo = criarDisplay();

        botaoRosto = new BotaoClassico();

        botaoRosto.setIcon(
                IconesCampoMinado.rostoNormal(26)
        );

        botaoRosto.setPreferredSize(
                new Dimension(34, 34)
        );

        botaoRosto.setMinimumSize(
                new Dimension(34, 34)
        );

        botaoRosto.setMaximumSize(
                new Dimension(34, 34)
        );

        botaoRosto.addActionListener(
                e -> reiniciarJogo()
        );

        painelSuperior.add(
                contadorMinas,
                BorderLayout.WEST
        );

        JPanel painelRosto = new JPanel(
                new GridBagLayout()
        );

        painelRosto.setBackground(
                CINZA_WINDOWS
        );

        painelRosto.add(botaoRosto);

        painelSuperior.add(
                painelRosto,
                BorderLayout.CENTER
        );

        painelSuperior.add(
                contadorTempo,
                BorderLayout.EAST
        );
    }

    private JLabel criarDisplay() {
        JLabel display = new JLabel("000");

        display.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        display.setVerticalAlignment(
                SwingConstants.CENTER
        );

        display.setFont(
                new Font(
                        "Monospaced",
                        Font.BOLD,
                        23
                )
        );

        display.setForeground(Color.RED);
        display.setBackground(Color.BLACK);
        display.setOpaque(true);

        display.setPreferredSize(
                new Dimension(58, 34)
        );

        display.setBorder(
                BorderFactory.createLoweredBevelBorder()
        );

        return display;
    }

    private void criarTabuleiro() {
        painelTabuleiro = new JPanel(
                new GridLayout(
                        campo.getLinhas(),
                        campo.getColunas(),
                        0,
                        0
                )
        );

        painelTabuleiro.setBackground(
                CINZA_ESCURO
        );

        painelTabuleiro.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createBevelBorder(
                                BevelBorder.LOWERED
                        ),
                        BorderFactory.createEmptyBorder(
                                2,
                                2,
                                2,
                                2
                        )
                )
        );

        botoes = new JButton[
                campo.getLinhas()
                ][
                campo.getColunas()
                ];

        for (int linha = 0;
             linha < campo.getLinhas();
             linha++) {

            for (int coluna = 0;
                 coluna < campo.getColunas();
                 coluna++) {

                JButton botao = criarBotao(
                        linha,
                        coluna
                );

                botoes[linha][coluna] = botao;
                painelTabuleiro.add(botao);
            }
        }
    }

    private JButton criarBotao(
            int linha,
            int coluna
    ) {
        JButton botao = new BotaoClassico();

        int tamanhoCelula = obterTamanhoCelula();

        Dimension tamanho = new Dimension(
                tamanhoCelula,
                tamanhoCelula
        );

        botao.setPreferredSize(tamanho);
        botao.setMinimumSize(tamanho);
        botao.setMaximumSize(tamanho);

        botao.setMargin(
                new Insets(0, 0, 0, 0)
        );

        botao.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        botao.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            clicarEsquerdo(
                                    linha,
                                    coluna
                            );
                        } else if (
                                SwingUtilities.isRightMouseButton(e)
                        ) {
                            clicarDireito(
                                    linha,
                                    coluna
                            );
                        }
                    }
                }
        );

        return botao;
    }

    private void clicarEsquerdo(
            int linha,
            int coluna
    ) {
        if (campo.isJogoTerminado()) {
            return;
        }

        boolean revelou = campo.revelar(
                linha,
                coluna
        );

        if (!revelou) {
            return;
        }

        atualizarInterface();

        if (campo.isJogoTerminado()) {
            pararCronometro();

            if (campo.isVenceu()) {
                botaoRosto.setIcon(
                        IconesCampoMinado.rostoVitoria(22)
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Parabéns! Você venceu!",
                        "Campo Minado",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                botaoRosto.setIcon(
                        IconesCampoMinado.rostoTriste(22)
                );

                JOptionPane.showMessageDialog(
                        this,
                        "BOOM! Você perdeu!",
                        "Campo Minado",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void clicarDireito(
            int linha,
            int coluna
    ) {
        if (campo.isJogoTerminado()) {
            return;
        }

        campo.alternarBandeira(
                linha,
                coluna
        );

        atualizarInterface();
    }

    private void atualizarInterface() {
        int minasRestantes =
                campo.getQuantidadeMinas()
                        - campo.getBandeirasColocadas();

        contadorMinas.setText(
                String.format(
                        "%03d",
                        minasRestantes
                )
        );

        contadorTempo.setText(
                String.format(
                        "%03d",
                        segundos
                )
        );

        for (int linha = 0;
             linha < campo.getLinhas();
             linha++) {

            for (int coluna = 0;
                 coluna < campo.getColunas();
                 coluna++) {

                atualizarBotao(
                        linha,
                        coluna
                );
            }
        }
    }

    private void atualizarBotao(
            int linha,
            int coluna
    ) {
        JButton botao = botoes[linha][coluna];

        Celula celula = campo.getCelula(
                linha,
                coluna
        );

        botao.setText("");
        botao.setIcon(null);

        if (celula.temBandeira()
                && !celula.estaRevelada()) {

            ((BotaoClassico) botao).setElevado(true);

            botao.setIcon(
                    IconesCampoMinado.bandeira(14)
            );

            return;
        }

        if (!celula.estaRevelada()) {
            ((BotaoClassico) botao).setElevado(true);
            return;
        }

        ((BotaoClassico) botao).setElevado(false);

        botao.setPressedIcon(null);

        if (celula.temMina()) {
            botao.setIcon(
                    IconesCampoMinado.bomba(15)
            );

            return;
        }

        int minas = celula.getMinasVizinhas();

        if (minas > 0) {
            botao.setText(
                    String.valueOf(minas)
            );

            botao.setForeground(
                    obterCorNumero(minas)
            );
        }
    }

    private Color obterCorNumero(int numero) {
        switch (numero) {
            case 1:
                return Color.BLUE;

            case 2:
                return new Color(
                        0,
                        128,
                        0
                );

            case 3:
                return Color.RED;

            case 4:
                return new Color(
                        0,
                        0,
                        128
                );

            case 5:
                return new Color(
                        128,
                        0,
                        0
                );

            case 6:
                return new Color(
                        0,
                        128,
                        128
                );

            case 7:
                return Color.BLACK;

            case 8:
                return Color.GRAY;

            default:
                return Color.BLACK;
        }
    }

    private void iniciarCronometro() {
        segundos = 0;

        timer = new Timer(
                1000,
                e -> {
                    if (!campo.isJogoTerminado()) {
                        segundos++;

                        if (segundos > 999) {
                            segundos = 999;
                        }

                        contadorTempo.setText(
                                String.format(
                                        "%03d",
                                        segundos
                                )
                        );
                    }
                }
        );

        timer.start();
    }

    private void pararCronometro() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
    }

    private void reiniciarJogo() {
        int linhas = campo.getLinhas();
        int colunas = campo.getColunas();
        int minas = campo.getQuantidadeMinas();

        criarJogo(
                linhas,
                colunas,
                minas
        );
    }

    private static class BotaoClassico extends JButton {
        private static final Color CINZA =
                new Color(192, 192, 192);

        private static final Color BRANCO =
                Color.WHITE;

        private static final Color CINZA_CLARO =
                new Color(223, 223, 223);

        private static final Color CINZA_ESCURO =
                new Color(128, 128, 128);

        private static final Color PRETO =
                Color.BLACK;

        private boolean elevado = true;

        private BotaoClassico() {
            setUI(new BasicButtonUI());
            setContentAreaFilled(false);
            setOpaque(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setFocusable(false);

            setMargin(
                    new Insets(0, 0, 0, 0)
            );
        }

        private void setElevado(boolean elevado) {
            this.elevado = elevado;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 =
                    (Graphics2D) g.create();

            int largura = getWidth();
            int altura = getHeight();

            boolean pressionado =
                    getModel().isPressed();

            g2.setColor(CINZA);

            g2.fillRect(
                    0,
                    0,
                    largura,
                    altura
            );

            if (elevado) {
                if (pressionado) {
                    pintarBordaPressionada(
                            g2,
                            largura,
                            altura
                    );
                } else {
                    pintarBordaElevada(
                            g2,
                            largura,
                            altura
                    );
                }
            } else {
                g2.setColor(
                        CINZA_ESCURO
                );

                g2.drawRect(
                        0,
                        0,
                        largura - 1,
                        altura - 1
                );
            }

            g2.dispose();

            super.paintComponent(g);
        }

        private void pintarBordaElevada(
                Graphics2D g,
                int largura,
                int altura
        ) {
            g.setColor(BRANCO);

            g.drawLine(
                    0,
                    0,
                    largura - 1,
                    0
            );

            g.drawLine(
                    0,
                    0,
                    0,
                    altura - 1
            );

            g.setColor(CINZA_CLARO);

            g.drawLine(
                    1,
                    1,
                    largura - 2,
                    1
            );

            g.drawLine(
                    1,
                    1,
                    1,
                    altura - 2
            );

            g.setColor(CINZA_ESCURO);

            g.drawLine(
                    largura - 2,
                    1,
                    largura - 2,
                    altura - 2
            );

            g.drawLine(
                    1,
                    altura - 2,
                    largura - 2,
                    altura - 2
            );

            g.setColor(PRETO);

            g.drawLine(
                    largura - 1,
                    0,
                    largura - 1,
                    altura - 1
            );

            g.drawLine(
                    0,
                    altura - 1,
                    largura - 1,
                    altura - 1
            );
        }

        private void pintarBordaPressionada(
                Graphics2D g,
                int largura,
                int altura
        ) {
            g.setColor(CINZA_ESCURO);

            g.drawLine(
                    0,
                    0,
                    largura - 1,
                    0
            );

            g.drawLine(
                    0,
                    0,
                    0,
                    altura - 1
            );

            g.setColor(PRETO);

            g.drawLine(
                    1,
                    1,
                    largura - 2,
                    1
            );

            g.drawLine(
                    1,
                    1,
                    1,
                    altura - 2
            );

            g.setColor(BRANCO);

            g.drawLine(
                    largura - 1,
                    0,
                    largura - 1,
                    altura - 1
            );

            g.drawLine(
                    0,
                    altura - 1,
                    largura - 1,
                    altura - 1
            );
        }
    }
}
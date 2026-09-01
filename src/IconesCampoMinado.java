import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public final class IconesCampoMinado {
    private static final String PASTA = "src/imagens/";

    private IconesCampoMinado() {
    }

    private static ImageIcon carregar(String nome, int largura, int altura) {
        File arquivo = new File(PASTA + nome);

        if (!arquivo.exists()) {
            throw new IllegalStateException(
                    "Imagem não encontrada: " + arquivo.getPath()
            );
        }

        Image original = new ImageIcon(
                arquivo.getAbsolutePath()
        ).getImage();

        Image imagem = removerTransparencia(original);

        Image redimensionada = imagem.getScaledInstance(
                largura,
                altura,
                Image.SCALE_REPLICATE
        );

        return new ImageIcon(redimensionada);
    }

    private static Image removerTransparencia(Image imagem) {
        int largura = imagem.getWidth(null);
        int altura = imagem.getHeight(null);

        if (largura <= 0 || altura <= 0) {
            return imagem;
        }

        BufferedImage copia = new BufferedImage(
                largura,
                altura,
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = copia.createGraphics();
        g.drawImage(imagem, 0, 0, null);
        g.dispose();

        int menorX = largura;
        int menorY = altura;
        int maiorX = -1;
        int maiorY = -1;

        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                int alpha = (copia.getRGB(x, y) >>> 24) & 0xFF;

                if (alpha > 0) {
                    menorX = Math.min(menorX, x);
                    menorY = Math.min(menorY, y);
                    maiorX = Math.max(maiorX, x);
                    maiorY = Math.max(maiorY, y);
                }
            }
        }

        if (maiorX < 0) {
            return imagem;
        }

        return copia.getSubimage(
                menorX,
                menorY,
                maiorX - menorX + 1,
                maiorY - menorY + 1
        );
    }

    public static ImageIcon rostoNormal(int tamanho) {
        return carregar("smile.png", tamanho, tamanho);
    }

    public static ImageIcon rostoVitoria(int tamanho) {
        return carregar("vitoria.png", tamanho, tamanho);
    }

    public static ImageIcon rostoTriste(int tamanho) {
        return carregar("rostoTriste.png", tamanho, tamanho);
    }

    public static ImageIcon bomba(int tamanho) {
        return carregar("bomba.png", tamanho, tamanho);
    }

    public static ImageIcon bandeira(int tamanho) {
        return carregar("flag.png", tamanho, tamanho);
    }
}
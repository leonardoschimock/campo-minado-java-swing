public class Celula {
    private boolean mina;
    private boolean revelada;
    private boolean bandeira;
    private int minasVizinhas;

    public Celula() {
        this.mina = false;
        this.revelada = false;
        this.bandeira = false;
        this.minasVizinhas = 0;
    }

    public boolean temMina() {
        return mina;
    }

    public void colocarMina() {
        mina = true;
    }

    public boolean estaRevelada() {
        return revelada;
    }

    public void revelar() {
        revelada = true;
    }

    public boolean temBandeira() {
        return bandeira;
    }

    public void alternarBandeira() {
        bandeira = !bandeira;
    }

    public int getMinasVizinhas() {
        return minasVizinhas;
    }

    public void setMinasVizinhas(int minasVizinhas) {
        this.minasVizinhas = minasVizinhas;
    }
}
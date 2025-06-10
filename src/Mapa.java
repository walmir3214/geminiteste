import java.util.ArrayList;
import java.util.List;

public class Mapa {
    private int largura;
    private int altura;
    private List<Inimigo> inimigos;

    public Mapa(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        this.inimigos = new ArrayList<>();
    }

    public void adicionarInimigo(Inimigo inimigo) {
        this.inimigos.add(inimigo);
    }

    public List<Inimigo> getInimigos() {
        return this.inimigos;
    }

    public Inimigo getInimigoEm(int x, int y) {
        for (Inimigo inimigo : inimigos) {
            if (inimigo.estaVivo() && inimigo.getX() == x && inimigo.getY() == y) {
                return inimigo;
            }
        }
        return null;
    }

    public void limparInimigos() {
        this.inimigos.clear();
    }

    public int getLargura() { return largura; }
    public int getAltura() { return altura; }

    public void adicionarChefe(Chefe chefe) {
        this.inimigos.add(chefe);
    }
    public Chefe getChefe() {
        for (Inimigo inimigo : inimigos) {
            if (inimigo instanceof Chefe && inimigo.estaVivo()) {
                return (Chefe) inimigo;
            }
        }
        return null;
    }
}

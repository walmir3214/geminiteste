public class Inimigo {
    private String nome;
    private int pontosDeVida;
    private int pontosDeVidaMax;
    private int forca;
    private int defesa;
    private int xpRecompensa; // XP que o inimigo concede ao ser derrotado
    private int x;
    private int y;
    private boolean vivo = true;

    public Inimigo(String nome, int vida, int forca, int defesa, int xp, int x, int y) {
        this.nome = nome;
        this.pontosDeVidaMax = vida;
        this.pontosDeVida = this.pontosDeVidaMax;
        this.forca = forca;
        this.defesa = defesa;
        this.xpRecompensa = xp;
        this.x = x;
        this.y = y;
    }

    // Getters
    public String getNome() { return nome; }
    public int getPontosDeVida() { return pontosDeVida; }
    public int getPontosDeVidaMax() { return pontosDeVidaMax; }
    public int getForca() { return forca; }
    public int getXpRecompensa() { return xpRecompensa; }
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean estaVivo() { return vivo; }

    // Métodos de combate
    public void receberDano(int dano) {
        this.pontosDeVida -= dano;
        if (this.pontosDeVida <= 0) {
            this.pontosDeVida = 0;
            this.vivo = false;
        }
    }

    public int calcularDano() {
        return this.forca;
    }

    public void reduzirDefesa(int quantidade) {
        this.defesa -= quantidade;
        if (this.defesa < 0) {
            this.defesa = 0;
        }
    }
}

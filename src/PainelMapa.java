import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image; // Mantido para compatibilidade, mas não usado para carregar
import java.io.IOException; // Mantido para compatibilidade, mas não usado para carregar

public class PainelMapa extends JPanel {

    private static final int TAMANHO_CELULA = 40;
    private Mapa mapa;
    private ClasseDoPersonagem personagem;

    public PainelMapa(Mapa mapa, ClasseDoPersonagem personagem) {
        this.mapa = mapa;
        this.personagem = personagem;

        this.setOpaque(true);
        this.setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1. Desenha o chão
        for (int y = 0; y < mapa.getAltura(); y++) {
            for (int x = 0; x < mapa.getLargura(); x++) {
                desenharChao(g, x * TAMANHO_CELULA, y * TAMANHO_CELULA);
            }
        }

        // 2. Desenha os inimigos normais
        for (Inimigo inimigo : mapa.getInimigos()) {
            if (inimigo.estaVivo()) {
                desenharGoblin(g, inimigo.getX() * TAMANHO_CELULA, inimigo.getY() * TAMANHO_CELULA);
            }
        }

        // 3. Desenha o Chefe (se existir)
        Chefe chefe = mapa.getChefe();
        if (chefe != null && chefe.estaVivo()) {
            desenharChefe(g, chefe.getX() * TAMANHO_CELULA, chefe.getY() * TAMANHO_CELULA);
        }

        // 4. Desenha o personagem principal
        if (personagem != null && personagem.estaVivo()) {
            desenharHeroi(g, personagem.getX() * TAMANHO_CELULA, personagem.getY() * TAMANHO_CELULA);
        }
    }

    // --- NOVOS MÉTODOS PARA DESENHAR OS SPRITES ---

    private void desenharChao(Graphics g, int x, int y) {
        g.setColor(new Color(101, 67, 33)); // Cor de terra/pedra escura
        g.fillRect(x, y, TAMANHO_CELULA, TAMANHO_CELULA);
        g.setColor(new Color(66, 40, 14)); // Cor da borda mais escura
        g.drawRect(x, y, TAMANHO_CELULA, TAMANHO_CELULA);
    }

    private void desenharHeroi(Graphics g, int x, int y) {
        // Corpo azul
        g.setColor(new Color(65, 105, 225)); // Royal Blue
        g.fillRect(x + 10, y + 15, 20, 20);
        // Cabeça
        g.setColor(new Color(255, 228, 196)); // Bisque (cor de pele)
        g.fillOval(x + 12, y + 5, 16, 16);
        // Espada
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x + 5, y + 10, 5, 25);
    }

    private void desenharGoblin(Graphics g, int x, int y) {
        // Corpo verde
        g.setColor(new Color(34, 139, 34)); // Forest Green
        g.fillRect(x + 12, y + 18, 16, 16);
        // Cabeça
        g.fillOval(x + 10, y + 8, 20, 20);
        // Olhos
        g.setColor(Color.YELLOW);
        g.fillOval(x + 15, y + 14, 4, 4);
        g.fillOval(x + 21, y + 14, 4, 4);
    }

    private void desenharChefe(Graphics g, int x, int y) {
        // Corpo verde escuro
        g.setColor(new Color(0, 100, 0)); // Dark Green
        g.fillRect(x + 8, y + 12, 24, 26);
        // Cabeça
        g.fillOval(x + 5, y + 2, 30, 30);
        // Olhos vermelhos
        g.setColor(Color.RED);
        g.fillOval(x + 12, y + 10, 6, 6);
        g.fillOval(x + 22, y + 10, 6, 6);
        // Machado
        g.setColor(Color.GRAY);
        g.fillRect(x + 2, y + 8, 8, 30);
    }

    public void atualizar() {
        repaint();
    }
}

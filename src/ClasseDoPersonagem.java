import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata que serve como base para todas as classes de personagens jogáveis.
 * Define os atributos e comportamentos comuns, como vida, mana, nível e habilidades.
 */
public abstract class ClasseDoPersonagem {
    protected String nome;
    protected String nomeDaClasse;

    // Atributos de Combate
    protected int pontosDeVida;
    protected int pontosDeVidaMax;
    protected int mana;
    protected int manaMax;
    protected int forca;
    protected int defesa;
    protected int valorEscudo = 0;

    // Atributos de Progressão
    protected int nivel;
    protected int xpAtual;
    protected int xpParaProximoNivel;

    // Atributos de Posição no Mapa
    protected int x;
    protected int y;

    // Habilidades
    protected List<Habilidade> habilidades;

    public ClasseDoPersonagem(String nome) {
        this.nome = nome;
        this.habilidades = new ArrayList<>();

        // Inicializa o sistema de nível
        this.nivel = 1;
        this.xpAtual = 0;
        this.xpParaProximoNivel = 100;

        // Chama os métodos que serão implementados pelas subclasses
        definirAtributosBase();
        aprenderHabilidadesIniciais();

        // Garante que o personagem comece com vida e mana cheias
        this.pontosDeVida = this.pontosDeVidaMax;
        this.mana = this.manaMax;
    }

    // Métodos Abstratos - Devem ser implementados por cada classe filha (Guerreiro, Mago, etc.)
    public abstract void definirAtributosBase();
    public abstract void aprenderHabilidadesIniciais();

    /**
     * Adiciona experiência ao personagem e verifica se ele subiu de nível.
     * @param xpGanha A quantidade de XP recebida.
     */
    public void ganharXp(int xpGanha) {
        this.xpAtual += xpGanha;
        // Loop para o caso de o personagem ganhar XP suficiente para subir múltiplos níveis de uma vez
        while (this.xpAtual >= this.xpParaProximoNivel) {
            subirDeNivel();
        }
    }

    /**
     * Aumenta o nível do personagem e melhora seus atributos.
     */
    private void subirDeNivel() {
        this.nivel++;
        this.xpAtual -= this.xpParaProximoNivel; // Mantém o XP excedente
        this.xpParaProximoNivel *= 1.5; // Aumenta a dificuldade para o próximo nível

        // Aumenta os atributos base
        this.pontosDeVidaMax += 10;
        this.manaMax += 5;
        this.forca += 2;
        this.defesa += 1;

        // Recupera toda a vida e mana
        this.pontosDeVida = this.pontosDeVidaMax;
        this.mana = this.manaMax;
        this.valorEscudo = 0; // Remove qualquer escudo ativo
    }

    /**
     * Retorna uma mensagem formatada para ser exibida no log quando o personagem sobe de nível.
     * @return A mensagem de level up.
     */
    public String getMensagemLevelUp() {
        return String.format("%s subiu para o nível %d!", this.nome, this.nivel);
    }

    /**
     * Move o personagem no mapa, respeitando os limites.
     * @param deltaX Movimento no eixo X.
     * @param deltaY Movimento no eixo Y.
     * @param larguraMapa Largura do mapa.
     * @param alturaMapa Altura do mapa.
     */
    public void mover(int deltaX, int deltaY, int larguraMapa, int alturaMapa) {
        int novoX = this.x + deltaX;
        int novoY = this.y + deltaY;
        if (novoX >= 0 && novoX < larguraMapa && novoY >= 0 && novoY < alturaMapa) {
            this.x = novoX;
            this.y = novoY;
        }
    }

    /**
     * Aplica dano ao personagem, considerando o escudo.
     * @param dano O dano bruto recebido.
     */
    public void receberDano(int dano) {
        if (this.valorEscudo > 0) {
            int danoAbsorvido = Math.min(this.valorEscudo, dano);
            this.valorEscudo -= danoAbsorvido;
            dano -= danoAbsorvido;
        }
        this.pontosDeVida -= dano;
        if (this.pontosDeVida < 0) this.pontosDeVida = 0;
    }

    /**
     * Cura o personagem, sem ultrapassar a vida máxima.
     * @param valor A quantidade de vida a ser restaurada.
     */
    public void receberCura(int valor) {
        this.pontosDeVida += valor;
        if (this.pontosDeVida > this.pontosDeVidaMax) {
            this.pontosDeVida = this.pontosDeVidaMax;
        }
    }

    public void adicionarEscudo(int valor) {
        this.valorEscudo += valor;
    }

    public boolean temManaSuficiente(int custo) {
        return this.mana >= custo;
    }

    public void gastarMana(int custo) {
        this.mana -= custo;
        if (this.mana < 0) this.mana = 0;
    }

    public void aprenderHabilidade(Habilidade novaHabilidade) {
        this.habilidades.add(novaHabilidade);
    }

    // Getters para os atributos
    public String getNome() { return nome; }
    public String getNomeDaClasse() { return nomeDaClasse; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getPontosDeVida() { return pontosDeVida; }
    public int getPontosDeVidaMax() { return pontosDeVidaMax; }
    public int getForca() { return forca; }
    public int getDefesa() { return defesa; }
    public boolean estaVivo() { return this.pontosDeVida > 0; }
    public List<Habilidade> getHabilidades() { return this.habilidades; }

    // Métodos para aplicar buffs de final de fase
    public void aplicarBuffVida(int valor) {
        this.pontosDeVidaMax += valor;
        this.receberCura(valor);
    }

    public void aplicarBuffMana(int valor) {
        this.manaMax += valor;
        this.mana += valor;
    }

    public void aplicarBuffForca(int valor) {
        this.forca += valor;
    }

    public void aplicarBuffDefesa(int valor) {
        this.defesa += valor;
    }

    @Override
    public String toString() {
        String escudoStr = (valorEscudo > 0) ? String.format("Escudo: %d\n", valorEscudo) : "";
        return String.format(
                "--- %s (Nível %d) ---\n" +
                        "Classe: %s\n" +
                        "HP: %d/%d\n" +
                        "MP: %d/%d\n" +
                        "XP: %d/%d\n" +
                        escudoStr +
                        "Força: %d | Defesa: %d\n",
                nome, nivel, nomeDaClasse, pontosDeVida, pontosDeVidaMax, mana, manaMax, xpAtual, xpParaProximoNivel, forca, defesa
        );
    }

    public void reduzirDefesa(int i) {
        this.defesa -= i;
        if (this.defesa < 0) {
            this.defesa = 0;
        }
    }
}

import java.util.List;
import java.util.Random;
import java.util.Collections;

public class Habilidade {

    public enum TipoHabilidade {
        DANO_FISICO,
        DANO_MAGICO,
        CURA,
        BUFF_DEBUFF
    }

    private String nome;
    private int custoMana;
    private int danoBase;
    private TipoHabilidade tipo;
    private List<String> descricoesDeAtaque;
    private static final Random rand = new Random();

    public Habilidade(String nome, int custoMana, int danoBase, TipoHabilidade tipo, List<String> descricoes) {
        this.nome = nome;
        this.custoMana = custoMana;
        this.danoBase = danoBase;
        this.tipo = tipo;
        this.descricoesDeAtaque = descricoes;
    }

    public Habilidade(String nome, int custoMana, int danoBase, TipoHabilidade tipo) {
        this(nome, custoMana, danoBase, tipo, Collections.emptyList());
    }

    public String getDescricaoAleatoria() {
        if (descricoesDeAtaque == null || descricoesDeAtaque.isEmpty()) {
            return "Usa " + nome + ".";
        }
        return descricoesDeAtaque.get(rand.nextInt(descricoesDeAtaque.size()));
    }

    public String getNome() { return nome; }
    public int getCustoMana() { return custoMana; }
    public int getDanoBase() { return danoBase; }
    public TipoHabilidade getTipo() { return tipo; }

    @Override
    public String toString() { return this.nome; }
}

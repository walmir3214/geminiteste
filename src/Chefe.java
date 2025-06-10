import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chefe extends Inimigo {

    private List<Habilidade> habilidadesDoChefe;
    private static final Random rand = new Random();

    public Chefe(String nome, int vida, int forca, int defesa, int xpRecompensa , int x, int y) {
        super(nome, vida, forca,  defesa, 40,  x, y);
        this.habilidadesDoChefe = new ArrayList<>();
        aprenderHabilidadesDoChefe();
    }

    private void aprenderHabilidadesDoChefe() {
        // Habilidades que o chefe pode usar
        habilidadesDoChefe.add(new Habilidade("Golpe Esmagador", 0, this.getForca() + 20, Habilidade.TipoHabilidade.DANO_FISICO));
        habilidadesDoChefe.add(new Habilidade("Rugido Amedrontador", 0, 0, Habilidade.TipoHabilidade.BUFF_DEBUFF));
    }

    public Habilidade escolherAcaoDoChefe() {
        // Lógica de IA simples para o chefe
        int chance = rand.nextInt(100);
        if (chance < 60) {
            return habilidadesDoChefe.get(0); // 60% de chance de usar o ataque forte
        } else {
            return habilidadesDoChefe.get(1); // 40% de chance de usar o rugido
        }
    }
}

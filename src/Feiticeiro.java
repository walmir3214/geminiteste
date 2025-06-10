import java.util.Arrays;

public class Feiticeiro extends ClasseDoPersonagem {

    public Feiticeiro(String nome) {
        super(nome);
    }

    @Override
    public void definirAtributosBase() {
        this.nomeDaClasse = "Feiticeiro";
        this.pontosDeVidaMax = 170;
        this.manaMax = 200;
        this.forca = 5;
        this.defesa = 3;
    }

    @Override
    public void aprenderHabilidadesIniciais() {
        aprenderHabilidade(new Habilidade("Toque Sombrio", 0, 15, Habilidade.TipoHabilidade.DANO_MAGICO, Arrays.asList(
                "Sombras se estendem de suas mãos e tocam o inimigo.",
                "O alvo sente um calafrio mortal com seu toque.",
                "Energia necromântica flui para o adversário.",
                "Você manipula as sombras para um ataque rápido.",
                "Um ataque sutil, mas carregado de poder sombrio."
        )));

        aprenderHabilidade(new Habilidade("Pacto de Sangue", 0, 60, Habilidade.TipoHabilidade.DANO_MAGICO, Arrays.asList(
                "Você sacrifica parte de sua vida para uma explosão de poder.",
                "Seu sangue ferve enquanto você libera uma magia devastadora.",
                "Um círculo de poder sombrio se forma antes do ataque.",
                "O preço é alto, mas o poder é inegável.",
                "O inimigo é aniquilado por sua magia proibida."
        )));

        aprenderHabilidade(new Habilidade("Correntes da Desolação", 20, 25, Habilidade.TipoHabilidade.DANO_MAGICO, Arrays.asList(
                "Correntes etéreas prendem e esmagam o inimigo.",
                "O alvo é imobilizado por correntes de pura sombra.",
                "A cada segundo, as correntes apertam mais.",
                "O inimigo não tem para onde fugir.",
                "As correntes sombrias drenam a força do adversário."
        )));

        aprenderHabilidade(new Habilidade("Maldição de Fraqueza", 15, 0, Habilidade.TipoHabilidade.BUFF_DEBUFF, Arrays.asList(
                "Você amaldiçoa o inimigo, tornando-o mais vulnerável.",
                "Uma aura negra envolve o alvo, reduzindo suas defesas.",
                "O inimigo se sente fraco e exposto.",
                "Sua maldição abre brechas na armadura do oponente.",
                "O adversário agora é um alvo fácil."
        )));
    }
}

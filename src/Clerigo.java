import java.util.Arrays;

public class Clerigo extends ClasseDoPersonagem {

    public Clerigo(String nome) {
        super(nome);
    }

    @Override
    public void definirAtributosBase() {
        this.nomeDaClasse = "Clérigo";
        this.pontosDeVidaMax = 150;
        this.manaMax = 120;
        this.forca = 10;
        this.defesa = 6;
    }

    @Override
    public void aprenderHabilidadesIniciais() {
        aprenderHabilidade(new Habilidade("Punição Divina", 0, this.forca, Habilidade.TipoHabilidade.DANO_MAGICO, Arrays.asList(
                "Você canaliza energia sagrada que golpeia o inimigo.",
                "Uma coluna de luz desce dos céus sobre o alvo.",
                "O inimigo recua perante o poder da sua fé.",
                "Seu símbolo sagrado brilha intensamente antes do impacto.",
                "Energia radiante queima o adversário."
        )));

        aprenderHabilidade(new Habilidade("Cura Maior", 30, -50, Habilidade.TipoHabilidade.CURA, Arrays.asList(
                "Uma prece fervorosa fecha os ferimentos mais graves.",
                "Luz dourada envolve o alvo, restaurando sua vitalidade.",
                "O poder divino flui, trazendo uma cura poderosa.",
                "Você realiza um milagre de cura no campo de batalha.",
                "A energia da vida é restaurada com sua bênção."
        )));

        aprenderHabilidade(new Habilidade("Barreira Sagrada", 20, 40, Habilidade.TipoHabilidade.BUFF_DEBUFF, Arrays.asList(
                "Um escudo de pura fé protege um aliado de danos.",
                "Você cria uma barreira de luz que repele os ataques.",
                "O alvo é envolvido por uma aura protetora dourada.",
                "A fé se torna um escudo impenetrável.",
                "O próximo ataque contra o alvo será absorvido."
        )));

        aprenderHabilidade(new Habilidade("Fortalecer Aliado", 15, 5, Habilidade.TipoHabilidade.BUFF_DEBUFF, Arrays.asList(
                "Você abençoa um aliado, aumentando sua força de ataque.",
                "Com uma oração, a arma de um companheiro brilha com poder.",
                "O aliado se sente mais forte e confiante.",
                "O poder divino inspira seu companheiro a lutar com mais vigor.",
                "A força do seu aliado é amplificada pela sua fé."
        )));
    }
}

import java.util.Arrays;

public class Guerreiro extends ClasseDoPersonagem {

    public Guerreiro(String nome) {
        super(nome);
    }

    @Override
    public void definirAtributosBase() {
        this.nomeDaClasse = "Guerreiro";
        this.pontosDeVidaMax = 220;
        this.manaMax = 40;
        this.forca = 20;
        this.defesa = 8;
    }

    @Override
    public void aprenderHabilidadesIniciais() {
        aprenderHabilidade(new Habilidade("Ataque com Espada", 0, this.forca, Habilidade.TipoHabilidade.DANO_FISICO, Arrays.asList(
                "Você avança e desfere um corte limpo.", "A lâmina da sua espada canta ao cortar o ar.", "Um golpe rápido e preciso atinge o alvo.",
                "Você gira e ataca com o peso do seu corpo.", "O inimigo recua perante seu ataque simples.", "Sua espada encontra uma brecha na defesa inimiga.",
                "Um ataque básico, mas eficaz.", "Você testa as defesas do oponente com um golpe certeiro.",
                "A ponta da sua espada arranha a armadura do inimigo.", "Com um movimento fluido, você ataca."
        )));

        aprenderHabilidade(new Habilidade("Golpe Poderoso", 10, 30, Habilidade.TipoHabilidade.DANO_FISICO, Arrays.asList(
                "Você reúne forças e esmaga o inimigo com um golpe devastador.", "O impacto do seu ataque arremessa o inimigo para trás.",
                "Um som de metal se partindo ecoa com seu golpe.", "A força do seu ataque deixa o inimigo atordoado.",
                "Com um urro, você desfere um golpe esmagador.", "A arma desce com violência, abrindo uma grande ferida.",
                "Seu golpe poderoso quebra a postura do oponente.", "O chão treme com a força do seu ataque.",
                "É um ataque brutal, quase selvagem.", "O inimigo sente o poder total da sua fúria."
        )));

        aprenderHabilidade(new Habilidade("Quebrar Armadura", 15, 10, Habilidade.TipoHabilidade.BUFF_DEBUFF, Arrays.asList(
                "Você mira um ponto fraco na armadura, causando uma rachadura.", "Seu golpe preciso danifica a proteção do inimigo.",
                "Uma placa da armadura do oponente se solta com o impacto.", "O inimigo fica mais vulnerável após seu ataque estratégico.",
                "Você expõe uma falha na defesa do inimigo.", "Com um golpe certeiro, a armadura do alvo cede.",
                "O som metálico indica que a defesa do inimigo foi comprometida.", "Você força uma abertura na guarda do oponente.",
                "O inimigo olha para sua armadura danificada com preocupação.", "A defesa do alvo foi reduzida!"
        )));

        aprenderHabilidade(new Habilidade("Fôlego Renovado", 20, -30, Habilidade.TipoHabilidade.CURA, Arrays.asList(
                "Você para por um instante, focando em sua respiração e recuperando o vigor.",
                "A dor diminui enquanto você se concentra para se curar.",
                "Com resiliência de guerreiro, você fecha algumas de suas feridas.",
                "Você ignora a dor e encontra novas forças para continuar.",
                "Uma onda de energia curativa percorre seu corpo.",
                "Você se sente revigorado, pronto para o próximo assalto.",
                "A adrenalina ajuda a fechar seus ferimentos.",
                "Você retoma sua postura, mais resistente do que antes.",
                "A determinação pura te cura no meio da batalha.",
                "Você recupera parte de sua vitalidade perdida."
        )));
    }
}

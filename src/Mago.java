import java.util.Arrays;

public class Mago extends ClasseDoPersonagem {

    public Mago(String nome) {
        super(nome);
    }

    @Override
    public void definirAtributosBase() {
        this.nomeDaClasse = "Mago";
        this.pontosDeVidaMax = 100;
        this.manaMax = 160;
        this.forca = 8;
        this.defesa = 4;
    }

    @Override
    public void aprenderHabilidadesIniciais() {
        aprenderHabilidade(new Habilidade("Rajada Mística", 0, 12, Habilidade.TipoHabilidade.DANO_MAGICO, Arrays.asList(
                "Um feixe de energia pura acerta o inimigo.", "Você dispara um projétil arcano de suas mãos.",
                "A magia crepita no ar antes de atingir o alvo.", "O inimigo é empurrado pela força da sua magia.",
                "Energia crepitante envolve o oponente.", "Um ataque mágico simples, mas focado.",
                "Você canaliza uma pequena quantidade de mana em um ataque.", "A rajada deixa um rastro de luz no caminho.",
                "O alvo é atingido por energia arcana instável.", "Sua magia básica atinge o inimigo em cheio."
        )));

        aprenderHabilidade(new Habilidade("Bola de Fogo", 20, 40, Habilidade.TipoHabilidade.DANO_MAGICO, Arrays.asList(
                "Você conjura uma esfera de fogo e a arremessa contra o inimigo.", "O cheiro de ozônio preenche o ar antes da explosão.",
                "As chamas envolvem o alvo, causando queimaduras graves.", "Uma explosão de fogo ensurdecedora engole o inimigo.",
                "O calor é intenso mesmo à distância.", "O inimigo grita ao ser atingido pelas chamas mágicas.",
                "A bola de fogo ilumina o campo de batalha antes do impacto.", "Fagulhas dançam ao redor do alvo após a explosão.",
                "Você molda o fogo primordial em uma arma devastadora.", "O alvo é carbonizado pelo seu feitiço."
        )));

        aprenderHabilidade(new Habilidade("Lança de Gelo", 15, 35, Habilidade.TipoHabilidade.DANO_MAGICO, Arrays.asList(
                "Um fragmento de gelo afiado se materializa e voa em direção ao alvo.", "O ar ao redor do inimigo congela no momento do impacto.",
                "A lança de gelo perfura a armadura do oponente com facilidade.", "O inimigo treme de frio e dor.",
                "Estilhaços de gelo voam para todos os lados.", "Você sente o frio da magia em suas mãos ao conjurá-la.",
                "A velocidade da lança de gelo a torna difícil de desviar.", "O ataque deixa uma camada de geada no alvo.",
                "Um silêncio gelado precede o impacto perfurante.", "O inimigo é empalado por sua magia glacial."
        )));

        aprenderHabilidade(new Habilidade("Drenar Vida", 25, 20, Habilidade.TipoHabilidade.DANO_MAGICO, Arrays.asList(
                "Zarcões de energia sombria conectam você ao inimigo, roubando sua vitalidade.", "Você sente sua força aumentar enquanto o inimigo enfraquece.",
                "A pele do inimigo empalidece enquanto sua energia vital é drenada.", "Uma parte da vida do seu oponente agora é sua.",
                "Magia necromântica flui do alvo para você.", "O inimigo cambaleia, enfraquecido pelo dreno.",
                "Você sorri enquanto a energia roubada cura suas feridas.", "É uma troca profana, mas eficaz.",
                "O inimigo sente a vida se esvaindo de seu corpo.", "A cor retorna ao seu rosto enquanto o inimigo definha."
        )));

        aprenderHabilidade(new Habilidade("Escudo de Mana", 15, 30, Habilidade.TipoHabilidade.BUFF_DEBUFF, Arrays.asList(
                "Você tece fios de mana pura em uma barreira protetora.", "Um escudo translúcido e cintilante se forma à sua frente.",
                "A barreira mágica pulsa com energia, pronta para absorver o próximo golpe.", "Você se prepara para o impacto, protegido por sua magia.",
                "O ar ao seu redor ondula com poder defensivo.", "Seu escudo arcano se solidifica, oferecendo proteção.",
                "Qualquer ataque será amortecido por esta barreira.", "Você canaliza sua mana para se defender.",
                "A proteção mágica brilha com uma luz suave.", "Você se sente mais seguro atrás de seu escudo."
        )));

        aprenderHabilidade(new Habilidade("Cura Leve", 25, -25, Habilidade.TipoHabilidade.CURA, Arrays.asList(
                "Luz suave emana de suas mãos, fechando as feridas do alvo.", "Uma sensação de calor e alívio percorre o corpo do aliado curado.",
                "A magia reconstrói tecidos e alivia a dor.", "Você canaliza energia positiva para restaurar a vitalidade.",
                "O alvo suspira de alívio com a onda de cura."
        )));
    }
}

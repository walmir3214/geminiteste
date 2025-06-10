import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class JogoRPG_GUI extends JFrame {

    // Enum para controlar o estado do jogo
    private enum EstadoJogo { EXPLORANDO, COMBATE, FIM_DE_FASE }
    private EstadoJogo estadoAtual;
    private int faseAtual = 1;

    private Mapa mapa;
    private List<ClasseDoPersonagem> equipe = new ArrayList<>();
    private ClasseDoPersonagem jogadorPrincipal;
    private Inimigo inimigoAtual;

    // Componentes da UI
    private PainelMapa painelMapa;
    private JTextArea areaFicha;
    private JTextPane areaLogCombate;
    private JPanel painelAcoes;
    private List<JButton> botoesHabilidades = new ArrayList<>();
    private JButton btnFugir;
    private static final Random rand = new Random();
    private static final int MAX_LOG_LINES = 150;

    public JogoRPG_GUI() {
        estadoAtual = EstadoJogo.EXPLORANDO;

        criarPersonagemInicial();

        mapa = new Mapa(15, 10);

        setTitle("Meu Jogo de RPG - Fase " + faseAtual);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painelMapa = new PainelMapa(mapa, jogadorPrincipal);
        painelMapa.setPreferredSize(new Dimension(mapa.getLargura() * 40, mapa.getAltura() * 40));
        add(painelMapa, BorderLayout.CENTER);

        JPanel painelLateral = new JPanel();
        painelLateral.setLayout(new BoxLayout(painelLateral, BoxLayout.Y_AXIS));
        add(painelLateral, BorderLayout.EAST);

        areaFicha = new JTextArea();
        areaFicha.setEditable(false);
        areaFicha.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPaneFicha = new JScrollPane(areaFicha);
        scrollPaneFicha.setBorder(BorderFactory.createTitledBorder("Sua Equipe"));
        scrollPaneFicha.setPreferredSize(new Dimension(400, 150));

        areaLogCombate = new JTextPane();
        areaLogCombate.setEditable(false);
        areaLogCombate.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPaneCombate = new JScrollPane(areaLogCombate);
        scrollPaneCombate.setBorder(BorderFactory.createTitledBorder("Log de Eventos"));
        scrollPaneCombate.setPreferredSize(new Dimension(400, 150));

        painelAcoes = new JPanel();
        painelAcoes.setLayout(new BoxLayout(painelAcoes, BoxLayout.Y_AXIS));
        painelAcoes.setBorder(BorderFactory.createTitledBorder("Ações de Combate"));

        painelLateral.add(scrollPaneFicha);
        painelLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        painelLateral.add(scrollPaneCombate);
        painelLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        painelLateral.add(painelAcoes);

        configurarTeclas();

        iniciarNovaFase();

        pack();
        setLocationRelativeTo(null);
        atualizarUI();
    }

    private void criarPersonagemInicial() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do seu personagem:", "Criação de Personagem", JOptionPane.PLAIN_MESSAGE);
        if (nome == null || nome.trim().isEmpty()) nome = "Herói";

        Object[] classes = {"Guerreiro", "Mago", "Clérigo", "Feiticeiro"};
        String classeEscolhida = (String) JOptionPane.showInputDialog(this, "Escolha sua classe:", "Criação de Personagem", JOptionPane.PLAIN_MESSAGE, null, classes, "Guerreiro");
        if (classeEscolhida == null) classeEscolhida = "Guerreiro";

        jogadorPrincipal = criarPersonagem(nome, classeEscolhida);
        equipe.add(jogadorPrincipal);
    }

    private ClasseDoPersonagem criarPersonagem(String nome, String classe) {
        switch (classe) {
            case "Mago": return new Mago(nome);
            case "Clérigo": return new Clerigo(nome);
            case "Feiticeiro": return new Feiticeiro(nome);
            case "Guerreiro":
            default: return new Guerreiro(nome);
        }
    }

    private void iniciarNovaFase() {
        estadoAtual = EstadoJogo.EXPLORANDO;
        setTitle("Meu Jogo de RPG - Fase " + faseAtual);
        mapa.limparInimigos();

        if (faseAtual > 0 && faseAtual % 5 == 0) { // FASE DE CHEFE
            adicionarChefe();
        } else { // FASE NORMAL
            int vidaExtra = (faseAtual - 1) * 10;
            int forcaExtra = (faseAtual - 1) * 2;
            int xpExtra = faseAtual * 5;
            adicionarInimigos(3 + faseAtual, vidaExtra, forcaExtra, xpExtra);
        }

        for(ClasseDoPersonagem membro : equipe) {
            if (membro != null) {
                membro.x = 0;
                membro.y = 0;
            }
        }

        appendStyledText("--- Fase " + faseAtual + " iniciada! ---", Color.BLACK, true);
        atualizarUI();
    }

    private void adicionarChefe() {
        int vidaChefe = 200 + (faseAtual * 20);
        int forcaChefe = 25 + (faseAtual * 3);
        int xpChefe = 250 + (faseAtual * 20);
        Chefe chefe = new Chefe("Orc Líder", vidaChefe, forcaChefe, 10, xpChefe, mapa.getLargura() - 2, mapa.getAltura() / 2);
        mapa.adicionarChefe(chefe);
        appendStyledText("!!! UM ORC LÍDER BLOQUEIA O CAMINHO !!!", Color.RED, true);
    }

    private void verificarFimDeFase() {
        boolean vitoria;
        if (mapa.getChefe() != null) {
            vitoria = !mapa.getChefe().estaVivo();
        } else {
            vitoria = mapa.getInimigos().stream().allMatch(i -> !i.estaVivo());
        }

        if (vitoria) {
            estadoAtual = EstadoJogo.FIM_DE_FASE;
            appendStyledText("!!! FASE CONCLUÍDA !!!", new Color(0, 128, 0), true);

            apresentarBuffsDeFase();

            if (faseAtual % 3 == 0) {
                adicionarNovoMembroNaEquipe();
            }

            faseAtual++;
            iniciarNovaFase();
        }
    }

    private void apresentarBuffsDeFase() {
        String[] buffs = {"+20 Vida Máxima", "+10 Mana Máxima", "+5 Força", "+3 Defesa"};
        int escolha = JOptionPane.showOptionDialog(this, "Fase " + (faseAtual) + " concluída! Escolha um buff para " + jogadorPrincipal.getNome() + ":", "Recompensa da Fase!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, buffs, buffs[0]);
        switch (escolha) {
            case 0: jogadorPrincipal.aplicarBuffVida(20); break;
            case 1: jogadorPrincipal.aplicarBuffMana(10); break;
            case 2: jogadorPrincipal.aplicarBuffForca(5); break;
            case 3: jogadorPrincipal.aplicarBuffDefesa(3); break;
            default: jogadorPrincipal.aplicarBuffVida(20); break;
        }
    }

    private void adicionarNovoMembroNaEquipe() {
        appendStyledText("Você pode recrutar um novo aliado para sua equipe!", new Color(0, 128, 0), true);
        String nome = JOptionPane.showInputDialog(this, "Nome do novo aliado:", "Novo Recruta", JOptionPane.PLAIN_MESSAGE);
        if (nome == null || nome.trim().isEmpty()) nome = "Aliado " + equipe.size();
        Object[] classes = {"Guerreiro", "Mago", "Clérigo", "Feiticeiro"};
        String classeEscolhida = (String) JOptionPane.showInputDialog(this, "Escolha a classe do novo aliado:", "Novo Recruta", JOptionPane.PLAIN_MESSAGE, null, classes, "Guerreiro");
        if (classeEscolhida == null) classeEscolhida = "Guerreiro";
        ClasseDoPersonagem novoMembro = criarPersonagem(nome, classeEscolhida);
        equipe.add(novoMembro);
        appendStyledText(novoMembro.getNome() + ", o " + novoMembro.getNomeDaClasse() + ", se juntou à sua equipe!", new Color(0, 128, 0), true);
    }

    private void executarAcaoDoJogador(Habilidade habilidade) {
        executarHabilidade(jogadorPrincipal, habilidade, inimigoAtual);
        if (!inimigoAtual.estaVivo()) { sairModoCombate(true); return; }

        for (int i = 1; i < equipe.size(); i++) {
            if (!inimigoAtual.estaVivo()) break;
            ClasseDoPersonagem aliado = equipe.get(i);
            if (aliado.estaVivo()) {
                Habilidade habilidadeAliado = escolherAcaoDoAliado(aliado);
                executarHabilidade(aliado, habilidadeAliado, inimigoAtual);
                if (!inimigoAtual.estaVivo()) { sairModoCombate(true); return; }
            }
        }

        appendStyledText("------", Color.GRAY, false);
        executarTurnoDoInimigo();
    }

    private void executarHabilidade(ClasseDoPersonagem atacante, Habilidade habilidade, Inimigo alvo) {
        Color corDoLog = (atacante == jogadorPrincipal) ? new Color(51, 102, 204) : new Color(102, 153, 255);
        appendStyledText("-> " + atacante.getNome() + ": " + habilidade.getDescricaoAleatoria(), corDoLog, true);

        if (habilidade.getNome().equals("Pacto de Sangue")) {
            atacante.receberDano(15);
            appendStyledText(String.format("   %s sacrifica 15 de vida!", atacante.getNome()), Color.MAGENTA, false);
        } else {
            atacante.gastarMana(habilidade.getCustoMana());
        }

        switch (habilidade.getTipo()) {
            case DANO_FISICO:
            case DANO_MAGICO:
                int danoCausado = habilidade.getDanoBase();
                alvo.receberDano(danoCausado);
                appendStyledText(String.format("   Causou %d de dano!", danoCausado), Color.DARK_GRAY, false);
                break;
            case CURA:
                ClasseDoPersonagem alvoCura = encontrarAlvoParaCura(atacante);
                int curaRecebida = -habilidade.getDanoBase();
                alvoCura.receberCura(curaRecebida);
                appendStyledText(String.format("   Curou %d de vida de %s!", curaRecebida, alvoCura.getNome()), new Color(0, 153, 51), false);
                break;
            case BUFF_DEBUFF:
                if (habilidade.getNome().equals("Quebrar Armadura") || habilidade.getNome().equals("Maldição de Fraqueza")) {
                    alvo.reduzirDefesa(5);
                    appendStyledText(String.format("   A defesa do %s foi reduzida!", alvo.getNome()), Color.ORANGE, false);
                } else if (habilidade.getNome().equals("Escudo de Mana") || habilidade.getNome().equals("Barreira Sagrada")) {
                    ClasseDoPersonagem alvoBuff = (habilidade.getNome().equals("Barreira Sagrada")) ? encontrarAlvoParaCura(atacante) : atacante;
                    alvoBuff.adicionarEscudo(habilidade.getDanoBase());
                    appendStyledText(String.format("   %s ganhou um escudo de %d pontos!", alvoBuff.getNome(), habilidade.getDanoBase()), new Color(0, 153, 204), false);
                } else if (habilidade.getNome().equals("Fortalecer Aliado")) {
                    ClasseDoPersonagem alvoBuff = encontrarAlvoParaCura(atacante);
                    alvoBuff.aplicarBuffForca(habilidade.getDanoBase());
                    appendStyledText(String.format("   A força de %s aumentou!", alvoBuff.getNome()), new Color(255, 204, 0), false);
                }
                break;
        }
    }

    private Habilidade escolherAcaoDoAliado(ClasseDoPersonagem aliado) {
        if (aliado instanceof Mago || aliado instanceof Clerigo) {
            Optional<Habilidade> cura = aliado.getHabilidades().stream()
                    .filter(h -> h.getTipo() == Habilidade.TipoHabilidade.CURA && aliado.temManaSuficiente(h.getCustoMana()))
                    .findFirst();

            if (cura.isPresent()) {
                boolean alguemPrecisandoDeCura = equipe.stream()
                        .anyMatch(membro -> membro.estaVivo() && (double)membro.getPontosDeVida() / membro.getPontosDeVidaMax() < 0.6);

                if (alguemPrecisandoDeCura) {
                    return cura.get();
                }
            }
        }

        List<Habilidade> habilidadesOfensivas = aliado.getHabilidades().stream()
                .filter(h -> h.getCustoMana() > 0 && aliado.temManaSuficiente(h.getCustoMana()) && h.getTipo() != Habilidade.TipoHabilidade.CURA)
                .collect(Collectors.toList());

        if (!habilidadesOfensivas.isEmpty() && rand.nextInt(100) < 50) {
            return habilidadesOfensivas.get(rand.nextInt(habilidadesOfensivas.size()));
        }

        return aliado.getHabilidades().get(0);
    }

    private void executarTurnoDoInimigo() {
        Habilidade acaoInimigo;
        if (inimigoAtual instanceof Chefe) {
            acaoInimigo = ((Chefe) inimigoAtual).escolherAcaoDoChefe();
        } else {
            acaoInimigo = new Habilidade("Ataque Básico", 0, inimigoAtual.calcularDano(), Habilidade.TipoHabilidade.DANO_FISICO, null);
        }

        appendStyledText("<- " + inimigoAtual.getNome() + " usa " + acaoInimigo.getNome() + "!", new Color(204, 51, 0), true);

        List<ClasseDoPersonagem> alvosVivos = equipe.stream().filter(ClasseDoPersonagem::estaVivo).collect(Collectors.toList());
        if (alvosVivos.isEmpty()) return;

        if (acaoInimigo.getTipo() == Habilidade.TipoHabilidade.BUFF_DEBUFF) {
            for (ClasseDoPersonagem membro : alvosVivos) {
                membro.reduzirDefesa(5);
            }
            appendStyledText("   A defesa de toda a sua equipe foi reduzida!", Color.ORANGE, false);
        } else {
            ClasseDoPersonagem alvo = alvosVivos.get(rand.nextInt(alvosVivos.size()));
            int danoRecebido = Math.max(0, acaoInimigo.getDanoBase() - alvo.getDefesa());
            alvo.receberDano(danoRecebido);
            appendStyledText(String.format("   Ataca %s e causa %d de dano!", alvo.getNome(), danoRecebido), Color.DARK_GRAY, false);
            if (!alvo.estaVivo()) {
                appendStyledText(alvo.getNome() + " foi derrotado!", Color.RED, false);
            }
        }

        if (!equipe.stream().anyMatch(ClasseDoPersonagem::estaVivo)) {
            appendStyledText("\nSUA EQUIPE FOI DERROTADA!", Color.RED, true);
            JOptionPane.showMessageDialog(this, "FIM DE JOGO!", "Game Over", JOptionPane.ERROR_MESSAGE);
            estadoAtual = EstadoJogo.FIM_DE_FASE;
            painelAcoes.removeAll();
            painelAcoes.revalidate();
            painelAcoes.repaint();
        }

        atualizarUI();
    }

    private ClasseDoPersonagem encontrarAlvoParaCura(ClasseDoPersonagem curador) {
        return equipe.stream()
                .filter(ClasseDoPersonagem::estaVivo)
                .min((p1, p2) -> Double.compare((double)p1.getPontosDeVida()/p1.getPontosDeVidaMax(), (double)p2.getPontosDeVida()/p2.getPontosDeVidaMax()))
                .orElse(curador);
    }

    private void adicionarInimigos(int quantidade, int vidaExtra, int forcaExtra, int xpExtra) {
        for (int i = 0; i < quantidade; i++) {
            int x, y;
            do {
                x = rand.nextInt(mapa.getLargura());
                y = rand.nextInt(mapa.getAltura());
            } while ((jogadorPrincipal != null && x == jogadorPrincipal.getX() && y == jogadorPrincipal.getY()) || mapa.getInimigoEm(x, y) != null);
            mapa.adicionarInimigo(new Inimigo("Goblin", 30 + vidaExtra, 8 + forcaExtra, 3, 25 + xpExtra, x, y));
        }
    }

    private void configurarTeclas() {
        JPanel contentPane = (JPanel) getContentPane();
        InputMap inputMap = contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = contentPane.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "moverCima");
        actionMap.put("moverCima", new MoverAction(0, -1));
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "moverEsquerda");
        actionMap.put("moverEsquerda", new MoverAction(-1, 0));
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "moverBaixo");
        actionMap.put("moverBaixo", new MoverAction(0, 1));
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "moverDireita");
        actionMap.put("moverDireita", new MoverAction(1, 0));
    }

    private void entrarModoCombate(Inimigo inimigo) {
        estadoAtual = EstadoJogo.COMBATE;
        inimigoAtual = inimigo;
        appendStyledText("==============\nVocê encontrou um " + inimigo.getNome() + "!", new Color(102, 0, 153), true);
        popularAcoesDeCombate();
        atualizarEstadoBotoes();
    }

    private void sairModoCombate(boolean vitoria) {
        if (vitoria) {
            appendStyledText("O " + inimigoAtual.getNome() + " foi derrotado!", new Color(102, 0, 153), true);
            int xpGanha = inimigoAtual.getXpRecompensa();
            appendStyledText("Sua equipe ganhou " + xpGanha + " de XP!", new Color(218, 165, 32), true);
            for (ClasseDoPersonagem membro : equipe) {
                if (membro.estaVivo()) {
                    int nivelAntes = membro.nivel;
                    membro.ganharXp(xpGanha);
                    if (membro.nivel > nivelAntes) {
                        appendStyledText(membro.getMensagemLevelUp(), new Color(218, 165, 32), true);
                    }
                }
            }
        } else {
            appendStyledText("Você fugiu da batalha.", new Color(102, 0, 153), true);
        }
        estadoAtual = EstadoJogo.EXPLORANDO;
        inimigoAtual = null;

        painelAcoes.removeAll();
        painelAcoes.revalidate();
        painelAcoes.repaint();
        pack();

        if (vitoria) {
            verificarFimDeFase();
        }
    }

    private void popularAcoesDeCombate() {
        painelAcoes.removeAll();
        botoesHabilidades.clear();

        for (Habilidade hab : jogadorPrincipal.getHabilidades()) {
            JButton btnHab = new JButton(String.format("%s (%d MP)", hab.getNome(), hab.getCustoMana()));
            btnHab.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnHab.addActionListener(e -> executarAcaoDoJogador(hab));
            botoesHabilidades.add(btnHab);
            painelAcoes.add(btnHab);
        }

        btnFugir = new JButton("Fugir");
        btnFugir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFugir.addActionListener(e -> tentarFugir());
        painelAcoes.add(Box.createRigidArea(new Dimension(0, 10)));
        painelAcoes.add(btnFugir);

        painelAcoes.revalidate();
        painelAcoes.repaint();
        pack();
    }

    private void tentarFugir() {
        sairModoCombate(false);
    }

    private void atualizarUI() {
        StringBuilder sb = new StringBuilder();
        for (ClasseDoPersonagem membro : equipe) {
            if(membro.estaVivo()) {
                sb.append(membro.toString());
                sb.append("------------------------\n");
            }
        }
        areaFicha.setText(sb.toString());

        if(estadoAtual == EstadoJogo.COMBATE) {
            atualizarEstadoBotoes();
        }
        if (painelMapa != null) {
            painelMapa.atualizar();
        }
    }

    private void atualizarEstadoBotoes() {
        for (int i = 0; i < jogadorPrincipal.getHabilidades().size(); i++) {
            Habilidade hab = jogadorPrincipal.getHabilidades().get(i);
            JButton btn = botoesHabilidades.get(i);
            if (hab.getTipo() == Habilidade.TipoHabilidade.CURA) {
                btn.setEnabled(jogadorPrincipal.temManaSuficiente(hab.getCustoMana()) && equipe.stream().anyMatch(m -> m.getPontosDeVida() < m.getPontosDeVidaMax()));
            } else {
                btn.setEnabled(jogadorPrincipal.temManaSuficiente(hab.getCustoMana()));
            }
        }
    }

    private void appendStyledText(String msg, Color c, boolean isBold) {
        if (areaLogCombate == null) return;

        SwingUtilities.invokeLater(() -> {
            StyledDocument doc = areaLogCombate.getStyledDocument();
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setForeground(attrs, c);
            StyleConstants.setBold(attrs, isBold);
            StyleConstants.setFontFamily(attrs, "Monospaced");

            try {
                doc.insertString(doc.getLength(), msg + "\n", attrs);

                Element root = doc.getDefaultRootElement();
                while (root.getElementCount() > MAX_LOG_LINES) {
                    Element firstLine = root.getElement(0);
                    doc.remove(0, firstLine.getEndOffset());
                }

                areaLogCombate.setCaretPosition(doc.getLength());
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        });
    }

    private class MoverAction extends AbstractAction {
        private int deltaX, deltaY;
        public MoverAction(int dx, int dy) { this.deltaX = dx; this.deltaY = dy; }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (estadoAtual != EstadoJogo.EXPLORANDO) { return; }
            int novoX = jogadorPrincipal.getX() + deltaX;
            int novoY = jogadorPrincipal.getY() + deltaY;
            Inimigo inimigoAlvo = mapa.getInimigoEm(novoX, novoY);
            if (inimigoAlvo != null) {
                entrarModoCombate(inimigoAlvo);
            } else {
                jogadorPrincipal.mover(deltaX, deltaY, mapa.getLargura(), mapa.getAltura());
            }
            atualizarUI();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JogoRPG_GUI().setVisible(true));
    }
}

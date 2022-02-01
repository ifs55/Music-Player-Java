package gg;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import static infraSW.Player.*;

public class GUI extends JFrame implements ActionListener, ListSelectionListener{

    private int progress = 0;
    private Timer timer;
    private final JLabel titulo;
    private final JList<String> musicJList;
    private final JProgressBar progressBar;
    private boolean isSequential = false;
    private boolean isRandon = false;
    private final JRadioButton botaoSeq;
    private final JRadioButton botaoRand;

    private final JButton botaoProximo;

    public GUI() {
        super("Player de Música");

        //Menu que contem as opçoes de add e remover musica
        JMenu menuLista = new JMenu("File");
        JMenuItem menuItemAdicionar = new JMenuItem("Adicionar Música");
        menuLista.add(menuItemAdicionar);
        menuItemAdicionar.addActionListener(this);
        menuItemAdicionar.setActionCommand("add");
        JMenuItem menuItemRemover = new JMenuItem("Remover Música");
        menuLista.add(menuItemRemover);
        menuItemRemover.addActionListener(this);
        menuItemRemover.setActionCommand("rmv");

        //Adicionando o Menu a barra de tarefas
        JMenuBar barra = new JMenuBar();
        setJMenuBar(barra);
        barra.add(menuLista);

        //Criando o painel Principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));

        // Criando uma Scroll Panel que exibirá a playlist
        musicJList = new JList<>();
        JScrollPane lista = new JScrollPane(musicJList);
        musicJList.addListSelectionListener(this);
        lista.setMaximumSize(new Dimension(400, 150));
        lista.setBorder(new LineBorder(Color.BLACK));


        //Barra de progresso
        JPanel progressBarPanel = new JPanel();
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBarPanel.setBackground(Color.WHITE);
        progressBarPanel.add(progressBar);
        progressBarPanel.add(Box.createVerticalStrut(20));


        // Panel Título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        titulo = new JLabel("Música");
        panelTitulo.add(titulo);


        // Panel de botoes
        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new BoxLayout(panelBotoes, BoxLayout.X_AXIS));

        // Painel para com botões para alternar entre modo Sequencial e Aleatório
        JPanel panelShuffle = new JPanel();
        panelShuffle.setLayout(new BoxLayout(panelShuffle,BoxLayout.Y_AXIS));

        // Botão modo sequencial
        botaoSeq = new JRadioButton("Modo Sequencial", false);
        botaoSeq.setBackground(Color.WHITE);
        botaoSeq.setMnemonic(KeyEvent.VK_B);
        botaoSeq.setActionCommand("seq");
        botaoSeq.addActionListener(this);
        panelShuffle.add(botaoSeq);

        // Botão modo aleatório
        botaoRand = new JRadioButton("Modo Aleatório", false);
        botaoRand.setBackground(Color.WHITE);
        botaoRand.setMnemonic(KeyEvent.VK_B);
        botaoRand.setActionCommand("randon");
        botaoRand.addActionListener(this);
        panelShuffle.add(botaoRand);

        panelBotoes.add(panelShuffle);
        panelBotoes.add(Box.createHorizontalStrut(20));
        panelShuffle.setBackground(Color.WHITE);


        //Botao Anterior
        JButton botaoAnterior = new JButton("Previous");
        botaoAnterior.addActionListener(this);
        botaoAnterior.setActionCommand("prev");
        panelBotoes.add(botaoAnterior);
        panelBotoes.add(Box.createHorizontalStrut(20));

        //Botao play
        JButton botaoPlay = new JButton("Play");
        botaoPlay.addActionListener(this);
        botaoPlay.setActionCommand("play");
        panelBotoes.add(botaoPlay);
        panelBotoes.add(Box.createHorizontalStrut(20));


        //Botao Pause
        JButton botaoPause = new JButton("Pause");
        botaoPause.addActionListener(this);
        botaoPause.setActionCommand("pause");
        panelBotoes.add(botaoPause);
        panelBotoes.add(Box.createHorizontalStrut(20));


        //Botao Proxima
        /*JButton*/ botaoProximo = new JButton("Next");
        botaoProximo.addActionListener(this);
        botaoProximo.setActionCommand("next");
        panelBotoes.add(botaoProximo);
        panelBotoes.add(Box.createHorizontalStrut(20));

        panelBotoes.setBackground(Color.WHITE);
        mainPanel.setBackground(Color.WHITE);

        // Adicionando tudo no MainPanel
        mainPanel.add(panelTitulo);
        mainPanel.add(lista);
        mainPanel.add(progressBarPanel);
        mainPanel.add(panelBotoes);
        mainPanel.setAutoscrolls(true);
        mainPanel.setVisible(true);

        add(mainPanel);
    }

    public static void main(String[] args) {
        new GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {

            case "add":

                // Criando caixa de diálogo ao clicar em "Adicionar Música"
                JTextField name = new JTextField(10);
                JTextField duration = new JTextField(10);
                JPanel panelAdd = new JPanel();
                panelAdd.add(new JLabel("Nome:"));
                panelAdd.add(name);
                panelAdd.add(Box.createHorizontalStrut(15));
                panelAdd.add(new JLabel("Duração (seg) :"));
                panelAdd.add(duration);
                JOptionPane.showConfirmDialog(null, panelAdd,
                        "Digite o nome e a duração da música", JOptionPane.OK_CANCEL_OPTION);

                Player.Music msc = new Player.Music(name.getText(), Integer.parseInt(duration.getText()));
                Player.AddMusic addMusic = new Player.AddMusic(msc);
                addMusic.start();

                // Adicionando nome da música a JList da GUI
                addToJList(name.getText());

                break;

            case "rmv":

                // Criando caixa de diálogo ao clicar em "Remover Música"
                JTextField nomeMusic = new JTextField(10);
                JPanel panelRemove = new JPanel();
                panelRemove.add(new JLabel("Nome:"));
                panelRemove.add(nomeMusic);
                JOptionPane.showConfirmDialog(null, panelRemove,
                        "Digite o nome da música a ser removida", JOptionPane.OK_CANCEL_OPTION);

                Player.RemoveMusic removeMusic = new Player.RemoveMusic(nomeMusic.getText());
                removeMusic.start();

                // Removendo nome da música a JList da GUI
                removeFromJList(nomeMusic.getText());

                break;

            case "next":
                NextORprevMusic nextMusic = new NextORprevMusic(true, false);
                nextMusic.start();

                // Após apertar Proximo, atribuimos progresso para 0
                progress = 0;
                progressBar.setValue(progress);

                // Se estiver no modo sequencial, automaticamente inicie a música.
                if(isSequential) timer.start();
                if(isRandon){
                    int randonIndex = new Random().nextInt(listaReproducao.size()-1);
                    goToMusic(randonIndex);
                    timer.start();
                }

                if(currentMusicIndex < listaReproducao.size()-1 )titulo.setText("Você está escutando: " + listaReproducao.get(currentMusicIndex + 1).getNameMusic());

                break;

            case "prev":
                NextORprevMusic prevMusic = new NextORprevMusic(false, true);
                prevMusic.start();
                if(currentMusicIndex > 0 )titulo.setText("Você está escutando: " + listaReproducao.get(currentMusicIndex - 1).getNameMusic());

                // Após apertar Anterior, atribuimos progresso para 0
                progress = 0;
                progressBar.setValue(progress);
                break;

            case "play":
                if(currentMusic != null) timer = new Timer(currentMusic.getDurationSong()*10, this::timerActionPerformed);
                assert currentMusic != null;
                titulo.setText("Você está escutando: " + currentMusic.getNameMusic());
                timer.start();
                break;

            case "pause":
                if(currentMusic != null) titulo.setText(currentMusic.getNameMusic() + " pausada.");
                timer.stop();
                break;

            case "seq":
                botaoRand.setSelected(false);
                isRandon = false;
                isSequential = true;
                break;

            case "randon":
                botaoSeq.setSelected(false);
                isSequential = false;
                isRandon = true;
                break;

        }

    }


    // Método auxiliar: Controla o timer e o progresso da JProgressBar
    private void timerActionPerformed(ActionEvent e) {

        timer.setDelay(currentMusic.getDurationSong()*10);
        progressBar.setValue(++progress);

        if(progress == 100){ timer.stop(); }

        if(progress == 100 && (isSequential || isRandon)) {
            botaoProximo.doClick(); // Finjo um click no botão next
        }
    }

    // Adiciona música na interface gráfica
    private void addToJList(String name){
        musicJList.setModel(playlistModel);
        playlistModel.addElement(name);
    }

    // Remove música da interface gráfica
    private void removeFromJList(String name){
        musicJList.setModel(playlistModel);
        playlistModel.removeElement(name);
    }

    private void goToMusic(int index){
        currentMusicIndex = index;
        currentMusic = listaReproducao.get(index);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) { }

}
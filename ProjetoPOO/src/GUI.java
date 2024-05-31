import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Class for custom Frame, used as GUI for the game
 */
public class GUI extends JFrame implements Serializable {
    /**
     * JPanel object, used as the canvas for the GUI (every J element must be inside this)
     */
    private JPanel panel;
    /**
     * ActionListener object, used to detect button presses
     * can be subclass mainMenuButtonListener or gameScreenButtonListener, depending on the stage
     */
    private ActionListener actionListener;
    /**
     * Pointer to Game object
     */
    private Game game;
    /**
     * Constructor for GUI object
     * @param game pointer to the game object that user is currently playing
     */
    public GUI(Game game) {
        super();
        this.game = game;
        this.displayMainMenu();
        this.setVisible(true);
    }
    /**
     * Method to display on the window the mainMenu.
     * create 3 buttons (exit, start, leaderboard) and a label (title) and place these elements on this.panel
     */
    private void displayMainMenu() {
        this.panel = new JPanel();
        this.getContentPane().removeAll();
        this.repaint();
        JLabel titulo;
        JButton buttonStart, buttonLeaderBoard, buttonExit;

        this.setSize(400, 600);
        this.setTitle("POOTrivia - MainMenu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Text inputs and labels
        titulo=new JLabel("WELCOME TO POOTRIVIA!", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial",Font.BOLD,25));
        // Action buttons
        buttonExit = new JButton("Sair");
        buttonStart = new JButton("Iniciar Jogo");
        buttonLeaderBoard = new JButton("TOP 3");

        this.panel=new JPanel(new GridLayout(4,1));
        this.panel.add(titulo);
        this.panel.add(buttonStart);
        this.panel.add(buttonLeaderBoard);
        this.panel.add(buttonExit);

        this.actionListener = new mainMenuButtonListener(buttonStart, buttonLeaderBoard, buttonExit);
        buttonExit.addActionListener(this.actionListener);
        buttonLeaderBoard.addActionListener(this.actionListener);
        buttonStart.addActionListener(this.actionListener);

        this.add(this.panel);
    }



    /**
     * Class to Handle button listening for the MainMenu
     */
    private class mainMenuButtonListener implements ActionListener, Serializable {

        /**
         * Jbuttons to be give users choice between start a new game, see leader board or exit program
         */
        private JButton buttonStart, buttonLeaderBoard, buttonExit;


        /**
         * Constructor for the "mainMenuButtonListener" object, takes in 3 JButtons
         * @param buttonStart JButton to Start game
         * @param buttonLeaderBoard JButton to see Leader Board
         * @param buttonExit JButton to Exit program
         */
        public mainMenuButtonListener(JButton buttonStart, JButton buttonLeaderBoard, JButton buttonExit) {
            this.buttonStart = buttonStart;
            this.buttonLeaderBoard = buttonLeaderBoard;
            this.buttonExit = buttonExit;
        }

        /**
         * Implementation of ActionListener's actionPerformed, detects button presses, and processes it
         * @param event the event to be processed
         */
        public void actionPerformed(ActionEvent event) {
            if (event.getSource()==this.buttonStart) {
                game = new Game();
                displayQuestion(game.getCurrentQuestion());
            } else if (event.getSource()==this.buttonLeaderBoard) {
                apresentarTOP3(game);
            } else if (event.getSource()==this.buttonExit) {
                if (JOptionPane.showConfirmDialog(null, "Tem a certeza que pretende sair?", "Sair", JOptionPane.YES_NO_OPTION) == 0) {
                    System.exit(0);
                }
            }
        }
    }



    /**
     * Updates this.panel to display current question.
     * @param question Question object to be displayed
     */
    private void displayQuestion(Question question) {
        this.panel = new JPanel();
        this.getContentPane().removeAll();
        this.repaint();
        JLabel categoryLabel;
        JLabel questionLabel;
        ArrayList<JButton> optionButtons = new ArrayList<JButton>();

        this.actionListener = new gameScreenButtonListener(question, optionButtons);

        this.setSize(1100, 500);
        this.setTitle("POOTrivia");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        categoryLabel = new JLabel();
        categoryLabel.setForeground(Color.BLUE);
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 45));
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o texto horizontalmente

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(new GridLayout(2,1));

        //this.panel.setLayout(new GridLayout(this.game.getQuestionsList().size()+2,1));
        this.panel.setLayout(new GridLayout(2,1));
        this.panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        categoryLabel.setText(question.addOpcaoCorreta().split("/")[0]);
        questionLabel.setText((this.game.getStage()+1)+": "+question.getDescription());
        questionLabel.setPreferredSize(new Dimension(800, 100));

        panelHeader.add(categoryLabel);
        panelHeader.add(questionLabel);
        this.panel.add(panelHeader);

        JButton newButton;
        JPanel panelOptions = new JPanel();
        panelOptions.setLayout(new GridLayout(this.game.getQuestionsList().size(),2));
        for (String option : game.getCurrentQuestion().getOptionList()) {
            newButton = new JButton(option);
            //newButton.setAlignmentX(Co.ALIGNMENT);
            newButton.addActionListener(this.actionListener);
            panelOptions.add(newButton);
            optionButtons.add(newButton);
        }
        this.panel.add(panelOptions);

        this.add(this.panel);
        this.setVisible(true);
    }



    /**
     * Class to Handle button listening for Question Screen
     */
    private class gameScreenButtonListener implements ActionListener, Serializable {

        /**
         * Question that is being displayed
         */
        private Question question;

        /**
         * ArrayList that holds the buttons for this question, one for each option in this.question
         */
        private ArrayList<JButton> optionButtons;


        /**
         * Constructor for the "gameScreenButtonListener" object
         * @param question Question object, for current question
         * @param optionButtons ArrayList, holds all JButtons, one for each option of question
         */
        public gameScreenButtonListener(Question question, ArrayList<JButton> optionButtons) {
            this.question = question;
            this.optionButtons = optionButtons;
        }

        /**
         * Implementation of ActionListener's actionPerformed, detects button presses, and processes it
         * @param event the event to be processed
         */
        public void actionPerformed(ActionEvent event) {
            for (int i=0; i<(int)this.optionButtons.size(); i++) {
                if (event.getSource()==this.optionButtons.get(i)) {
                    if (question.checkOption(question.getOptionList().get(i))) {
                        JOptionPane.showMessageDialog(null, "Resposta correta!");
                        JOptionPane.showMessageDialog(null, "Pontuação da pergunta: " + game.getQuestionsList().get(game.getStage()).getPoints());
                        game.getCorrectAnswers().add(game.getQuestionsList().get(game.getStage()));

                        if (game.nextStage()) {
                            // Next question, mark as correct
                            displayQuestion(game.getCurrentQuestion());
                        } else {
                            JOptionPane.showMessageDialog(null, "Pontuação Final: " + game.calculatePoints(game.getCorrectAnswers()));
                            String value = JOptionPane.showInputDialog(null,"Introduza o seu nome");// Mostra mensagem de fim do jogo
                            while (value == null || value.isEmpty()){
                                value = JOptionPane.showInputDialog(null, "Introduza o seu nome");
                            }
                            game.setName(value);

                            String aux= game.creatFileObjectName();
                            game.creatFileObject(aux);
                            apresentarTOP3(game);
                            displayMainMenu();
                        }
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Resposta incorreta!");
                        JOptionPane.showMessageDialog(null, "Resposta correta: " + game.getQuestionsList().get(game.getStage()).getCorrectOption());
                        if (game.nextStage()) {
                            // Next question
                            displayQuestion(game.getCurrentQuestion());
                        } else {
                            // Game has ended, ask for name and save file
                            JOptionPane.showMessageDialog(null, "Pontuação Final: " + game.calculatePoints(game.getCorrectAnswers()));
                            String value = JOptionPane.showInputDialog(null,"Introduza o seu nome");// Mostra mensagem de fim do jogo
                            while (value == null || value.isEmpty()){
                                value = JOptionPane.showInputDialog(null, "Introduza o seu nome");
                            }
                            game.setName(value);

                            String aux= game.creatFileObjectName();
                            game.creatFileObject(aux);
                            apresentarTOP3(game);
                            displayMainMenu();;
                        }
                        break;
                    }
                }
            }
        }
    }



    /**
     * Create new window, and present TOP3 games from file
     * @param game used to calculate the TOP3
     */
    public static void apresentarTOP3(Game game){
        JFrame ranking = new JFrame();
        ranking.setSize(500,250);
        ranking.setTitle("TOP3");

        JPanel panelRanking = new JPanel(new GridLayout(game.calculateTOP3().size()+1,1));
        JLabel titulo = new JLabel("TOP3",SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 40)); // Ajusta a fonte, estilo e tamanho
        panelRanking.add(titulo);

        for (int i=0;i<game.calculateTOP3().size();i++){
            JLabel gameRanking = new JLabel(" Classificação: "
                    +game.calculatePoints(game.calculateTOP3().get(i).getCorrectAnswers())+" - "+game.calculateTOP3().get(i).getName()+" "
                    +game.calculateTOP3().get(i).getDate()+" "+game.calculateTOP3().get(i).getTime(),SwingConstants.CENTER);
            panelRanking.add(gameRanking);
        }

        ranking.add(panelRanking);
        ranking.setVisible(true);
    }
}
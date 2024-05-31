import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
 * Class for Game, handles the list of questions, points, time and date and user's name
 */
public class Game implements Serializable {

    /**
     * ArrayList, holds the chosen questions for this game
     */
    private ArrayList<Question> questionsList;

    /**
     * ArrayList of all the questions that were answered correctly
     */
    private ArrayList<Question> correctAnswers = new ArrayList<>();

    /**
     * Value of the index of current question
     */
    private int stage = 0;

    /**
     * Holds the name of the user that played the game
     */
    private String name;

    /**
     * Holds the date and time of when the game was started
     */
    private String date, time;



    /**
     * Constructor for "Game" object.
     * Takes in no arguments, but calls setTime() and setDate() (for save file purposes) and loads 5 questions from file
     */
    public Game() {
        setTime();
        setDate();
        this.questionsList = this.LoadQuestions();
    }


    /**
     * Function to get name of user
     * @return name of user
     */
    public String getName() { return this.name; }

    /**
     * Function to get date of when the game started
     * @return date
     */
    public String getDate() { return this.date; }

    /**
     * Function to get time of when the game started
     * @return time
     */
    public String getTime() { return this.time; }

    /**
     * Get copy of ArrayList of this.questionsList
     * @return this.questionsList copy
     */
    public ArrayList<Question> getQuestionsList() { return new ArrayList<Question>(this.questionsList); }

    /**
     * Get integer of stage value
     * @return this.stage
     */
    public int getStage() { return this.stage; }

    /**
     * Get current question, from this.questionsList, index this.stage
     * @return question from this.questionsList.get(this.stage)
     */
    public Question getCurrentQuestion() {
        return this.questionsList.get(this.stage);
    }

    /**
     * Get list of Answers that were correctly answered
     * @return this.correctAnswers
     */
    public ArrayList<Question> getCorrectAnswers(){ return this.correctAnswers; }



    /**
     * Renames question
     * @param name new name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Sets this.date to current date
     */
    private void setDate() {
        LocalDateTime data = LocalDateTime.now();
        DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date = data.format(dataFormato);
    }

    /**
     * Sets this.time to current time
     */
    private void setTime() {
        LocalDateTime hora = LocalDateTime.now();
        DateTimeFormatter horaFormato = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.time = hora.format(horaFormato);
    }

    /**
     * Sets this.questionList
     * @param questionsList ArrayList new questionList
     */
    public void setQuestionsList(ArrayList<Question> questionsList) { this.questionsList = new ArrayList<Question>(questionsList); }

    /**
     * Sets stage value to +1, if there are still questions left
     * @return boolean value of wether or not this.stage was increased
     */
    public boolean nextStage() {
        if (this.stage+1==(int)this.questionsList.size()) {
            return false;
        } else {
            this.stage++;
            return true;
        }
    }


    /**
     * Calculates TOP3 games registered on file
     * @return ArrayList of TOP3
     */
    public ArrayList<Game> calculateTOP3() {
        ArrayList<Game> jogos = readNameOfFileObjects();
        ArrayList<Game> top3 = new ArrayList<>();
        for (Game jogo : jogos) {
            int pontuacaoAtual = jogo.calculatePoints(jogo.getCorrectAnswers());

            if (top3.isEmpty()) {
                top3.add(jogo);
            } else {
                boolean added = false;
                for (int i = 0; i < top3.size(); i++) {
                    int pontuacaoTop = top3.get(i).calculatePoints(top3.get(i).getCorrectAnswers());
                    if (pontuacaoAtual > pontuacaoTop) {
                        top3.add(i, jogo);
                        added = true;
                        break;
                    }
                }
                if (!added && top3.size() < 3) {
                    top3.add(jogo);
                }
                if (top3.size() > 3) {
                    top3.remove(3);
                }
            }
        }
        return top3;
    }


    /**
     * Calculates points earned this game, using the record of questions answered correctly in respostasCorretas
     * @param respostasCorretas ArrayList of questions answered correctly
     * @return value of points earned
     */
    public int calculatePoints(ArrayList<Question> respostasCorretas) {
        int classificacao = 0;
        for (Question respostasCorreta : respostasCorretas) {
            classificacao += respostasCorreta.getPoints();
        }
        return classificacao;
    }


    /**
     * Load in 5 questions from file "perguntas.txt"
     * @return ArrayList of 5 random questions from file
     */
    private ArrayList<Question> LoadQuestions() {
        ArrayList<Question> questionsList = new ArrayList<Question>();
        File questionsFile = new File("perguntas.txt");

        try{
            FileReader fileReader = new FileReader(questionsFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            ArrayList<String> optionsList;
            ArrayList<String> Lines = new ArrayList<String>();
            String newLine = bufferedReader.readLine();
            while(newLine != null) {
                Lines.add(newLine);
                newLine = bufferedReader.readLine();
            }
            Collections.shuffle(Lines);

            for (int i = 0; i< 5 && i<(int)Lines.size(); i++) {
                String[] content = Lines.get(i).split("/");
                if(!checkFileQuestions(content)){
                    throw new Exception("Ficheiro Mal Escrito!");
                }
                switch (content[0]) {
                    case "arte" -> {
                        Q_Art artQuestion = new Q_Art(
                                content[1],
                                new ArrayList<String> (Arrays.asList(content[2].split(";"))),
                                Arrays.asList(content[2].split(";")).get(0),
                                Integer.parseInt(content[3]),
                                i+1);
                        questionsList.add(artQuestion);
                    }
                    case "ciencia" -> {
                        Q_Science scienceQuestion = new Q_Science(
                                content[1],
                                new ArrayList<String> (Arrays.asList(content[2].split(";"))),
                                new ArrayList<String> (Arrays.asList(content[3].split(";"))),
                                Arrays.asList(content[2].split(";")).get(0),
                                Integer.parseInt(content[4]),
                                i+1);
                        questionsList.add(scienceQuestion);
                    }
                    case "futebol" -> {
                        ArrayList<String> namesOptionList = new ArrayList<String>();
                        ArrayList<String> numbsOptionList = new ArrayList<String>();
                        ArrayList<String> aux = new ArrayList<String>(Arrays.asList(content[2].split(";")));
                        for (String options : aux) {
                            namesOptionList.add( (options.split("_"))[0] );
                            numbsOptionList.add( (options.split("_"))[1] );
                        }
                        Q_S_Football footballQuestion = new Q_S_Football(
                                content[1],
                                namesOptionList,
                                numbsOptionList,
                                namesOptionList.get(0),
                                numbsOptionList.get(0),
                                Integer.parseInt(content[3]),
                                i+1);
                        questionsList.add(footballQuestion);
                    }
                    case "ski" -> {
                        Q_S_Ski skiQuestion = new Q_S_Ski(
                                content[1],
                                content[2],
                                Integer.parseInt(content[3]),
                                i+1);
                        questionsList.add(skiQuestion);
                    }
                    case "natacao" -> {
                        Q_S_Swim swimmingQuestion = new Q_S_Swim(
                                content[1],
                                content[2],
                                Integer.parseInt(content[3]),
                                i+1);
                        questionsList.add(swimmingQuestion);
                    }
                }
                questionsList.get(i).setRightMode();
            }
            bufferedReader.close();
        } catch(java.io.FileNotFoundException exception) {
            System.out.printf("File \"%s\" not found! :/\n", "perguntas.txt");
        } catch (java.io.IOException exception) {
            System.out.printf("Could not write to \"%s\"! :/\n", "perguntas.txt");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return questionsList;
    }



    /**
     * Create game file object, with this game, with "filepath" name
     * @param filePath String of game file object path
     */
    public void creatFileObject(String filePath) {
        File f = new File(filePath);

        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Erro a criar ficheiro.");
        } catch (IOException ex) {
            System.out.println("Erro a escrever para o ficheiro.");
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

    /**
     * Creates new game file's name, and lists it in "ficheirosObjetos.txt"
     * @return String name of game file
     */
    public String creatFileObjectName() {
        String nomeFicheiro = "pootrivia_jogo_";
        if (!(this.name.isEmpty())) {
            String[] nomeSplit = this.name.split(" ");
            String[] dataSplit = this.date.split("-");
            String[] horaSplit = this.time.split(":");

            nomeFicheiro += dataSplit[0] + dataSplit[1] + dataSplit[2] + horaSplit[0] + horaSplit[1] + "_";
            if (!(nomeSplit.length == 1)) {
                for (String s : nomeSplit) {
                    String aux = s.toUpperCase();
                    nomeFicheiro += aux.charAt(0);
                }
            } else {
                String aux = nomeSplit[0].toUpperCase();
                nomeFicheiro += aux.charAt(0);
            }
        } else {
            throw new RuntimeException("NOME INEXISTENTE!");
        }

        nomeFicheiro += ".obj";

        File f = new File("ficheirosObjetos.txt");
        try {
            FileWriter fw = new FileWriter(f, true);
            BufferedWriter bw = new BufferedWriter(fw);
            if (f.length() != 0) {
                bw.newLine();
            }
            bw.write(nomeFicheiro);
            bw.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Erro a abrir ficheiro de texto.");
        } catch (IOException ex) {
            System.out.println("Erro a ler ficheiro de texto.");
        }
        return nomeFicheiro;
    }

    /**
     * Loads in Game file from "filename"
     * @param fileName filepath to game file that is being read
     * @return Game loaded from file
     */
    public Game readFileObject(String fileName) {
        File f = new File(fileName);
        try {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Game test = (Game) ois.readObject();
            ois.close();
            return test;
        } catch (FileNotFoundException ex) {
            System.out.println("Erro a abrir ficheiro.");
        } catch (IOException ex) {
            System.out.println("Erro a ler ficheiro.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro a converter objeto.");
        }
        return new Game();
    }

    /**
     * Loads in all Game files that exist in "ficheirosObjetos.txt"
     * @return ArrayList with all the games that were on files listed in "ficheirosObjetos.txt"
     */
    private ArrayList<Game> readNameOfFileObjects() {      // lê os nomes dos ficheiros de objetos que está guardado no ficheiro de text
        ArrayList<Game> jogos = new ArrayList<>();
        File f = new File("ficheirosObjetos.txt");   // e guardar dentro dum ArrayList

        if (f.exists() && f.isFile()) {
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    Game aux=readFileObject(line);
                    jogos.add(aux);
                }
                br.close();
                return jogos;
            } catch (FileNotFoundException ex) {
                System.out.println("Erro a abrir ficheiro de texto.");
            } catch (IOException ex) {
                System.out.println("Erro a ler ficheiro de texto.");
            }
        } else {
            System.out.println("Ficheiro não existe.");
        }
        return jogos;
    }


    /**
     * Checks if the question file is legal, and not corrupted
     * @param content Array of Strings, each is a line of the question file
     * @return boolean for if the file is legal or not
     */
    private boolean checkFileQuestions(String[] content){
        boolean check = true;
        switch(content[0]){
            case "arte", "futebol" -> {
                if(content.length != 4){
                    check = false;
                    System.out.print("arte ou futebol");
                } else if (content[2].split(";").length < 5 || content[2].split(";").length > 10){
                    check = false;
                    System.out.print("arte ou futebol");
                }

            }
            case "ciencia" ->{
                if(content.length != 5){
                    check = false;
                    System.out.println("ciencia");
                } else if (content[3].split(";").length < 5 || content[3].split(";").length > 10){
                    check = false;
                    System.out.println("ciencia");
                }

            }
            case "ski","natacao"  ->{
                if(content.length != 4){
                    check = false;
                    System.out.println("ski ou natacao");
                } else if (!(content[2].equals("Falso") || content[2].equals("Verdadeiro"))){
                    check = false;
                    System.out.println("ski ou natacao");
                }

            }
        }
        return check;
    }

    /**
     * Get string with all questions' information
     * @return String with all questions "toString()"
     */
    public String toString() {
        String out = "All questions:\n\n";
        for (Question question : this.questionsList) {
            out = out.concat( String.format("%s\n\n", question.toString() ) );
        }
        return out;
    }
}
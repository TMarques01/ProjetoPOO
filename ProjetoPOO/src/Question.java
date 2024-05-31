import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Abstract Class that holds most relevant characteristics of a question (description, options, correct option, points and index)
 */
abstract public class Question implements Serializable {

    /**
     * Actual text of the question
     */
    private final String description;

    /**
     * List of options for the question
     */
    private ArrayList<String> optionList;

    /**
     * Correct option for the question (should be included in this.optionList)
     */
    private String correctOption;

    /**
     * Value of point this question is worth
     */
    private int points;

    /**
     * Position of this question in the order of the game
     */
    private int number;


    /**
     * Constructor for Question object
     * @param description String for actual text of the question
     * @param optionList ArrayList of all Options to the question (including the correct one)
     * @param correctOption String for correct option of question
     * @param points value of points of this question
     * @param number position of this question in the game
     */
    public Question(String description, ArrayList<String> optionList, String correctOption, int points, int number) {
        this.description = description;
        this.optionList = optionList;
        this.correctOption = correctOption;
        this.points = points;
        this.number = number;

    }

    /**
     * Get description (text of the question)
     * @return this.description
     */
    public String getDescription() { return this.description; }

    /**
     * Get position of the question
     * @return this.number
     */
    public int getNumber() { return this.number;}

    /**
     * Get copy of list of all options
     * @return copy of this.optionList
     */
    public ArrayList<String> getOptionList() {return new ArrayList<>(this.optionList);} // Retorna uma cópia da lista para evitar modificação externa

    /**
     * Get correct option
     * @return this.correctOption
     */
    public String getCorrectOption() {return this.correctOption;}

    /**
     * Get points this question is worth
     * @return this.points
     */
    public int getPoints() { return this.points; }


    /**
     * Protected set of the optionList
     * @param newOptionList new optionList
     */
    protected void setOptionList(ArrayList<String> newOptionList) { this.optionList = new ArrayList<String>(newOptionList); }

    /**
     * Protected set of correct option
     * @param newCorrectOption new correctOption
     */
    protected void setCorrectOption(String newCorrectOption){ this.correctOption = newCorrectOption; }




    /**
     * Abstract method to add to the game files
     * @return String of the type of question, and the number
     */
    public abstract String addOpcaoCorreta();

    /**
     * Set question to easyMode (depends on subclass)
     */
    public abstract void setEasyMode();

    /**
     * Set question to hardMode (depends on subclass)
     */
    public abstract void setHardMode();

    /**
     * Set question to the right Mode (easy or hard)
     */
    public void setRightMode() {
        if(this.number<3){
            // System.out.printf("setting easy mode for \"%s\"\n", this.description);
            this.setEasyMode();
        } else {
            // System.out.printf("setting hard mode for %s\n", this.description);
            this.setHardMode();
        }
    }


    /**
     * Shuffles the optionList randomly
     */
    public void shuffleOptionList() { Collections.shuffle(this.optionList); }

    /**
     * Checks if an option is the correct one
     * @param opcaoEscolhida String option chosen
     * @return boolean of if chosen option is correct
     */
    public boolean checkOption(String opcaoEscolhida) {return this.correctOption.equals(opcaoEscolhida);}


    /**
     * Convert Question to String, displaying number, points worth, description, correct option, and listing all options
     * @return String version of Question
     */
    public String toString() {
        String out = "";
        out = String.format("%d.-\npoints: %s\ndescription: %s\ncorrectoption: %s\noptions:\n", this.number, this.points, this.description, this.correctOption);
        for (String option : this.optionList) {
            out += "   → ";
            out += option;
            out += "\n";
        }
        return out;
    }
}
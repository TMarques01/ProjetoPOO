import java.io.Serializable;
import java.util.ArrayList;

/**
 * Abstract subclass for abstract class Question
 */
abstract class Q_Sport extends Question implements Serializable {

    /**
     * Constructor for Q_Science
     * @param description actual text for Question
     * @param optionList options list
     * @param correctOption correct option
     * @param points amount of points this question is worth without question type modifiers
     * @param number position of this question in the game
     */
    public Q_Sport(String description, ArrayList<String> optionList, String correctOption, int points, int number) {
        super(description, new ArrayList<String>(optionList), correctOption, points+3, number);
    }

    /**
     * Abstract method to add to the game files
     * @return String of the type of question, and the number
     */
    public abstract String addOpcaoCorreta();
}

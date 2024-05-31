import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Subclass of abstract subclass Q_Sport, that only takes in as optionList the arrayList ("Verdadeiro", "Falso")
 */
public class Q_S_Ski extends Q_Sport implements Serializable {

    /**
     * Constructor for Q_S_Ski
     * @param description  actual text for Question
     * @param correctOption correct option ("Verdadeiro"/"Falso")
     * @param points amount of points this question is worth without question type modifiers
     * @param number position of this question in the game
     */
    public Q_S_Ski(String description, String correctOption, int points, int number) {
        super(description, new ArrayList<String>( Arrays.asList("Verdadeiro", "Falso")), correctOption, points*2, number);
    }



    /**
     * Implementation of abstract method to add to the game files
     * @return String of the type of question, and the number
     */
    public String addOpcaoCorreta(){return "Ski/"+super.getNumber();}


    /**
     * Set question to hardMode (Does nothing on subclass Q_S_Ski)
     */
    public void setEasyMode() {}

    /**
     * Set question to hardMode (Does nothing on subclass Q_S_Ski)
     */
    public void setHardMode() {}
}

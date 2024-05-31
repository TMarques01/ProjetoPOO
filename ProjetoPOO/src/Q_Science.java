import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * SubClass for abstract class Question, that holds question type specific information (easyOptionList hardOptionList)
 */
public class Q_Science extends Question implements Serializable {

    /**
     * ArrayList of the easy version of the Question
     */
    private ArrayList<String> easyOptionList;

    /**
     * ArrayList of the hard version of the Question
     */
    private ArrayList<String> hardOptionList;


    /**
     * Constructor for Q_Science
     * @param description actual text for Question
     * @param easyOptionList easy options list
     * @param hardOptionList hard options list
     * @param correctOption correct option (same for both OptionList's)
     * @param points amount of points this question is worth without question type modifiers
     * @param number position of this question in the game
     */
    public Q_Science(String description, ArrayList<String> easyOptionList, ArrayList<String> hardOptionList, String correctOption, int points, int number) {
        super(description, new ArrayList<String>(easyOptionList), correctOption, points+5, number);
        this.easyOptionList = easyOptionList;
        this.hardOptionList = hardOptionList;

        super.shuffleOptionList();      // baralhar a lista
    }


    /**
     * Implementation of abstract method to add to the game files
     * @return String of the type of question, and the number
     */
    public String addOpcaoCorreta(){return "Ciencia/"+super.getNumber();}


    /**
     * Set question to hardMode (sets super.optionList to this.easyOptionList)
     */
    public void setEasyMode() { super.setOptionList(this.easyOptionList); }

    /**
     * Set question to hardMode (sets super.optionList to this.hardOptionList)
     */
    public void setHardMode() { super.setOptionList(this.hardOptionList); }
}

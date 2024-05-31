import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * SubClass for abstract class Question, that holds question type specific information (fullOptionList)
 */
public class Q_Art extends Question implements Serializable {

    /**
     * ArrayList that holds all options.
     * Because in Q_Art's easy mode we have to set Question's optionList to a sublist of all options, we need to make
     * sure we don't lose that information in case we want to revert to hard mode, so a full copy o all Options is
     * stored
     */
    private ArrayList<String> fullOptionList;  // com todas as opções


    /**
     * Constructor for Q_Art
     * @param description actual text for Question
     * @param optionList full option list
     * @param correctOption correct option
     * @param points amount of points this question is worth without question type modifiers
     * @param number position of this question in the game
     */
    public Q_Art(String description, ArrayList<String> optionList, String correctOption, int points, int number) {
        super(description, new ArrayList<String>(optionList), correctOption, points*10, number);
        this.fullOptionList = optionList;

        super.shuffleOptionList();      // baralhar a lista
    }

    /**
     * Implementation of abstract method to add to the game files
     * @return String of the type of question, and the number
     */
    public String addOpcaoCorreta(){return "Arte/"+super.getNumber();}


    /**
     * Set question to hardMode (sets super.optionList to 2 random elements of this.fullOptionList + the correctOption)
     */
    public void setEasyMode() {
        ArrayList<String> newOptionList = new ArrayList<>(this.fullOptionList);

        newOptionList.remove(0);
        Collections.shuffle(newOptionList);                                     // } get two random options
        newOptionList = new ArrayList<String>( newOptionList.subList(0,2) );    // } (excluding the correct one)
        newOptionList.add( getCorrectOption() );   // add back the correct option
        setOptionList(newOptionList);           // set newOptionList
    }

    /**
     * Set question to hardMode (sets super.optionList to this.fullOptionList)
     */
    public void setHardMode() { setOptionList(this.fullOptionList); }
}


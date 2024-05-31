import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Subclass of abstract subclass Q_Sport, has two types of optionList and two correctOptions
 */
public class Q_S_Football extends Q_Sport implements Serializable {

    /**
     * ArrayList of the names version of the Question
     */
    private ArrayList<String> namesOptionList = new ArrayList<String>();

    /**
     * ArrayList of the numbs version of the Question
     */
    private ArrayList<String> numbsOptionList = new ArrayList<String>();

    /**
     * String correct option for names version of the Question
     */
    private String namesCorrectOption;

    /**
     * String correct option for numbs version of the Question
     */
    private String numbsCorrectOption;

    /**
     * Constructor for Q_S_Football
     * @param description  actual text for Question
     * @param namesOptionList name option list
     * @param numbsOptionList numb option list
     * @param namesCorrectOption name correct option
     * @param numbsCorrectOption numb correct option
     * @param points amount of points this question is worth without question type modifiers
     * @param number position of this question in the game
     */
    public Q_S_Football(String description, ArrayList<String> namesOptionList, ArrayList<String> numbsOptionList, String namesCorrectOption, String numbsCorrectOption, int points, int number) {
        super(description, namesOptionList, namesCorrectOption, points+1, number);
        this.namesOptionList = namesOptionList;
        this.numbsOptionList = numbsOptionList;
        this.namesCorrectOption = namesCorrectOption;
        this.numbsCorrectOption = numbsCorrectOption;

        super.shuffleOptionList();      // baralhar a lista
    }



    /**
     * Implementation of abstract method to add to the game files
     * @return String of the type of question, and the number
     */
    public String addOpcaoCorreta(){return "Futebol/"+super.getNumber();}


    /**
     * Set question to hardMode (Does nothing on subclass Q_S_Ski)
     */
    public void setEasyMode() {
        super.setOptionList( this.namesOptionList );
        super.setCorrectOption( this.namesCorrectOption );
    }

    /**
     * Set question to hardMode (Does nothing on subclass Q_S_Ski)
     */
    public void setHardMode() {
        super.setOptionList( this.numbsOptionList );
        super.setCorrectOption( this.numbsCorrectOption );
    }
}
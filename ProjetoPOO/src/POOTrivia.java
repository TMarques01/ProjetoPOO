/**
 * Main Class that handles all the game
 */
public class POOTrivia {

    /**
     * Main Method that handles the entire game
     * @param args nothing, no initial arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
        GUI gui = new GUI(game);

        // System.out.printf("%s", game.toString() );      // Print game to console
    }
}
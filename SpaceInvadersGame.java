import javax.swing.*;

public class SpaceInvadersGame extends JFrame {
    
    // Constructor for the SpaceInvadersGame class
    public SpaceInvadersGame() {
        // Setting the title of the JFrame
        setTitle("Space Invaders");
        
        // Setting the default close operation to exit the program when the JFrame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Making the JFrame non-resizable
        setResizable(false);
        
        // Setting the size of the JFrame
        setSize(600, 600);

        // Creating an instance of the GameScreen class, which presumably contains the game logic and graphics
        GameScreen gameScreen = new GameScreen();
         
        // Adding the GameScreen instance to the JFrame
        add(gameScreen);

        // Making the JFrame visible
        setVisible(true);
    }

    // The main method, the entry point of the program
    public static void main(String[] args) {
        // Using SwingUtilities to ensure that GUI components are created and modified on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new SpaceInvadersGame());
    }
}

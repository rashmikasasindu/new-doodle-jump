import javax.swing.*;

/**
 * Main game class that sets up and runs the Doodle Jump game
 * Demonstrates ENCAPSULATION - private game setup and management
 * Demonstrates ABSTRACTION - provides high-level game interface
 */
public class DoodleJumpGame extends JFrame {
    private GameEngine gameEngine;
    private GamePanel gamePanel;
    private InputHandler inputHandler;
    
    /**
     * Constructor for DoodleJumpGame
     */
    public DoodleJumpGame() {
        initializeGame();
        setupWindow();
        setupInput();
    }
    
    /**
     * Initialize the game components
     * Demonstrates ENCAPSULATION - private initialization logic
     */
    private void initializeGame() {
        // Create game engine
        gameEngine = new GameEngine();
        
        // Create game panel
        gamePanel = new GamePanel(gameEngine);
        
        // Create input handler
        inputHandler = new InputHandler(gameEngine, gamePanel);
    }
    
    /**
     * Set up the game window
     */
    private void setupWindow() {
        setTitle("Doodle Jump - Java Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Add game panel to window
        add(gamePanel);
        
        // Pack and center window
        pack();
        setLocationRelativeTo(null);
        
        // Make window visible
        setVisible(true);
    }
    
    /**
     * Set up input handling
     */
    private void setupInput() {
        gamePanel.addKeyListener(inputHandler);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
    }
    
    /**
     * Start the game
     */
    public void start() {
        // Show the main menu initially
        gamePanel.showMainMenu();
    }
    
    /**
     * Main method to run the game
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Use default look and feel
        
        // Create and start the game on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            DoodleJumpGame game = new DoodleJumpGame();
            game.start();
        });
    }
}

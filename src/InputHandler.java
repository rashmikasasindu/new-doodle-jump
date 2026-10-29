import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Input handler for keyboard events
 * Demonstrates ENCAPSULATION - private input handling logic
 * Demonstrates ABSTRACTION - provides clean interface for input handling
 */
public class InputHandler extends KeyAdapter {
    private GameEngine gameEngine;
    private GamePanel gamePanel;
    
    /**
     * Constructor for InputHandler
     * @param gameEngine The game engine to control
     * @param gamePanel The game panel for focus management
     */
    public InputHandler(GameEngine gameEngine, GamePanel gamePanel) {
        this.gameEngine = gameEngine;
        this.gamePanel = gamePanel;
    }
    
    /**
     * Handle key pressed events
     * @param e KeyEvent object
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        switch (gameEngine.getGameState()) {
            case MAIN_MENU:
                // No keyboard input handling for main menu (buttons handle input)
                break;
            case MENU:
                handleMenuInput(keyCode);
                break;
            case PLAYING:
                handleGameInput(keyCode);
                break;
            case PAUSED:
                handlePauseInput(keyCode);
                break;
            case GAME_OVER:
                handleGameOverInput(keyCode);
                break;
            case RESTARTING:
                // No input handling during restart
                break;
        }
    }
    
    /**
     * Handle key released events
     * @param e KeyEvent object
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        // Stop horizontal movement when keys are released
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A ||
            keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            gameEngine.getDoodler().stopHorizontalMovement();
        }
    }
    
    /**
     * Handle input during menu state
     * @param keyCode The key code pressed
     */
    private void handleMenuInput(int keyCode) {
        if (keyCode == KeyEvent.VK_SPACE) {
            gameEngine.startGame();
            gamePanel.startGame();
            gamePanel.requestFocus();
        }
    }
    
    /**
     * Handle input during game state
     * @param keyCode The key code pressed
     */
    private void handleGameInput(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                gameEngine.getDoodler().handleInput(keyCode);
                break;
            case KeyEvent.VK_P:
                gameEngine.pauseGame();
                gamePanel.stopGame();
                break;
            case KeyEvent.VK_ESCAPE:
                gameEngine.pauseGame();
                gamePanel.stopGame();
                break;
        }
    }
    
    /**
     * Handle input during pause state
     * @param keyCode The key code pressed
     */
    private void handlePauseInput(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_P:
            case KeyEvent.VK_ESCAPE:
                gameEngine.resumeGame();
                gamePanel.startGame();
                gamePanel.requestFocus();
                break;
        }
    }
    
    /**
     * Handle input during game over state
     * @param keyCode The key code pressed
     */
    private void handleGameOverInput(int keyCode) {
        if (keyCode == KeyEvent.VK_SPACE) {
            gameEngine.restartGame();
            gamePanel.startGame();
            gamePanel.requestFocus();
        }
    }
}

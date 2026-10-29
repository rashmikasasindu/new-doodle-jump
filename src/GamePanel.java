import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Game panel that handles rendering and user input
 * Demonstrates INHERITANCE - extends JPanel
 * Demonstrates ENCAPSULATION - private rendering logic
 */
public class GamePanel extends JPanel implements ActionListener, MenuPanel.MenuActionListener {
    private GameEngine gameEngine;
    private Timer gameTimer;
    private Font gameFont;
    private BufferedImage backgroundImage;
    private MenuPanel menuPanel;
    
    /**
     * Constructor for GamePanel
     * @param gameEngine The game engine to render
     */
    public GamePanel(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.gameFont = new Font("Arial", Font.BOLD, 16);
        
        // Load background image
        loadBackgroundImage();
        
        // Create menu panel
        menuPanel = new MenuPanel(this);
        
        // Set up the panel
        setPreferredSize(new Dimension(GameConstants.BOARD_WIDTH, GameConstants.BOARD_HEIGHT));
        setBackground(GameConstants.BACKGROUND_COLOR);
        setFocusable(true);
        setLayout(new BorderLayout());
        
        // Set up game timer
        gameTimer = new Timer(GameConstants.GAME_LOOP_DELAY, this);
    }
    
    /**
     * Load the background image
     */
    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(new File("Screenshot 2025-09-15 202918.png"));
        } catch (IOException e) {
            System.err.println("Could not load background image: " + e.getMessage());
            backgroundImage = null;
        }
    }
    
    /**
     * Start the game timer
     */
    public void startGame() {
        gameTimer.start();
    }
    
    /**
     * Stop the game timer
     */
    public void stopGame() {
        gameTimer.stop();
    }
    
    /**
     * Paint the game components
     * @param g Graphics object for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Enable anti-aliasing for smoother graphics
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw based on game state
        switch (gameEngine.getGameState()) {
            case MAIN_MENU:
                // Menu panel will handle its own drawing
                break;
            case MENU:
                drawMenu(g2d);
                break;
            case PLAYING:
            case PAUSED:
                // Draw background image if available
                if (backgroundImage != null) {
                    g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                }
                drawGame(g2d);
                if (gameEngine.getGameState() == GameState.PAUSED) {
                    drawPauseOverlay(g2d);
                }
                break;
            case GAME_OVER:
                // Draw background image if available
                if (backgroundImage != null) {
                    g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                }
                drawGame(g2d);
                drawGameOverScreen(g2d);
                break;
            case RESTARTING:
                // Draw background image if available
                if (backgroundImage != null) {
                    g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                }
                drawGame(g2d);
                break;
        }
    }
    
    /**
     * Draw the main menu
     * @param g2d Graphics2D object for drawing
     */
    private void drawMenu(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        
        String title = "Doodle Jump";
        FontMetrics fm = g2d.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        int titleX = (getWidth() - titleWidth) / 2;
        int titleY = getHeight() / 3;
        
        g2d.drawString(title, titleX, titleY);
        
        g2d.setFont(gameFont);
        String instruction = "Press SPACE to Start";
        fm = g2d.getFontMetrics();
        int instructionWidth = fm.stringWidth(instruction);
        int instructionX = (getWidth() - instructionWidth) / 2;
        int instructionY = titleY + 50;
        
        g2d.drawString(instruction, instructionX, instructionY);
        
        String controls = "Use A/D or Arrow Keys to Move";
        fm = g2d.getFontMetrics();
        int controlsWidth = fm.stringWidth(controls);
        int controlsX = (getWidth() - controlsWidth) / 2;
        int controlsY = instructionY + 30;
        
        g2d.drawString(controls, controlsX, controlsY);
    }
    
    /**
     * Draw the game elements
     * @param g2d Graphics2D object for drawing
     */
    private void drawGame(Graphics2D g2d) {
        // Draw platforms
        for (Platform platform : gameEngine.getPlatforms()) {
            platform.render(g2d);
        }
        
        // Draw doodler
        gameEngine.getDoodler().render(g2d);
        
        // Draw score
        drawScore(g2d);
    }
    
    /**
     * Draw the score
     * @param g2d Graphics2D object for drawing
     */
    private void drawScore(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(gameFont);
        g2d.drawString("Score: " + gameEngine.getScore(), 10, 25);
    }
    
    /**
     * Draw pause overlay
     * @param g2d Graphics2D object for drawing
     */
    private void drawPauseOverlay(Graphics2D g2d) {
        // Semi-transparent overlay
        g2d.setColor(new Color(0, 0, 0, 128));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Pause text
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        String pauseText = "PAUSED";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(pauseText);
        int textX = (getWidth() - textWidth) / 2;
        int textY = getHeight() / 2;
        
        g2d.drawString(pauseText, textX, textY);
        
        g2d.setFont(gameFont);
        String resumeText = "Press P to Resume";
        fm = g2d.getFontMetrics();
        int resumeWidth = fm.stringWidth(resumeText);
        int resumeX = (getWidth() - resumeWidth) / 2;
        int resumeY = textY + 40;
        
        g2d.drawString(resumeText, resumeX, resumeY);
    }
    
    /**
     * Draw game over screen
     * @param g2d Graphics2D object for drawing
     */
    private void drawGameOverScreen(Graphics2D g2d) {
        // Semi-transparent overlay
        g2d.setColor(new Color(0, 0, 0, 128));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Game over text
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        String gameOverText = "GAME OVER";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(gameOverText);
        int textX = (getWidth() - textWidth) / 2;
        int textY = getHeight() / 2 - 40;
        
        g2d.drawString(gameOverText, textX, textY);
        
        // Final score
        g2d.setFont(gameFont);
        String scoreText = "Final Score: " + gameEngine.getScore();
        fm = g2d.getFontMetrics();
        int scoreWidth = fm.stringWidth(scoreText);
        int scoreX = (getWidth() - scoreWidth) / 2;
        int scoreY = textY + 40;
        
        g2d.drawString(scoreText, scoreX, scoreY);
        
        // Restart instruction
        String restartText = "Press SPACE to Restart";
        fm = g2d.getFontMetrics();
        int restartWidth = fm.stringWidth(restartText);
        int restartX = (getWidth() - restartWidth) / 2;
        int restartY = scoreY + 30;
        
        g2d.drawString(restartText, restartX, restartY);
    }
    
    /**
     * Handle timer events (game loop)
     * @param e ActionEvent from timer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        GameState currentState = gameEngine.getGameState();
        
        // Check if we need to show the main menu
        if (currentState == GameState.MAIN_MENU) {
            showMainMenu();
            return;
        }
        
        gameEngine.update();
        repaint();
    }
    
    // Menu action listener methods
    @Override
    public void onNewGame() {
        removeAll();
        gameEngine.restartGame();
        startGame();
        revalidate();
        repaint();
    }
    
    @Override
    public void onQuit() {
        System.exit(0);
    }
    
    
    /**
     * Show the main menu
     */
    public void showMainMenu() {
        removeAll();
        add(menuPanel, BorderLayout.CENTER);
        stopGame();
        revalidate();
        repaint();
    }
}

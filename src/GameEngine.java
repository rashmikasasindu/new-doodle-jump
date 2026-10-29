import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Game engine that manages the game logic, physics, and state
 * Demonstrates ENCAPSULATION - private game state and controlled access
 * Demonstrates ABSTRACTION - provides high-level game control interface
 */
public class GameEngine {
    private Doodler doodler;
    private List<Platform> platforms;
    private GameState gameState;
    private int score;
    private int maxScore;
    private Random random;
    
    /**
     * Constructor for GameEngine
     */
    public GameEngine() {
        this.random = new Random();
        this.platforms = new ArrayList<>();
        this.gameState = GameState.MAIN_MENU;
        this.score = 0;
        this.maxScore = 0;
        initializeGame();
    }
    
    /**
     * Initialize the game with starting objects
     * Demonstrates ENCAPSULATION - private initialization logic
     */
    private void initializeGame() {
        // Create doodler at starting position
        double doodlerX = GameConstants.BOARD_WIDTH / 3.0 - GameConstants.DOODLER_WIDTH / 1.0;
        double doodlerY = GameConstants.BOARD_HEIGHT * 7 / 8.0 - GameConstants.DOODLER_HEIGHT;
        this.doodler = new Doodler(doodlerX, doodlerY);
        
        // Create initial platforms
        createInitialPlatforms();
    }
    
    /**
     * Create the initial set of platforms
     */
    private void createInitialPlatforms() {
        platforms.clear();
        
        // Starting platform
        Platform startingPlatform = new Platform(
            GameConstants.BOARD_WIDTH / 2.0,
            GameConstants.BOARD_HEIGHT - 50
        );
        platforms.add(startingPlatform);
        
        // Generate random platforms
        for (int i = 0; i < GameConstants.INITIAL_PLATFORM_COUNT; i++) {
            double randomX = random.nextDouble() * GameConstants.BOARD_WIDTH * 3 / 4;
            double y = GameConstants.BOARD_HEIGHT - 75 * i - 150;
            
            // 20% chance for broken platform
            boolean isBroken = random.nextDouble() < 0.2;
            Platform platform = new Platform(randomX, y, isBroken);
            platforms.add(platform);
        }
    }
    
    /**
     * Update the game state
     * Demonstrates ABSTRACTION - high-level update interface
     */
    public void update() {
        if (gameState != GameState.PLAYING) {
            return; // Don't update game logic if not playing
        }
        
        // Update doodler
        doodler.update();
        
        // Check for game over condition
        if (doodler.isFallen()) {
            gameState = GameState.MAIN_MENU; // Go back to main menu instead of game over screen
            return;
        }
        
        // Handle camera scrolling and platform movement
        handleCameraScrolling();
        
        // Check collisions
        checkCollisions();
        
        // Update score
        updateScore();
        
        // Clean up off-screen platforms and add new ones
        managePlatforms();
    }
    
    /**
     * Handle camera scrolling when doodler moves up
     */
    private void handleCameraScrolling() {
        if (doodler.getVelocityY() < 0 && doodler.getY() < GameConstants.CAMERA_SCROLL_THRESHOLD) {
            double scrollAmount = -GameConstants.INITIAL_JUMP_VELOCITY;
            
            // Move all platforms down
            for (Platform platform : platforms) {
                platform.moveVertical(scrollAmount);
            }
        }
    }
    
    /**
     * Check for collisions between doodler and platforms
     */
    private void checkCollisions() {
        for (Platform platform : platforms) {
            if (platform.handleCollision(doodler)) {
                break; // Only one collision per frame
            }
        }
    }
    
    /**
     * Update the score based on doodler's movement
     */
    private void updateScore() {
        int points = random.nextInt(GameConstants.SCORE_MULTIPLIER);
        
        if (doodler.getVelocityY() < 0) { // Going up
            maxScore += points;
            if (score < maxScore) {
                score = maxScore;
            }
        } else if (doodler.getVelocityY() >= 0) { // Going down
            maxScore -= points;
        }
    }
    
    /**
     * Manage platforms - remove off-screen ones and add new ones
     */
    private void managePlatforms() {
        // Remove platforms that are off-screen
        platforms.removeIf(Platform::isOffScreen);
        
        // Add new platforms at the top
        while (platforms.size() < GameConstants.INITIAL_PLATFORM_COUNT + 1) {
            addNewPlatform();
        }
    }
    
    /**
     * Add a new platform at the top of the screen
     */
    private void addNewPlatform() {
        double randomX = random.nextDouble() * GameConstants.BOARD_WIDTH * 3 / 4;
        boolean isBroken = random.nextDouble() < 0.2; // 20% chance for broken platform
        
        Platform newPlatform = new Platform(randomX, GameConstants.PLATFORM_SPAWN_HEIGHT, isBroken);
        platforms.add(newPlatform);
    }
    
    /**
     * Start the game
     */
    public void startGame() {
        gameState = GameState.PLAYING;
    }
    
    /**
     * Restart the game
     */
    public void restartGame() {
        gameState = GameState.RESTARTING;
        score = 0;
        maxScore = 0;
        initializeGame();
        gameState = GameState.PLAYING;
    }
    
    /**
     * Pause the game
     */
    public void pauseGame() {
        if (gameState == GameState.PLAYING) {
            gameState = GameState.PAUSED;
        }
    }
    
    /**
     * Resume the game
     */
    public void resumeGame() {
        if (gameState == GameState.PAUSED) {
            gameState = GameState.PLAYING;
        }
    }
    
    // Getters - Demonstrates ENCAPSULATION
    public Doodler getDoodler() { return doodler; }
    public List<Platform> getPlatforms() { return new ArrayList<>(platforms); }
    public GameState getGameState() { return gameState; }
    public int getScore() { return score; }
}



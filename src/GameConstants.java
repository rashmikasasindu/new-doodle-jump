/**
 * Constants class for game configuration
 * Demonstrates ENCAPSULATION - centralized configuration
 */
public class GameConstants {
    // Board dimensions
    public static final int BOARD_WIDTH = 360;
    public static final int BOARD_HEIGHT = 576;
    
    // Doodler properties
    public static final int DOODLER_WIDTH = 46;
    public static final int DOODLER_HEIGHT = 46;
    
    // Platform properties
    public static final int PLATFORM_WIDTH = 60;
    public static final int PLATFORM_HEIGHT = 18;
    
    // Game timing
    public static final int GAME_LOOP_DELAY = 16; // ~60 FPS
    
    // Physics constants
    public static final double GRAVITY = 0.4;
    public static final double INITIAL_JUMP_VELOCITY = -8.0;
    public static final double MAX_HORIZONTAL_VELOCITY = 4.0;
    
    // Game rules
    public static final int INITIAL_PLATFORM_COUNT = 6;
    public static final double PLATFORM_SPAWN_HEIGHT = -PLATFORM_HEIGHT;
    public static final double CAMERA_SCROLL_THRESHOLD = BOARD_HEIGHT * 3.0 / 4.0;
    
    // Scoring
    public static final int SCORE_MULTIPLIER = 50;
    
    // Colors
    public static final java.awt.Color BACKGROUND_COLOR = new java.awt.Color(135, 206, 235); // Sky blue
    public static final java.awt.Color DOODLER_COLOR = new java.awt.Color(0, 128, 0); // Green
    public static final java.awt.Color PLATFORM_COLOR = new java.awt.Color(0, 0, 255); // Blue
    public static final java.awt.Color BROKEN_PLATFORM_COLOR = new java.awt.Color(255, 0, 0); // Red
    public static final java.awt.Color TEXT_COLOR = new java.awt.Color(0, 0, 0); // Black
}



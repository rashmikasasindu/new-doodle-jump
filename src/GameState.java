/**
 * Enum representing different game states
 * Demonstrates ENCAPSULATION - controlled state management
 */
public enum GameState {
    MAIN_MENU, // Main menu with buttons
    MENU,      // Game menu/start screen (legacy)
    PLAYING,   // Game is actively running
    PAUSED,    // Game is paused
    GAME_OVER, // Game has ended
    RESTARTING // Game is being restarted
}



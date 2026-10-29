import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Doodler class representing the main character
 * Demonstrates INHERITANCE - extends GameObject
 * Demonstrates ENCAPSULATION - private fields with controlled access
 */
public class Doodler extends GameObject {
    private double velocityX, velocityY;
    private final double initialVelocityY = -8.0;
    private final double gravity = 0.4;
    private final double maxVelocityX = 4.0;
    private boolean isJumping = false;
    private BufferedImage doodlerImage;
    
    /**
     * Constructor for Doodler
     * @param x Initial X coordinate
     * @param y Initial Y coordinate
     */
    public Doodler(double x, double y) {
        super(x, y, 46, 46, Color.GREEN);
        this.velocityX = 0;
        this.velocityY = initialVelocityY;
        loadDoodlerImage();
    }
    
    /**
     * Load the doodler image
     */
    private void loadDoodlerImage() {
        try {
            doodlerImage = ImageIO.read(new File("doodler.png"));
        } catch (IOException e) {
            System.err.println("Could not load doodler image: " + e.getMessage());
            doodlerImage = null;
        }
    }
    
    /**
     * Update the doodler's position and physics
     * Demonstrates ENCAPSULATION - internal physics calculations
     */
    @Override
    public void update() {
        // Apply gravity
        velocityY += gravity;
        
        // Update position
        x += velocityX;
        y += velocityY;
        
        // Handle screen wrapping
        if (x > GameConstants.BOARD_WIDTH) {
            x = 0;
        } else if (x + width < 0) {
            x = GameConstants.BOARD_WIDTH;
        }
        
        // Check if falling
        if (velocityY > 0) {
            isJumping = false;
        }
    }
    
    /**
     * Render the doodler
     * @param g Graphics2D object for drawing
     */
    @Override
    public void render(Graphics2D g) {
        if (doodlerImage != null) {
            // Create a circular clipping area
            int radius = Math.min(width, height) / 2;
            int centerX = (int)(x + width/2);
            int centerY = (int)(y + height/2);
            
            // Save the current graphics state
            Graphics2D g2d = (Graphics2D) g.create();
            
            // Set circular clipping area
            g2d.setClip(centerX - radius, centerY - radius, radius * 2, radius * 2);
            
            // Draw the image scaled to fit the circular area
            g2d.drawImage(doodlerImage, (int)x, (int)y, width, height, null);
            
            // Restore the graphics state
            g2d.dispose();
        } else {
            // Fallback: draw a simple circle if image fails to load
            g.setColor(color);
            int radius = Math.min(width, height) / 2;
            int centerX = (int)(x + width/2);
            int centerY = (int)(y + height/2);
            g.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        }
    }
    
    /**
     * Handle keyboard input for movement
     * @param keyCode The key code pressed
     */
    public void handleInput(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                velocityX = -maxVelocityX;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                velocityX = maxVelocityX;
                break;
        }
    }
    
    /**
     * Stop horizontal movement
     */
    public void stopHorizontalMovement() {
        velocityX = 0;
    }
    
    /**
     * Make the doodler jump
     */
    public void jump() {
        if (!isJumping) {
            velocityY = initialVelocityY;
            isJumping = true;
        }
    }
    
    /**
     * Check if doodler is falling off the screen
     * @return true if doodler has fallen off the bottom
     */
    public boolean isFallen() {
        return y > GameConstants.BOARD_HEIGHT;
    }
    
    /**
     * Reset doodler to initial position
     */
    public void reset() {
        x = GameConstants.BOARD_WIDTH / 2.0 - width / 2.0;
        y = GameConstants.BOARD_HEIGHT * 7 / 8.0 - height;
        velocityX = 0;
        velocityY = initialVelocityY;
        isJumping = false;
    }
    
    // Getters - Demonstrates ENCAPSULATION
    public double getVelocityX() { return velocityX; }
    public double getVelocityY() { return velocityY; }
}



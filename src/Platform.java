import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Platform class representing platforms the doodler can jump on
 * Demonstrates INHERITANCE - extends GameObject
 * Demonstrates ENCAPSULATION - private fields with controlled access
 */
public class Platform extends GameObject {
    private boolean isBroken;
    private boolean hasBeenLandedOn;
    private BufferedImage platformImage;
    private BufferedImage brokenPlatformImage;
    
    /**
     * Constructor for Platform
     * @param x X coordinate
     * @param y Y coordinate
     * @param isBroken Whether this platform breaks when landed on
     */
    public Platform(double x, double y, boolean isBroken) {
        super(x, y, 60, 18, isBroken ? Color.RED : Color.BLUE);
        this.isBroken = isBroken;
        this.hasBeenLandedOn = false;
        loadPlatformImages();
    }
    
    /**
     * Default constructor for regular platform
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Platform(double x, double y) {
        this(x, y, false);
    }
    
    /**
     * Load platform images
     */
    private void loadPlatformImages() {
        try {
            platformImage = ImageIO.read(new File("platform.png"));
        } catch (IOException e) {
            System.err.println("Could not load platform image: " + e.getMessage());
            platformImage = null;
        }
        
        try {
            brokenPlatformImage = ImageIO.read(new File("platform-broken.png"));
        } catch (IOException e) {
            System.err.println("Could not load broken platform image: " + e.getMessage());
            brokenPlatformImage = null;
        }
    }
    
    /**
     * Update platform state
     */
    @Override
    public void update() {
        // Platforms don't need to update their position in this implementation
        // They are moved by the game engine when the camera scrolls
    }
    
    /**
     * Render the platform
     * @param g Graphics2D object for drawing
     */
    @Override
    public void render(Graphics2D g) {
        if (isBroken && hasBeenLandedOn) {
            // Don't draw broken platforms - they just disappear
            return;
        } else if (isBroken && brokenPlatformImage != null) {
            // Draw broken platform image
            g.drawImage(brokenPlatformImage, (int)x, (int)y, width, height, null);
        } else if (!isBroken && platformImage != null) {
            // Draw regular platform image
            g.drawImage(platformImage, (int)x, (int)y, width, height, null);
        } else {
            // Fallback: draw solid platform if images fail to load
            g.setColor(color);
            g.fillRect((int)x, (int)y, width, height);
        }
    }
    
    /**
     * Handle collision with doodler
     * @param doodler The doodler object
     * @return true if collision occurred and doodler should jump
     */
    public boolean handleCollision(Doodler doodler) {
        if (hasBeenLandedOn && isBroken) {
            return false; // Broken platforms can't be jumped on again
        }
        
        Rectangle doodlerBounds = doodler.getBounds();
        Rectangle platformBounds = getBounds();
        
        if (doodlerBounds.intersects(platformBounds)) {
            // Check if doodler is falling and above the platform
            if (doodler.getVelocityY() >= 0 && 
                doodler.getY() + doodler.getHeight() <= y + height + 5) {
                
                doodler.jump();
                hasBeenLandedOn = true;
                return true;
            }
        }
        return false;
    }
    
    /**
     * Move platform vertically (for camera scrolling)
     * @param deltaY Amount to move vertically
     */
    public void moveVertical(double deltaY) {
        y += deltaY;
    }
    
    /**
     * Check if platform is off screen
     * @return true if platform is below the visible area
     */
    public boolean isOffScreen() {
        return y > GameConstants.BOARD_HEIGHT;
    }
    
    // Getters and Setters - Demonstrates ENCAPSULATION
    public boolean isBroken() { return isBroken; }
    public void setBroken(boolean isBroken) { this.isBroken = isBroken; }
    
    public boolean hasBeenLandedOn() { return hasBeenLandedOn; }
    public void setHasBeenLandedOn(boolean hasBeenLandedOn) { 
        this.hasBeenLandedOn = hasBeenLandedOn; 
    }
}



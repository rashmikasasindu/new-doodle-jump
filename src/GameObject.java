import java.awt.*;

/**
 * Abstract base class for all game objects
 * Demonstrates ABSTRACTION - defines common interface for all game objects
 */
public abstract class GameObject {
    protected double x, y;
    protected int width, height;
    protected Color color;
    
    /**
     * Constructor for GameObject
     * @param x X coordinate
     * @param y Y coordinate
     * @param width Width of the object
     * @param height Height of the object
     * @param color Color of the object
     */
    public GameObject(double x, double y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    
    /**
     * Abstract method for rendering the object
     * Demonstrates ABSTRACTION - each subclass must implement its own rendering
     * @param g Graphics2D object for drawing
     */
    public abstract void render(Graphics2D g);
    
    /**
     * Abstract method for updating the object's state
     * Demonstrates ABSTRACTION - each subclass defines its own update behavior
     */
    public abstract void update();
    
    /**
     * Get the bounding rectangle for collision detection
     * @return Rectangle representing the object's bounds
     */
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }
    
    // Getters and Setters - Demonstrates ENCAPSULATION
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
    
    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }
    
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
    
    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }
}



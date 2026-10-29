import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Menu panel with buttons for New Game, Quit Game, and Game Rules
 */
public class MenuPanel extends JPanel {
    private BufferedImage menuBackground;
    private JButton newGameButton;
    private JButton quitButton;
    private JButton rulesButton;
    private JLabel titleLabel;
    private JTextArea rulesText;
    private JScrollPane rulesScrollPane;
    private boolean showingRules;
    
    // Callback interface for menu actions
    public interface MenuActionListener {
        void onNewGame();
        void onQuit();
    }
    
    private MenuActionListener actionListener;
    
    public MenuPanel(MenuActionListener actionListener) {
        this.actionListener = actionListener;
        this.showingRules = false;
        
        loadMenuBackground();
        setupUI();
    }
    
    private void loadMenuBackground() {
        try {
            menuBackground = ImageIO.read(new File("fornt ground.jpg"));
        } catch (IOException e) {
            System.err.println("Could not load menu background image: " + e.getMessage());
            menuBackground = null;
        }
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(GameConstants.BOARD_WIDTH, GameConstants.BOARD_HEIGHT));
        
        // Create title
        titleLabel = new JLabel("Doodle Jump", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 30, 0));
        
        // Create buttons
        newGameButton = createStyledButton("New Game");
        quitButton = createStyledButton("Quit Game");
        rulesButton = createStyledButton("Game Rules");
        
        // Add button listeners
        newGameButton.addActionListener(e -> {
            if (actionListener != null) {
                actionListener.onNewGame();
            }
        });
        
        quitButton.addActionListener(e -> {
            if (actionListener != null) {
                actionListener.onQuit();
            }
        });
        
        rulesButton.addActionListener(e -> {
            if (showingRules) {
                hideRules();
            } else {
                showRules();
            }
        });
        
        // Create rules text area
        createRulesArea();
        
        // Initially show main menu
        showMainMenu();
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(200, 50));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setFocusPainted(false);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 160, 210));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
        
        return button;
    }
    
    private void createRulesArea() {
        rulesText = new JTextArea();
        rulesText.setFont(new Font("Arial", Font.PLAIN, 14));
        rulesText.setForeground(Color.WHITE);
        rulesText.setBackground(new Color(0, 0, 0, 150));
        rulesText.setEditable(false);
        rulesText.setWrapStyleWord(true);
        rulesText.setLineWrap(true);
        
        String rules = "GAME RULES:\n\n" +
                      "1. Use A/D or Arrow Keys to move left/right\n" +
                      "2. Jump on platforms to go higher\n" +
                      "3. Blue platforms are solid and reusable\n" +
                      "4. Red platforms break after one use\n" +
                      "5. Don't fall off the bottom of the screen\n" +
                      "6. Score points by going higher\n" +
                      "7. Press P or ESC to pause during game\n\n" +
                      "OBJECTIVE:\n" +
                      "Jump as high as possible without falling!";
        
        rulesText.setText(rules);
        
        rulesScrollPane = new JScrollPane(rulesText);
        rulesScrollPane.setPreferredSize(new Dimension(300, 400));
        rulesScrollPane.setBorder(BorderFactory.createTitledBorder("Rules"));
        rulesScrollPane.getViewport().setBackground(new Color(0, 0, 0, 150));
        rulesScrollPane.setVisible(false);
    }
    
    private void showMainMenu() {
        removeAll();
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));
        
        // Add buttons with spacing
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(newGameButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(rulesButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(quitButton);
        buttonPanel.add(Box.createVerticalGlue());
        
        // Center buttons
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rulesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add components to main panel
        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(rulesScrollPane, BorderLayout.EAST);
        
        revalidate();
        repaint();
    }
    
    private void showRules() {
        showingRules = true;
        rulesScrollPane.setVisible(true);
        rulesButton.setText("Hide Rules");
        revalidate();
        repaint();
    }
    
    private void hideRules() {
        showingRules = false;
        rulesScrollPane.setVisible(false);
        rulesButton.setText("Game Rules");
        revalidate();
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw background image
        if (menuBackground != null) {
            g.drawImage(menuBackground, 0, 0, getWidth(), getHeight(), null);
        } else {
            // Fallback background
            g.setColor(new Color(135, 206, 235));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}

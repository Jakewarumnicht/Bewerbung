import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Start Screen View für die Startbildschirm-Ansicht.
 * Zeigt Login- und Registrierungsfelder sowie animiertes Logo an.
 * 
 */

public class StartScreenView extends JPanel {
	
    public JTextField usernameField;  // public für Controller-Zugriff
    public JPasswordField passwordField;  // public für Controller-Zugriff
    private JButton loginButton;
    private JButton registerButton;
    private StartScreenController controller;

    // Start Buttons hinzufügen
    public JButton exit, settings;  // public für Controller-Zugriff
    
    // Label für Logo & Vorbereitung der Animation
    private JLabel plantLabel;
    private int plantX = 180;
    private final int plantY = 100;
    private int dx = 2;
    private Timer timer;
  
    // Bild hinzufügen
  	ImageIcon logoIcon = new ImageIcon(getClass().getResource("logo.png"));
	Image scaled = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	public ImageIcon smallerLogoIcon = new ImageIcon(scaled);  // public für Controller-Zugriff
    
    public StartScreenView() {
    	// Objekt des StartScreenControllers
    	controller = new StartScreenController();
        controller.setView(this);  // View an Controller übergeben
        
        setBackground(new Color(0x28B572));
        setLayout(null);
        
        addStartButtons();
        controller.ButtonListener();  // Controller ruft ButtonListener auf
        addAnimatedLogo(); 
        
        
        // Username
        JLabel userLabel = new JLabel("User name:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(200, 430, 140, 30);
        add(userLabel);
        
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setBounds(350, 440, 150, 30);
        add(usernameField);
        
        
        // Passwort
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(200, 480, 140, 30);
        add(passLabel);
        
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBounds(350, 480, 150, 30);
        add(passwordField);
        
       
        // Login
        loginButton = new JButton("Log In");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(0xFFFFFF));
        loginButton.setBounds(200, 530, 300, 30);
        add(loginButton);
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleLogin();  // Controller aufrufen
            }
        });
        
        
        
        // Register
        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setBackground(new Color(0xDDDDDD));
        registerButton.setBounds(200, 580, 300, 30);
        add(registerButton);
               
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleRegister();  // Controller aufrufen
            }
        });
    }
    
    private void addAnimatedLogo() {
		
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("logo.png"));
        Image logoImage = logoIcon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
        plantLabel = new JLabel(new ImageIcon(logoImage));
        plantLabel.setBounds(plantX, plantY, 340, 340);
        add(plantLabel);
        
        int moovingplant = 100;
        
		timer = new Timer( 80, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        plantX += dx;
		        if (plantX < 150 || plantX > 210) {
		            dx = -dx;
		        }
		        plantLabel.setBounds(plantX, plantY, 340, 340);
		        repaint();
			}
		});
		timer.start();    		
	} 
    
    private void addStartButtons() {
    	// Exit-Button
    	ImageIcon goBackIcon = new ImageIcon(getClass().getResource("exit.png"));
    	exit = new JButton(goBackIcon);
    	Image img2 = goBackIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
    	exit.setIcon(new ImageIcon(img2));	
    	exit.setBounds(10, 10, 40, 40);
    		
    	exit.setContentAreaFilled(false);
    	exit.setBorderPainted(false);
    	exit.setFocusPainted(false);
    	exit.setOpaque(false);
    		
   
        // Settings-Button
        ImageIcon settingsIcon = new ImageIcon(getClass().getResource("settings.png"));
        settings = new JButton(new ImageIcon(settingsIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
        settings.setBackground(Color.WHITE);
    	settings.setBounds(55, 10, 40, 40);
    	
    	settings.setContentAreaFilled(false);
    	settings.setBorderPainted(false);
    	settings.setFocusPainted(false);
    	settings.setOpaque(false);
        
        
        add(exit);
        add(settings);	       
	}
}
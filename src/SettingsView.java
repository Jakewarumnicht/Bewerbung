import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Settings View für die Settings-Ansicht.
 * Zeigt verschiedene Einstellungsmöglichkeiten an.
 * 
 */
public class SettingsView extends JPanel {
	
	public JButton homeButton, exitButton, supportButton, versionInfoButton, changePasswordButton, deletePlantButton;
	public JLabel logo;
	public ImageIcon logoIconCP, smallerLogoIconCP, logoIconDP, smallerLogoIconDP;
	
	
	// View
	public SettingsView() {
		setBackground(new Color(0x28B572));
		setLayout(null);
		
		initializeHomeButton();
		initializeExitButton();
		initializeSettingsLabel();
		initializeChangePasswordButton();
		initializeDeletePlantButton();
		initializeSupportButton();
		initializeVersionInfoButton();
		initializeLogo();
	}
	
	private void initializeHomeButton() {
		ImageIcon homeIcon = new ImageIcon(getClass().getResource("home.png"));
		homeButton = new JButton(homeIcon);
		Image img = homeIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		homeButton.setIcon(new ImageIcon(img));
		homeButton.setBounds(10, 10, 40, 40);
		
		homeButton.setContentAreaFilled(false);
		homeButton.setBorderPainted(false);
		homeButton.setFocusPainted(false);
		homeButton.setOpaque(false);
		
		add(homeButton);
	}
	
	private void initializeExitButton() {
		ImageIcon exitIcon = new ImageIcon(getClass().getResource("exit.png"));
		exitButton = new JButton(exitIcon);
		Image img2 = exitIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		exitButton.setIcon(new ImageIcon(img2));
		exitButton.setBounds(55, 10, 40, 40);
		
		exitButton.setContentAreaFilled(false);
		exitButton.setBorderPainted(false);
		exitButton.setFocusPainted(false);
		exitButton.setOpaque(false);
		exitButton.setToolTipText("Exit Application");
		
		add(exitButton);
	}
	
	private void initializeSettingsLabel() {
		JLabel settingsLabel = new JLabel("Settings");
		settingsLabel.setFont(new Font("Arial", Font.BOLD, 24));
		settingsLabel.setForeground(Color.BLACK);
		settingsLabel.setBounds(70, 15, 200, 200);
		
		add(settingsLabel);
	}
	
	private void initializeChangePasswordButton() {
		changePasswordButton = new JButton("Change Password");
		changePasswordButton.setBounds(60, 170, 150, 30);
		logoIconCP = new ImageIcon(getClass().getResource("logo.png"));
		Image scaledCP = logoIconCP.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		smallerLogoIconCP = new ImageIcon(scaledCP);
		
		add(changePasswordButton);
	}
	
	private void initializeDeletePlantButton() {
		deletePlantButton = new JButton("Delete Plant");
		deletePlantButton.setBounds(60, 220, 150, 30);
		logoIconDP = new ImageIcon(getClass().getResource("logo.png"));
		Image scaledDP = logoIconDP.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		smallerLogoIconDP = new ImageIcon(scaledDP);
		
		add(deletePlantButton);
	}
	
	private void initializeSupportButton() {
		supportButton = new JButton("Support");
		supportButton.setSelected(true);
		supportButton.setBounds(60, 270, 150, 30);
		
		add(supportButton);
	}
	
	private void initializeVersionInfoButton() {
		versionInfoButton = new JButton("Version");
		versionInfoButton.setSelected(true);
		versionInfoButton.setBounds(60, 320, 150, 30);
		
		add(versionInfoButton);
	}
	
	private void initializeLogo() {
		ImageIcon logoIcon = new ImageIcon(getClass().getResource("logo.png"));
		logo = new JLabel(logoIcon);
		Image img3 = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		logo.setIcon(new ImageIcon(img3));
		logo.setBounds(550, 550, 150, 150);
		
		add(logo);
	}
}

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * AddPlantView für die Ansicht zum Hinzufügen neuer Pflanzen.
 * Zeigt Kategorien zur Auswahl und Eingabefeld für Pflanzenname an.
 */

public class AddPlantView extends JPanel {
	private JButton exit, settings;
	private JButton SunLovingPlants, ShadowLovingPlants, OutdoorPlants, TropicalPlants, saveButton;
	private JLabel plantName, addPlant, choosePlant;

	
	private JLabel selectedCategoryLabel;
	private String selectedCategory = "";

	private JTextField addName;
	private JPanel topPanel, HESButtons;
	private JPanel centerPanel;
	private JPanel bottomPanel;
	
	ImageIcon logoIcon = new ImageIcon(getClass().getResource("logo.png"));
	Image scaled = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	ImageIcon smallerLogoIcon = new ImageIcon(scaled);
			

	public AddPlantView() {

		setBackground(new Color(0x28B572));
		setLayout(null);

		topPanel = new JPanel();
		topPanel.setBackground(new Color(0x28B572));
		topPanel.setLayout(null);
		topPanel.setBounds(0, 0, 700, 120);

		HESButtons = new JPanel();
		HESButtons.setBackground(new Color(0x28B572));
		HESButtons.setLayout(null);
		HESButtons.setBounds(10, 10, 140, 50);

		ImageIcon exiticon = new ImageIcon(getClass().getResource("exit.png"));
		exit = new JButton(new ImageIcon(exiticon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
		exit.setBackground(Color.WHITE);
		exit.setBounds(0, 0, 40, 40);

		exit.setContentAreaFilled(false);
		exit.setBorderPainted(false);
		exit.setFocusPainted(false);
		exit.setOpaque(false);

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		ImageIcon settingsicon = new ImageIcon(getClass().getResource("settings.png"));
		settings = new JButton(new ImageIcon(settingsicon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
		settings.setBackground(Color.WHITE);
		settings.setBounds(45, 0, 40, 40);

		settings.setContentAreaFilled(false);
		settings.setBorderPainted(false);
		settings.setFocusPainted(false);
		settings.setOpaque(false);
		
		
		settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlantMain.switchPanel("Settings");
			}
		});
		
		HESButtons.add(exit);
		HESButtons.add(settings);

		addPlant = new JLabel("Choose your plant category", SwingConstants.CENTER);
		addPlant.setFont(new Font("Arial", Font.BOLD, 25));
		addPlant.setBounds(0, 50, 700, 30);
		addPlant.setForeground(Color.BLACK);

		selectedCategoryLabel = new JLabel("", SwingConstants.CENTER);
		selectedCategoryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		selectedCategoryLabel.setBounds(0, 82, 700, 22);
		selectedCategoryLabel.setForeground(Color.WHITE);

		topPanel.add(HESButtons);
		topPanel.add(addPlant);
		topPanel.add(selectedCategoryLabel);

		centerPanel = new JPanel();
		centerPanel.setBackground(new Color(0x28B572));
		centerPanel.setLayout(null);
		centerPanel.setBounds(0, 120, 700, 480);

		SunLovingPlants = createPlantCategoryButton("Sun Loving Plants", "sunplant.png");
		ShadowLovingPlants = createPlantCategoryButton("Shadow Loving Plants", "shadeplant.png");
		OutdoorPlants = createPlantCategoryButton("Outdoor Plants", "outdoorplant.png");
		TropicalPlants = createPlantCategoryButton("Tropical Plants", "tropicalplant.png");

		SunLovingPlants.setActionCommand("SUN");
		ShadowLovingPlants.setActionCommand("SHADOW");
		OutdoorPlants.setActionCommand("OUTDOOR");
		TropicalPlants.setActionCommand("TROPICAL");


		
		int x1 = 20, x2 = 360;
		int y1 = 10, y2 = 245;
		int w = 320, h = 220;

		SunLovingPlants.setBounds(x1, y1, w, h);
		ShadowLovingPlants.setBounds(x2, y1, w, h);
		OutdoorPlants.setBounds(x1, y2, w, h);
		TropicalPlants.setBounds(x2, y2, w, h);

		centerPanel.add(SunLovingPlants);
		centerPanel.add(ShadowLovingPlants);
		centerPanel.add(OutdoorPlants);
		centerPanel.add(TropicalPlants);

		
		bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(0x28B572));
		bottomPanel.setLayout(null);
		bottomPanel.setBounds(0, 600, 700, 100);

		plantName = new JLabel("Name your plant: ");
		plantName.setFont(new Font("Arial", Font.BOLD, 20));
		plantName.setBounds(140, 30, 180, 30);
		plantName.setForeground(Color.white);

		addName = new JTextField();
		addName.setPreferredSize(new Dimension(200, 50));
		addName.setBounds(330, 25, 200, 40);

		saveButton = new JButton("Save");
		saveButton.setActionCommand("SAVE");
		saveButton.setBounds(545, 32, 80, 28);

		bottomPanel.add(plantName);
		bottomPanel.add(addName);
		bottomPanel.add(saveButton);

		
		add(topPanel);
		add(centerPanel);
		add(bottomPanel);
	}

	
	public void setSelectedCategory(String category) {
		this.selectedCategory = category;
		selectedCategoryLabel.setText("Selected category: " + category);
	}


	public String getPlantName() {
		return addName.getText();
	}

	
	public void clearForm() {
		addName.setText("");
		selectedCategory = "";
		selectedCategoryLabel.setText("");
	}

	private JButton createPlantCategoryButton(String titleText, String imageFileName) {
		JButton button = new JButton();
		button.setLayout(new BorderLayout());
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.setOpaque(false);

		
		JLabel title = new JLabel(titleText, SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 18));
		title.setOpaque(false);
		title.setForeground(Color.WHITE); 
		title.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));

		
		JLabel imgLabel = new JLabel();
		imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

		ImageIcon icon = new ImageIcon(getClass().getResource(imageFileName));
		ImageIcon scaledIcon = scaleIconKeepRatio(icon, 170, 170);
		imgLabel.setIcon(scaledIcon);
		imgLabel.setOpaque(false);

		button.add(title, BorderLayout.NORTH);
		button.add(imgLabel, BorderLayout.CENTER);

		return button;
	}

	private ImageIcon scaleIconKeepRatio(ImageIcon src, int maxW, int maxH) {
		int w = src.getIconWidth();
		int h = src.getIconHeight();

		if (w <= 0 || h <= 0) return src;

		double scale = Math.min((double) maxW / w, (double) maxH / h);
		int newW = (int) Math.round(w * scale);
		int newH = (int) Math.round(h * scale);

		Image scaled = src.getImage().getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		return new ImageIcon(scaled);
	}

	public void showError(String message) {
		JOptionPane.showMessageDialog(
				this,
				message,
				"Error",
				JOptionPane.ERROR_MESSAGE,
				smallerLogoIcon
		);
	}

	public void setController(ActionListener controller) {
		SunLovingPlants.addActionListener(controller);
		ShadowLovingPlants.addActionListener(controller);
		OutdoorPlants.addActionListener(controller);
		TropicalPlants.addActionListener(controller);
		saveButton.addActionListener(controller);
	}
}

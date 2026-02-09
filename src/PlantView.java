import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
/**
 * View-Klasse für die Pflanzenanzeige.
 * Verantwortlich für die grafische Darstellung und Benutzerinteraktion.
 */

public class PlantView extends JPanel {

	//Hintergrund farben -- je nach Zustand
    private static final Color backgroundGreen = new Color(0x28B572);
    private static final Color backgroundGrey = new Color(180, 180, 180);
    private static final Color backgroundWarningRed = new Color(0xD32F2F);

    // Inforbar -- Textfarben
    private static final Color infoTextColor = new Color(60, 60, 60);
    private static final Color infoBarBackground = new Color(255, 255, 255, 220);
    private static final Color infoBarBackgroundWarning = new Color(255, 200, 200, 220);
    private static final Color deadMessageTextColor = new Color(139, 0, 0);

    // Fonts + Textgrößen
    private static final Font infoBarFont = new Font("Arial", Font.PLAIN, 15);
    private static final Font deadMessageFont = new Font("Arial", Font.BOLD, 40);
    private static final Font plantNameFont = new Font("Arial", Font.BOLD, 22);
    private static final Font buttonFont = new Font("Arial", Font.BOLD, 18);

    //Panel größe
    private static final Dimension panelSize = new Dimension(700, 700);

    //Button Größe
    private static final int topIconButtonWidth = 40;
    private static final int topIconButtonHeight = 40;

    //Icon-größe der Buttons
    private static final int iconImageWidth = 35;
    private static final int iconImageHeight = 35;

    //Größe für das anzeigen des Pflanzen Bildes
    private static final int plantImageWidth = 300;
    private static final int plantImageHeight = 300;

    //Buttons & Labels deklarieren
    private JButton exit, settings, careForPlant, addNewPlantButton;
    private JLabel logo, plantNameLabel, plantImageLabel, temperature, soilMoisture, light, infoBar, deadMessageLabel;

    
    //Aufrufen aller Methoden im Konstruktor
    public PlantView() {
        setupPanel();
        headerButtons();
        setupInfoBar();
        initDeadMessage();
        setupLogo();
        setupPlantName();
        setupPlantImage();
        setupSensors();
        setupBottomButtons();
        addAllComponents();
    }

   // Grundkonfiguration des Panels (Look + Größe + absolute Positionierung)
    private void setupPanel() {
        setBackground(backgroundGreen);
        setPreferredSize(panelSize);
        setLayout(null);
    }

    //Obere App-Buttons
    private void headerButtons() {
        exit = createIconButton("exit.png");
        exit.setBounds(10, 10, topIconButtonWidth, topIconButtonHeight);

        settings = createIconButton("settings.png");
        settings.setBounds(55, 10, topIconButtonWidth, topIconButtonHeight);
    }

    private void setupInfoBar() {
        infoBar = new JLabel();
        infoBar.setFont(infoBarFont);
        infoBar.setForeground(infoTextColor);
        infoBar.setOpaque(true); // Damit der Hintergrund der InfoBar (setBackground) gezeichnet wird
        infoBar.setBackground(infoBarBackground);
        infoBar.setBorder(BorderFactory.createCompoundBorder( //Zuständig für Info-Kasten Aufbau
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(6, 14, 6, 14)
        ));
        infoBar.setBounds(120, 70, 400, 45);
        infoBar.setVerticalAlignment(SwingConstants.TOP);

        updateCriticalValues(null); //Keine Pflanzenkategorie ausgewählt --> Anzeige Default-Fall 
    }

    //Nachricht --> Pflanze tot
    private void initDeadMessage() {
        deadMessageLabel = new JLabel("Your plant died 😔", SwingConstants.CENTER);
        deadMessageLabel.setFont(deadMessageFont);
        deadMessageLabel.setForeground(deadMessageTextColor);
        deadMessageLabel.setBounds(40, 270, 620, 90);
        deadMessageLabel.setVisible(false);
    }

    private void setupLogo() {
        ImageIcon logoIcon = loadScaledIcon("logo.png", 100, 100);
   
        //Falls logo-bild nicht gefunden wurde:
        if (logoIcon != null) {
            logo = new JLabel(logoIcon);
        } else {
            logo = new JLabel(new ImageIcon()); //leeres Icon
        }
        logo.setBounds(560, 585, 100, 100);
    }

    private void setupPlantName() {
        plantNameLabel = new JLabel("Your Plant: -");
        plantNameLabel.setFont(plantNameFont);
        plantNameLabel.setForeground(new Color(0xFFF5CC));
        plantNameLabel.setBounds(60, 120, 500, 30);
    }

    private void setupPlantImage() {
        plantImageLabel = new JLabel();
        plantImageLabel.setBounds(200, 160, plantImageWidth, plantImageHeight);

        ImageIcon defaultPlant = loadScaledIcon("yourPlant.png", plantImageWidth, plantImageHeight);
        
        //fall-back falls Pflanzenbild nicht geladen wurde
        if (defaultPlant == null) defaultPlant = loadScaledIcon("tropicalplant.png", plantImageWidth, plantImageHeight); 
        //Bild ggb = Bild anzeigen
        if (defaultPlant != null) plantImageLabel.setIcon(defaultPlant);
    }

    private void setupSensors() {
        temperature = new JLabel();
        soilMoisture = new JLabel();
        light = new JLabel();

        temperature.setBounds(70, 520, 180, 30);
        soilMoisture.setBounds(270, 520, 180, 30);
        light.setBounds(470, 520, 180, 30);

        updateSensorValues(8, 26, 15); //Startwerte die angezeigt werden beim öffnen
    }

    private void setupBottomButtons() {
    	//Pflanze schlechte Werte:
        careForPlant = new JButton("Care for plant");
        careForPlant.setFont(buttonFont);
        careForPlant.setForeground(new Color(0x1D3D67));
        careForPlant.setBounds(230, 580, 240, 45);

        //Pflanze stirbt:
        addNewPlantButton = new JButton("Add new plant");
        addNewPlantButton.setFont(buttonFont);
        addNewPlantButton.setForeground(new Color(0x1D3D67));
        addNewPlantButton.setBounds(230, 580, 240, 45);
        addNewPlantButton.setVisible(false);
    }

    //Fügt alle Komponenten dem Panel hinzu
    private void addAllComponents() {
        add(exit);
        add(settings);
        add(infoBar);
        add(plantNameLabel);
        add(plantImageLabel);
        add(temperature);
        add(soilMoisture);
        add(light);
        add(careForPlant);
        add(addNewPlantButton);
        add(deadMessageLabel);
        add(logo);
    }
     /**
      * Anzeigen der persönlichen Pflanze mit Name und Kategorie
      */
    //Anzeigen persönlicher Pflanze + Name
    public void displayPlant(String plantName, String categoryKey) {
        //Name
    	if (plantName == null) {
            plantNameLabel.setText("Your Plant: -");
        } else {
            plantNameLabel.setText("Your Plant: " + plantName);
        }
        //Bild + Grenzwerte
        setPlantIconByCategory(categoryKey);
        updateCriticalValues(categoryKey);
        refresh();
    }
    /**
    */
    
    //Zustand Pflanze gut
    public void showAliveState(String categoryKey) {
        deadMessageLabel.setVisible(false);
        
        plantImageLabel.setVisible(true);
        temperature.setVisible(true);
        soilMoisture.setVisible(true);
        light.setVisible(true);

        careForPlant.setVisible(true); 
        addNewPlantButton.setVisible(false);

        setWarningState(false);
        setBackground(backgroundGreen);
        updateCriticalValues(categoryKey); //zeigt Critical Values passend zur Kategorie

        refresh();
    }

    //Pflanze tod
    public void showDeadState(String plantName) {
        setBackground(backgroundGrey);
        setWarningState(false);

        deadMessageLabel.setText("Your plant died 😔");
        deadMessageLabel.setVisible(true);

        plantImageLabel.setVisible(false);
        temperature.setVisible(false);
        soilMoisture.setVisible(false);
        light.setVisible(false);

        careForPlant.setVisible(false);
        addNewPlantButton.setVisible(true);

        infoBar.setText("<html><div style='width:320px;'>"
                + "<b>Please add a new plant.</b><br>"
                + "Your previous plant could not survive due to missing care."
                + "</div></html>");

        if (plantName == null) {
            plantNameLabel.setText("Your Plant: -");
        } else {
            plantNameLabel.setText("Your Plant: " + plantName);
        }
        refresh();
    }

    //Wenn keine aktuelle Pflanze vorhanden:
    public void showNoPlantState() {
        setBackground(backgroundGreen);
        deadMessageLabel.setVisible(false);

        plantImageLabel.setVisible(true);
        temperature.setVisible(true);
        soilMoisture.setVisible(true);
        light.setVisible(true);

        careForPlant.setVisible(false);
        addNewPlantButton.setVisible(true);

        plantNameLabel.setText("Your Plant: -");
        updateCriticalValues(null);

        refresh();
    }
  
    /**
     * Aktualisiert die kritischen Grenzwerte in der InfoBar basierend auf der Pflanzenkategorie
     */
    // Setzt die Mindest-Grenzwerte (kritische Werte) je nach Kategorie und zeigt sie formatiert in der InfoBar an
    private void updateCriticalValues(String categoryKey) {
        int tempMin, waterMin, lightMin;
        
        if (categoryKey == null) {
            tempMin = 10; waterMin = 30; lightMin = 20;
        } else {
            switch (categoryKey.toLowerCase()) {
                case "sun":
                    tempMin = 8;  waterMin = 20; lightMin = 40; break;
                case "shade":
                    tempMin = 6;  waterMin = 25; lightMin = 15; break;
                case "outdoor":
                    tempMin = 5;  waterMin = 25; lightMin = 20; break;
                case "tropical":
                default:
                    tempMin = 10; waterMin = 30; lightMin = 20; break;
            }
        }
        String html = "<html><b>Critical values:</b> " +
                "<span style='color:#6C4AB6;'>T &lt; " + tempMin + "°</span> | " +
                "<span style='color:#f0ad4e;'>H₂O &lt; " + waterMin + "%</span> | " +
                "<span style='color:#2F80ED;'>Light &lt; " + lightMin + "%</span></html>";
        infoBar.setText(html);
    }
    /**
     */
    

    //Fügt das Pflanzenbild je nach Kategorie im Panel hinzu
    private void setPlantIconByCategory(String categoryKey) {
        if (categoryKey == null) return;
        String file;
        switch (categoryKey.toLowerCase()) {
            case "sun":
                file = "sunplant.png";
                break;
            case "shade":
                file = "shadeplant.png";
                break;
            case "outdoor":
                file = "outdoorplant.png";
                break;
            case "tropical":
                file = "tropicalplant.png";
                break;
            default:
                return;
        }
        ImageIcon icon = loadScaledIcon(file, plantImageWidth, plantImageHeight);
        if (icon != null) plantImageLabel.setIcon(icon);
    }

    //Verbindet PlantView-Klasse mit PlantController-Klasse
    public void setController(ActionListener controller) {
        if (exit != null) {
            exit.setActionCommand("EXIT");
            exit.addActionListener(controller); //Button klick --> actionPerformed () aufgerufen
        }
        if (settings != null) {
            settings.setActionCommand("SETTINGS");
            settings.addActionListener(controller);
        }
        if (careForPlant != null) {
            careForPlant.setActionCommand("CARE");
            careForPlant.addActionListener(controller);
        }
        if (addNewPlantButton != null) {
            addNewPlantButton.setActionCommand("ADD_NEW_PLANT");
            addNewPlantButton.addActionListener(controller); 
        }
    }

    // Aktualisiert die Sensor-Anzeige (Temp/Feuchte/Licht) in der UI und zeichnet neu
    public void updateSensorValues(double temp, double moisture, double lightValue) {
        temperature.setText("<html><span style='color:white; font-size:14px;'>Temperature </span>" +
                "<span style='color:#6C4AB6; font-size:16px; font-weight:bold;'>" +
                String.format("%.1f°", temp) + "</span></html>");

        soilMoisture.setText("<html><span style='color:white; font-size:14px;'>Soilmoisture </span>" +
                "<span style='color:#EAD7B7; font-size:16px; font-weight:bold;'>" +
                String.format("%.0f%%", moisture) + "</span></html>");

        light.setText("<html><span style='color:white; font-size:14px;'>Light </span>" +
                "<span style='color:#2F80ED; font-size:16px; font-weight:bold;'>" +
                String.format("%.0f%%", lightValue) + "</span></html>");
        
        refresh();
    }

    //schaltet Warnung(rot) an/aus
    public void setWarningState(boolean isWarning) {
        if (isWarning) {
            setBackground(backgroundWarningRed);
            infoBar.setBackground(infoBarBackgroundWarning);
        } else {
            infoBar.setBackground(infoBarBackground);
        }
        refresh();
    }

    //zwingt UI-Update (Layout + Neuzeichnen)
    private void refresh() {
        revalidate();
        repaint();
    }
    
    private JButton createIconButton(String name) {
        ImageIcon icon = loadScaledIcon(name, iconImageWidth, iconImageHeight);
        JButton b;
        if (icon != null) { // Wenn das Icon geladen wurde: Button mit Bild
            b = new JButton(icon);
        } else { // Fallback-icon nicht geladen
            b = new JButton(name);
        }
        //Button clean machen
        b.setContentAreaFilled(false);//ohne hintergrund
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        return b;
    }

     // Logo aus Resources laden (mit/ohne "/") und auf w×h skalieren
    private ImageIcon loadScaledIcon(String resourceName, int w, int h) {
        URL url = getClass().getResource(resourceName);
        if (url == null) url = getClass().getResource("/" + resourceName);
        if (url == null) return null; //Abbruch wenn nichts gefunden

        ImageIcon icon = new ImageIcon(url);
        Image scaled = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}

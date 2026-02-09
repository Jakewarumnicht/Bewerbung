import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Einstiegpunkt der Anwendun: 
 * initialisiert das Frame/CardLayout und startet die GUI
 */
public class PlantMain {

    // Konstanten für CardLayout-Namen
    public static final String PANEL_START = "Start";
    public static final String PANEL_SETTINGS = "Settings";
    public static final String PANEL_MY_PLANT = "My Plant";
    public static final String PANEL_ADD_PLANT = "Add your plant";

    // Zentrale statische Referenzen
    public static CardLayout cardLayout;
    public static JPanel cardPanel;
    public static UserModel currentUser;
    public static PlantController plantController;
    
    
    public static void main(String[] args) {

        JFrame myFrame = new JFrame("Plant App");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // CardLayout & Hauptpanel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setPreferredSize(new Dimension(700, 700));
        myFrame.setContentPane(cardPanel);

        // Views (Panels)
        StartScreenView startPanel = new StartScreenView();
        SettingsView settingsPanel = new SettingsView();
        PlantView plantPanel = new PlantView();
        AddPlantView addPlantView = new AddPlantView();

        // Einheitliche Größe für alle Panels
        startPanel.setPreferredSize(new Dimension(700, 700));
        settingsPanel.setPreferredSize(new Dimension(700, 700));
        plantPanel.setPreferredSize(new Dimension(700, 700));
        addPlantView.setPreferredSize(new Dimension(700, 700));

        // DAO
        PlantCSVDAO plantCSVDAO = new PlantCSVDAO();

        // Controller
        plantController = new PlantController(plantPanel);
        new AddPlantController(addPlantView, plantCSVDAO, plantController);
        new SettingsController(settingsPanel, null, null);

        // Panels registrieren
        cardPanel.add(startPanel, PANEL_START);
        cardPanel.add(settingsPanel, PANEL_SETTINGS);
        cardPanel.add(plantPanel, PANEL_MY_PLANT);
        cardPanel.add(addPlantView, PANEL_ADD_PLANT);

        // Fenster anzeigen
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);

        // Startpanel anzeigen
        switchPanel(PANEL_START);
    }

   
    // Methode zum Wechseln der Panels
    public static void switchPanel(String name) {
        cardLayout.show(cardPanel, name);
        cardPanel.revalidate();
        cardPanel.repaint();
    }
}

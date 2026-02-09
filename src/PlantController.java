import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * Controller für PlantView.
 * Handhabt die Simulation und Benutzerinteraktionen.
 */


public class PlantController implements ActionListener {

	//unter 200 ms darf Simulationstimer nicht laufen
    private static final int minimumDelayMs = 200;

    //ActionCommands der Button
    private static final String commandExit = "EXIT";
    private static final String commandSettings = "SETTINGS";
    private static final String commandCare = "CARE";
    private static final String commandAddNewPlant = "ADD_NEW_PLANT";

    //Datenzugriff (CSV)
    private final PlantCSVDAO plantcsvdao;
    //Referenz auf UI
    private final PlantView view;
    //Referenz auf Simulations-Model
    private final PlantSimulationModel simulator;

    //Aktuell ausgewählte Pflanze im Controller
    private PlantModel currentPlant;
    
    /**
     * 
     */
    //Timer 
    private final Timer simulationTimer;
    //Start-Delay für den Timer: 2000 ms = 2 Sekunden pro Tick
    private int simulationDelayMs = 2000;

    
    public PlantController(PlantView view) {
        this.plantcsvdao = new PlantCSVDAO(); //Dao-Zugriff
        this.view = view;  //merken von PlantView
        this.simulator = new PlantSimulationModel(); //Simulationslogik
        this.view.setController(this); //Controller an View binden
        
        //Timer für Simulation erstellen
        this.simulationTimer = new Timer(simulationDelayMs, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runSimulationTick();
            }
        });
    }

     // Setzt die Simulationsgeschwindigkeit (Timer-Delay), aber nicht schneller als minimumDelayMs
    public void setSimulationDelayMs(int ms) {
        if (ms < minimumDelayMs) ms = minimumDelayMs;
        this.simulationDelayMs = ms;
        simulationTimer.setDelay(ms);
        simulationTimer.setInitialDelay(ms);
    }

    // Setzt die aktuelle Pflanze, initialisiert die UI (alive + Name/Bild + Sensorwerte) und startet den Simulationstimer
    public void startSimulation(PlantModel plant) {
        this.currentPlant = plant;
        view.showAliveState(plant.getCategory());
        view.displayPlant(plant.getName(), plant.getCategory());
        view.updateSensorValues(
                plant.getTemperature(),
                plant.getSoilMoisture(),
                plant.getLight()
        );
        simulationTimer.start();
    }

    public void stopSimulation() {
        simulationTimer.stop();
    }
    /**
     */
    
    
    // Pflanze des Users aus CSV & startet Simulation || Simulation stoppen "No Plant"-UI anzeigen
    public void loadAndShowPlantForUser(int userId) {
        PlantModel p = plantcsvdao.getLatestPlantForUser(userId);
        if (p!= null) {
            startSimulation(p);
        } else {
            currentPlant = null;
            stopSimulation();
            view.showNoPlantState();
        }
    }
 
    //Aktualisiert Pflanzenname + Bild + Werte
    public void updatePlant(String plantName, String categoryKey,
                            double temp, double moisture, double lightValue) {
        view.displayPlant(plantName, categoryKey);
        view.updateSensorValues(temp, moisture, lightValue);
    }

    //Simulation der Pflanzenwerte
    private void runSimulationTick() {
        if (currentPlant == null) return;
        simulator.simulate(currentPlant);
        if (!currentPlant.getAlive()) {
            onPlantDied();
            return;
        }
        view.updateSensorValues(
                currentPlant.getTemperature(),
                currentPlant.getSoilMoisture(),
                currentPlant.getLight()
        );

        if (currentPlant.getCriticalTicks() > 5) {
            view.displayPlant(currentPlant.getName() + " ⚠️", currentPlant.getCategory());
            view.setWarningState(true);
        } else {
            view.displayPlant(currentPlant.getName(), currentPlant.getCategory());
            view.setWarningState(false);
        }
    }

    //Simulation stoppen --> Pflanze tod
    private void onPlantDied() {
        simulationTimer.stop();
        view.showDeadState(currentPlant.getName());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd == null) return;
        switch (cmd) {
            case commandExit:
                simulationTimer.stop();
                System.exit(0);
                break;

            case commandSettings:
                PlantMain.switchPanel(PlantMain.PANEL_SETTINGS);
                break;

            case commandCare:
                handleCareForPlant();
                break;

            case commandAddNewPlant:
                stopSimulation();
                PlantMain.switchPanel(PlantMain.PANEL_ADD_PLANT);
                break;

            default:
                break;
        }
    }

    //Funktion Care-For-Plant Button
    private void handleCareForPlant() {
        if (currentPlant == null) return;
        if (!currentPlant.getAlive()) return;
        ImageIcon smallIcon = loadScaledIcon("logo.png", 80, 80);
        Object[] options = {"No", "Yes"};
        int answer = JOptionPane.showOptionDialog(
                view,
                "Do you want to take care of your plant?",
                "Plant Well-being",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                smallIcon,
                options,
                options[0]
        );
        if (answer == 1) {
            currentPlant.careForPlant();

            view.setWarningState(false);
            view.showAliveState(currentPlant.getCategory());
            view.displayPlant(currentPlant.getName(), currentPlant.getCategory());

            view.updateSensorValues(
                    currentPlant.getTemperature(),
                    currentPlant.getSoilMoisture(),
                    currentPlant.getLight()
            );
            JOptionPane.showMessageDialog(
                    view,
                    "Your plant feels better now 😊",
                    "Care successful",
                    JOptionPane.INFORMATION_MESSAGE,
                    smallIcon
            );
        } else if (answer == 0) {
            JOptionPane.showMessageDialog(
                    view,
                    "<html><b>Warning:</b><br>Your plant is still waiting 🥲</html>",
                    "Care declined",
                    JOptionPane.WARNING_MESSAGE,
                    smallIcon
            );
        }
    }

    // Lädt ein Resource-Bild und gibt es als skaliertes ImageIcon zurück (null, wenn nicht gefunden)
    private ImageIcon loadScaledIcon(String resourceName, int w, int h) {
        URL url = view.getClass().getResource(resourceName);
        if (url == null) url = view.getClass().getResource("/" + resourceName);
        if (url == null) return null;

        ImageIcon icon = new ImageIcon(url);
        Image scaled = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}

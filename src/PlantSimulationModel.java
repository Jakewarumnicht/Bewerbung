import java.util.Random;

/**
 * Modell für die Pflanzensimulation.
 * Simuliert Umweltbedingungen und deren Auswirkungen auf die Pflanze.
 */
public class PlantSimulationModel {

    // Kritische Mindestwerte (unterhalb davon = kritisch)
    public static final double CRIT_TEMP  = 10.0;
    public static final double CRIT_MOIST = 30.0;
    public static final double CRIT_LIGHT = 20.0;

    private final Random rand = new Random();

    // Verlust-Spannen pro Tick (wie stark Wasser/Licht sinken)
    private double moistLossMin = 0.6;
    private double moistLossMax = 1.5;

    private double lightLossMin = 0.3;
    private double lightLossMax = 1.0;

    // Temperatur ändert sich pro Tick leicht zufällig
    private double tempChangeMin = -0.4;
    private double tempChangeMax =  0.4;

    // Wie viele kritische Ticks hintereinander bis die Pflanze stirbt
    private int deathAfterCriticalTicks = 12;

    
    public PlantSimulationModel() {
    }

    public void setMoistureLoss(double min, double max) {
        this.moistLossMin = min;
        this.moistLossMax = max;
    }

    public void setLightLoss(double min, double max) {
        this.lightLossMin = min;
        this.lightLossMax = max;
    }

    public void setDeathAfterCriticalTicks(int ticks) {
        this.deathAfterCriticalTicks = ticks;
    }

    /**
     * Simulation
     */
    public void simulate(PlantModel plant) {
        if (plant == null) return;

        // Zufällige Verluste bestimmen
        double moistLoss = randomBetween(moistLossMin, moistLossMax);
        double lightLoss = randomBetween(lightLossMin, lightLossMax);

        //Wasser reduzieren
        double moisture = Math.max(0, Math.min(100,
                plant.getSoilMoisture() - moistLoss));
        plant.setSoilMoisture(moisture);

        //Licht reduzieren
        double light = Math.max(0, Math.min(100,
                plant.getLight() - lightLoss));
        plant.setLight(light);

        // Temperatur verändern 
        double tempChange = randomBetween(tempChangeMin, tempChangeMax);
        double temperature = Math.max(0, Math.min(40,
                plant.getTemperature() + tempChange));
        plant.setTemperature(temperature);

        //Prüfen, ob Werte kritisch sind
        boolean isCritical = isCritical(plant);

        //Kritische Ticks zählen
        if (isCritical) {
            plant.setCriticalTicks(plant.getCriticalTicks() + 1);
        } else {
            plant.setCriticalTicks(0);
        }

        //Wenn zu lange kritisch -> Pflanze stirbt
        if (plant.getCriticalTicks() >= deathAfterCriticalTicks) {
            plant.setAlive(false);
        }
    }

    //Prüfen ob Pflanzen Werte kritisch
    private boolean isCritical(PlantModel plant) {
        return plant.getTemperature() < CRIT_TEMP
                || plant.getSoilMoisture() < CRIT_MOIST
                || plant.getLight() < CRIT_LIGHT;
    }
    /**
     */
    
    //Zufallszahl zwischen min und max
    private double randomBetween(double min, double max) {
        return min + rand.nextDouble() * (max - min);
    }

    //begrenzt einen Wert auf einen Bereich (min/max)
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}


import java.util.List;
import java.util.Random;

/**
 * Modell für eine Pflanze.
 * Enthält Attribute und Methoden zur Verwaltung des Pflanzenzustands.
 */

public class PlantModel {
    
    private int plantID;
    private int userID;
    private String plantName;
    private String plantCategory;  
    private double currentMoisture;  
    private double currentLight;       
    private double currentTemperature;     
   
    private int criticalTicks = 0;
    private boolean alive = true;     
    private PlantCSVDAO plantcsvdao = new PlantCSVDAO();
    
   
    public PlantModel(String category) {
        this.plantCategory = category;
        initializeValuesByCategory();
        this.alive = true;
        this.criticalTicks = 0;
    }
    
   
    public PlantModel(int plantID, int userID, String plantName, String plantCategory,
                      double currentMoisture, double currentLight, double currentTemperature) {
        this.plantID = plantID;
        this.userID = userID;
        this.plantName = plantName;
        this.plantCategory = plantCategory;
        this.currentMoisture = currentMoisture;
        this.currentLight = currentLight;
        this.currentTemperature = currentTemperature;
        this.alive = true;
        this.criticalTicks = 0;
    }
    
   
    
    private void initializeValuesByCategory() {
        if (plantCategory == null) {
            plantCategory = "tropical";
        }
        
        Random random = new Random();
        
        switch (plantCategory.toLowerCase()) {
            case "sun":
                currentLight = 70 + random.nextInt(21);           
                currentMoisture = 40 + random.nextInt(31);   
                currentTemperature = 18 + random.nextInt(11);    
                break;
                
            case "shade":
                currentLight = 30 + random.nextInt(21);          
                currentMoisture = 50 + random.nextInt(31);    
                currentTemperature = 16 + random.nextInt(9);     
                break;
                
            case "tropical":
                currentLight = 50 + random.nextInt(31);          
                currentMoisture = 60 + random.nextInt(21);   
                currentTemperature = 22 + random.nextInt(7);     
                break;
                
            case "outdoor":
                currentLight = 40 + random.nextInt(41);          
                currentMoisture = 40 + random.nextInt(41);      
                currentTemperature = 10 + random.nextInt(16);     
                break;
                
            default:
                currentLight = 50;
                currentMoisture = 50;
                currentTemperature = 22;
                break;
        }
    }
    

    public int getPlantID() {
        return plantID;
    }
    
    public int getUserID() {
        return userID;
    }
    
    public String getName() {
        return plantName;
    }
    
    public String getCategory() {
        return plantCategory;
    }  
   
    public double getSoilMoisture() {
        return currentMoisture;
    }
    
    public double getLight() {
        return currentLight;
    }
    
    public double getTemperature() {
    	return currentTemperature;
    }
        
    public int getCriticalTicks() {
        return criticalTicks;
    }
    
    public boolean getAlive() {
		return alive;
	}
    
    public void setSoilMoisture(double value) {
        this.currentMoisture = value;
    }
    
    public void setLight(double value) {
        this.currentLight = value;
    }
    
    public void setTemperature(double value) {
        this.currentTemperature = value;
    }

         
    public void setCriticalTicks(int ticks) {
        this.criticalTicks = ticks;
    }
 
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    

    public void careForPlant() {
        this.currentMoisture = Math.min(100, this.currentMoisture + 20);
        this.currentLight = Math.min(100, this.currentLight + 15);
        this.currentTemperature = Math.min(30, this.currentTemperature + 5);
        this.criticalTicks = 0;
    }

    public boolean deletePlant(int plantID, int userID) {
        List<PlantModel> plants = plantcsvdao.loadPlants();
        boolean removed = plants.removeIf(
            plant -> plant.getPlantID() == plantID && plant.getUserID() == userID
        );
        if (removed) {
            plantcsvdao.updatePlants(plants);
        }
        return removed;
    }
    
}
	
    

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Controller für AddPlantView.
 * Handhabt die Logik zum Hinzufügen neuer Pflanzen.
 */

public class AddPlantController implements ActionListener {
    private AddPlantView view;
    private PlantCSVDAO plantCSVDAO;
    private PlantController plantController;
    private String selectedCategory = null;
    
    ImageIcon logoIcon = new ImageIcon(getClass().getResource("logo.png"));
	Image scaled = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	ImageIcon smallerLogoIcon = new ImageIcon(scaled);
    

   
    public AddPlantController(AddPlantView view, PlantCSVDAO plantCSVDAO, PlantController plantController) {
        this.view = view;
        this.plantCSVDAO = plantCSVDAO;
        this.plantController = plantController;
        this.view.setController(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "SUN":
            case "SHADOW":
            case "OUTDOOR":
            case "TROPICAL":
                handleCategorySelection(command);
                break;

            case "SAVE":
                handlePlantCreation();
                break;
        }
    }

    private void handleCategorySelection(String command) {
        switch (command) {
            case "SUN": selectedCategory = "sun"; 
            		view.setSelectedCategory("Sun Loving Plants");
            		break;
            case "SHADOW": selectedCategory = "shade"; 
            		view.setSelectedCategory("Shadow Loving Plants");
            		break;
            case "OUTDOOR": selectedCategory = "outdoor";
    				view.setSelectedCategory("Outdoor Plants");
            		break;
            case "TROPICAL": selectedCategory = "tropical";
            		view.setSelectedCategory("Tropical Plants");
            		break;
        }
    }
    public void handlePlantCreation() {
        String plantName = view.getPlantName();
        
        if (plantName == null || plantName.isBlank()) {
            view.showError("Please insert a name");
            return;
        }
        if (selectedCategory == null) {
            view.showError("Please select a category");
            return;
        }
        
        int id = plantCSVDAO.getNextPlantId();
        int userId = 0;
        if (PlantMain.currentUser != null) userId = PlantMain.currentUser.getUserID();
        
        
        PlantModel simulation = new PlantModel(selectedCategory);
        double currentMoisture = simulation.getSoilMoisture();
        double currentLight = simulation.getLight();
        double currentTemperature = simulation.getTemperature();
        
       
        PlantModel plant = new PlantModel(
                id, 
                userId, 
                plantName, 
                selectedCategory,  
                currentMoisture, 
                currentLight, 
                currentTemperature);
        
        plantCSVDAO.savePlant(plant);
       
        if (plantController != null) {
            plantController.updatePlant(
                plantName, 
                selectedCategory, 
                currentTemperature, 
                currentMoisture, 
                currentLight
            );
            plantController.startSimulation(plant);  
        }
        
        JOptionPane.showMessageDialog(view, "Plant saved successfully", 
                                     "Saved", JOptionPane.INFORMATION_MESSAGE,smallerLogoIcon);
        view.clearForm();
        
        PlantMain.switchPanel("My Plant");
    }
}

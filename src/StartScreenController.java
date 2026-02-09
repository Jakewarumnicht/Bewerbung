import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
/**
 * Controller für StartScreenView.
 * Handhabt Login- und Registrierungslogik.
 */
public class StartScreenController {
    private PlantCSVDAO plantcsvdao;
    private StartScreenView view;

    public StartScreenController() {
        this.plantcsvdao = new PlantCSVDAO();
    }
    
    public void setView(StartScreenView view) {
        this.view = view;
    }

    // Login
    public UserModel login(String username, String password) {
        List<UserModel> users = plantcsvdao.loadUsers();
        for (UserModel user : users) {
            if (user.getUsername().equals(username) &&
                user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    // Register
    public boolean register(String username, String password) {
        List<UserModel> users = plantcsvdao.loadUsers();

        for (UserModel user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }

        int newId = plantcsvdao.getNextUserId();
        UserModel newUser = new UserModel(newId, username, password);
        plantcsvdao.saveUser(newUser);
        return true;
    }
    // Check if user has plants
    public boolean userHasPlants(int userId) {
        return plantcsvdao.hasPlantForUser(userId);
    }
    
    // Button Listener
    public void ButtonListener() {
        // Exit Button
        view.exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.exit(0);	
            }
        });
        // Setting Button
        view.settings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PlantMain.switchPanel("Settings");
            }
        });			
    }

    // Login Controll
    public void handleLogin() {
        String username = view.usernameField.getText();
        String password = new String(view.passwordField.getPassword());

        UserModel user = login(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(
                view,
                "Invalid login details!",
                "Error",
                JOptionPane.ERROR_MESSAGE,
                view.smallerLogoIcon
            );
            return;
        }

        // Panel-Switch
        PlantMain.currentUser = user;
        int userId = user.getUserID();
        if (userHasPlants(userId)) {
            if (PlantMain.plantController != null) {
                PlantMain.plantController.loadAndShowPlantForUser(userId);
            }
            PlantMain.switchPanel("My Plant");
        } else {
            PlantMain.switchPanel("Add your plant");
        }
    }
    
      
    // Register Controll
    public void handleRegister() {
        String username = view.usernameField.getText();
        String password = new String(view.passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                view,
                "Please fill in all fields!",
                "Error",
                JOptionPane.ERROR_MESSAGE,
                view.smallerLogoIcon
            );
            return;
        }
        
        if (register(username, password)) {
            JOptionPane.showMessageDialog(
                view,
                "Registration successful! Please log in",
                "Success",
                JOptionPane.INFORMATION_MESSAGE,
                view.smallerLogoIcon
            );
            view.usernameField.setText("");
            view.passwordField.setText("");
        } else {
            JOptionPane.showMessageDialog(
                view,
                "Username already exists!",
                "Error",
                JOptionPane.ERROR_MESSAGE,
                view.smallerLogoIcon
            );
        }
    }
}
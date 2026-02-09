import java.io.*;
import java.util.ArrayList;
import java.util.List;


/** Data Access Object (DAO) für Benutzer- und Pflanzenmodelle.
 * Liest und schreibt Benutzerdaten und Pflanzendaten in CSV-Dateien.
 */

public class PlantCSVDAO {
    private static final String DATA_PATH = "data/";
    private static final String USERS_FILE = DATA_PATH + "users.csv";
    private static final String PLANTS_FILE = DATA_PATH + "plants.csv";
    
    // Konstruktor: Initialisiert Datenverzeichnis und Dateien
    // Data Ordner wird erstellt, wenn nicht vorhanden
    public PlantCSVDAO() {
        File dataDir = new File(DATA_PATH);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
        initializeFiles();
    }
    
    // Initialisiert CSV-Dateien mit Headern, falls sie nicht existieren
    private void initializeFiles() {
        try {
            File usersFile = new File(USERS_FILE);
            if (!usersFile.exists()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
                    writer.write("id,username,password\n");
                }
            }
            
            File plantsFile = new File(PLANTS_FILE);
            if (!plantsFile.exists()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(PLANTS_FILE))) {
                    writer.write("plantId,userId,name,category,currentMoisture,currentLight,currentTemperature\n");
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Lädt alle Benutzer aus der users.csv Datei
    public List<UserModel> loadUsers() {
        List<UserModel> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            reader.readLine(); // skip header
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    users.add(new UserModel(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        parts[2]
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    // Speichert einen neuen Benutzer in der users.csv Datei
    public void saveUser(UserModel user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(user.getUserID() + "," + 
                        user.getUsername() + "," + 
                        user.getPassword() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Bestimmt die nächste verfügbare Benutzer-ID
    public int getNextUserId() {
        List<UserModel> users = loadUsers();
        int maxId = 0;
        for (UserModel u : users) {
            if (u.getUserID() > maxId) maxId = u.getUserID();
        }
        return maxId + 1;
    }
    // Lädt alle Pflanzen aus der plants.csv Datei
    public List<PlantModel> loadPlants() {
        List<PlantModel> plants = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PLANTS_FILE))) {
            String line;
            reader.readLine(); // skip header
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    plants.add(new PlantModel(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        parts[2],
                        parts[3],
                        Double.parseDouble(parts[4]),
                        Double.parseDouble(parts[5]),
                        Double.parseDouble(parts[6])
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return plants;
    }
    
    // Speichert eine neue Pflanze in der plants.csv Datei
    public void savePlant(PlantModel plant) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PLANTS_FILE, true))) {
            writer.write(plant.getPlantID() + "," +
                        plant.getUserID() + "," +
                        plant.getName() + "," +
                        plant.getCategory() + "," +
                        plant.getSoilMoisture() + "," +
                        plant.getLight() + "," +
                        plant.getTemperature() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Aktualisiert alle Pflanzen in der plants.csv Datei
    public void updatePlants(List<PlantModel> plants) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PLANTS_FILE))) {
            writer.write("plantId,userId,name,category,currentMoisture,currentLight,currentTemperature\n");
            
            for (PlantModel plant : plants) {
                writer.write(plant.getPlantID() + "," +
                            plant.getUserID() + "," +
                            plant.getName() + "," +
                            plant.getCategory() + "," +
                            plant.getSoilMoisture() + "," +
                            plant.getLight() + "," +
                            plant.getTemperature() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Bestimmt die nächste verfügbare Pflanzen-ID
    public int getNextPlantId() {
        List<PlantModel> plants = loadPlants();
        int maxId = 0;
        for (PlantModel p : plants) {
            if (p.getPlantID() > maxId) maxId = p.getPlantID();
        }
        return maxId + 1;
    }
    
    // Prüft, ob ein Benutzer mindestens eine Pflanze hat
    public static boolean hasPlantForUser(int userId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PLANTS_FILE))) {
            String line;
            reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int storedUserId = Integer.parseInt(parts[1]);

                if (storedUserId == userId) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Holt die zuletzt hinzugefügte Pflanze für einen bestimmten Benutzer
    public PlantModel getLatestPlantForUser(int userId) {
        PlantModel latest = null;
        for (PlantModel p : loadPlants()) {
            if (p.getUserID() == userId) {
                if (latest == null || p.getPlantID() > latest.getPlantID()) {
                    latest = p;
                }
            }
        }
        return latest;
    }

    // Aktualisiert das Passwort eines Benutzers
    public boolean updateUserPassword(int userId, String newPassword) {
        List<UserModel> users = loadUsers();
        boolean found = false;
        
        // User finden und Passwort ändern
        for (UserModel user : users) {
            if (user.getUserID() == userId) {
                user.setPassword(newPassword);
                found = true;
                break;
            }
        }
        
        // Wenn User gefunden wurde, alle User neu schreiben
        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
                writer.write("id,username,password\n");
                for (UserModel user : users) {
                    writer.write(user.getUserID() + "," + 
                               user.getUsername() + "," + 
                               user.getPassword() + "\n");
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
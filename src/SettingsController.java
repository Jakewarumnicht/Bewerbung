import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JToggleButton;
/**
 * Controller für Settings-Ansicht. 
 * reagiert auf Benutzereingaben und aktualisiert Modelle.
 */

	public class SettingsController {
		
		public JButton homeButton, exitButton, supportButton, versionInfoButton, changePasswordButton, deletePlantButton;
		public JToggleButton notifToggle;
		public JLabel logo;
		public ImageIcon logoIconCP, smallerLogoIconCP, logoIconDP, smallerLogoIconDP;
		private SettingsView view;
		private UserModel model;
		private PlantModel plantmodel;
		
		ImageIcon logoIcon = new ImageIcon(getClass().getResource("logo.png"));
		Image scaled = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon smallerLogoIcon = new ImageIcon(scaled);
				
		public SettingsController(SettingsView view, UserModel model, PlantModel plantmodel) {
		this.view = view;
		this.model = model;
		this.plantmodel = plantmodel;
		Listeners();
	}
		
				
		private void Listeners() {
			
		// Die Logik vom Home button. Wechselt zum Start Panel.
		view.homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			PlantMain.switchPanel("Start");
					
				}
			});
			
			// Die Logik vom Exit button. Beendet die Applikation.
			view.exitButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
				
			});
			// Delete Plant Button Actionlistener
			view.deletePlantButton.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			
			        if (PlantMain.currentUser == null) {
			            JOptionPane.showMessageDialog(null, 
			                "Please login first!", 
			                "Error", 
			                JOptionPane.ERROR_MESSAGE,
			                smallerLogoIcon);
			            return;
			        }
			        
			        int userID = PlantMain.currentUser.getUserID();
			        
			 
			        PlantCSVDAO dao = new PlantCSVDAO();
			        PlantModel plantToDelete = dao.getLatestPlantForUser(userID);
			        
			        if (plantToDelete == null) {
			            JOptionPane.showMessageDialog(null, 
			                "You don't have any plants to delete!", 
			                "No Plant Found", 
			                JOptionPane.INFORMATION_MESSAGE,
			                smallerLogoIcon);
			            return;
			        }
			        
			        int plantID = plantToDelete.getPlantID();
			        
			        
			        Object[] options = {"Yes", "No"};
			        
			        int response = JOptionPane.showOptionDialog(null, 
			                "Are you sure you want to delete '" + plantToDelete.getName() + "'?\nThis action cannot be undone.",
			                "Delete Plant",
			                JOptionPane.YES_NO_OPTION,
			                JOptionPane.WARNING_MESSAGE,
			                smallerLogoIcon,
			                options,
			                options[1]);
			        
			        if (response == JOptionPane.YES_OPTION) {
			            boolean deleted = plantToDelete.deletePlant(plantID, userID);
			            
			            if (deleted) {
			                JOptionPane.showMessageDialog(null,
			                    "'" + plantToDelete.getName() + "' has been deleted successfully.",
			                    "Plant Deleted", 
			                    JOptionPane.INFORMATION_MESSAGE, 
			                    smallerLogoIcon);
			                
			                PlantMain.switchPanel("Start");
			                
			            } else {
			                JOptionPane.showMessageDialog(null, 
			                    "Failed to delete plant.\nPlease try again.", 
			                    "Error", 
			                    JOptionPane.ERROR_MESSAGE, 
			                    smallerLogoIcon);
			            }
			        }
			    }
			});
			
			// Change Password Button Actionlistener
			view.changePasswordButton.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	
			        if (PlantMain.currentUser == null) {
			            JOptionPane.showMessageDialog(null, 
			                "Please login first!", 
			                "Error", 
			                JOptionPane.ERROR_MESSAGE,
			                smallerLogoIcon);
			            return;
			        }
			        
			        JPasswordField oldPass = new JPasswordField();
			        JPasswordField newPass = new JPasswordField();
			        JPasswordField confirmPass = new JPasswordField();
			        
			        Object[] message = {
			                "Type old password:", oldPass,
			                "Type new password:", newPass,
			                "Confirm new password:", confirmPass
			        };
			        
			        Object[] options = {"Ok", "Cancel"};
			        
			        int option = JOptionPane.showOptionDialog(
			                null, 
			                message, 
			                "Change Password", 
			                JOptionPane.OK_CANCEL_OPTION,
			                JOptionPane.PLAIN_MESSAGE,
			                smallerLogoIconCP,
			                options,
			                options[0]);
			        
			        if (option == JOptionPane.OK_OPTION) {
			            String oldPassword = new String(oldPass.getPassword());
			            String newPassword = new String(newPass.getPassword());
			            String confirmPassword = new String(confirmPass.getPassword());
			            
			          
			            if (newPassword.isEmpty()) {
			                JOptionPane.showMessageDialog(null, 
			                    "New password cannot be empty!", 
			                    "Error", 
			                    JOptionPane.ERROR_MESSAGE, 
			                    smallerLogoIcon);
			                return;
			            }
			            
			          
			            if (!PlantMain.currentUser.checkPassword(oldPassword)) {
			                JOptionPane.showMessageDialog(null, 
			                    "Old password is wrong!", 
			                    "Error", 
			                    JOptionPane.ERROR_MESSAGE, 
			                    smallerLogoIcon);
			                return;
			            }
			            
			            
			            if (!newPassword.equals(confirmPassword)) {
			                JOptionPane.showMessageDialog(null, 
			                    "New passwords do not match!", 
			                    "Error", 
			                    JOptionPane.ERROR_MESSAGE, 
			                    smallerLogoIcon);
			                return;
			            }
			            
			           
			            PlantCSVDAO dao = new PlantCSVDAO();
			            boolean updated = dao.updateUserPassword(
			                PlantMain.currentUser.getUserID(), 
			                newPassword
			            );
			            
			            if (updated) {
			               
			                PlantMain.currentUser.setPassword(newPassword);
			                
			                JOptionPane.showMessageDialog(null, 
			                    "Password successfully changed!", 
			                    "Success", 
			                    JOptionPane.INFORMATION_MESSAGE, 
			                    smallerLogoIcon);
			            } else {
			                JOptionPane.showMessageDialog(null, 
			                    "Failed to update password in database!", 
			                    "Error", 
			                    JOptionPane.ERROR_MESSAGE, 
			                    smallerLogoIcon);
			            }
			        }
			    }
			});
		
			// Support Button Actionlistener (Pop-up mit Anweisungen)
			view.supportButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					JOptionPane.showOptionDialog(null, 
							"Welcome to the PlantApp!\n Here is how to use the main features:\n "
							+ "Home: Overview of all sections in the app.\n "
							+ "My Plant: View and manage your saved plants.\n"
							+ "Add Your Plant: Add new plants and store important information such as name and watering date.\n"
							+ "Settings: Turn sound and notifications on or off, and adjust additional preferences.\n"
							+ "Navigation: Use the menu or buttons to switch between the different sections.",
							"Support",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE,
							smallerLogoIcon,
							null,
							null);
				}	
			});
			
			// Version Info Button Actionlistener (Pop-up mit Versionsinformation)
			view.versionInfoButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					JOptionPane.showOptionDialog(null,
							"PlantApp Version 1.0\n Developed by Team Mysmartplant\n © 2025 All rights reserved.",
							"Version",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE,
							smallerLogoIcon,
							null,
							null);
					
				}
			});
				
			}
		
		// Methode zum Aktualisieren der Modelle
		public void updateModels(UserModel userModel, PlantModel plantModel) {
		    this.model = userModel;
		    this.plantmodel = plantModel;
		}

	}




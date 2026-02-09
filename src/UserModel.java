 public class UserModel {
	
	 /**
	  * UserModel class represents a user with an ID, username, and password.
	  */

    private int userID;
    private String username;
    private String password;

    public UserModel(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    
    public int getUserID() {
        return userID;
    }

    public void setUserID(int id) {
        this.userID = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
   
    public boolean checkPassword(String oldPass) {
        return this.password.equals(oldPass);
    }

}
    




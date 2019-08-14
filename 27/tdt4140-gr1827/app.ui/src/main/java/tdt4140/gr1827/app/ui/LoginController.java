package tdt4140.gr1827.app.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import tdt4140.gr1827.app.core.Login;
import tdt4140.gr1827.app.core.Login.loginEnum;
import tdt4140.gr1827.app.core.MySqlConnector;


public class LoginController implements Initializable{
	
	@FXML private PasswordField passwordInput;
	@FXML private TextField usernameInput;
	@FXML private Button loginButton;
	@FXML private Button registerButton;
	@FXML private Label errorOutput;
	

	public LoginController() {
        MySqlConnector.connect();
    }
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (AlertBox.isUserDeleted) {
			errorOutput.setText("Brukeren er slettet!");
			AlertBox.isUserDeleted = false;
		}
		if (RegisterController.isUserRegistered) {
			errorOutput.setText("Brukeren er registrert!");
			RegisterController.isUserRegistered = false;
		}
		loginButton.setOnAction((event) -> { 
			try {
				handleLogin(event);
			} catch (IOException e) {
				e.printStackTrace();
			}});
		registerButton.setOnAction((event) -> { 
			register();
			});
	}
	
	@FXML
    private void handleLogin(ActionEvent event) throws IOException {
        login();
    }
	
	public void login() {
		Login login = new Login();
		loginEnum userType = login.checkLogin(usernameInput.getText(), passwordInput.getText());
		if (userType == loginEnum.doctor) {
			loginStage("DoctorFrontpage.fxml", "Doctor", loginButton);
	    }
		else if (userType == loginEnum.user) {
		    	loginStage("UserFrontpage.fxml", "Main", loginButton);
	    }
		else if (userType == loginEnum.fail) {
			errorOutput.setText("Feil brukernavn eller passord");
		}

	}
	

	public void register() {
		loginStage("Register.fxml", "Registrering", registerButton);
	}
	
	public void loginStage(String fxml, String title, Button button) {
		Stage stage = (Stage) button.getScene().getWindow();
		stage.close();
    		try {
	    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
	    		Parent root1 = (Parent) fxmlLoader.load();
	    		Stage stage2 = new Stage();
	    		stage2.setTitle(title);
	    		stage2.setScene(new Scene(root1, 715, 500));  
	    		stage2.show();
	    } 
	    	catch (IOException e) {
	    		System.err.println("Got an exception!");
	    		e.printStackTrace();
	    }
    }
}

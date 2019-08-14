package tdt4140.gr1827.app.ui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import tdt4140.gr1827.app.core.Login;

public class UserFrontpageController extends UserMainController implements Initializable{
	
	
	@FXML private Label welcomeLabel;
	
	
	public void initialize(URL location, ResourceBundle resources) {
		try {
			welcomeLabel.setText("Velkommen " + Login.getUserNameString() + "!");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}


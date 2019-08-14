package tdt4140.gr1827.app.ui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import tdt4140.gr1827.app.core.DataControl;

public class DataControlController extends UserMainController implements Initializable {

	private DataControl dataControl = new DataControl();
	@FXML private CheckBox pulseAccess;
	@FXML private CheckBox stepsAccess;
	@FXML private Button enterData;
	@FXML private Label pulseOutput;
	@FXML private Label stepsOutput;
	
	public void initialize(URL location, ResourceBundle resources) {
		try {
			if (dataControl.isAccessTrue("PulseAccess")) {
				pulseAccess.setSelected(true);
			}
			if (dataControl.isAccessTrue("StepsAccess")) {
				stepsAccess.setSelected(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void handleDataControl(ActionEvent event) throws SQLException {
		dataControl();
	}

	public void dataControl() throws SQLException {
		dataControl.setDataAccess(pulseAccess.isSelected(), stepsAccess.isSelected());
		pulseOutput.setText(outputMessage(pulseAccess.isSelected(), "Puls"));
		stepsOutput.setText(outputMessage(stepsAccess.isSelected(), "Skritt"));
	}
	
	public String outputMessage(Boolean accessIsTrue, String accessType) {
		if (accessIsTrue) {
			return accessType + " vil bli registrert!";
		}
		return accessType + " vil ikke bli registrert!";
	}
}

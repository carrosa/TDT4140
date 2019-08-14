package tdt4140.gr1827.app.ui;

import java.util.Calendar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		primaryStage.setTitle("Health Manager");
		Scene scene = new Scene(root, 715, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
		Calendar calendar = Calendar.getInstance();
		int today = calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	public static void main(String[] args) {
		System.setProperty("file.encoding", "UTF-8");
		launch(args);
	}
}

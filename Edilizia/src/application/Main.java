package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {

		@Override
		public void start(Stage primaryStage) throws Exception{
			//lancia la pagina di login
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
			primaryStage.setTitle("login");
			primaryStage.setScene(new Scene(root, 600, 400));
			primaryStage.setResizable(false);
			primaryStage.show();
		}
	
	public static void main(String[] args) {
		launch(args);
	}
}

package boundary;

import java.io.IOException;
import java.sql.SQLException;

import control.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginBoundary {

	@FXML 
	private TextField tf_id;
	@FXML
	private PasswordField pf_pass;
	@FXML
	private Button b_accedi;
	
	
	@FXML
	protected void doAccess(ActionEvent onAction) throws IOException, ClassNotFoundException, SQLException{
		if(LoginController.accedi(tf_id.getText(), pf_pass.getText()) == true){
			//chiude la finestra in cui è contenuto il pulsante accedi
			Stage primaryStage = (Stage) b_accedi.getScene().getWindow();
			primaryStage.close();
			
			String ruolo = LoginController.ruoloUtente(tf_id.getText());
		
			//apre la finestra principale
			if ( ruolo.equals("segreteria")){
				Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Cerca.fxml"));
				Scene scene = new Scene(parent);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("Cerca le stanze in base alle attrezzature");
				stage.setResizable(false);
				stage.show();
			} else {
				Parent parent = FXMLLoader.load(getClass().getResource("/fxml/CercaPerUtente.fxml"));
				Scene scene = new Scene(parent);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("Cerca le stanze in base alle attrezzature");
				stage.setResizable(false);
				stage.show();
			}
			
			
		}else{
			//crea alert nel caso di errore inserimento
			Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Errore");
    		alert.setHeaderText("Errore di autenticazione");
    		alert.setContentText("UserID o Password errati. Riprovare.");
    		alert.showAndWait();
		}
	}
	
	
}

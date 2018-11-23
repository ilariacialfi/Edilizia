package boundary;

import java.io.IOException;
import java.sql.SQLException;
import control.ModelloController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ModelloBoundary {

	@FXML
    private ComboBox<String> cb_attr;

    @FXML
    private Button b_add;

    @FXML
    private ComboBox<String> cb_modello;

    @FXML
    private Button b_rinom;

    @FXML
    private Button b_crea;

    @FXML
    private Button b_elimina;

    @FXML
    private Button b_importa;

    @FXML
    private TextField tf_nome;

    @FXML
    private ListView<String> lv_attr;

    @FXML
    private Button b_salva;

    @FXML
    private Button b_elAttr;

    @FXML
    private Button b_back;
    
    @FXML
    public void initialize() throws ClassNotFoundException, SQLException {
    	cb_attr.setItems(ModelloController.estraiAttrezzatura());
    	cb_modello.setItems(ModelloController.estraiModelli());
    	
    	lv_attr.getItems().clear();
    	
    	b_elAttr.setDisable(true);
    	cb_attr.setDisable(true);
    	b_add.setDisable(true);
    	b_rinom.setDisable(true);
    	b_salva.setDisable(true);
    	
    }

    @FXML
    void creaModello(ActionEvent event) throws ClassNotFoundException, SQLException {
    	String mod = tf_nome.getText();
    	if (mod == null){
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Errore");
    		alert.setHeaderText("Scelta non valida");
    		alert.setContentText("E' necessario inserire il nome del modello");;
    		alert.showAndWait();
    		return;
    	}
    	//se esiste un modello con questo nome mando l'alert
    	if (ModelloController.cercaModello(mod) == true) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Errore");
    		alert.setHeaderText("Scelta non valida");
    		alert.setContentText("Un modello con questo nome già esiste");
    		alert.showAndWait();
    		return;
    	} else {
    		tf_nome.setDisable(true);
    		cb_modello.setDisable(true);
    		b_importa.setDisable(true);
    		b_elimina.setDisable(false);
    		b_rinom.setDisable(false);
    		cb_attr.setDisable(false);
    		b_add.setDisable(false);
    	}
    }
    
    @FXML
    void importaModello(ActionEvent event) throws ClassNotFoundException, SQLException {
    	String mod = cb_modello.getSelectionModel().getSelectedItem();
    	lv_attr.getItems().clear();
    	
    	if (mod == null){
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Errore");
    		alert.setHeaderText("Scelta non valida");
    		alert.setContentText("E' necessario selezionare un modello!");
    		alert.showAndWait();
    		return;
    	}
    	ObservableList<String> listAttr = ModelloController.estraiAttr(mod);
    	for (String a : listAttr){
    		lv_attr.getItems().add(a);
    	}
    	tf_nome.setText(mod);
    	tf_nome.setDisable(true);
    	lv_attr.refresh();
    	b_importa.setDisable(true);
    	cb_modello.setDisable(true);
    	b_crea.setDisable(true);
    	
    	cb_attr.setDisable(false);
    	b_add.setDisable(false);
    	b_rinom.setDisable(false);
    	
    }
    
    @FXML
    void addAttrezzatura(ActionEvent event) {
 
    	if (cb_attr.getValue() == null) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Errore");
    		alert.setHeaderText("Scelta non valida");
    		alert.setContentText("E' necessario selezionare un'attrezzatura!");
    		alert.showAndWait();
    		return;
    	}
    	for (String attr : lv_attr.getItems()){
    		if (attr.equals(cb_attr.getSelectionModel().getSelectedItem())){
    			lv_attr.refresh();
    			Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Errore");
        		alert.setHeaderText("Scelta non valida");
        		alert.setContentText("Hai già inserito questa attrezzatura!");
        		alert.showAndWait();
    			return;
    		}
    	}
    	lv_attr.getItems().add(cb_attr.getValue());
    	lv_attr.refresh();
    	
    	b_elAttr.setDisable(false);
    	b_rinom.setDisable(false);
    	b_salva.setDisable(false);
    }

    @FXML
    void eliminaAttrezzatura(ActionEvent event) {
    	String attr = lv_attr.getSelectionModel().getSelectedItem();
    	if (attr != null) {
    		lv_attr.getItems().remove(attr);
    		if (lv_attr.getItems().size() == 0) {
    			b_elAttr.setDisable(true);
    			b_salva.setDisable(true);
    		}
    	} else {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Errore");
    		alert.setHeaderText("Elemento non selezionato");
    		alert.setContentText("Nessun modello è stato selezionato!");
    		alert.showAndWait();
    		return;
    	}
    }

    @FXML
    void eliminaModello(ActionEvent event) throws ClassNotFoundException, SQLException {
    	String mod = tf_nome.getText();
    	if (mod != null){
    		ModelloController.eliminaModello(mod);
    		cb_modello.setItems(ModelloController.estraiModelli());;
    	
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Conferma");
    		alert.setHeaderText("Eliminazione Completata");
    		alert.setContentText("Modello eliminato con successo!");
    		alert.showAndWait();
    		
    		tf_nome.setDisable(false);
    		b_crea.setDisable(false);
    		cb_modello.setDisable(false);
    		b_importa.setDisable(false);
    		
    		b_elimina.setDisable(true);
    		cb_attr.setDisable(true);
    		b_add.setDisable(true);
    		b_salva.setDisable(true);
    	}
    }

    @FXML
    void rinominaModello(ActionEvent event) {
    	//trovare un modo per rinominare anche per le stanze
    }

    @FXML
    void salvaModello(ActionEvent event) throws ClassNotFoundException, SQLException {
    	ObservableList<String> attr = lv_attr.getItems();
    	String mod = tf_nome.getText();
    	
    	if (attr == null) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Errore");
    		alert.setHeaderText("Informazioni incomplete.");
    		alert.setContentText("Per creare un nuovo modello è necessario inserire almeno un'attrezzatura.");
    		alert.showAndWait();
    		return;
    	} else {
    		//controllo se il modello già esiste (TRUE)
    		if (ModelloController.cercaModello(mod) == true) {
    			ModelloController.aggiornaModello(mod, attr);
    			
    			//Mando una notifica di conferma
	    		Alert alert = new Alert(AlertType.CONFIRMATION);
	    		alert.setTitle("Conferma");
	    		alert.setHeaderText("Update effettuato.");
	    		alert.setContentText("Modello aggiornato con successo!");
	    		alert.showAndWait();
	    		initialize();
	    		return;
    		} else {
    			ModelloController.salvaModello(mod, attr);
    			
    			Alert alert = new Alert(AlertType.CONFIRMATION);
	    		alert.setTitle("Conferma");
	    		alert.setHeaderText("Salvataggio effettuato.");
	    		alert.setContentText("Modello salvato con successo!");
	    		alert.showAndWait();
	    		initialize();
	    		return;
    		}
    	}
    	
    }

    @FXML
    protected void tornaIndietro(ActionEvent event) throws IOException {
    	Stage primaryStage = (Stage) b_back.getScene().getWindow();
    	primaryStage.close();
    	
    	Parent parent = FXMLLoader.load(getClass().getResource(("/fxml/Cerca.fxml")));
    	Scene scene = new Scene(parent);
    	Stage newStage = new Stage();
    	newStage.setScene(scene);
    	newStage.setTitle("Cerca le stanze in base alle attrezzature");
    	newStage.setResizable(false);
    	newStage.show();
    }

}

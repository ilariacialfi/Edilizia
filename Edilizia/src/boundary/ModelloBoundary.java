package boundary;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
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
	private Button b_okRin;

	@FXML
	private ComboBox<String> cb_modello;

	@FXML
	private Button b_rinom;

	@FXML
	private Button b_crea;

	@FXML
	private Button b_elimina;

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

		// ordino la combobox dei modelli
		cb_modello.getItems().clear();
		ObservableList<String> items = ModelloController.estraiModelli();
		Collections.sort(items);
		cb_modello.getItems().addAll(items);
		cb_modello.getItems().add("Crea nuovo modello");

		lv_attr.getItems().clear();

		// nascondo pulsanti e oggetti che non possono essere usati
		tf_nome.setVisible(false);
		b_crea.setVisible(false);
		b_okRin.setVisible(false);
		b_rinom.setVisible(false);
		b_elimina.setVisible(false);
		b_elAttr.setVisible(false);
		cb_attr.setVisible(false);
		b_add.setVisible(false);
		b_salva.setVisible(false);

	}

	@FXML
	protected void importaModello(ActionEvent event) throws ClassNotFoundException, SQLException {

		String mod = cb_modello.getValue();
		lv_attr.getItems().clear();

		// pulisco il campo nome che potrebbe già essere compilato
		tf_nome.setVisible(true);
		tf_nome.clear();

		// campi che devono essere sempre invisibili ad una nuova selezione
		b_okRin.setVisible(false);
		b_elAttr.setVisible(false);
		b_salva.setVisible(false);

		if (mod == "Crea nuovo modello") {

			b_crea.setVisible(true);
			tf_nome.setVisible(true);
			tf_nome.setDisable(false);

			b_rinom.setVisible(false);
			cb_attr.setVisible(false);
			b_add.setVisible(false);
			b_elimina.setVisible(false);

			return;

		} else {
			ObservableList<String> listAttr = ModelloController.estraiAttr(mod);
			for (String a : listAttr) {
				lv_attr.getItems().add(a);
			}
			lv_attr.refresh();

			tf_nome.setDisable(true);
			tf_nome.setText(mod);

			b_crea.setVisible(false);

			cb_attr.setVisible(true);
			b_add.setVisible(true);
			b_rinom.setVisible(true);
			b_elimina.setVisible(true);
		}

	}

	@FXML
	protected void creaModello(ActionEvent event) throws ClassNotFoundException, SQLException {

		String mod = tf_nome.getText();

		if (mod == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setHeaderText("Scelta non valida");
			alert.setContentText("E' necessario inserire il nome del modello");
			alert.showAndWait();
			return;
		}
		// (TRUE) il modello già esiste
		if (ModelloController.cercaModello(mod) == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setHeaderText("Scelta non valida");
			alert.setContentText("Un modello con questo nome già esiste");
			alert.showAndWait();
			return;
			// (FALSE) il modello non esiste
		} else {
			tf_nome.setDisable(true);

			b_crea.setVisible(false);

			cb_attr.setVisible(true);
			b_add.setVisible(true);
		}
	}

	@FXML
	protected void addAttrezzatura(ActionEvent event) {

		if (cb_attr.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setHeaderText("Scelta non valida");
			alert.setContentText("E' necessario selezionare un'attrezzatura!");
			alert.showAndWait();
			return;
		}
		for (String attr : lv_attr.getItems()) {
			if (attr.equals(cb_attr.getSelectionModel().getSelectedItem())) {
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

		b_elAttr.setVisible(true);
		b_salva.setVisible(true);
	}

	@FXML
	protected void eliminaAttrezzatura(ActionEvent event) {
		String attr = lv_attr.getSelectionModel().getSelectedItem();
		if (attr != null) {
			lv_attr.getItems().remove(attr);
			if (lv_attr.getItems().size() == 0) {
				b_elAttr.setVisible(false);
				b_salva.setVisible(false);
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setHeaderText("Elemento non selezionato");
			alert.setContentText("Nessun elemento è stato selezionato!");
			alert.showAndWait();
			return;
		}
	}

	@FXML
	protected void eliminaModello(ActionEvent event) throws ClassNotFoundException, SQLException {
		String mod = tf_nome.getText();
		if (mod != null) {
			ModelloController.eliminaModello(mod);
			cb_modello.setItems(ModelloController.estraiModelli());
			cb_modello.getItems().add("Crea nuovo modello");

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Conferma");
			alert.setHeaderText("Eliminazione Completata");
			alert.setContentText("Modello eliminato con successo!");
			alert.showAndWait();

			tf_nome.setDisable(false);
			b_crea.setVisible(true);
			cb_modello.setVisible(true);

			b_elimina.setVisible(false);
			cb_attr.setVisible(false);
			b_add.setVisible(false);
			b_salva.setVisible(false);
		}
	}

	@FXML
	protected void rinominaModello(ActionEvent event) {
		b_rinom.setVisible(false);
		b_crea.setVisible(false);

		tf_nome.setDisable(false);

		b_okRin.setVisible(true);
	}

	@FXML
	protected void okRinomina(ActionEvent event) throws ClassNotFoundException, SQLException {
		String prevName = cb_modello.getValue();
		String nextName = tf_nome.getText();

		if (nextName.equals(prevName)) {
			b_okRin.setVisible(false);
			b_rinom.setVisible(true);
			tf_nome.setDisable(true);
			return;
		}
		for (String modello : ModelloController.estraiModelli()) {
			if (nextName.equals(modello)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Errore");
				alert.setHeaderText("Scelta non valida");
				alert.setContentText("Un modello con questo nome già esiste");
				alert.showAndWait();
				return;
			}
		}

		ModelloController.rinominaModello(prevName, nextName);

		cb_modello.getItems().clear();
		ObservableList<String> items = ModelloController.estraiModelli();
		Collections.sort(items);
		cb_modello.getItems().addAll(items);
		cb_modello.getItems().add("Crea nuovo modello");
		cb_modello.setValue(nextName);

		b_okRin.setVisible(false);
		tf_nome.setDisable(true);
		b_rinom.setVisible(true);
		b_salva.setVisible(true);
		return;

	}

	@FXML
	protected void salvaModello(ActionEvent event) throws ClassNotFoundException, SQLException {
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
			// (TRUE) se il modello già esiste
			if (ModelloController.cercaModello(mod) == true) {
				ModelloController.aggiornaModello(mod, attr);

				// Mando una notifica di conferma
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Conferma");
				alert.setHeaderText("Update effettuato.");
				alert.setContentText("Modello aggiornato con successo!");
				alert.showAndWait();

				return;
			} else {
				ModelloController.salvaModello(mod, attr);

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Conferma");
				alert.setHeaderText("Salvataggio effettuato.");
				alert.setContentText("Modello salvato con successo!");
				alert.showAndWait();

				cb_modello.getItems().clear();
				ObservableList<String> items = ModelloController.estraiModelli();
				Collections.sort(items);
				cb_modello.setItems(items);
				cb_modello.getItems().add("Crea nuovo modello");

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

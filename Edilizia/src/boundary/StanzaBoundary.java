package boundary;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import control.ModelloController;
import control.StanzaController;
import entity.AttrezzaturaModello;
import entity.AttrezzaturaStanza;
import entity.Stanza;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StanzaBoundary {

	@FXML
	private Button b_crea;

	@FXML
	private TextField tf_nome;

	@FXML
	private Button b_okRin;

	@FXML
	private ComboBox<String> cb_edificio;

	@FXML
	private ComboBox<String> cb_piano;

	@FXML
	private ComboBox<String> cb_tipo;

	@FXML
	private ComboBox<String> cb_attr;

	@FXML
	private Button b_add;

	@FXML
	private ComboBox<String> cb_stanza;

	@FXML
	private Button b_rinom;

	@FXML
	private Button b_elimina;

	@FXML
	private Spinner<Integer> sp_quantita;

	@FXML
	private ComboBox<String> cb_modello;

	@FXML
	private Button b_impMod;

	@FXML
	private TableView<AttrezzaturaStanza> tv_attr;

	@FXML
	private TableColumn<AttrezzaturaStanza, String> tc_attr;

	@FXML
	private TableColumn<AttrezzaturaStanza, Integer> tc_quantita;

	@FXML
	private Button b_salva;

	@FXML
	private Button b_elAttr;

	@FXML
	private Button b_back;

	@FXML
	private Button b_modQuantita;

	@FXML
	public void initialize() throws ClassNotFoundException, SQLException, IOException {

		// Inizializzo le tre ComboBox
		cb_attr.setItems(StanzaController.estraiAttrezzatura());
		cb_modello.setItems(StanzaController.estraiModello());

		// ordino la combobox delle stanze
		cb_stanza.getItems().clear();
		ObservableList<String> items = StanzaController.estraiStanza();
		Collections.sort(items);
		cb_stanza.getItems().addAll(items);
		cb_stanza.getItems().add("Crea nuova stanza");

		//cb_edificio.getItems().addAll("A", "B", "C", "D", "E", "F", "G", "H", "I", "L", "M", "N");
		cb_edificio.getItems().addAll(StanzaController.fileEdifici());
		cb_piano.getItems().addAll("T", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		cb_tipo.getItems().addAll("aula", "ufficio", "laboratorio", "sala convegni");

		// nascondo tutte le aree che non possono essere usate prima di
		// scegliere se creare o modificare un modello
		b_add.setVisible(false);
		b_elAttr.setVisible(false);
		b_elimina.setVisible(false);
		b_impMod.setVisible(false);
		sp_quantita.setVisible(false);
		b_salva.setVisible(false);
		b_rinom.setVisible(false);
		cb_modello.setVisible(false);
		cb_attr.setVisible(false);
		b_modQuantita.setVisible(false);
		tf_nome.setVisible(false);
		cb_edificio.setVisible(false);
		cb_piano.setVisible(false);
		cb_tipo.setVisible(false);
		b_crea.setVisible(false);
		b_okRin.setVisible(false);

		// inizializzo la TableView
		tv_attr.getColumns().clear();
		tc_attr.setCellValueFactory(new PropertyValueFactory<AttrezzaturaStanza, String>("attr"));
		tc_quantita.setCellValueFactory(new PropertyValueFactory<AttrezzaturaStanza, Integer>("quantita"));
		// ci sono problemi perchè e colonne hanno elementi di tipo diverso, ma
		// non crea problemi nell'uso dell'applicazione
		tv_attr.getColumns().addAll(tc_attr, tc_quantita);

		// inizializzo lo Spinner
		sp_quantita.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
		sp_quantita.setEditable(true);

		return;
	}

	@FXML
	protected void importaStanza(ActionEvent event) throws ClassNotFoundException, SQLException {

		String stanzaImp = cb_stanza.getValue();
		tv_attr.getItems().clear();

		// rendo visibili i campi necessari
		tf_nome.setVisible(true);
		cb_edificio.setVisible(true);
		cb_piano.setVisible(true);
		cb_tipo.setVisible(true);

		// rendo invisibili i campi che non servono
		cb_attr.setVisible(false);
		b_add.setVisible(false);
		sp_quantita.setVisible(false);
		b_elimina.setVisible(false);
		b_rinom.setVisible(false);

		// abilito i campi che potrebbero essere disabilitati
		tf_nome.setDisable(false);
		cb_edificio.setDisable(false);
		cb_piano.setDisable(false);
		cb_tipo.setDisable(false);

		// pulisco i campi che potrebbero essere già compilati da selezioni
		// precedenti
		tf_nome.clear();
		cb_edificio.setValue(null);
		cb_piano.setValue(null);
		cb_tipo.setValue(null);

		// se viene premuto il campo "crea nuova stanza" apre le impostazioni
		// per crearne una nuova
		if (cb_stanza.getValue() == "Crea nuova stanza") {
			// rendo visibile il pulsante crea con cui vado a fare il controllo
			b_crea.setVisible(true);

			return;
		} else {
			// Prendo i dati della stanza dal DB
			Stanza st = StanzaController.trovaStanza(stanzaImp);
			// Mostro i dati della stanza presi dal db nei campi
			// QUANDO RINOMINO LA STANZA MI DA ERRORE SU QUESTA RIGA MA FUNZIONA
			tf_nome.setText(st.getNome());
			cb_edificio.setValue(st.getEdificio());
			cb_piano.setValue(st.getPiano());
			cb_tipo.setValue(st.getTipo());

			// inserisco nella seconda tabella le attrezzature della stanza
			ObservableList<AttrezzaturaStanza> ListAttrSt = StanzaController.estraiAttrStanza(stanzaImp);
			for (AttrezzaturaStanza as : ListAttrSt) {
				tv_attr.getItems().add(as);
			}
			tv_attr.refresh();

			// impedisco ai campi di essere modificati
			tf_nome.setDisable(true);
			cb_edificio.setDisable(true);
			cb_piano.setDisable(true);
			cb_tipo.setDisable(true);
			// nascondo i campi che non servono
			b_crea.setVisible(false);
			b_impMod.setVisible(false);
			cb_modello.setVisible(false);
			// abilito
			cb_attr.setVisible(true);
			b_add.setVisible(true);
			sp_quantita.setVisible(true);
			b_elimina.setVisible(true);
			b_rinom.setVisible(true);

			return;
		}
	}

	@FXML
	protected void creaStanza(ActionEvent event) throws ClassNotFoundException, SQLException {

		String nomeStanza = tf_nome.getText();
		String edificio = cb_edificio.getSelectionModel().getSelectedItem();
		String piano = cb_piano.getSelectionModel().getSelectedItem();
		String tipo = cb_tipo.getSelectionModel().getSelectedItem();

		if (nomeStanza == null | edificio == null | piano == null | tipo == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setHeaderText("Scelta non valida");
			alert.setContentText("E' necessario inserire nome, edificio, piano e tipo per la nuova stanza");
			;
			alert.showAndWait();
			return;
		}
		// (TRUE) la stanza non esiste
		if (StanzaController.cercaStanza(nomeStanza) == true) {
			// nascondo alcuni campi
			b_crea.setVisible(false);
			// blocco quelli che voglio vedere, ma non modificare
			tf_nome.setDisable(true);
			cb_edificio.setDisable(true);
			cb_piano.setDisable(true);
			cb_tipo.setDisable(true);
			// abilito la possibilità di importare un modello e aggiungere
			// attrezzatura
			cb_modello.setVisible(true);
			b_impMod.setVisible(true);
			cb_attr.setVisible(true);
			b_add.setVisible(true);
			sp_quantita.setVisible(true);
			b_rinom.setVisible(true);
			return;
			// (FALSE) la stanza è già esistente
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setHeaderText("Scelta non valida");
			alert.setContentText("Una stanza con questo nome già esiste");
			alert.showAndWait();
			return;
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
		if (sp_quantita.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setHeaderText("Scelta non valida");
			alert.setContentText("Selezionare una quantità diversa da zero");
			alert.showAndWait();
			return;
		}
		String attrSel = cb_attr.getSelectionModel().getSelectedItem();
		int quantita = sp_quantita.getValue();

		for (AttrezzaturaStanza attr : tv_attr.getItems()) {
			if (attr.getAttr().equals(attrSel)) {
				tv_attr.refresh();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Errore");
				alert.setHeaderText("Scelta non valida");
				alert.setContentText("Hai già inserito questa attrezzatura!");
				alert.showAndWait();
				return;
			}
		}
		tv_attr.getItems().add(StanzaController.aggiungiRiga(attrSel, quantita));
		b_elAttr.setVisible(true);
		b_salva.setVisible(true);

		// Svuoto ComboBox e Spinner
		cb_attr.getSelectionModel().clearSelection();
		sp_quantita.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
		sp_quantita.setEditable(true);
	}

	@FXML
	protected void azzeraParametri(ActionEvent event) {
		sp_quantita.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
		sp_quantita.setEditable(true);
		b_add.setVisible(true);
		b_modQuantita.setVisible(false);
	}

	@FXML
	protected void eliminaAttrezzatura(ActionEvent event) {
		AttrezzaturaStanza attrSel = tv_attr.getSelectionModel().getSelectedItem();
		if (attrSel != null) {
			tv_attr.getItems().remove(attrSel);
			if (tv_attr.getItems().size() == 0) {
				b_elAttr.setVisible(false);
				b_salva.setVisible(false);
			}
			cb_attr.setVisible(true);
			sp_quantita.setVisible(true);
			cb_attr.setVisible(true);
			b_add.setVisible(true);
			b_salva.setVisible(true);
			b_modQuantita.setVisible(false);

			return;
		}
	}

	@FXML
	void importaModello(ActionEvent event) throws ClassNotFoundException, SQLException {
		String modImp = cb_modello.getSelectionModel().getSelectedItem();
		tv_attr.getItems().clear();
		if (cb_modello.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setHeaderText("Scelta non valida");
			alert.setContentText("E' necessario selezionare un modello!");
			alert.showAndWait();
			return;
		}
		// cerco il modello nel DB ed estraggo i dati
		ObservableList<AttrezzaturaModello> attrMod = ModelloController.trovaModello(modImp);

		for (AttrezzaturaModello am : attrMod) {
			String s = am.getAttr();
			AttrezzaturaStanza as = new AttrezzaturaStanza(s, tf_nome.getText(), 0);
			tv_attr.getItems().add(as);
		}
		tv_attr.refresh();
		b_salva.setVisible(true);
	}

	@FXML
	void modificaQuantita(MouseEvent event) {
		if (tv_attr.getSelectionModel().getSelectedItem() != null) {
			int qta = tv_attr.getSelectionModel().getSelectedItem().getQuantita();
			cb_attr.setValue(tv_attr.getSelectionModel().getSelectedItem().getAttr());
			sp_quantita.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, qta));
			b_modQuantita.setVisible(true);
			b_elAttr.setVisible(true);
			sp_quantita.setVisible(true);
			b_add.setVisible(false);
		}
		return;
	}

	@FXML
	void cambiaQuantita(ActionEvent event) {
		int qt = sp_quantita.getValue();
		String a = tv_attr.getSelectionModel().getSelectedItem().getAttr();
		AttrezzaturaStanza as = new AttrezzaturaStanza(a, null, qt);
		tv_attr.getItems().remove(tv_attr.getSelectionModel().getSelectedItem());
		tv_attr.getItems().add(as);
		tv_attr.refresh();
		// ripristino lo Spinner
		sp_quantita.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
		sp_quantita.setEditable(true);
		// riabilito i pulsanti
		b_modQuantita.setVisible(false);
		b_elAttr.setVisible(false);
		cb_attr.setVisible(true);
		b_add.setVisible(true);
		b_salva.setVisible(true);
	}

	@FXML
	protected void rinominaStanza(ActionEvent event) {
		tf_nome.setVisible(true);
		tf_nome.setDisable(false);
		cb_edificio.setVisible(true);
		cb_piano.setVisible(true);
		cb_tipo.setVisible(true);
		b_rinom.setVisible(false);
		b_okRin.setVisible(true);

		return;
	}

	@FXML
	protected void okRinomina(ActionEvent event) throws ClassNotFoundException, SQLException {
		String prevName = cb_stanza.getValue();
		String nextName = tf_nome.getText();

		if (prevName == nextName) {
			b_okRin.setVisible(false);
			b_rinom.setVisible(true);
			tf_nome.setDisable(true);
			return;
		}
		for (String stanza : cb_stanza.getItems()) {
			if (nextName.equals(stanza)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Errore");
				alert.setHeaderText("Scelta non valida");
				alert.setContentText("Una stanza con questo nome già esiste");
				alert.showAndWait();
				return;
			}
		}
		StanzaController.rinominaStanza(prevName, nextName);

		// modifico la combobox riordinandola
		cb_stanza.getItems().clear();
		ObservableList<String> items = StanzaController.estraiStanza();
		Collections.sort(items);
		cb_stanza.getItems().addAll(items);
		cb_stanza.getItems().add("Crea nuova stanza");
		cb_stanza.setValue(nextName);
		importaStanza(event);

		b_okRin.setVisible(false);
		tf_nome.setDisable(true);
		b_rinom.setVisible(true);
		b_salva.setVisible(true);
		return;

	}

	@FXML
	protected void eliminaStanza(ActionEvent event) throws ClassNotFoundException, SQLException {
		String StanzaSel = cb_stanza.getSelectionModel().getSelectedItem();
		if (StanzaSel != null) {
			StanzaController.eliminaStanza(StanzaSel);
			cb_stanza.setItems(StanzaController.estraiStanza());
			cb_stanza.getItems().add("Crea nuova stanza");

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Conferma");
			alert.setHeaderText("Eliminazione Completata");
			alert.setContentText("Stanza eliminata con successo!");
			alert.showAndWait();

			b_add.setVisible(false);
			b_elAttr.setVisible(false);
			b_elimina.setVisible(false);
			b_impMod.setVisible(false);
			sp_quantita.setVisible(false);
			b_salva.setVisible(false);
			b_rinom.setVisible(false);
			cb_modello.setVisible(false);
			cb_attr.setVisible(false);
			b_modQuantita.setVisible(false);
			return;
		}

	}

	@FXML
	protected void salvaStanza(ActionEvent event) throws ClassNotFoundException, SQLException {
		// Memorizzo tutte le attrezzature in una lista
		ObservableList<AttrezzaturaStanza> attrSt = tv_attr.getItems();
		String stanza = tf_nome.getText();
		String edificio = cb_edificio.getSelectionModel().getSelectedItem();
		String piano = cb_piano.getSelectionModel().getSelectedItem();
		String tipo = cb_tipo.getSelectionModel().getSelectedItem();

		if (attrSt == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setHeaderText("Informazioni incomplete.");
			alert.setContentText("Per creare una nuova stanza è necessario inserire almeno un'attrezzatura.");
			alert.showAndWait();
			return;
		} else {
			// se la stanza già esiste (FALSE)
			if (StanzaController.cercaStanza(stanza) == false) {
				StanzaController.aggiornaStanza(stanza, attrSt);
				// Mando una notifica di conferma
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Conferma");
				alert.setHeaderText("Update effettuato.");
				alert.setContentText("Stanza aggiornata con successo!");
				alert.showAndWait();

				return;
			} else {
				// se NON esiste (TRUE) la aggiungo semplicemente
				// Passo questa lista ad una funzione che la memorizza nel db
				// sotto al nome della stanza
				StanzaController.salvaStanza(stanza, edificio, piano, tipo, attrSt);
				// Mando una notifica di conferma
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Conferma");
				alert.setHeaderText("Salvataggio effettuato.");
				alert.setContentText("Stanza creata con successo!");
				alert.showAndWait();

				cb_stanza.getItems().clear();
				ObservableList<String> items = StanzaController.estraiStanza();
				Collections.sort(items);
				cb_stanza.setItems(items);
				cb_stanza.getItems().add("Crea nuova stanza");

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
		return;
	}

}

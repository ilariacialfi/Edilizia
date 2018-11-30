package boundary;

import java.io.IOException;
import java.sql.SQLException;

import control.CercaController;
import entity.Attrezzatura;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CercaBoundary {

	@FXML
	private ComboBox<String> cb_attr;

	@FXML
	private Spinner<Integer> sp_min;

	@FXML
	private Spinner<Integer> sp_max;

	@FXML
	private Button b_cerca;

	@FXML
	private Button b_del;

	@FXML
	private Button b_addAttr;

	@FXML
	private TableView<Attrezzatura> tv_attr;

	@FXML
	private TableColumn<Attrezzatura, String> tc_attr;

	@FXML
	private TableColumn<Attrezzatura, Integer> tc_min;

	@FXML
	private TableColumn<Attrezzatura, Integer> tc_max;

	@FXML
	private TableView<Stanza> tv_stanza;

	@FXML
	private TableColumn<Stanza, String> tc_nome;

	@FXML
	private TableColumn<Stanza, String> tc_edificio;

	@FXML
	private TableColumn<Stanza, String> tc_piano;

	@FXML
	private TableColumn<Stanza, String> tc_tipo;

	@FXML
	private TableView<AttrezzaturaStanza> tv_attr_stanza;

	@FXML
	private TableColumn<AttrezzaturaStanza, String> tc_attr_stanza;

	@FXML
	private TableColumn<AttrezzaturaStanza, Integer> tc_quantita;

	@FXML
	private Button b_creaModello;

	@FXML
	private Button b_creaStanza;

	@FXML
	private Button b_exit;

	// inizializza i dati della finestra
	@FXML
	public void initialize() throws ClassNotFoundException, SQLException {

		// Inizializzo la ComboBox delle attrezzature dal DB
		cb_attr.setItems(CercaController.estraiAttrezzatura());

		// inizializzo le TableView e le colonne
		tv_attr.getColumns().clear();
		tc_attr.setCellValueFactory(new PropertyValueFactory<Attrezzatura, String>("nome"));
		tc_min.setCellValueFactory(new PropertyValueFactory<Attrezzatura, Integer>("min"));
		tc_max.setCellValueFactory(new PropertyValueFactory<Attrezzatura, Integer>("max"));
		tv_attr.getColumns().addAll(tc_attr, tc_min, tc_max);

		tv_stanza.getColumns().clear();
		tc_nome.setCellValueFactory(new PropertyValueFactory<Stanza, String>("nome"));
		tc_edificio.setCellValueFactory(new PropertyValueFactory<Stanza, String>("edificio"));
		tc_piano.setCellValueFactory(new PropertyValueFactory<Stanza, String>("piano"));
		tc_tipo.setCellValueFactory(new PropertyValueFactory<Stanza, String>("tipo"));
		tv_stanza.getColumns().addAll(tc_nome, tc_edificio, tc_piano, tc_tipo);

		tv_attr_stanza.getColumns().clear();
		tc_attr_stanza.setCellValueFactory(new PropertyValueFactory<AttrezzaturaStanza, String>("attr"));
		tc_quantita.setCellValueFactory(new PropertyValueFactory<AttrezzaturaStanza, Integer>("quantita"));
		tv_attr_stanza.getColumns().addAll(tc_attr_stanza, tc_quantita);

		// inizializzo gli Spinner
		sp_min.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
		sp_min.setEditable(true);
		sp_max.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
		sp_max.setEditable(true);

		// disabilito i pulsanti che non possono essere usati inizialmente
		b_cerca.setDisable(true);
		b_del.setDisable(true);

	}

	// Cliccando su "Aggiungi" si inserisce una riga nella tabella delle
	// attrezzature
	@FXML
	protected void aggiungiAttrezzatura(ActionEvent event) {
		if (cb_attr.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setHeaderText("Scelta non valida");
			alert.setContentText("E' necessario selezionare un'attrezzatura!");
			alert.showAndWait();
			return;
		}
		if (sp_max.getValue() <= sp_min.getValue()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setHeaderText("Scelta non valida");
			alert.setContentText("Errore nell'intervallo scelto!");
			alert.showAndWait();
			return;
		}
		// Memorizzo i valori di ComboBox e Spinners in delle variabili
		String attrSel = cb_attr.getSelectionModel().getSelectedItem();
		int minSel = sp_min.getValue();
		int maxSel = sp_max.getValue();

		// Mostro il contenuto nella TableView
		for (Attrezzatura attr : tv_attr.getItems()) {
			// Se inserisco un'attrezzatura uguale ad una già inserita mando un
			// Alert
			if (attr.getNome().equals(attrSel)) {
				tv_attr.refresh();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Errore");
				alert.setHeaderText("Scelta non valida");
				alert.setContentText("Hai già inserito questa attrezzatura!");
				alert.showAndWait();
				return;
			}
		}
		// Aggiungo l'elemento alla tabella delle caratteristiche
		tv_attr.getItems().add(CercaController.addRowAttr(attrSel, minSel, maxSel));
		b_cerca.setDisable(false);
		b_del.setDisable(false);

		// Svuoto la ComboBox e gli Spinner
		cb_attr.getSelectionModel().clearSelection();
		sp_min.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
		sp_min.setEditable(true);
		sp_max.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
		sp_max.setEditable(true);

	}

	@FXML
	protected void eliminaAttrezzatura(ActionEvent event) {

		Attrezzatura attrSel = tv_attr.getSelectionModel().getSelectedItem();
		if (attrSel != null) {
			tv_attr.getItems().remove(attrSel);
			if (tv_attr.getItems().size() == 0) {
				b_del.setDisable(true);
				b_cerca.setDisable(true);
			}
		}
	}

	@FXML
	protected void cercaStanze(ActionEvent event) throws ClassNotFoundException, SQLException {
		// Svuoto la tabella prima di ripopolarla
		tv_stanza.getItems().clear();

		// Prendo tutte le righe della tabella e ne faccio una lista
		ObservableList<Attrezzatura> listAttr = tv_attr.getItems();

		// creo una ObservableList di stanze dai risultati del DB
		ObservableList<Stanza> listStanzeTrovate = CercaController.cercaStanze(listAttr);

		// inserisco le stanze nella tabella
		for (Stanza s : listStanzeTrovate) {
			tv_stanza.getItems().add(s);
		}
		tv_stanza.refresh();
	}

	@FXML
	void visualizzaAttrStanza(MouseEvent event) throws ClassNotFoundException, SQLException {
		// Svuoto la tabella prima di ripopolarla
		tv_attr_stanza.getItems().clear();

		// prendo il nome della stanza selezionata
		String st = tv_stanza.getSelectionModel().getSelectedItem().getNome();
		// restituisco l'attrezzatura della stanza
		ObservableList<AttrezzaturaStanza> listAttrSt = CercaController.cercaAttr(st);
		// inserisco l'attrezzatura nella tabella
		for (AttrezzaturaStanza as : listAttrSt) {
			tv_attr_stanza.getItems().add(as);
		}
		tv_attr_stanza.refresh();
	}

	// Cliccando "Crea/Modifica Modello" si passa all'interfaccia di modifica
	// modello
	@FXML
	protected void creaModello(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) b_creaModello.getScene().getWindow();
		primaryStage.close();

		Parent parent = FXMLLoader.load(getClass().getResource(("/fxml/Modello.fxml")));
		Scene scene = new Scene(parent);
		Stage newStage = new Stage();
		newStage.setScene(scene);
		newStage.setTitle("Crea il tuo modello di stanza");
		newStage.setResizable(false);
		newStage.show();
	}

	// Cliccando "Crea/Modifica Stanza" si passa all'interfaccia di modifica
	// stanza
	@FXML
	protected void creaStanza(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) b_creaStanza.getScene().getWindow();
		primaryStage.close();

		Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Stanza.fxml"));
		Scene scene = new Scene(parent);
		Stage newStage = new Stage();
		newStage.setScene(scene);
		newStage.setTitle("Crea la tua stanza");
		newStage.setResizable(false);
		newStage.show();
	}

	// Cliccando "Esci" si esce dall'applicazione
	@FXML
	protected void exit(ActionEvent event) {
		Stage stage = (Stage) b_exit.getScene().getWindow();
		stage.close();
	}

}

package boundary;

import java.io.IOException;
import java.sql.SQLException;
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
    private Button b_impSt;

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
    public void initialize() throws ClassNotFoundException, SQLException {
    	
    	//Inizializzo le tre ComboBox
    	cb_attr.setItems(StanzaController.estraiAttrezzatura());
    	cb_modello.setItems(StanzaController.estraiModello());
    	cb_stanza.setItems(StanzaController.estraiStanza());

    	cb_edificio.getItems().addAll("A", "B", "C", "D", "E", "F", "G", "H", "I", "L", "M", "N");
    	cb_piano.getItems().addAll("T", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    	cb_tipo.getItems().addAll("aula", "ufficio", "laboratorio");
    	
    	
    	//disabilito tutte le aree che non possono essere usate prima di scegliere se creare o modificare un modello
    	b_add.setDisable(true);
    	b_elAttr.setDisable(true);
    	b_elimina.setDisable(true);
    	b_impMod.setDisable(true);
    	sp_quantita.setDisable(true);
    	b_salva.setDisable(true);
    	b_rinom.setDisable(true);
    	cb_modello.setDisable(true);
    	cb_attr.setDisable(true);
    	b_modQuantita.setVisible(false);
    	
    	//inizializzo la TableView
    	tv_attr.getColumns().clear();
    	tc_attr.setCellValueFactory(new PropertyValueFactory<AttrezzaturaStanza, String>("attr"));
    	tc_quantita.setCellValueFactory(new PropertyValueFactory<AttrezzaturaStanza, Integer>("quantita"));
    	tv_attr.getColumns().addAll(tc_attr, tc_quantita);
    	
    	//inizializzo lo Spinner
    	sp_quantita.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
    	sp_quantita.setEditable(true);
    }

    @FXML
    protected void creaStanza(ActionEvent event) throws ClassNotFoundException, SQLException {
    	String nomeStanza = tf_nome.getText();
    	String edificio = cb_edificio.getSelectionModel().getSelectedItem();
    	String piano = cb_piano.getSelectionModel().getSelectedItem();
    	String tipo = cb_tipo.getSelectionModel().getSelectedItem();
    	
    	if (nomeStanza == null | edificio == null | piano == null | tipo == null){
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Errore");
    		alert.setHeaderText("Scelta non valida");
    		alert.setContentText("E' necessario inserire nome, edificio, piano e tipo per la nuova stanza");;
    		alert.showAndWait();
    		return;
    	}
    	//se il risultato è true la stanza ricercata NON ESISTE
    	if (StanzaController.cercaStanza(nomeStanza) == true){
	    		//disabilito alcuni campi
				cb_stanza.setDisable(true);
				tf_nome.setDisable(true);
				cb_edificio.setDisable(true);
				cb_piano.setDisable(true);
				cb_tipo.setDisable(true);
				b_impSt.setDisable(true);
				//riabilito altro
	    		cb_modello.setDisable(false);
	    		b_impMod.setDisable(false);
	    		cb_attr.setDisable(false);
	    		b_add.setDisable(false);
	    		sp_quantita.setDisable(false);
	    		b_rinom.setDisable(false);
	    		return;
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
    	if (sp_quantita.getValue() == null){
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Errore");
    		alert.setHeaderText("Scelta non valida");
    		alert.setContentText("Selezionare una quantità diversa da zero");
    		alert.showAndWait();
    		return;
    	}
    	String attrSel = cb_attr.getSelectionModel().getSelectedItem();
    	int quantita = sp_quantita.getValue();
    	
    	for (AttrezzaturaStanza attr : tv_attr.getItems()){
    		if (attr.getAttr().equals(attrSel)){
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
    	b_elAttr.setDisable(false);
    	b_salva.setDisable(false);
    	
    	//Svuoto ComboBox e Spinner
    	cb_attr.getSelectionModel().clearSelection();
    	sp_quantita.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
    	sp_quantita.setEditable(true);
    }

    @FXML
    protected void eliminaAttrezzatura(ActionEvent event) {
    	AttrezzaturaStanza attrSel = tv_attr.getSelectionModel().getSelectedItem();
    	if (attrSel != null){
    		tv_attr.getItems().remove(attrSel);
    		if (tv_attr.getItems().size() == 0){
    			b_elAttr.setDisable(true);
    			b_salva.setDisable(true);
    		}
    	}
    }



    @FXML
    protected void importaStanza(ActionEvent event) throws ClassNotFoundException, SQLException {
    	String stanzaImp = cb_stanza.getSelectionModel().getSelectedItem();
    	tv_attr.getItems().clear();
    	
    	if (cb_stanza.getValue() == null){
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Errore");
    		alert.setHeaderText("Scelta non valida");
    		alert.setContentText("E' necessario selezionare una stanza!");
    		alert.showAndWait();
    		return;
    	}
    	//Prendo i dati della stanza dal DB
    	Stanza st = StanzaController.trovaStanza(stanzaImp);
    	//Mostro i dati della stanza
    	tf_nome.setText(st.getNome());
    	cb_edificio.setPromptText(st.getEdificio());
    	cb_piano.setPromptText(st.getPiano());
    	cb_tipo.setPromptText(st.getTipo());
    	//inserisco nella tabella le attrezzature della stanza
    	ObservableList<AttrezzaturaStanza> ListAttrSt = StanzaController.estraiAttrStanza(stanzaImp);
    	for (AttrezzaturaStanza as : ListAttrSt){
    		tv_attr.getItems().add(as);
    	}
    	tv_attr.refresh();
    	//impedisco ai campi di essere modificati
    	tf_nome.setDisable(true);
    	cb_edificio.setDisable(true);
    	cb_piano.setDisable(true);
    	cb_tipo.setDisable(true);
    	b_crea.setDisable(true);
    	b_impSt.setDisable(true);
    	cb_stanza.setDisable(true);
    	b_impMod.setDisable(true);
    	cb_modello.setDisable(true);
    	//abilito 
    	cb_attr.setDisable(false);
    	b_add.setDisable(false);
    	sp_quantita.setDisable(false);
    	b_elimina.setDisable(false);
    	b_rinom.setDisable(false);
    }
    
    @FXML
    void importaModello(ActionEvent event) throws ClassNotFoundException, SQLException {
    	String modImp = cb_modello.getSelectionModel().getSelectedItem();
    	tv_attr.getItems().clear();
    	if (cb_modello.getValue() == null){
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Errore");
    		alert.setHeaderText("Scelta non valida");
    		alert.setContentText("E' necessario selezionare un modello!");
    		alert.showAndWait();
    		return;
    	}
    	//cerco il modello nel DB ed estraggo i dati
    	ObservableList<AttrezzaturaModello> attrMod = ModelloController.trovaModello(modImp);
    	
    	for (AttrezzaturaModello am : attrMod){
    		String s = am.getAttr();
    		AttrezzaturaStanza as = new AttrezzaturaStanza(s, tf_nome.getText(), 0);
    		tv_attr.getItems().add(as);
    	}
    	tv_attr.refresh();
    	b_salva.setDisable(false);
    }
    
    @FXML
    void modificaQuantita(MouseEvent event) {
    	if (tv_attr.getSelectionModel().getSelectedItem() != null){
			b_modQuantita.setVisible(true);
			b_elAttr.setDisable(false);
			sp_quantita.setDisable(false);
			cb_attr.setDisable(true);
			b_add.setDisable(true);
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
    	//ripristino lo Spinner
    	sp_quantita.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
    	sp_quantita.setEditable(true);
    	//riabilito i pulsanti
    	b_modQuantita.setVisible(false);
    	b_elAttr.setDisable(true);
    	cb_attr.setDisable(false);
    	b_add.setDisable(false);
    	b_salva.setDisable(false);
    }

    //ATTENZIONE VA RINOMINATA NEL DB
    @FXML
    protected void rinominaStanza(ActionEvent event) {
    	//Mi attiva solo il TextField in cui ho il nome scritto da me precedentemente, oppure quello preso dalla combobox
    	tf_nome.setDisable(false);
    }

    @FXML
    protected void eliminaStanza(ActionEvent event) throws ClassNotFoundException, SQLException {
    	String StanzaSel = cb_stanza.getSelectionModel().getSelectedItem();
    	if (StanzaSel != null){
    		StanzaController.eliminaStanza(StanzaSel);
    		cb_stanza.setItems(StanzaController.estraiStanza());
    		
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Conferma");
    		alert.setHeaderText("Eliminazione Completata");
    		alert.setContentText("Stanza eliminata con successo!");
    		alert.showAndWait();
    		
    		b_add.setDisable(true);
        	b_elAttr.setDisable(true);
        	b_elimina.setDisable(true);
        	b_impMod.setDisable(true);
        	sp_quantita.setDisable(true);
        	b_salva.setDisable(true);
        	b_rinom.setDisable(true);
        	cb_modello.setDisable(true);
        	cb_attr.setDisable(true);
        	b_modQuantita.setVisible(false);
    		return;
    	}
    	
    }
    
    @FXML
    protected void salvaStanza(ActionEvent event) throws ClassNotFoundException, SQLException {
    	//Memorizzo tutte le attrezzature in una lista
    	ObservableList<AttrezzaturaStanza> attrSt = tv_attr.getItems();
    	String stanza = tf_nome.getText();
    	String edificio = cb_edificio.getSelectionModel().getSelectedItem();
    	String piano = cb_piano.getSelectionModel().getSelectedItem();
    	String tipo = cb_tipo.getSelectionModel().getSelectedItem();
    	
    	if (attrSt == null){
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Errore");
    		alert.setHeaderText("Informazioni incomplete.");
    		alert.setContentText("Per creare una nuova stanza è necessario inserire almeno un'Mattrezzatura.");
    		alert.showAndWait();
    		return;
    	} else {
    		//se la stanza già esiste (FALSE) nel database vado solo a modificare i campi e ad aggiungerne altri 
    		if (StanzaController.cercaStanza(stanza) == false) {
    			StanzaController.aggiornaStanza(stanza, attrSt);
    			//Mando una notifica di conferma
	    		Alert alert = new Alert(AlertType.CONFIRMATION);
	    		alert.setTitle("Conferma");
	    		alert.setHeaderText("Update effettuato.");
	    		alert.setContentText("Stanza aggiornata con successo!");
	    		alert.showAndWait();
	    		initialize();
	    		return;
    		} else {
	    		//se NON esiste (TRUE) la aggiungo semplicemente
	    		//Passo questa lista ad una funzione che la memorizza nel db sotto al nome della stanza
	    		StanzaController.salvaStanza(stanza, edificio, piano, tipo, attrSt);
	    		//Mando una notifica di conferma
	    		Alert alert = new Alert(AlertType.CONFIRMATION);
	    		alert.setTitle("Conferma");
	    		alert.setHeaderText("Salvataggio effettuato.");
	    		alert.setContentText("Stanza creata con successo!");
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

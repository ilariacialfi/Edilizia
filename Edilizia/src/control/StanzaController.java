package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import dao.AttrezzaturaDAO;
import dao.AttrezzaturaStanzaDAO;
import dao.ModelloDAO;
import dao.StanzaDAO;
import entity.AttrezzaturaStanza;
import entity.Stanza;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StanzaController {

	public static ObservableList<String> estraiAttrezzatura() throws ClassNotFoundException, SQLException {
		return FXCollections.observableArrayList(AttrezzaturaDAO.getInstance().getAttrezzatura());
	}

	public static ObservableList<String> estraiModello() throws ClassNotFoundException, SQLException {
		return FXCollections.observableArrayList(ModelloDAO.getInstance().getModello());
	}

	public static ObservableList<String> estraiStanza() throws ClassNotFoundException, SQLException {
		return FXCollections.observableArrayList(StanzaDAO.getInstance().getStanza());
	}

	public static boolean cercaStanza(String nomeStanza) throws SQLException, ClassNotFoundException {
		if (StanzaDAO.getInstance().cercaStanza(nomeStanza) == null) {
			// se ha restituito null la stanza non esiste quindi può essere
			// creata
			return true;
		}
		return false;
	}

	public static AttrezzaturaStanza aggiungiRiga(String attrSel, int quantita) {
		return new AttrezzaturaStanza(attrSel, null, quantita);
	}

	public static Stanza trovaStanza(String stanzaImp) throws ClassNotFoundException, SQLException {
		return StanzaDAO.getInstance().getStanzaByName(stanzaImp);
	}

	public static ObservableList<AttrezzaturaStanza> estraiAttrStanza(String stanzaImp)
			throws ClassNotFoundException, SQLException {
		return FXCollections.observableArrayList(AttrezzaturaStanzaDAO.getInstance().getAttrSt(stanzaImp));
	}

	public static void eliminaStanza(String stanzaSel) throws ClassNotFoundException, SQLException {
		StanzaDAO.getInstance().eliminaStanza(stanzaSel);
		AttrezzaturaStanzaDAO.getInstance().eliminaStanza(stanzaSel);
		return;
	}

	public static void salvaStanza(String stanza, String edificio, String piano, String tipo,
			ObservableList<AttrezzaturaStanza> attrSt) throws ClassNotFoundException, SQLException {
		// faccio salvare la stanza nelle due diverse tabelle del db
		AttrezzaturaStanzaDAO.getInstance().salvaStanza(stanza, attrSt);
		StanzaDAO.getInstance().salvaStanza(stanza, edificio, piano, tipo);
		return;
	}

	public static void aggiornaStanza(String stanza, ObservableList<AttrezzaturaStanza> attrSt)
			throws ClassNotFoundException, SQLException {
		AttrezzaturaStanzaDAO.getInstance().aggiornaStanza(stanza, attrSt);
		return;
	}

	public static void rinominaStanza(String prevName, String nextName) throws ClassNotFoundException, SQLException {
		AttrezzaturaStanzaDAO.getInstance().rinominaStanza(prevName, nextName);
		StanzaDAO.getInstance().rinominaStanza(prevName, nextName);
		return;
	}

	public static ArrayList<String> fileEdifici() throws IOException {
		String s = new String();
		File file = new File("Edifici.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String> edifici = new ArrayList<>();
		while ((s = br.readLine())!=null){
			edifici.add(s);
		}
		br.close();
		return edifici;
	}
	
	public static ArrayList<String> filePiani() throws IOException {
		String s = new String();
		File file = new File("Piani.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String> piani = new ArrayList<>();
		while ((s = br.readLine())!=null){
			piani.add(s);
		}
		br.close();
		return piani;
	}
	
	public static ArrayList<String> fileTipi() throws IOException {
		String s = new String();
		File file = new File("Tipi.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String> tipi = new ArrayList<>();
		while ((s = br.readLine())!=null){
			tipi.add(s);
		}
		br.close();
		return tipi;
	}

}

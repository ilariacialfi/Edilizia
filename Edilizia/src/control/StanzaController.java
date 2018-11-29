package control;

import java.sql.SQLException;
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

}

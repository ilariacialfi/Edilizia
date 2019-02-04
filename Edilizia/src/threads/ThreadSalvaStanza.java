package threads;

import java.sql.SQLException;

import dao.AttrezzaturaStanzaDAO;
import dao.StanzaDAO;
import entity.AttrezzaturaStanza;
import javafx.collections.ObservableList;

public class ThreadSalvaStanza implements Runnable{
	private String stanza;
	private String edificio;
	private String piano;
	private String tipo;
	private ObservableList<AttrezzaturaStanza> attrSt;
	
	public ThreadSalvaStanza(String stanza, String edificio, String piano, String tipo,
			ObservableList<AttrezzaturaStanza> attrSt) {
		this.stanza = stanza;
		this.edificio = edificio;
		this.piano = piano;
		this.tipo = tipo;
		this.attrSt = attrSt;
	}
	
	public void run() {
		try {
			// faccio salvare la stanza nelle due diverse tabelle del db
			AttrezzaturaStanzaDAO.getInstance().salvaStanza(stanza, attrSt);
			StanzaDAO.getInstance().salvaStanza(stanza, edificio, piano, tipo);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}

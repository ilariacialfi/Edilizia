package threads;

import java.sql.SQLException;

import dao.AttrezzaturaStanzaDAO;
import entity.AttrezzaturaStanza;
import javafx.collections.ObservableList;

public class ThreadAggiornaStanza implements Runnable{

	private String stanza;
	private ObservableList<AttrezzaturaStanza> attrSt;
	
	public ThreadAggiornaStanza(String stanza, ObservableList<AttrezzaturaStanza> attrSt) {
		this.stanza = stanza;
		this.attrSt = attrSt;
	}
	
	public void run(){
		try {
			AttrezzaturaStanzaDAO.getInstance().aggiornaStanza(stanza, attrSt);
		} catch (ClassNotFoundException | SQLException e){
			e.printStackTrace();
		}
	}
	
}

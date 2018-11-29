package control;

import java.sql.SQLException;

import dao.AttrezzaturaDAO;
import dao.AttrezzaturaStanzaDAO;
import entity.Attrezzatura;
import entity.AttrezzaturaStanza;
import entity.Stanza;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CercaController {

	public static ObservableList<String> estraiAttrezzatura() throws ClassNotFoundException, SQLException {
		return FXCollections.observableArrayList(AttrezzaturaDAO.getInstance().getAttrezzatura());
	}

	public static Attrezzatura addRowAttr(String attr, int min, int max) {
		return new Attrezzatura(attr, min, max);
	}

	public static ObservableList<Stanza> cercaStanze(ObservableList<Attrezzatura> listAttr)
			throws ClassNotFoundException, SQLException {
		return FXCollections.observableArrayList(AttrezzaturaStanzaDAO.getInstance().getStanzaConAttr(listAttr));
	}

	public static ObservableList<AttrezzaturaStanza> cercaAttr(String st) throws ClassNotFoundException, SQLException {
		return FXCollections.observableArrayList(AttrezzaturaStanzaDAO.getInstance().getAttrSt(st));
	}
}

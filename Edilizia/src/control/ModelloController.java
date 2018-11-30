package control;

import java.sql.SQLException;

import dao.AttrezzaturaDAO;
import dao.AttrezzaturaModelloDAO;
import dao.ModelloDAO;
import entity.AttrezzaturaModello;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModelloController {

	public static ObservableList<AttrezzaturaModello> trovaModello(String modImp)
			throws ClassNotFoundException, SQLException {
		return FXCollections.observableArrayList(AttrezzaturaModelloDAO.getInstance().getModelloByName(modImp));
	}

	public static ObservableList<String> estraiAttrezzatura() throws ClassNotFoundException, SQLException {
		return FXCollections.observableArrayList(AttrezzaturaDAO.getInstance().getAttrezzatura());
	}

	public static ObservableList<String> estraiModelli() throws ClassNotFoundException, SQLException {
		return FXCollections.observableArrayList(ModelloDAO.getInstance().getModello());
	}

	public static boolean cercaModello(String mod) throws ClassNotFoundException, SQLException {
		// true se il modello gia esiste
		return ModelloDAO.getInstance().cercaModello(mod);
	}

	public static ObservableList<String> estraiAttr(String mod) throws ClassNotFoundException, SQLException {
		return FXCollections.observableArrayList(AttrezzaturaModelloDAO.getInstance().getAttrezzatura(mod));
	}

	public static void eliminaModello(String mod) throws ClassNotFoundException, SQLException {
		AttrezzaturaModelloDAO.getInstance().eliminaModello(mod);
	}

	public static void aggiornaModello(String mod, ObservableList<String> attrMod)
			throws ClassNotFoundException, SQLException {
		AttrezzaturaModelloDAO.getInstance().aggiornaModello(mod, attrMod);
	}

	public static void salvaModello(String mod, ObservableList<String> attrMod)
			throws ClassNotFoundException, SQLException {
		AttrezzaturaModelloDAO.getInstance().salvaModello(mod, attrMod);
	}

	public static void rinominaModello(String prevName, String nextName) throws ClassNotFoundException, SQLException {
		AttrezzaturaModelloDAO.getInstance().rinominaModello(prevName, nextName);
		return;
	}

}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import control.ControllerDB;
import entity.AttrezzaturaModello;
import javafx.collections.ObservableList;

public class AttrezzaturaModelloDAO {

	private static final String TROVA_MOD_ATTR = "SELECT * FROM attrezzatura_modello WHERE modello = ?";
	private static final String ELIMINA_MODELLO = "DELETE FROM attrezzatura_modello WHERE modello = ?";	
	private static final String UPDATE_MODELLO = "UPDATE attrezzatura_modello SET attrezzatura = ? WHERE modello = ?";
	private static final String SALVA_MODELLO = "INSERTO INTO attrezzatura_modello (modello, attrezzatura) VALUES (?, ?)";
	
	private static AttrezzaturaModelloDAO instance = null;
	private ResultSet rs = null;
	private PreparedStatement pstmn = null;
	
	private AttrezzaturaModelloDAO(){
	}
	
	public static AttrezzaturaModelloDAO getInstance(){
		if (instance == null){
			return instance = new AttrezzaturaModelloDAO();
		}
		return instance;
	}

	public ArrayList<AttrezzaturaModello> getModelloByName(String modImp) throws ClassNotFoundException, SQLException {
		ArrayList<AttrezzaturaModello> listAttr = null;
		
		try {
			Connection conn = ControllerDB.getInstance().connect();
			pstmn = conn.prepareStatement(TROVA_MOD_ATTR);
			pstmn.setString(1, modImp);
			rs = pstmn.executeQuery();
			
			listAttr = new ArrayList<>();
			while (rs.next()){
				listAttr.add(new AttrezzaturaModello(rs.getString("attrezzatura"), modImp));
			}
			
		} catch (SQLException se){
			se.printStackTrace();
		} finally {
			if (! rs.isClosed()){
				rs.close();
			}
			if (! pstmn.isClosed()){
				pstmn.close();
			}
		}
		return listAttr;
	}

	public ArrayList<String> getAttrezzatura(String mod) throws ClassNotFoundException, SQLException {
		ArrayList<String> attr = null;
		try {
			Connection conn = ControllerDB.getInstance().connect();
			pstmn = conn.prepareStatement(TROVA_MOD_ATTR);
			pstmn.setString(1, mod);
			rs = pstmn.executeQuery();
			
			attr = new ArrayList<>();
			while (rs.next()){
				attr.add(rs.getString("attrezzatura"));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (! rs.isClosed()) {
				rs.close();
			}
			if (! pstmn.isClosed()) {
				pstmn.close();
			}
		}
		
		return attr;
	}

	public synchronized void eliminaModello(String mod) throws ClassNotFoundException, SQLException {
		try {
			Connection conn = ControllerDB.getInstance().connect();
			pstmn = conn.prepareStatement(ELIMINA_MODELLO);
			pstmn.setString(1, mod);
			pstmn.executeUpdate();
			
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (! pstmn.isClosed()) {
				pstmn.close();
			}
		}

	}

	//ATTENZIONE QUERY SBAGLIATA DEVO DARE LA LISTA DI ATTRIBUTI CONTROLLA ANCHE STANZA
	public synchronized void aggiornaModello(String mod, ObservableList<String> attrMod) throws SQLException, ClassNotFoundException {
		try {
			Connection conn = ControllerDB.getInstance().connect();
			pstmn = conn.prepareStatement(UPDATE_MODELLO);
			
			for (String a : attrMod){
				pstmn.setString(1, a);
				pstmn.setString(2, mod);
				
				pstmn.executeUpdate();
			}
		
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (! pstmn.isClosed()) {
				pstmn.close();
			}
		}
	}

	public void salvaModello(String mod, ObservableList<String> attrMod) throws ClassNotFoundException, SQLException {
		try {
			Connection conn = ControllerDB.getInstance().connect();
			pstmn = conn.prepareStatement(SALVA_MODELLO);
			
			for (String a : attrMod){
				pstmn.setString(1, mod);
				pstmn.setString(2, a);	
				
				pstmn.executeUpdate();
			}
		} catch (SQLException se){
			se.printStackTrace();
		} finally {
			if (! pstmn.isClosed()) {
				pstmn.close();
			}
		}
	}
}
	

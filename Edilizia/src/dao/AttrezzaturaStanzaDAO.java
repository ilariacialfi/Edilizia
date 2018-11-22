package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import control.ControllerDB;
import entity.Attrezzatura;
import entity.AttrezzaturaStanza;
import entity.Stanza;
import javafx.collections.ObservableList;

public class AttrezzaturaStanzaDAO {
	
	private static final String ESTRAI_INFO_STANZA = "SELECT * FROM attrezzatura_stanza WHERE stanza=?";
	private static final String STANZA_CON_ATTR = "SELECT nome, edificio, piano, tipo FROM attrezzatura_stanza JOIN stanza ON stanza = nome WHERE attrezzatura=? AND quantita>=? AND quantita<=?";
	private static final String ELIMINA_STANZA = "DELETE FROM attrezzatura_stanza WHERE stanza = ?";
	private static final String SALVA_STANZA = "INSERT INTO attrezzatura_stanza (stanza, attrezzatura, quantita) VALUES (?, ?, ?)";
	private static final String UPDATE_ATTR_Q = "UPDATE attrezzatura_stanza SET attrezzatura = ?, quantita = ? WHERE stanza = ?";
	private static final String UPDATE_Q = "UPDATE attrezzatura_stanza SET quantita = ? WHERE stanza = ? AND attrezzatura = ?";
	private static final String CERCA_ST_ATTR = "SELECT stanza, attrezzatura FROM attrezzatura_stanza WHERE stanza = ? AND attrezzatura = ?";
	
	private static AttrezzaturaStanzaDAO instance = null;
	private ResultSet rs = null;
	private PreparedStatement pstmn = null;
	private PreparedStatement pstmn2 = null;
	
	//SINGLETON
	private AttrezzaturaStanzaDAO(){
	}
	
	public static AttrezzaturaStanzaDAO getInstance(){
		if (instance == null){
			instance = new AttrezzaturaStanzaDAO();
		}
		return instance;
	}

	public ArrayList<AttrezzaturaStanza> getAttrSt(String st) throws SQLException, ClassNotFoundException {
		ArrayList<AttrezzaturaStanza> attrSt = null;
		
		try{
			Connection conn = ControllerDB.getInstance().connect();
			pstmn = conn.prepareStatement(ESTRAI_INFO_STANZA);
			pstmn.setString(1, st);
			rs = pstmn.executeQuery();
			
			attrSt = new ArrayList<AttrezzaturaStanza>();
			while(rs.next()){
				attrSt.add(new AttrezzaturaStanza(rs.getString("attrezzatura"), st, rs.getInt("quantita")));
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
		return attrSt;
	}
	
	//crea una ObservableList di stanze trovate in base alle quantità minime e massime selezionate
	public ArrayList<Stanza> getStanzaConAttr(ObservableList<Attrezzatura> listAttr) throws ClassNotFoundException, SQLException{
		ArrayList<Stanza> listStanze = null;
		
		try{
			
			Connection conn = ControllerDB.getInstance().connect();
			pstmn = conn.prepareStatement(STANZA_CON_ATTR);
			
			for (Attrezzatura attr : listAttr){
				pstmn.setString(1, attr.getNome());
				pstmn.setInt(2, attr.getMin());
				pstmn.setInt(3, attr.getMax());
				rs = pstmn.executeQuery();
				
				listStanze = new ArrayList<Stanza>();
				while(rs.next()){
					listStanze.add(new Stanza(rs.getString("nome"), rs.getString("edificio"), rs.getString("piano"), rs.getString("tipo")));
				}
			}
		} catch (SQLException se){
			se.printStackTrace();
		} finally {
			if (!rs.isClosed()){
				rs.close();
			}
			if (!pstmn.isClosed()){
				pstmn.close();
			}
		}
		
		return listStanze;
	}

	public synchronized void eliminaStanza(String stanzaSel) throws ClassNotFoundException, SQLException {

		try {
			Connection conn = ControllerDB.getInstance().connect();
			pstmn = conn.prepareStatement(ELIMINA_STANZA);
			pstmn.setString(1, stanzaSel);
			pstmn.executeUpdate();
			
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (! pstmn.isClosed()) {
				pstmn.close();
			}
		}
		
	}

	public synchronized void salvaStanza(String stanza, ObservableList<AttrezzaturaStanza> attrSt) throws SQLException, ClassNotFoundException {

		try {
			Connection conn = ControllerDB.getInstance().connect();
			pstmn = conn.prepareStatement(SALVA_STANZA);
			
			for (AttrezzaturaStanza attr : attrSt){
				pstmn.setString(1, stanza);
				pstmn.setString(2, attr.getAttr());
				pstmn.setInt(3, attr.getQuantita());	
				
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

	public void aggiornaStanza(String stanza, ObservableList<AttrezzaturaStanza> attrSt) throws SQLException, ClassNotFoundException {
		try {
			Connection conn = ControllerDB.getInstance().connect();
			pstmn = conn.prepareStatement(UPDATE_ATTR_Q);
			pstmn2 = conn.prepareStatement(UPDATE_Q);
			
			for (AttrezzaturaStanza attr : attrSt){
				String a = attr.getAttr();
				int q = attr.getQuantita();
				
				//Se la chiave primaria stanza, attrezzatura è già presente (TRUE) modifico solo la quantita
				if (controllaDuplicati(stanza, a) == true){
					pstmn2.setInt(1, q);
					pstmn2.setString(2, stanza);
					pstmn2.setString(3, a);
					
					pstmn2.executeUpdate();
				} else {
					//se non è già presente modifico attrezzatura e quantita
					pstmn.setString(1, attr.getAttr());
					pstmn.setInt(2, attr.getQuantita());	
					pstmn.setString(3, stanza);
					
					pstmn.executeUpdate();
				}
			}
			
		} catch (SQLException se){
			se.printStackTrace();
		} finally {
			if (! pstmn.isClosed()) {
				pstmn.close();
			}
			if (! pstmn2.isClosed()) {
				pstmn2.close();
			}
		}
	}

	private boolean controllaDuplicati(String stanza, String attr) throws SQLException, ClassNotFoundException {
		
		try {
			Connection conn = ControllerDB.getInstance().connect();
			pstmn = conn.prepareStatement(CERCA_ST_ATTR);
			pstmn.setString(1, stanza);
			pstmn.setString(2, attr);
			rs = pstmn.executeQuery();
			
			if (rs.next()){
				return true;
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
		return false;
	}

	
}

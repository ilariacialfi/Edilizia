package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import control.ControllerDB;
import entity.Stanza;

public class StanzaDAO {
	
	private static final String ESTRAI_STANZA = "SELECT nome FROM stanza";
	private static final String CERCA_STANZA = "SELECT nome FROM stanza WHERE nome = ?";
	private static final String STANZA_CON_NOME = "SELECT nome, edificio, piano, tipo FROM stanza WHERE nome = ?";
	private static final String ELIMINA_STANZA = "DELETE FROM stanza WHERE nome = ?";
	private static final String SALVA_STANZA = "INSERT INTO stanza (nome, edificio, piano, tipo) VALUES (?, ?, ?, ?) ";
	
	private static StanzaDAO instance = null;
	private ResultSet rs = null;
	private PreparedStatement pstmn = null;
	private Statement stmn = null;
	private Stanza stanza = null;
	
	private StanzaDAO(){
	}
	
	public static StanzaDAO getInstance(){
		if (instance == null){
			return instance = new StanzaDAO();
		}
		return instance;
	}
	
	
	public ArrayList<String> getStanza() throws SQLException, ClassNotFoundException {
		ArrayList<String> listSt = null;
		
		try {
			Connection conn = ControllerDB.getInstance().connect();
			stmn = conn.createStatement();
			rs = stmn.executeQuery(ESTRAI_STANZA);
			
			listSt = new ArrayList<>();
			while (rs.next()){
				listSt.add(new Stanza(rs.getString("nome"), null, null, null).getNome());
			}
			
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (! rs.isClosed()) {
				rs.close();
			}
			if (! stmn.isClosed()) {
				stmn.close();
			}
		}
		return listSt;
	}

	//Se esiste una stanza con il nome indicato la restituisce
	public Stanza cercaStanza(String nomeStanza) throws SQLException, ClassNotFoundException {
		try {
			Connection conn = ControllerDB.getInstance().connect();
			pstmn = conn.prepareStatement(CERCA_STANZA);
			pstmn.setString(1, nomeStanza);
			rs = pstmn.executeQuery();
			
			if (rs.next()){
				stanza = new Stanza(nomeStanza, null, null, null);
				return stanza;
			}
				return null;
			
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (! rs.isClosed()){
				rs.close();
			}
			if (! pstmn.isClosed()){
				pstmn.close();
			}
		}
		return stanza;
	}

	public Stanza getStanzaByName(String stanzaImp) throws ClassNotFoundException, SQLException {
		
		try {
			Connection conn = ControllerDB.getInstance().connect();
			pstmn = conn.prepareStatement(STANZA_CON_NOME);
			pstmn.setString(1, stanzaImp);
			rs = pstmn.executeQuery();
			
			if (rs.next()){
				stanza = new Stanza(stanzaImp, rs.getString("edificio"), rs.getString("piano"), rs.getString("tipo"));
				return stanza;
			}
				return null;
			
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (! rs.isClosed()){
				rs.close();
			}
			if (! pstmn.isClosed()){
				pstmn.close();
			}
		}
		return stanza;
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

	public synchronized void salvaStanza(String stanza, String edificio, String piano, String tipo) throws ClassNotFoundException, SQLException {
		try {
			Connection conn = ControllerDB.getInstance().connect();
			pstmn = conn.prepareStatement(SALVA_STANZA);
			pstmn.setString(1, stanza);
			pstmn.setString(2, edificio);
			pstmn.setString(3, piano);
			pstmn.setString(4, tipo);
			
			pstmn.executeUpdate();
			
		} catch (SQLException se){
			se.printStackTrace();
		} finally {
			if (! pstmn.isClosed()) {
				pstmn.close();
			}
		}
	}
	
}
			
			
			
			
			
				

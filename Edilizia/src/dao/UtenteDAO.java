package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import control.ControllerDB;
import entity.Utente;

public class UtenteDAO {

	// QUERY (perche final?)
	private static final String CERCA_UTENTE = "SELECT * FROM utente WHERE id=? and password=?";
	private static final String CERCA_RUOLO = "SELECT ruolo FROM utente WHERE id = ?";

	private static UtenteDAO instance = null;
	private ResultSet rs = null;
	private PreparedStatement pstmn = null;
	private Utente utente = null;

	// SINGLETON
	private UtenteDAO() {
	}

	// da definire syncronized il metodo a cui possono accedere le classi
	// esterne??
	public static UtenteDAO getInstance() {
		// se non esiste nessuna istanza la creo
		if (instance == null) {
			instance = new UtenteDAO();
		}
		return instance;
	}

	// Ricerca utente per id e password
	public Utente cercaUtente(String id, String pass) throws ClassNotFoundException, SQLException {

		try {
			Connection conn = ControllerDB.getInstance().connect();
			pstmn = conn.prepareStatement(CERCA_UTENTE);
			pstmn.setString(1, id);
			pstmn.setString(2, pass);
			rs = pstmn.executeQuery();

			if (rs.next()) {
				utente = new Utente(id, rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), pass,
						rs.getString("ruolo"));
				return utente;
			} else {
				return null;
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (!rs.isClosed()) {
				rs.close();
			}
			if (!pstmn.isClosed()) {
				pstmn.close();
			}
		}
		return utente;
	}

	public String getRuolo(String id) throws SQLException, ClassNotFoundException {
		String ruolo = null;
		try {
			Connection conn = ControllerDB.getInstance().connect();
			pstmn = conn.prepareStatement(CERCA_RUOLO);
			pstmn.setString(1, id);
			rs = pstmn.executeQuery();

			if (rs.next()) {
				ruolo = rs.getString("ruolo");
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (!rs.isClosed()) {
				rs.close();
			}
			if (!pstmn.isClosed()) {
				pstmn.close();
			}
		}

		return ruolo;
	}
}

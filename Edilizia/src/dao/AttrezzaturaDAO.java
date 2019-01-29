package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import control.ControllerDB;
import entity.Attrezzatura;

public class AttrezzaturaDAO {

	private static final String ESTRAI_ATTREZZATURA = "SELECT nome FROM attrezzatura ORDER BY nome";

	private static AttrezzaturaDAO instance = null;
	
	private Statement stmn = null;
	private ResultSet rs = null;

	protected AttrezzaturaDAO() {
	}

	public synchronized static final AttrezzaturaDAO getInstance() {
		if (AttrezzaturaDAO.instance == null) {
			AttrezzaturaDAO.instance = new AttrezzaturaDAO();
		}
		return instance;
	}

	// creo una observable list dal db
	public ArrayList<String> getAttrezzatura() throws SQLException, ClassNotFoundException {
		ArrayList<String> listAttr = null;

		try {
			Connection conn = ControllerDB.getInstance().connect();
			stmn = conn.createStatement();
			rs = stmn.executeQuery(ESTRAI_ATTREZZATURA);

			listAttr = new ArrayList<String>();
			while (rs.next()) {
				listAttr.add(new Attrezzatura(rs.getString("nome"), 0, 0).getNome());
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (!rs.isClosed()) {
				rs.close();
			}
			if (!stmn.isClosed()) {
				stmn.close();
			}
		}

		return listAttr;
	}

}

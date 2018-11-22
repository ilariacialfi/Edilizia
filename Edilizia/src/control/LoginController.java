package control;

import java.sql.SQLException;
import dao.UtenteDAO;

public class LoginController {

	public static boolean accedi(String id, String pass) throws ClassNotFoundException, SQLException {
		UtenteDAO utente = UtenteDAO.getInstance();
		//controlla se esiste l'utente che ha tentato l'accesso
		if (utente.cercaUtente(id, pass) != null){
			return true;
		}
			return false;
	}

	public static String ruoloUtente(String id) throws SQLException, ClassNotFoundException {
		return UtenteDAO.getInstance().getRuolo(id);
	}
}

package vista;


import ConexionMySQL.DatabaseConnectionException;

import java.sql.SQLException;

public class OnlineStore {


	public static void call() throws SQLException, ClassNotFoundException, DatabaseConnectionException {
		GestionOS gestion = new GestionOS();
		gestion.inicio();
		}
}

package it.ingsoft.persistence.db2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import it.ingsoft.model.fattura.FatturaDAO;
import it.ingsoft.model.struttura.StrutturaDAO;
import it.ingsoft.model.tempo.TempoDAO;
import it.ingsoft.model.turno.TurnoDAO;
import it.ingsoft.model.utente.UtenteDAO;
import it.ingsoft.persistence.FactoryDAO;

public class DB2FactoryDAO extends FactoryDAO {
	private static String DB2_DRIVER = "com.ibm.db2.jcc.DB2Driver";
	private static String DB2_URL = "jdbc:db2://diva.deis.unibo.it:50000/tw_stud";
	
	private static Connection CONNECTION;
	
	static
	{
		try {
			Class.forName(DB2_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection createConnection() throws SQLException {
		CONNECTION = DriverManager.getConnection(DB2_URL, null, null);
		return CONNECTION;
	}

	public static void closeConnection() throws SQLException {
		CONNECTION.close();
	}
	
	@Override
	public FatturaDAO getFatturaDAO() {
		return new DB2FatturaDAO();
	}

	@Override
	public StrutturaDAO getStrutturaDAO() {
		return new DB2StrutturaDAO();
	}

	@Override
	public TempoDAO getTempoDAO() {
		return new DB2TempoDAO();
	}
	
	@Override
	public TurnoDAO getTurnoDAO()
	{
		return new DB2TurnoDAO();
	}

	@Override
	public UtenteDAO getUtenteDAO() {
		return new DB2UtenteDAO();
	}
}

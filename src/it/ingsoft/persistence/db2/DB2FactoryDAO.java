package it.ingsoft.persistence.db2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	
	private static String DB_USERNAME = null;
	private static String DB_PASSWORD = null;
	
	static
	{
		try {
			Class.forName(DB2_DRIVER);
			
			BufferedReader fileReader = new BufferedReader(new FileReader("configDB"));
			
			DB_USERNAME = fileReader.readLine();
			DB_PASSWORD = fileReader.readLine();
			
			fileReader.close();
			
		} catch(FileNotFoundException e) { 
			System.err.println("File configDB mancante: il file deve contenere username e passowrd scritti su due righe per poter connettersi al DB");
			System.exit(-101);
			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection createConnection() throws SQLException {
		CONNECTION = DriverManager.getConnection(DB2_URL, DB_USERNAME, DB_PASSWORD);
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

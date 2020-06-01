package it.ingsoft.persistence.mysql;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import it.ingsoft.model.fattura.FatturaDAO;
import it.ingsoft.model.struttura.StrutturaDAO;
import it.ingsoft.model.tempo.TempoDAO;
import it.ingsoft.model.turno.TurnoDAO;
import it.ingsoft.model.utente.UtenteDAO;
import it.ingsoft.persistence.FactoryDAO;

public class MySQLFactoryDAO extends FactoryDAO {
	private static String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String MYSQL_URL = "jdbc:mysql://192.168.85.199:3306?serverTimezone=UTC";
	
	private static Connection CONNECTION;
	
	private static String DB_USERNAME = null;
	private static String DB_PASSWORD = null;
	
	static
	{
		try {
			Class.forName(MYSQL_DRIVER);
			
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
		CONNECTION = DriverManager.getConnection(MYSQL_URL, DB_USERNAME, DB_PASSWORD);
		Statement statement = CONNECTION.createStatement();
		statement.executeUpdate("USE " + DB_USERNAME);	//TODO possible sql injection
		return CONNECTION;
	}

	public static void closeConnection() throws SQLException {
		CONNECTION.close();
	}
	
	@Override
	public FatturaDAO getFatturaDAO() {
		return new MySQLFatturaDAO();
	}

	@Override
	public StrutturaDAO getStrutturaDAO() {
		return new MySQLStrutturaDAO();
	}

	@Override
	public TempoDAO getTempoDAO() {
		return new MySQLTempoDAO();
	}
	
	@Override
	public TurnoDAO getTurnoDAO()
	{
		return new MySQLTurnoDAO();
	}

	@Override
	public UtenteDAO getUtenteDAO() {
		return new MySQLUtenteDAO();
	}
}

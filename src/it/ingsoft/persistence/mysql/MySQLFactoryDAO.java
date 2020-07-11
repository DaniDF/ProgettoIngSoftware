package it.ingsoft.persistence.mysql;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import it.ingsoft.model.fattura.FatturaDAO;
import it.ingsoft.model.security.CredentialsDAO;
import it.ingsoft.model.struttura.StrutturaDAO;
import it.ingsoft.model.tempo.TempoDAO;
import it.ingsoft.model.turno.TurnoDAO;
import it.ingsoft.model.utente.UtenteDAO;
import it.ingsoft.persistence.ConfigDBParser;
import it.ingsoft.persistence.DBInstance;
import it.ingsoft.persistence.FactoryDAO;

public class MySQLFactoryDAO extends FactoryDAO {
	private static String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String MYSQL_URL = "jdbc:";
	
	private static Connection CONNECTION;
	
	private static String DB_USERNAME = null;
	private static String DB_PASSWORD = null;
	
	static
	{
		try {
			Class.forName(MYSQL_DRIVER);
			
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			ConfigDBParser dbHandler = new ConfigDBParser(DBInstance.MYSQL);
			
			parser.parse("web/db/configDB.xml", dbHandler);
			
			MYSQL_URL += dbHandler.getUrl();
			DB_USERNAME = dbHandler.getUsr();
			DB_PASSWORD = dbHandler.getPsw();
			
		} catch(FileNotFoundException e) { 
			System.err.println("File configDB mancante: il file deve contenere username e passowrd scritti su due righe per poter connettersi al DB");
			System.exit(-101);
			
		} catch (ClassNotFoundException | IOException | ParserConfigurationException | SAXException e) {
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
	
	@Override
	public CredentialsDAO getCredentialsDAO() {
		return new MySQLCredentialsDAO();
	}
}

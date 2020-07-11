package it.ingsoft.persistence.db2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import it.ingsoft.model.fattura.FatturaDAO;
import it.ingsoft.model.relations.FatturaTurnoMappingDAO;
import it.ingsoft.model.relations.StrutturaTurnoMappingDAO;
import it.ingsoft.model.relations.TurnoTempoMappingDAO;
import it.ingsoft.model.relations.UtenteCredentialsMappingDAO;
import it.ingsoft.model.relations.UtenteFatturaMappingDAO;
import it.ingsoft.model.relations.UtenteTempoMappingDAO;
import it.ingsoft.model.security.CredentialsDAO;
import it.ingsoft.model.struttura.StrutturaDAO;
import it.ingsoft.model.tempo.TempoDAO;
import it.ingsoft.model.turno.TurnoDAO;
import it.ingsoft.model.utente.UtenteDAO;
import it.ingsoft.persistence.ConfigDBParser;
import it.ingsoft.persistence.DBInstance;
import it.ingsoft.persistence.FactoryDAO;

public class DB2FactoryDAO extends FactoryDAO {
	private static String DB2_DRIVER = "com.ibm.db2.jcc.DB2Driver";
	private static String DB2_URL = "jdbc:";
	
	private static String DB_USERNAME = null;
	private static String DB_PASSWORD = null;
	
	static
	{
		try {
			Class.forName(DB2_DRIVER);
			
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			ConfigDBParser dbHandler = new ConfigDBParser();
			
			parser.parse("web/db/configDB.xml", dbHandler);
			
			DB2_URL += dbHandler.getDB(DBInstance.DB2).getUrl();
			DB_USERNAME = dbHandler.getDB(DBInstance.DB2).getUsername();
			DB_PASSWORD = dbHandler.getDB(DBInstance.DB2).getPassword();
			
		} catch(FileNotFoundException e) { 
			System.err.println("File configDB mancante: il file deve contenere username e passowrd scritti su due righe per poter connettersi al DB");
			System.exit(-101);
			
		} catch (ClassNotFoundException | IOException | ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection createConnection() throws SQLException {
		return DriverManager.getConnection(DB2_URL, DB_USERNAME, DB_PASSWORD);
	}

	public static void closeConnection(Connection connection) throws SQLException {
		connection.close();
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
	
	@Override
	public CredentialsDAO getCredentialsDAO()
	{
		return new DB2CredentialsDAO();
	}

	@Override
	public StrutturaTurnoMappingDAO getStrutturaTurnoMappingDAO() throws SQLException {
		return new DB2StrutturaTurnoMappingDAO();
	}

	@Override
	public TurnoTempoMappingDAO getTurnoTempoMappingDAO() throws SQLException {
		return new DB2TurnoTempoMappingDAO();
	}

	@Override
	public UtenteTempoMappingDAO getUtenteTempoMappingDAO() throws SQLException {
		return new DB2UtenteTempoMappingDAO();
	}

	@Override
	public FatturaTurnoMappingDAO getFatturaTurnoMappingDAO() throws SQLException {
		return new DB2FatturaTurnoMappingDAO();
	}

	@Override
	public UtenteFatturaMappingDAO getUtenteFatturaMappingDAO() throws SQLException {
		return new DB2UtenteFatturaMappingDAO();
	}

	@Override
	public UtenteCredentialsMappingDAO getUtenteCredentialsMappingDAO() throws SQLException {
		return new DB2UtenteCredentialsMappingDAO();
	}
}

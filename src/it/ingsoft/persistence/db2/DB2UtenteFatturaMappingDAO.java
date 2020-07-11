package it.ingsoft.persistence.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.ingsoft.model.fattura.Fattura;
import it.ingsoft.model.fattura.FatturaDAO;
import it.ingsoft.model.relations.UtenteFatturaMappingDAO;
import it.ingsoft.model.utente.Utente;
import it.ingsoft.model.utente.UtenteDAO;
import it.ingsoft.persistence.DBInstance;

public class DB2UtenteFatturaMappingDAO implements UtenteFatturaMappingDAO {

	@Override
	public void createTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		Statement statement = connection.createStatement();
		
		statement.execute("CREATE TABLE UTENTE_FATTURA_MAPPING ("
								+ "UTENTE VARCHAR(20) NOT NULL REFERENCES UTENTE(CODICEFISCALE), "
								+ "FATTURA INT NOT NULL REFERENCES FATTURA(IDFATTURA), "
								+ "CONSTRAINT PK PRIMARY KEY (UTENTE,FATTURA))");
		
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void dropTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		Statement statement = connection.createStatement();
		
		statement.execute("DROP TABLE UTENTE_FATTURA_MAPPING");
		
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void insert(Utente utente, Fattura fattura) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO UTENTE_FATTURA_MAPPING ("
																			+ "UTENTE, "
																			+ "FATTURA"
																			+ ") VALUES (?,?)");
		
		prepStatement.setString(1, utente.getCodiceFiscale());
		prepStatement.setInt(2, fattura.getIdFattura());
		
		prepStatement.execute();
		
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void delete(Utente utente, Fattura fattura) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM UTENTE_FATTURA_MAPPING WHERE "
																			+ "UTENTE = ? AND FATTURA = ?");
		
		prepStatement.setString(1, utente.getCodiceFiscale());
		prepStatement.setInt(2, fattura.getIdFattura());
		
		prepStatement.execute();
		
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public List<Fattura> getFatture(Utente utente) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM UTENTE_FATTURA_MAPPING WHERE UTENTE = ?");
		
		prepStatement.setString(1, utente.getCodiceFiscale());
		
		List<Fattura> result = new ArrayList<>();
		ResultSet resS = prepStatement.executeQuery();
		while(resS.next())
		{
			FatturaDAO fatturaDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getFatturaDAO();
			Fattura newFattura = fatturaDAO.get(resS.getInt("FATTURA"));
			
			if(newFattura != null) result.add(newFattura);
		}
		
		DB2FactoryDAO.closeConnection(connection);
		
		return result;
	}

	@Override
	public Utente getUtente(Fattura fattura) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM UTENTE_FATTURA_MAPPING WHERE FATTURA = ?");
		
		prepStatement.setInt(1, fattura.getIdFattura());
		
		ResultSet resS = prepStatement.executeQuery();
		if(!resS.next()) throw new SQLException("No result");
		
		UtenteDAO utenteDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getUtenteDAO();
		Utente result = utenteDAO.get(resS.getString("UTENTE"));
		
		DB2FactoryDAO.closeConnection(connection);
		
		return result;
	}
}

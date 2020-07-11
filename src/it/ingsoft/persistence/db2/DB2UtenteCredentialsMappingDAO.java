package it.ingsoft.persistence.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.ingsoft.model.relations.UtenteCredentialsMappingDAO;
import it.ingsoft.model.utente.Utente;
import it.ingsoft.persistence.db2.proxy.DB2UtenteProxy;

public class DB2UtenteCredentialsMappingDAO implements UtenteCredentialsMappingDAO {

	@Override
	public void createTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		Statement statement = connection.createStatement();
		
		statement.execute("CREATE TABLE UTENTE_CREDENTIALS_MAPPING ("
								+ "UTENTE VARCHAR(20) NOT NULL PRIMARY KEY REFERENCES UTENTE(CODICEFISCALE), "
								+ "USERNAME VARCHAR(255) NOT NULL REFERENCES CREDENTIALS(USERNAME) )");

		statement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void dropTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		Statement statement = connection.createStatement();
		
		statement.execute("DROP TABLE UTENTE_CREDENTIALS_MAPPING");

		statement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void insert(Utente utente, String username) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO UTENTE_CREDENTIALS_MAPPING ("
																			+ "UTENTE, "
																			+ "USERNAME"
																			+ ") VALUES (?,?)");
		
		prepStatement.setString(1, utente.getCodiceFiscale());
		prepStatement.setString(2, username);
		
		prepStatement.execute();
		
		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void delete(Utente utente) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM UTENTE_CREDENTIALS_MAPPING WHERE "
																			+ "UTENTE = ?");
		
		prepStatement.setString(1, utente.getCodiceFiscale());
		
		prepStatement.execute();

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public String getUsername(Utente utente) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM UTENTE_CREDENTIALS_MAPPING WHERE UTENTE = ?");
		
		prepStatement.setString(1, utente.getCodiceFiscale());
		
		String result = null;
		ResultSet resS = prepStatement.executeQuery();
		if(!resS.next()) throw new SQLException("No result");

		result = resS.getString("USERNAME");
		
		if(resS.next()) throw new SQLException("Not unique identifier: multiple response");

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
		
		return result;
	}

	@Override
	public Utente getUtente(String username) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM UTENTE_CREDENTIALS_MAPPING WHERE USERNAME = ?");
		
		prepStatement.setString(1, username);
		
		ResultSet resS = prepStatement.executeQuery();
		if(!resS.next()) throw new SQLException("No result");

		Utente result = new DB2UtenteProxy(resS.getString("USERNAME"));
		
		if(resS.next()) throw new SQLException("Not unique identifier: multiple response");

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
		
		return result;
	}

}

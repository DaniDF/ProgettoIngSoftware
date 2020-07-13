package it.ingsoft.persistence.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.ingsoft.model.relations.StrutturaCredentialsMappingDAO;
import it.ingsoft.model.struttura.Struttura;
import it.ingsoft.persistence.db2.proxy.DB2StrutturaProxy;

public class DB2StrutturaCredentialsMappingDAO implements StrutturaCredentialsMappingDAO {

	@Override
	public void createTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		Statement statement = connection.createStatement();
		
		statement.execute("CREATE TABLE STRUTTURA_CREDENTIALS_MAPPING ("
								+ "STRUTTURA VARCHAR(20) NOT NULL PRIMARY KEY REFERENCES STRUTTURA(PARTITAIVA), "
								+ "USERNAME VARCHAR(255) NOT NULL REFERENCES CREDENTIALS(USERNAME) )");

		statement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void dropTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		Statement statement = connection.createStatement();
		
		statement.execute("DROP TABLE STRUTTURA_CREDENTIALS_MAPPING");

		statement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void insert(Struttura struttura, String username) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO STRUTTURA_CREDENTIALS_MAPPING ("
																			+ "STRUTTURA, "
																			+ "USERNAME"
																			+ ") VALUES (?,?)");
		
		prepStatement.setString(1, struttura.getPartitaIva());
		prepStatement.setString(2, username);
		
		prepStatement.execute();
		
		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void delete(Struttura struttura) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM STRUTTURA_CREDENTIALS_MAPPING WHERE "
																			+ "STRUTTURA = ?");
		
		prepStatement.setString(1, struttura.getPartitaIva());
		
		prepStatement.execute();

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public String getUsername(Struttura struttura) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM STRUTTURA_CREDENTIALS_MAPPING "
																			+ "WHERE STRUTTURA = ?");
		
		prepStatement.setString(1, struttura.getPartitaIva());
		
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
	public Struttura getStruttura(String username) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM STRUTTURA_CREDENTIALS_MAPPING "
																			+ "WHERE STRUTTURA = ?");
		
		prepStatement.setString(1, username);
		
		ResultSet resS = prepStatement.executeQuery();
		if(!resS.next()) throw new SQLException("No result");

		Struttura result = new DB2StrutturaProxy(resS.getString("STRUTTURA"));
		
		if(resS.next()) throw new SQLException("Not unique identifier: multiple response");

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
		
		return result;
	}

}

package it.ingsoft.persistence.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import it.ingsoft.model.fattura.Fattura;
import it.ingsoft.model.fattura.FatturaDAO;
import it.ingsoft.model.turno.Turno;

public class DB2FatturaDAO implements FatturaDAO {

	@Override
	public void createTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		
		String query = "CREATE TABLE FATTURA (" +
					   "IDFATTURA INT NOT NULL PRIMARY KEY )";
		
		Statement statement = connection.createStatement();
		statement.execute(query);
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void dropTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		
		String query = "DROP TABLE FATTURA";
		
		Statement statement = connection.createStatement();
		statement.execute(query);
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void insert(Fattura fattura) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO FATTURA (IDFATTURA " +
																						   ") VALUES (?)");
		
		prepStatement.setInt(1, fattura.getIdFattura());
		
		prepStatement.executeUpdate();
		
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override @Deprecated
	public void update(Fattura fattura) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("UPDATE FATTURA SET " +
																					   "IDFATTURA = ? " +
																					   "WHERE IDFATTURA = ?");
		
		prepStatement.setInt(1, fattura.getIdFattura());
		
		prepStatement.executeUpdate();
		
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void delete(Fattura fattura) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM FATTURA WHERE IDFATTURA = ?");
		
		prepStatement.setInt(1, fattura.getIdFattura());
		
		prepStatement.executeUpdate();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public Fattura get(int idFattura) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM FATTURA WHERE IDFATTURA = ?");

		prepStatement.setInt(1, idFattura);
		
		ResultSet resS = prepStatement.executeQuery();
		if(!resS.next()) throw new SQLException("No result");
		
		Fattura result = new Fattura();
		result.setAcquisti(new ArrayList<Turno>());
		
		result.setIdFattura(resS.getInt("IDFATTURA"));
		
		if(resS.next()) throw new SQLException("Not unique identifier: multiple response");
		
		DB2FactoryDAO.closeConnection(connection);
		
		return result;
	}
}
